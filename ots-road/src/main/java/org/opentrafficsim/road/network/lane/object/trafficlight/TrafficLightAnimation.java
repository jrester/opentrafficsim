package org.opentrafficsim.road.network.lane.object.trafficlight;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.io.Serializable;
import java.rmi.RemoteException;

import javax.naming.NamingException;

import org.opentrafficsim.core.dsol.OTSSimulatorInterface;

import nl.tudelft.simulation.dsol.animation.D2.Renderable2D;

/**
 * Draw a traffic light on the road at th place where the cars are expected to stop.
 * <p>
 * Copyright (c) 2013-2016 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * <p>
 * @version $Revision: 1401 $, $LastChangedDate: 2015-09-14 01:33:02 +0200 (Mon, 14 Sep 2015) $, by $Author: averbraeck $,
 *          initial version 29 dec. 2014 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 */
public class TrafficLightAnimation extends Renderable2D implements Serializable
{
    /** */
    private static final long serialVersionUID = 20160000L;

    /** The half width left and right of the center line that is used to draw the block. */
    private final double halfWidth;

    /**
     * Construct the DefaultCarAnimation for a LaneBlock (road block).
     * @param source the CSEBlock to draw
     * @param simulator the simulator to schedule on
     * @throws NamingException in case of registration failure of the animation
     * @throws RemoteException on communication failure
     */
    public TrafficLightAnimation(final TrafficLight source, final OTSSimulatorInterface simulator)
            throws NamingException, RemoteException
    {
        super(source, simulator);
        this.halfWidth = 0.45 * source.getLane().getWidth(source.getLongitudinalPosition()).getSI();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void paint(final Graphics2D graphics, final ImageObserver observer) throws RemoteException
    {
        TrafficLight trafficLight = (TrafficLight) this.getSource();
        Color fillColor;
        switch (trafficLight.getTrafficLightColor())
        {
            case RED:
                fillColor = Color.red;
                break;

            case YELLOW:
                fillColor = Color.yellow;
                break;

            case GREEN:
                fillColor = Color.green;
                break;

            default:
                fillColor = Color.black;
                break;
        }
        
        // PaintPolygons.paintMultiPolygon(graphics, fillColor, trafficLight.getLocation(), trafficLight.getGeometry(), false);
        graphics.setColor(fillColor);
        Rectangle2D rectangle = new Rectangle2D.Double(-0.25, -this.halfWidth, 0.5, 2 * this.halfWidth);
        graphics.fill(rectangle);
    }

    /** {@inheritDoc} */
    @Override
    public final String toString()
    {
        return "TrafficLightAnimation [getSource()=" + this.getSource() + "]";
    }

}
