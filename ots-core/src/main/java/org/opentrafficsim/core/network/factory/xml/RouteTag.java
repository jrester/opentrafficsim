package org.opentrafficsim.core.network.factory.xml;

import java.util.ArrayList;
import java.util.List;

import org.opentrafficsim.core.network.NetworkException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * <p>
 * Copyright (c) 2013-2014 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * <p>
 * $LastChangedDate: 2015-07-24 02:58:59 +0200 (Fri, 24 Jul 2015) $, @version $Revision: 1147 $, by $Author: averbraeck $,
 * initial version Jul 23, 2015 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 */
class RouteTag
{
    /** name. */
    @SuppressWarnings("checkstyle:visibilitymodifier")
    String name = null;

    /** Nodes. */
    @SuppressWarnings("checkstyle:visibilitymodifier")
    List<NodeTag> routeNodeTags = new ArrayList<NodeTag>();

    /**
     * Parse the ROUTE tag.
     * @param nodeList nodeList the top-level nodes of the XML-file
     * @param parser the parser with the lists of information
     * @throws SAXException when parsing of the tag fails
     * @throws NetworkException when parsing of the tag fails
     */
    @SuppressWarnings("checkstyle:needbraces")
    static void parseRoutes(final NodeList nodeList, final XmlNetworkLaneParser parser) throws SAXException,
        NetworkException
    {
        for (Node node : XMLParser.getNodes(nodeList, "ROUTE"))
        {
            NamedNodeMap attributes = node.getAttributes();
            RouteTag routeTag = new RouteTag();

            if (attributes.getNamedItem("NAME") == null)
                throw new SAXException("ROUTE: missing attribute NAME");
            routeTag.name = attributes.getNamedItem("NAME").getNodeValue().trim();
            if (parser.routeTags.keySet().contains(routeTag.name))
                throw new SAXException("ROUTE: NAME " + routeTag.name + " defined twice");

            if (attributes.getNamedItem("NODELIST") == null)
                throw new SAXException("ROUTE " + routeTag.name + ": missing attribute NODELIST");
            String routeNodes = attributes.getNamedItem("NODELIST").getNodeValue().trim();
            routeTag.routeNodeTags = NodeTag.parseNodeList(routeNodes, parser);

            parser.routeTags.put(routeTag.name, routeTag);
        }
    }
}
