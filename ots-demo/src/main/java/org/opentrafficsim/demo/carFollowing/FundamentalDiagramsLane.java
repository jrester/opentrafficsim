package org.opentrafficsim.demo.carFollowing;

import static org.opentrafficsim.core.gtu.GTUType.CAR;

import java.awt.Frame;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.naming.NamingException;
import javax.swing.SwingUtilities;

import org.djunits.unit.TimeUnit;
import org.djunits.unit.UNITS;
import org.djunits.value.vdouble.scalar.Acceleration;
import org.djunits.value.vdouble.scalar.Duration;
import org.djunits.value.vdouble.scalar.Length;
import org.djunits.value.vdouble.scalar.Speed;
import org.djunits.value.vdouble.scalar.Time;
import org.opentrafficsim.base.modelproperties.ProbabilityDistributionProperty;
import org.opentrafficsim.base.modelproperties.Property;
import org.opentrafficsim.base.modelproperties.PropertyException;
import org.opentrafficsim.base.modelproperties.SelectionProperty;
import org.opentrafficsim.base.parameters.Parameters;
import org.opentrafficsim.core.compatibility.Compatible;
import org.opentrafficsim.core.dsol.OTSModelInterface;
import org.opentrafficsim.core.geometry.OTSGeometryException;
import org.opentrafficsim.core.geometry.OTSPoint3D;
import org.opentrafficsim.core.gtu.GTUDirectionality;
import org.opentrafficsim.core.gtu.GTUException;
import org.opentrafficsim.core.gtu.GTUType;
import org.opentrafficsim.core.network.NetworkException;
import org.opentrafficsim.core.network.OTSNetwork;
import org.opentrafficsim.core.network.OTSNode;
import org.opentrafficsim.graphs.FundamentalDiagramLane;
import org.opentrafficsim.road.animation.AnimationToggles;
import org.opentrafficsim.road.gtu.animation.DefaultCarAnimation;
import org.opentrafficsim.road.gtu.lane.LaneBasedIndividualGTU;
import org.opentrafficsim.road.gtu.lane.tactical.LaneBasedGTUFollowingTacticalPlanner;
import org.opentrafficsim.road.gtu.lane.tactical.following.GTUFollowingModelOld;
import org.opentrafficsim.road.gtu.lane.tactical.following.IDMOld;
import org.opentrafficsim.road.gtu.lane.tactical.following.IDMPlusOld;
import org.opentrafficsim.road.gtu.strategical.LaneBasedStrategicalPlanner;
import org.opentrafficsim.road.gtu.strategical.route.LaneBasedStrategicalRoutePlanner;
import org.opentrafficsim.road.network.factory.LaneFactory;
import org.opentrafficsim.road.network.lane.CrossSectionLink;
import org.opentrafficsim.road.network.lane.DirectedLanePosition;
import org.opentrafficsim.road.network.lane.Lane;
import org.opentrafficsim.road.network.lane.LaneType;
import org.opentrafficsim.road.network.lane.changing.OvertakingConditions;
import org.opentrafficsim.road.network.lane.object.sensor.SinkSensor;
import org.opentrafficsim.simulationengine.AbstractWrappableAnimation;
import org.opentrafficsim.simulationengine.OTSSimulationException;
import org.opentrafficsim.simulationengine.OTSSimulatorInterface;

import nl.tudelft.simulation.dsol.SimRuntimeException;
import nl.tudelft.simulation.dsol.gui.swing.TablePanel;
import nl.tudelft.simulation.dsol.simtime.SimTimeDoubleUnit;
import nl.tudelft.simulation.dsol.simulators.DEVSSimulatorInterface;
import nl.tudelft.simulation.dsol.simulators.SimulatorInterface;

/**
 * Demonstrate the FundamentalDiagram plot.
 * <p>
 * Copyright (c) 2013-2018 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * <p>
 * $LastChangedDate: 2015-09-14 01:33:02 +0200 (Mon, 14 Sep 2015) $, @version $Revision: 1401 $, by $Author: averbraeck $,
 * initial version 17 dec. 2014 <br>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 */
public class FundamentalDiagramsLane extends AbstractWrappableAnimation implements UNITS
{
    /** */
    private static final long serialVersionUID = 1L;

    /** The model. */
    private FundamentalDiagramLanePlotsModel model;

    /** Create a FundamentalDiagrams simulation. */
    public FundamentalDiagramsLane()
    {
        try
        {
            this.properties.add(new SelectionProperty("CarFollowingModel", "Car following model",
                    "<html>The car following model determines "
                            + "the acceleration that a vehicle will make taking into account nearby vehicles, "
                            + "infrastructural restrictions (e.g. speed limit, curvature of the road) "
                            + "capabilities of the vehicle and personality of the driver.</html>",
                    new String[] { "IDM", "IDM+" }, 1, false, 500));
            this.properties.add(new ProbabilityDistributionProperty("TrafficComposition", "Traffic composition",
                    "<html>Mix of passenger cars and trucks</html>", new String[] { "passenger car", "truck" },
                    new Double[] { 0.8, 0.2 }, false, 10));
        }
        catch (PropertyException exception)
        {
            exception.printStackTrace();
        }
    }

    /** {@inheritDoc} */
    @Override
    public final void stopTimersThreads()
    {
        super.stopTimersThreads();
        this.model = null;
    }

    /**
     * Main program.
     * @param args String[]; the command line arguments (not used)
     * @throws SimRuntimeException on ???
     */
    public static void main(final String[] args) throws SimRuntimeException
    {
        // Create the simulation and wrap its panel in a JFrame. It does not get much easier/shorter than this...
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    FundamentalDiagramsLane fundamentalDiagramsLane = new FundamentalDiagramsLane();
                    fundamentalDiagramsLane.buildAnimator(Time.ZERO, Duration.ZERO, new Duration(3600.0, SECOND),
                            fundamentalDiagramsLane.getProperties(), null, true);
                }
                catch (SimRuntimeException | NamingException | OTSSimulationException | PropertyException exception)
                {
                    exception.printStackTrace();
                }
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    protected final OTSModelInterface makeModel()
    {
        this.model = new FundamentalDiagramLanePlotsModel(this.savedUserModifiedProperties);
        return this.model;
    }

    /** {@inheritDoc} */
    @Override
    protected final void addAnimationToggles()
    {
        AnimationToggles.setTextAnimationTogglesStandard(this);
    }

    /** {@inheritDoc} */
    @Override
    protected final void addTabs(final OTSSimulatorInterface simulator) throws OTSSimulationException
    {
        final int panelsPerRow = 3;
        TablePanel charts = new TablePanel(3, panelsPerRow);
        for (int plotNumber = 0; plotNumber < 9; plotNumber++)
        {
            FundamentalDiagramLane fd;
            try
            {
                Lane lane = this.model.getLane(plotNumber);
                int xs = (int) lane.getParentLink().getStartNode().getPoint().x;
                int xe = (int) lane.getParentLink().getEndNode().getPoint().x;
                fd = new FundamentalDiagramLane("Fundamental Diagram for [" + xs + ", " + xe + "] m", new Duration(1.0, SECOND),
                        lane, Compatible.EVERYTHING, (DEVSSimulatorInterface.TimeDoubleUnit) this.model.getSimulator());
                fd.setTitle("Fundamental Diagram Graph");
                fd.setExtendedState(Frame.MAXIMIZED_BOTH);
                this.model.getFundamentalDiagrams().add(fd);
                charts.setCell(fd.getContentPane(), plotNumber / panelsPerRow, plotNumber % panelsPerRow);
            }
            catch (NetworkException | SimRuntimeException exception)
            {
                exception.printStackTrace();
            }
        }
        addTab(getTabCount(), "statistics", charts);
    }

    /** {@inheritDoc} */
    @Override
    public final String shortName()
    {
        return "Fundamental Diagrams";
    }

    /** {@inheritDoc} */
    @Override
    public final String description()
    {
        return "<html><h1>Fundamental Diagram Plots</H1>"
                + "Simulation of a single lane road of 5 km length. Vechicles are generated at a constant rate of "
                + "1500 veh/hour. At time 300s a blockade is inserted at position 4km; this blockade is removed at time "
                + "500s. This blockade simulates a bridge opening.<br>"
                + "The blockade causes a traffic jam that slowly dissolves after the blockade is removed.<br>"
                + "Output is a set of Diagrams that plot observed density, flow and speed plots against each other.</html>";
    }

    /**
     * Simulate a single lane road of 5 km length. Vehicles are generated at a constant rate of 1500 veh/hour. At time 300s a
     * blockade is inserted at position 4 km; this blockade is removed at time 500s. The used car following algorithm is IDM+
     * <a href="http://opentrafficsim.org/downloads/MOTUS%20reference.pdf"><i>Integrated Lane Change Model with Relaxation and
     * Synchronization</i>, by Wouter J. Schakel, Victor L. Knoop and Bart van Arem, 2012</a>. <br>
     * Output is a set of FundamentalDiagram plots for various point along the lane.
     * <p>
     * Copyright (c) 2013-2018 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved.
     * <br>
     * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
     * <p>
     * $LastChangedDate: 2015-09-14 01:33:02 +0200 (Mon, 14 Sep 2015) $, @version $Revision: 1401 $, by $Author: averbraeck $,
     * initial version ug 1, 2014 <br>
     * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
     */
    class FundamentalDiagramLanePlotsModel implements OTSModelInterface, UNITS
    {
        /** */
        private static final long serialVersionUID = 20140820L;

        /** The network. */
        private OTSNetwork network = new OTSNetwork("network");

        /** The simulator. */
        private OTSSimulatorInterface simulator;

        /** The headway (inter-vehicle time). */
        private Duration headway;

        /** Number of cars created. */
        private int carsCreated = 0;

        /** Type of all GTUs. */
        private GTUType gtuType = CAR;

        /** The car following model, e.g. IDM Plus for cars. */
        private GTUFollowingModelOld carFollowingModelCars;

        /** The car following model, e.g. IDM Plus for trucks. */
        private GTUFollowingModelOld carFollowingModelTrucks;

        /** The probability that the next generated GTU is a passenger car. */
        private double carProbability;

        /** The blocking car. */
        private LaneBasedIndividualGTU block = null;

        /** Starting x-position. */
        private Length startX = new Length(0, METER);

        /** Length per lane. */
        private Length laneLength = new Length(500, METER);

        /** The Lanes containing the simulated Cars. */
        private List<Lane> lanes = new ArrayList<>();

        /** The speed limit. */
        private Speed speedLimit = new Speed(100, KM_PER_HOUR);

        /** The fundamental diagram plots. */
        private List<FundamentalDiagramLane> fundamentalDiagramsLane = new ArrayList<>();

        /** User settable properties. */
        private List<Property<?>> fundamentalDiagramsLaneProperties = null;

        /** The random number generator used to decide what kind of GTU to generate. */
        private Random randomGenerator = new Random(12345);

        /**
         * @param properties List&lt;Property&lt;?&gt;&gt;; the properties
         */
        FundamentalDiagramLanePlotsModel(final List<Property<?>> properties)
        {
            this.fundamentalDiagramsLaneProperties = properties;
        }

        /** {@inheritDoc} */
        @Override
        public final void constructModel(final SimulatorInterface<Time, Duration, SimTimeDoubleUnit> theSimulator)
                throws SimRuntimeException
        {
            this.simulator = (OTSSimulatorInterface) theSimulator;
            try
            {
                LaneType laneType = LaneType.TWO_WAY_LANE;
                OTSNode node = new OTSNode(this.network, "Node 0", new OTSPoint3D(this.startX.getSI(), 0, 0));
                for (int laneNr = 0; laneNr < 10; laneNr++)
                {
                    OTSNode next = new OTSNode(this.network, "Node " + (laneNr + 1),
                            new OTSPoint3D(node.getPoint().x + this.laneLength.si, 0, 0));
                    Lane lane = LaneFactory.makeLane(this.network, "Lane", node, next, null, laneType, this.speedLimit,
                            this.simulator);
                    this.lanes.add(lane);
                    node = next;
                }
                // create SinkLane
                OTSNode end = new OTSNode(this.network, "End", new OTSPoint3D(node.getPoint().x + 50.0, 0, 0));
                CrossSectionLink endLink = LaneFactory.makeLink(this.network, "endLink", node, end, null, this.simulator);
                int last = this.lanes.size() - 1;
                Lane sinkLane = new Lane(endLink, "sinkLane", this.lanes.get(last).getLateralCenterPosition(1.0),
                        this.lanes.get(last).getLateralCenterPosition(1.0), this.lanes.get(last).getWidth(1.0),
                        this.lanes.get(last).getWidth(1.0), laneType, this.speedLimit, new OvertakingConditions.None());
                new SinkSensor(sinkLane, new Length(10.0, METER), this.simulator);
            }
            catch (NamingException | NetworkException | OTSGeometryException exception)
            {
                exception.printStackTrace();
            }

            for (Property<?> p : this.fundamentalDiagramsLaneProperties)
            {
                if (p instanceof SelectionProperty)
                {
                    SelectionProperty sp = (SelectionProperty) p;
                    if ("CarGollowingModel".equals(sp.getKey()))
                    {
                        String modelName = sp.getValue();
                        if (modelName.equals("IDM"))
                        {
                            this.carFollowingModelCars = new IDMOld(new Acceleration(1, METER_PER_SECOND_2),
                                    new Acceleration(1.5, METER_PER_SECOND_2), new Length(2, METER), new Duration(1, SECOND),
                                    1d);
                            this.carFollowingModelTrucks = new IDMOld(new Acceleration(0.5, METER_PER_SECOND_2),
                                    new Acceleration(1.5, METER_PER_SECOND_2), new Length(2, METER), new Duration(1, SECOND),
                                    1d);
                        }
                        else if (modelName.equals("IDM+"))
                        {
                            this.carFollowingModelCars = new IDMPlusOld(new Acceleration(1, METER_PER_SECOND_2),
                                    new Acceleration(1.5, METER_PER_SECOND_2), new Length(2, METER), new Duration(1, SECOND),
                                    1d);
                            this.carFollowingModelTrucks = new IDMPlusOld(new Acceleration(0.5, METER_PER_SECOND_2),
                                    new Acceleration(1.5, METER_PER_SECOND_2), new Length(2, METER), new Duration(1, SECOND),
                                    1d);
                        }
                        else
                        {
                            throw new Error("Car following model " + modelName + " not implemented");
                        }
                    }
                    else
                    {
                        throw new Error("Unhandled SelectionProperty " + p.getKey());
                    }
                }
                else if (p instanceof ProbabilityDistributionProperty)
                {
                    ProbabilityDistributionProperty pdp = (ProbabilityDistributionProperty) p;
                    String modelName = p.getKey();
                    if (modelName.equals("TrafficComposition"))
                    {
                        this.carProbability = pdp.getValue()[0];
                    }
                    else
                    {
                        throw new Error("Unhandled ProbabilityDistributionProperty " + p.getKey());
                    }
                }
                else
                {
                    throw new Error("Unhandled property: " + p);
                }
            }

            // 1500 [veh / hour] == 2.4s headway
            this.headway = new Duration(3600.0 / 1500.0, SECOND);

            try
            {
                // Schedule creation of the first car (this will re-schedule itself one headway later, etc.).
                this.simulator.scheduleEventAbs(Time.ZERO, this, this, "generateCar", null);
                // Create a block at t = 5 minutes
                this.simulator.scheduleEventAbs(new Time(1000, TimeUnit.BASE_SECOND), this, this, "createBlock", null);
                // Remove the block at t = 7 minutes
                this.simulator.scheduleEventAbs(new Time(1200, TimeUnit.BASE_SECOND), this, this, "removeBlock", null);
                // Schedule regular updates of the graph
                for (int t = 1; t <= this.simulator.getReplication().getTreatment().getRunLength().si / 25; t++)
                {
                    this.simulator.scheduleEventAbs(new Time(25 * t - 0.001, TimeUnit.BASE_SECOND), this, this, "drawGraphs",
                            null);
                }
            }
            catch (SimRuntimeException exception)
            {
                exception.printStackTrace();
            }
        }

        /**
         * Set up the block in the last lane of the list.
         * @throws RemoteException on communications failure
         */
        protected final void createBlock() throws RemoteException
        {
            Length initialPosition = new Length(200, METER);
            Set<DirectedLanePosition> initialPositions = new LinkedHashSet<>(1);
            try
            {
                initialPositions.add(new DirectedLanePosition(this.lanes.get(this.lanes.size() - 1), initialPosition,
                        GTUDirectionality.DIR_PLUS));
                Parameters parameters = DefaultsFactory.getDefaultParameters();
                this.block = new LaneBasedIndividualGTU("999999", this.gtuType, new Length(4, METER), new Length(1.8, METER),
                        Speed.ZERO, Length.createSI(2.0), this.simulator, this.network);
                LaneBasedStrategicalPlanner strategicalPlanner = new LaneBasedStrategicalRoutePlanner(
                        new LaneBasedGTUFollowingTacticalPlanner(this.carFollowingModelCars, this.block), this.block);
                this.block.setParameters(parameters);
                this.block.initWithAnimation(strategicalPlanner, initialPositions, Speed.ZERO, DefaultCarAnimation.class,
                        FundamentalDiagramsLane.this.getColorer());
            }
            catch (SimRuntimeException | NamingException | NetworkException | GTUException | OTSGeometryException exception)
            {
                exception.printStackTrace();
            }
        }

        /**
         * Remove the block.
         */
        protected final void removeBlock()
        {
            this.block.destroy();
            this.block = null;
        }

        /**
         * Generate cars at a fixed rate (implemented by re-scheduling this method).
         */
        protected final void generateCar()
        {
            boolean generateTruck = this.randomGenerator.nextDouble() > this.carProbability;
            Length initialPosition = new Length(0, METER);
            Speed initialSpeed = new Speed(100, KM_PER_HOUR);
            Set<DirectedLanePosition> initialPositions = new LinkedHashSet<>(1);
            try
            {
                initialPositions.add(new DirectedLanePosition(this.lanes.get(0), initialPosition, GTUDirectionality.DIR_PLUS));
                Length vehicleLength = new Length(generateTruck ? 15 : 4, METER);
                GTUFollowingModelOld gtuFollowingModel =
                        generateTruck ? this.carFollowingModelTrucks : this.carFollowingModelCars;
                if (null == gtuFollowingModel)
                {
                    throw new Error("gtuFollowingModel is null");
                }
                Parameters parameters = DefaultsFactory.getDefaultParameters();

                LaneBasedIndividualGTU gtu = new LaneBasedIndividualGTU("" + (++this.carsCreated), this.gtuType, vehicleLength,
                        new Length(1.8, METER), new Speed(200, KM_PER_HOUR), vehicleLength.multiplyBy(0.5), this.simulator,
                        this.network);
                LaneBasedStrategicalPlanner strategicalPlanner = new LaneBasedStrategicalRoutePlanner(
                        new LaneBasedGTUFollowingTacticalPlanner(gtuFollowingModel, gtu), gtu);
                gtu.setParameters(parameters);
                gtu.initWithAnimation(strategicalPlanner, initialPositions, initialSpeed, DefaultCarAnimation.class,
                        FundamentalDiagramsLane.this.getColorer());
                this.simulator.scheduleEventRel(this.headway, this, this, "generateCar", null);
            }
            catch (SimRuntimeException | NamingException | NetworkException | GTUException | OTSGeometryException exception)
            {
                exception.printStackTrace();
            }
        }

        /**
        * 
        */
        protected final void drawGraphs()
        {
            // Notify the Fundamental Diagram plots that the underlying data has changed
            for (FundamentalDiagramLane fd : this.fundamentalDiagramsLane)
            {
                fd.reGraph();
            }
        }

        /** {@inheritDoc} */
        @Override
        public final SimulatorInterface<Time, Duration, SimTimeDoubleUnit> getSimulator()
        {
            return this.simulator;
        }

        /** {@inheritDoc} */
        @Override
        public OTSNetwork getNetwork()
        {
            return this.network;
        }

        /**
         * @return fundamentalDiagramPlots
         */
        public final List<FundamentalDiagramLane> getFundamentalDiagrams()
        {
            return this.fundamentalDiagramsLane;
        }

        /**
         * @param laneNr int; the lane in the list.
         * @return lane.
         */
        public Lane getLane(final int laneNr)
        {
            return this.lanes.get(laneNr);
        }
    }
}
