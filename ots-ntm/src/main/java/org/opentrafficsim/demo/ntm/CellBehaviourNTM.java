package org.opentrafficsim.demo.ntm;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import org.opentrafficsim.core.unit.FrequencyUnit;
import org.opentrafficsim.core.unit.LengthUnit;
import org.opentrafficsim.core.unit.LinearDensityUnit;
import org.opentrafficsim.core.unit.SpeedUnit;
import org.opentrafficsim.core.unit.TimeUnit;
import org.opentrafficsim.core.value.vdouble.scalar.DoubleScalar;
import org.opentrafficsim.core.value.vdouble.scalar.DoubleScalar.Abs;
import org.opentrafficsim.core.value.vdouble.scalar.DoubleScalar.Rel;
import org.opentrafficsim.demo.ntm.fundamentaldiagrams.FundamentalDiagram;

/**
 * <p>
 * Copyright (c) 2013-2014 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights
 * reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
 * <p>
 * @version 26 Sep 2014 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://Hansvanlint.weblog.tudelft.nl">Hans van Lint</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 * @author <a href="http://www.citg.tudelft.nl">Guus Tamminga</a>
 * @author <a href="http://www.citg.tudelft.nl">Yufei Yuan</a>
 */
public class CellBehaviourNTM extends CellBehaviour
{
    /** */
    private static final long serialVersionUID = 20140903L;

    /** currentSpeed: average current speed of Cars in this CELL. */
    private DoubleScalar.Abs<SpeedUnit> freeSpeed;
    
    /** currentSpeed: average current speed of Cars in this CELL. */
    private DoubleScalar.Abs<SpeedUnit> currentSpeed;

    /** currentSpeed: average current speed of Cars in this CELL. */
    private DoubleScalar.Abs<SpeedUnit> currentTravelTime;

    /** */
    private Abs<FrequencyUnit> maxCapacity;

    /** */
    private HashMap<BoundedNode, Abs<FrequencyUnit>> borderCapacity;

    /** */
    private HashMap<BoundedNode, Abs<FrequencyUnit>> borderDemand;

    /**
     * parametersNTM are: - id ID - accCritical1 low param - accCritical2 high param - accJam jam param - freeSpeed -
     * uncongested speed - roadLength length of all roads.
     */
    private ParametersNTM parametersNTM;

    /** */
    private Area area;

    /**
     * @param parametersNTM contains a set of params
     * @param area that contains this behaviour
     */
    public CellBehaviourNTM(final Area area, final ParametersNTM parametersNTM)
    {
        this.parametersNTM = parametersNTM;
        // double maxCap = parametersNTM.getAccCritical().get(0) *
        // parametersNTM.getFreeSpeed().getInUnit(SpeedUnit.KM_PER_HOUR)
        // * parametersNTM.getRoadLength().getInUnit(LengthUnit.KILOMETER);
        double maxCap =
                parametersNTM.getCapacity().getInUnit(FrequencyUnit.PER_HOUR)
                        * parametersNTM.getRoadLength().getInUnit(LengthUnit.KILOMETER);
        this.maxCapacity = new Abs<FrequencyUnit>(maxCap, FrequencyUnit.PER_HOUR);
        // gedeeld door gemiddelde triplengte in een gebied
        // (lengte zone?)

    }

    /**
     * @param accumulatedCars
     * @return actualSpeed.
     */
    public DoubleScalar.Abs<SpeedUnit> retrieveCurrentSpeed(final double accumulatedCars)
    {
        Abs<FrequencyUnit> actualCapacity = retrieveSupply(accumulatedCars, this.maxCapacity, this.getParametersNTM());
        double densityDouble = this.getAccumulatedCars() / this.area.getRoadLength().getInUnit(LengthUnit.KILOMETER);
        Abs<LinearDensityUnit> density =
                new DoubleScalar.Abs<LinearDensityUnit>(densityDouble, LinearDensityUnit.PER_KILOMETER);
        double speedDouble =
                actualCapacity.getInUnit(FrequencyUnit.PER_HOUR) / density.getInUnit(LinearDensityUnit.PER_KILOMETER);
        return this.setCurrentSpeed(new DoubleScalar.Abs<SpeedUnit>(speedDouble, SpeedUnit.KM_PER_HOUR));
    }

    /**
     * @param accumulatedCars
     * @return actualSpeed.
     */
    public DoubleScalar.Abs<TimeUnit> retrieveCurrentTravelTime()
    {

        double timeDouble =  this.area.getRoadLength().getInUnit(LengthUnit.KILOMETER) /
                retrieveCurrentSpeed(this.getAccumulatedCars()).getInUnit(SpeedUnit.KM_PER_HOUR);
        return this.setCurrentTravelTime(new DoubleScalar.Abs(timeDouble, TimeUnit.HOUR));
    }

    /**
     * {@inheritDoc}
     * @param accumulatedCars
     * @param maximumCapacity
     * @param param
     * @return
     */
    // @Override
    public Abs<FrequencyUnit> retrieveSupply(final Double accumulatedCars, final Abs<FrequencyUnit> maximumCapacity,
            final ParametersNTM param)
    {
        Abs<FrequencyUnit> carProduction = maximumCapacity;
        if (accumulatedCars > param.getAccCritical().get(1))
        {
            carProduction = retrieveDemand(accumulatedCars, maximumCapacity, param);
        }
        else
        {
            carProduction = maximumCapacity;
        }
        return carProduction;
    }

    /**
     * Retrieves car production from network fundamental diagram.
     * @param accumulatedCars number of cars in Cell
     * @param maximumCapacity based on area information
     * @param param
     * @return carProduction
     */
    public final Abs<FrequencyUnit> retrieveDemand(final double accumulatedCars,
            final Abs<FrequencyUnit> maximumCapacity, final ParametersNTM param)
    {
        ArrayList<Point2D> xyPairs = new ArrayList<Point2D>();
        Point2D p = new Point2D.Double();
        p.setLocation(0, 0);
        xyPairs.add(p);
        p = new Point2D.Double();
        p.setLocation(param.getAccCritical().get(0), maximumCapacity.getInUnit());
        xyPairs.add(p);
        p = new Point2D.Double();
        p.setLocation(param.getAccCritical().get(1), maximumCapacity.getInUnit());
        xyPairs.add(p);
        p = new Point2D.Double();
        p.setLocation(param.getAccCritical().get(2), 0);
        xyPairs.add(p);
        Abs<FrequencyUnit> carProduction = FundamentalDiagram.PieceWiseLinear(xyPairs, accumulatedCars);
        return carProduction;
    }

    /**
     * @return parametersNTM.
     */
    public final ParametersNTM getParametersNTM()
    {
        return this.parametersNTM;
    }

    /**
     * @param parametersNTM set parametersNTM.
     */
    public void setParametersNTM(ParametersNTM parametersNTM)
    {
        this.parametersNTM = parametersNTM;
    }

    /**
     * @return area.
     */
    public final Area getArea()
    {
        return this.area;
    }

    /**
     * @param area set area.
     */
    public void setArea(final Area area)
    {
        this.area = area;
    }

    /**
     * @return maxCapacity
     */
    public final Abs<FrequencyUnit> getMaxCapacity()
    {
        return this.maxCapacity;
    }

    /**
     * @param maxCapacity set maxCapacity.
     */
    public final void setMaxCapacity(final Abs<FrequencyUnit> maxCapacity)
    {
        this.maxCapacity = maxCapacity;
    }

    /**
     * @return averageSpeed
     */
    public final DoubleScalar.Abs<SpeedUnit> getCurrentSpeed()
    {
        return this.currentSpeed;
    }

    /**
     * @param currentSpeed set currentSpeed.
     * @return 
     */
    public Abs<SpeedUnit> setCurrentSpeed(DoubleScalar.Abs<SpeedUnit> currentSpeed)
    {
        return this.currentSpeed = currentSpeed;
    }
    /**
     * @return borderCapacity.
     */
    public HashMap<BoundedNode, Abs<FrequencyUnit>> getBorderCapacity()
    {
        return this.borderCapacity;
    }

    /**
     * @param borderCapacity set borderCapacity.
     */
    public void setBorderCapacity(HashMap<BoundedNode, Abs<FrequencyUnit>> borderCapacity)
    {
        this.borderCapacity = borderCapacity;
    }

    /**
     * @return borderDemand.
     */
    public HashMap<BoundedNode, Abs<FrequencyUnit>> getBorderDemand()
    {
        return this.borderDemand;
    }

    /**
     * @param borderDemand set borderDemand.
     */
    public void setBorderDemand(HashMap<BoundedNode, Abs<FrequencyUnit>> borderDemand)
    {
        this.borderDemand = borderDemand;
    }

    /**
     * @param demand
     * @param linkData set linkData.
     */
    public void addBorderDemand(BoundedNode node, Abs<FrequencyUnit> demand)
    {
        double cap = demand.getInUnit(FrequencyUnit.PER_HOUR);
        Rel<FrequencyUnit> addCap = new Rel<FrequencyUnit>(cap, FrequencyUnit.PER_HOUR);
        if (this.getBorderDemand().get(node) == null)
        {
            Abs<FrequencyUnit> zeroCap = new Abs<FrequencyUnit>(0.0, FrequencyUnit.PER_HOUR);
            this.getBorderDemand().put(node, zeroCap);
        }
        Abs<FrequencyUnit> total = DoubleScalar.plus(this.getBorderDemand().get(node), addCap).immutable();
        HashMap<BoundedNode, Abs<FrequencyUnit>> newDemand = new HashMap<BoundedNode, Abs<FrequencyUnit>>();
        newDemand.put(node, total);
        this.setBorderDemand(newDemand);
    }

    /**
     * @return currentTravelTime.
     */
    public DoubleScalar.Abs<SpeedUnit> getCurrentTravelTime()
    {
        return currentTravelTime;
    }

    /**
     * @param currentTravelTime set currentTravelTime.
     * @return 
     */
    public Abs<TimeUnit> setCurrentTravelTime(DoubleScalar.Abs<SpeedUnit> currentTravelTime)
    {
        this.currentTravelTime = currentTravelTime;
        return null;
    }

    /**
     * @return freeSpeed.
     */
    public DoubleScalar.Abs<SpeedUnit> getFreeSpeed()
    {
        this.freeSpeed = this.area.getAverageSpeed();
        return this.freeSpeed;
    }

    /**
     * @param freeSpeed set freeSpeed.
     */
    public void setFreeSpeed(DoubleScalar.Abs<SpeedUnit> freeSpeed)
    {
        this.freeSpeed = freeSpeed;
    }

}
