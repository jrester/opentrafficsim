package org.sim0mq.publisher;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.djunits.Throw;
import org.djutils.event.EventProducerInterface;
import org.djutils.metadata.MetaData;
import org.djutils.metadata.ObjectDescriptor;
import org.djutils.serialization.SerializationException;
import org.djutils.serialization.SerializationRuntimeException;
import org.opentrafficsim.core.gtu.GTU;
import org.opentrafficsim.core.network.Link;
import org.opentrafficsim.core.network.Network;
import org.opentrafficsim.core.network.OTSNetwork;
import org.opentrafficsim.road.network.lane.CrossSectionLink;
import org.sim0mq.Sim0MQException;
import org.sim0mq.message.Sim0MQMessage;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

/**
 * Publish all available transceivers for an OTS network to a Sim0MQ master and handle its requests. <br>
 * Example sequence of events: <br>
 * <ol>
 * <li>OTSNetwork is somehow constructed and then a Publisher for that network is constructed.</li>
 * <li>Sim0MQ master requests names of all available subscription handlers</li>
 * <li>Sim0MQ master decides that it wants all GTU MOVE events of all GTUs. To do that it needs to know about all GTUs when they
 * are created and about all GTUs that have already been created. The Sim0MQ master issues to the publisher a request to
 * subscribe to all NETWORK.GTU_ADD_EVENTs of the GTUs_in_network SubscriptionHandler</li>
 * <li>This Publisher requests the GTUs_in_network SubscriptionHandler to subscribe to the add events. From now on, the
 * GTUs_in_network SubscriptionHandler will receive these events generated by the OTSNetwork and transcribe those into a Sim0MQ
 * events which are transmitted to the Sim0MQ master.</li>
 * <li>Sim0MQ master requests publisher to list all the elements of the GTUs_in_network SubscriptionHandler</li>
 * <li>This Publisher calls the list method of the GTUs_in_network SubscriptionHandler which results in a list of all active
 * GTUs being sent to the Sim0MQ master</li>
 * <li>The Sim0MQ master requests this Publisher to create a subscription for the update events of the GTU_move
 * SubscriptionHandler, providing the GTU id as address. It does that once for every GTU id.</li>
 * <li>This Publishers creates the subscriptions. From now on any GTU.MOVE_EVENT event is transcribed by the GTU_move
 * SubscriptionHandler in to a corresponding Sim0MQ event and sent to the Sim0MQ master.</li>
 * </ol>
 * <p>
 * Copyright (c) 2020-2020 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * <p>
 * $LastChangedDate: 2020-02-13 11:08:16 +0100 (Thu, 13 Feb 2020) $, @version $Revision: 6383 $, by $Author: pknoppers $,
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 */
public class Publisher extends AbstractTransceiver
{
    /** Map Publisher names to the corresponding Publisher object. */
    private final Map<String, SubscriptionHandler> subscriptionHandlerMap = new LinkedHashMap<>();

    /** The OTS network. */
    private final OTSNetwork network;

    /**
     * Construct a Publisher for an OTS network.
     * @param network OTSNetwork; the OTS network
     * @throws RemoteException ...
     */
    public Publisher(final OTSNetwork network) throws RemoteException
    {
        super("Publisher for " + Throw.whenNull(network, "Network may not be null").getId(),
                new MetaData("Publisher for " + network.getId(), "Publisher",
                        new ObjectDescriptor[] {
                                new ObjectDescriptor("Name of subscription handler", "String", String.class) }),
                new MetaData("Subscription handlers", "Subscription handlers", new ObjectDescriptor[] {
                        new ObjectDescriptor("Name of subscription handler", "String", String.class) }));
        this.network = network;

        GTUIdTransceiver gtuIdTransceiver = new GTUIdTransceiver(network);
        GTUTransceiver gtuTransceiver = new GTUTransceiver(network, gtuIdTransceiver);
        SubscriptionHandler gtuSubscriptionHandler =
                new SubscriptionHandler("GTU move", gtuTransceiver, new LookupEventProducerInterface()
                {
                    @Override
                    public EventProducerInterface lookup(final Object[] address, final ReturnWrapper returnWrapper)
                            throws Sim0MQException, SerializationException
                    {
                        String bad = AbstractTransceiver.verifyMetaData(getAddressMetaData(), address);
                        if (bad != null)
                        {
                            returnWrapper.encodeReplyAndTransmit("Bad address: " + bad);
                            return null;
                        }
                        EventProducerInterface result = network.getGTU((String) address[0]);
                        if (null == result)
                        {
                            returnWrapper.nack("No GTU with id \"" + address[0] + "\" found");
                        }
                        return result;
                    }

                    private final MetaData metaData = new MetaData("GTU Id", "GTU Id",
                            new ObjectDescriptor[] { new ObjectDescriptor("GTU ID", "GTU Id", String.class) });

                    @Override
                    public MetaData getAddressMetaData()
                    {
                        return this.metaData;
                    }
                }, null, null, GTU.MOVE_EVENT, null);
        addSubscriptionHandler(gtuSubscriptionHandler);
        addSubscriptionHandler(new SubscriptionHandler("GTUs in network", gtuIdTransceiver, new LookupEventProducerInterface()
        {
            @Override
            public EventProducerInterface lookup(final Object[] address, final ReturnWrapper returnWrapper)
                    throws Sim0MQException, SerializationException
            {
                String bad = AbstractTransceiver.verifyMetaData(getAddressMetaData(), address);
                if (bad != null)
                {
                    returnWrapper.encodeReplyAndTransmit("Bad address: " + bad);
                    return null;
                }
                return network;
            }

            @Override
            public String toString()
            {
                return "Subscription handler for GTUs in network";
            }

            @Override
            public MetaData getAddressMetaData()
            {
                return MetaData.EMPTY;
            }
        }, Network.GTU_ADD_EVENT, Network.GTU_REMOVE_EVENT, null, gtuSubscriptionHandler));
        LinkIdTransceiver linkIdTransceiver = new LinkIdTransceiver(network);
        LinkTransceiver linkTransceiver = new LinkTransceiver(network, linkIdTransceiver);
        SubscriptionHandler linkSubscriptionHandler = new SubscriptionHandler("Link change", linkTransceiver, lookupLink,
                Link.GTU_ADD_EVENT, Link.GTU_REMOVE_EVENT, null, null);
        addSubscriptionHandler(linkSubscriptionHandler);
        addSubscriptionHandler(new SubscriptionHandler("Links in network", linkIdTransceiver, new LookupEventProducerInterface()
        {
            @Override
            public EventProducerInterface lookup(final Object[] address, final ReturnWrapper returnWrapper)
                    throws Sim0MQException, SerializationException
            {
                String bad = AbstractTransceiver.verifyMetaData(getAddressMetaData(), address);
                if (bad != null)
                {
                    returnWrapper.encodeReplyAndTransmit("Bad address: " + bad);
                    return null;
                }
                return network;
            }

            @Override
            public String toString()
            {
                return "Subscription handler for Links in network";
            }

            @Override
            public MetaData getAddressMetaData()
            {
                return MetaData.EMPTY;
            }
        }, Network.LINK_ADD_EVENT, Network.LINK_REMOVE_EVENT, null, linkSubscriptionHandler));
        NodeIdTransceiver nodeIdTransceiver = new NodeIdTransceiver(network);
        NodeTransceiver nodeTransceiver = new NodeTransceiver(network, nodeIdTransceiver);
        // addTransceiver(nodeIdTransceiver);
        // addTransceiver(new NodeTransceiver(network, nodeIdTransceiver));
        SubscriptionHandler nodeSubscriptionHandler =
                new SubscriptionHandler("Node change", nodeTransceiver, new LookupEventProducerInterface()
                {
                    @Override
                    public EventProducerInterface lookup(final Object[] address, final ReturnWrapper returnWrapper)
                    {
                        return null; // Nodes do not emit events
                    }

                    @Override
                    public String toString()
                    {
                        return "Subscription handler for Node change";
                    }

                    private final MetaData metaData = new MetaData("Node Id", "Node Id",
                            new ObjectDescriptor[] { new ObjectDescriptor("Node ID", "Node Id", String.class) });

                    @Override
                    public MetaData getAddressMetaData()
                    {
                        return this.metaData;
                    }
                }, null, null, null, null);
        addSubscriptionHandler(nodeSubscriptionHandler);
        addSubscriptionHandler(new SubscriptionHandler("Nodes in network", nodeIdTransceiver, new LookupEventProducerInterface()
        {
            @Override
            public EventProducerInterface lookup(final Object[] address, final ReturnWrapper returnWrapper)
                    throws Sim0MQException, SerializationException
            {
                String bad = AbstractTransceiver.verifyMetaData(getAddressMetaData(), address);
                if (bad != null)
                {
                    returnWrapper.encodeReplyAndTransmit("Bad address: " + bad);
                    return null;
                }
                return network;
            }

            @Override
            public String toString()
            {
                return "Subscription handler for Nodes in network";
            }

            @Override
            public MetaData getAddressMetaData()
            {
                return MetaData.EMPTY;
            }
        }, Network.NODE_ADD_EVENT, Network.NODE_REMOVE_EVENT, null, nodeSubscriptionHandler));
        SubscriptionHandler linkGTUIdSubscriptionHandler = new SubscriptionHandler("GTUs on Link",
                new LinkGTUIdTransceiver(network), lookupLink, Link.GTU_ADD_EVENT, Link.GTU_REMOVE_EVENT, null, null);
        addSubscriptionHandler(linkGTUIdSubscriptionHandler);
        addSubscriptionHandler(new SubscriptionHandler("Cross section elements on Link",
                new CrossSectionElementTransceiver(network), lookupLink, CrossSectionLink.LANE_ADD_EVENT,
                CrossSectionLink.LANE_REMOVE_EVENT, null, linkGTUIdSubscriptionHandler));
        // addTransceiver(new LaneGTUIdTransceiver(network));
        SimulatorStateTransceiver stt = new SimulatorStateTransceiver(network.getSimulator());
        SubscriptionHandler simulatorStateSubscriptionHandler = new SubscriptionHandler("Simulator running", stt,
                stt.getLookupEventProducerInterface(), null, null, SimulatorStateTransceiver.SIMULATOR_STATE_CHANGED, null);
        addSubscriptionHandler(simulatorStateSubscriptionHandler);

        addSubscriptionHandler(new SubscriptionHandler("", this, null, null, null, null, null)); // The meta transceiver
    }

    /** Lookup a CrossSectionLink in the network. */
    private LookupEventProducerInterface lookupLink = new LookupEventProducerInterface()
    {
        @Override
        public EventProducerInterface lookup(final Object[] address, final ReturnWrapper returnWrapper)
                throws IndexOutOfBoundsException, Sim0MQException, SerializationException
        {
            Throw.whenNull(address, "LookupLink requires the name of a link");
            Throw.when(address.length != 1 || !(address[1] instanceof String), IllegalArgumentException.class, "Bad address");
            Link link = network.getLink((String) address[0]);
            if (null == link)
            {
                returnWrapper.encodeReplyAndTransmit("Network does not contain a Link with id " + address[0]);
                return null;
            }
            if (!(link instanceof EventProducerInterface))
            {
                returnWrapper.encodeReplyAndTransmit(
                        new Object[] { "Link \"" + address[0] + "\" is not able to handle subscriptions" });
                return null;
            }
            return (CrossSectionLink) link;
        }

        @Override
        public String toString()
        {
            return "LookupProducerInterface that looks up a Link in the network";
        }

        @Override
        public MetaData getAddressMetaData()
        {
            return new MetaData("Link id", "Name of a link in the network",
                    new ObjectDescriptor[] { new ObjectDescriptor("Link id", "Name of a link in the network", String.class) });
        }
    };

    /**
     * Add a SubscriptionHandler to the map.
     * @param subscriptionHandler SubscriptionHandler; the subscription handler to add to the map
     */
    private void addSubscriptionHandler(final SubscriptionHandler subscriptionHandler)
    {
        this.subscriptionHandlerMap.put(subscriptionHandler.getId(), subscriptionHandler);
    }

    /** {@inheritDoc} */
    @Override
    public Object[] get(final Object[] address, final ReturnWrapper returnWrapper)
            throws Sim0MQException, SerializationException
    {
        Throw.whenNull(returnWrapper, "returnWrapper may not be null");
        String bad = verifyMetaData(getAddressFields(), address);
        if (bad != null)
        {
            returnWrapper.encodeReplyAndTransmit("Bad address (should be the name of a transceiver): " + bad);
            return null;
        }
        SubscriptionHandler subscriptionHandler = this.subscriptionHandlerMap.get(address[0]);
        if (null == subscriptionHandler)
        {
            returnWrapper.encodeReplyAndTransmit("No transceiver with name \"" + address[0] + "\"");
            return null;
        }
        return new Object[] { subscriptionHandler };
    }

    /** Returned by the getIdSource method. */
    private final TransceiverInterface idSource = new TransceiverInterface()
    {
        @Override
        public String getId()
        {
            return "Transceiver for names of available transceivers in Publisher";
        }

        @Override
        public MetaData getAddressFields()
        {
            return MetaData.EMPTY;
        }

        /** Result of getResultFields. */
        private MetaData resultMetaData =
                new MetaData("Transceiver names available in Publisher", "String array", new ObjectDescriptor[] {
                        new ObjectDescriptor("Transceiver names available in Publisher", "String array", String[].class) });

        @Override
        public MetaData getResultFields()
        {
            return resultMetaData;
        }

        @Override
        public Object[] get(final Object[] address, final ReturnWrapper returnWrapper)
                throws RemoteException, Sim0MQException, SerializationException
        {
            Object[] result = new Object[subscriptionHandlerMap.size()];
            int index = 0;
            for (String key : subscriptionHandlerMap.keySet())
            {
                result[index++] = key;
            }
            return result;
        }
    };

    /** {@inheritDoc} */
    @Override
    public TransceiverInterface getIdSource(final int addressLevel, final ReturnWrapper returnWrapper)
            throws Sim0MQException, SerializationException
    {
        if (0 != addressLevel)
        {
            returnWrapper.encodeReplyAndTransmit("Address should be 0");
            return null;
        }
        return this.idSource;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasIdSource()
    {
        return true;
    }

    /**
     * Execute one command.
     * @param subscriptionHandlerName String; name of the SubscriptionHandler for which the command is destined
     * @param command SubscriptionHandler.Command; the operation to perform
     * @param address Object[]; the address on which to perform the operation
     * @param returnWrapper ReturnWrapper; to transmit the result
     * @throws RemoteException on RMI network failure
     * @throws SerializationException on illegal type in serialization
     * @throws Sim0MQException on communication error
     */
    public void executeCommand(final String subscriptionHandlerName, final SubscriptionHandler.Command command,
            final Object[] address, final ReturnWrapper returnWrapper)
            throws RemoteException, Sim0MQException, SerializationException
    {
        SubscriptionHandler subscriptionHandler = this.subscriptionHandlerMap.get(subscriptionHandlerName);
        if (null == subscriptionHandler)
        {
            returnWrapper.nack("No subscription handler for \"" + subscriptionHandlerName + "\"");
            return;
        }
        subscriptionHandler.executeCommand(command, address, returnWrapper);
    }

    /**
     * Execute one command.
     * @param subscriptionHandlerName String; name of the SubscriptionHandler for which the command is destined
     * @param commandString String; the operation to perform
     * @param address Object[]; the address on which to perform the operation
     * @param returnWrapper ReturnWrapper; to transmit the result
     * @throws RemoteException on RMI network failure
     * @throws SerializationException on illegal type in serialization
     * @throws Sim0MQException on communication error
     */
    public void executeCommand(final String subscriptionHandlerName, final String commandString, final Object[] address,
            final ReturnWrapperImpl returnWrapper) throws RemoteException, Sim0MQException, SerializationException
    {
        executeCommand(subscriptionHandlerName,
                Throw.whenNull(SubscriptionHandler.lookupCommand(commandString), "Invalid command (%s)", commandString),
                address, returnWrapper);
    }

}

/**
 * Container for all data needed to reply (once, or multiple times) to a Sim0MQ request.
 */
class ReturnWrapperImpl implements ReturnWrapper
{
    /** The ZContext needed to create the return socket(s). */
    private final ZContext zContext;

    /** Federation id. */
    private final Object federationId;

    /** Sender id (to be used as return address). */
    private final Object returnAddress;

    /** Our id (to be used as sender address in replies). */
    private final Object ourAddress;

    /** Message type id used by the sender; re-used in the reply. */
    private final Object messageTypeId;

    /** Message id used by the sender; re-used in the reply; post-incremented by one if it is an Integer. */
    private final Object messageId;

    /** Number of replies sent. SHOULD NOT BE USED IN equals or hashCode! */
    private int replyCount = 0;

    /**
     * Construct a new ReturnWrapper.
     * @param zContext ZContext; the ZContext needed to create sockets for returned messages
     * @param receivedMessage byte[]; the received message from which the reply envelope will be derived
     * @param socketMap Map&lt;Long, ZMQ.Socket&gt;; cache of created sockets for returned messages
     * @param packetsSent AtomicInteger; counter for returned messages
     * @throws SerializationException when the received message has an incorrect envelope
     * @throws Sim0MQException when the received message cannot be decoded
     */
    ReturnWrapperImpl(final ZContext zContext, final byte[] receivedMessage, final Map<Long, Socket> socketMap,
            final AtomicInteger packetsSent) throws Sim0MQException, SerializationException
    {
        this(zContext, Sim0MQMessage.decode(receivedMessage).createObjectArray(), socketMap);
    }

    /**
     * Construct a new ReturnWrapper.
     * @param zContext ZContext; the ZContext needed to create sockets for returned messages
     * @param decodedReceivedMessage Object[]; decoded Sim0MQ message
     * @param socketMap Map&lt;Long, ZMQ.Socket&gt;; cache of created sockets for returned messages
     */
    ReturnWrapperImpl(final ZContext zContext, final Object[] decodedReceivedMessage, final Map<Long, Socket> socketMap)
    {
        Throw.whenNull(zContext, "zContext may not be null");
        Throw.whenNull(socketMap, "socket map may not be null");
        this.zContext = zContext;
        this.socketMap = socketMap;
        Throw.when(decodedReceivedMessage.length < 8, SerializationRuntimeException.class,
                "Received message is too short (minumum number of elements is 8; got %d", decodedReceivedMessage.length);
        this.federationId = decodedReceivedMessage[2];
        this.returnAddress = decodedReceivedMessage[3];
        this.ourAddress = decodedReceivedMessage[4];
        this.messageTypeId = decodedReceivedMessage[5];
        this.messageId = decodedReceivedMessage[6];
    }

    /** In memory sockets to talk to the multiplexer. */
    private final Map<Long, ZMQ.Socket> socketMap;

    /**
     * Central portal to send a message to the master.
     * @param data byte[]; the data to send
     */
    public synchronized void sendToMaster(final byte[] data)
    {
        Long threadId = Thread.currentThread().getId();
        ZMQ.Socket socket = this.socketMap.get(threadId);
        while (null == socket)
        {
            // System.out.println("socket map is " + this.socketMap);
            System.out.println("Creating new internal socket for thread " + threadId + " (map currently contains "
                    + this.socketMap.size() + " entries)");
            socket = this.zContext.createSocket(SocketType.PUSH);
            socket.setHWM(100000);
            socket.connect("inproc://simulationEvents");
            this.socketMap.put(threadId, socket);
            // System.out.println("Socket created; map now contains " + this.socketMap.size() + " entries");
        }
        // System.out.println("pre send");
        socket.send(data, 0);
        // System.out.println("post send");
    }

    /** {@inheritDoc} */
    @Override
    public void encodeReplyAndTransmit(final Boolean ackNack, final Object[] payload)
            throws Sim0MQException, SerializationException
    {
        Throw.whenNull(payload, "payload may not be null (but it can be an emty Object array)");
        Object fixedMessageTypeId = this.messageTypeId;
        Object[] fixedPayload = payload;
        if (null != ackNack)
        {
            fixedPayload = new Object[payload.length + 1];
            fixedPayload[0] = ackNack;
            for (int index = 0; index < payload.length; index++)
            {
                fixedPayload[index + 1] = payload[index];
            }
        }
        byte[] result = Sim0MQMessage.encodeUTF8(true, this.federationId, this.ourAddress, this.returnAddress,
                fixedMessageTypeId, this.messageId, fixedPayload);
        sendToMaster(result);
        // System.out.println(SerialDataDumper.serialDataDumper(EndianUtil.BIG_ENDIAN, result));
    }

    /** {@inheritDoc} */
    @Override
    public String toString()
    {
        return "ReturnWrapper [federationId=" + federationId + ", returnAddress=" + returnAddress + ", ourAddress=" + ourAddress
                + ", messageTypeId=" + messageTypeId + ", messageId=" + messageId + ", replyCount=" + replyCount + "]";
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((federationId == null) ? 0 : federationId.hashCode());
        result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
        result = prime * result + ((messageTypeId == null) ? 0 : messageTypeId.hashCode());
        result = prime * result + ((ourAddress == null) ? 0 : ourAddress.hashCode());
        result = prime * result + ((returnAddress == null) ? 0 : returnAddress.hashCode());
        return result; // replyCount is NOT used!
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("checkstyle:needbraces")
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReturnWrapperImpl other = (ReturnWrapperImpl) obj;
        if (federationId == null)
        {
            if (other.federationId != null)
                return false;
        }
        else if (!federationId.equals(other.federationId))
            return false;
        if (messageId == null)
        {
            if (other.messageId != null)
                return false;
        }
        else if (!messageId.equals(other.messageId))
            return false;
        if (messageTypeId == null)
        {
            if (other.messageTypeId != null)
                return false;
        }
        else if (!messageTypeId.equals(other.messageTypeId))
            return false;
        if (ourAddress == null)
        {
            if (other.ourAddress != null)
                return false;
        }
        else if (!ourAddress.equals(other.ourAddress))
            return false;
        if (returnAddress == null)
        {
            if (other.returnAddress != null)
                return false;
        }
        else if (!returnAddress.equals(other.returnAddress))
            return false;
        return true; // replyCount is NOT used
    }

}
