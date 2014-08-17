package org.opentrafficsim.core.value;

import java.io.Serializable;

import org.opentrafficsim.core.unit.Unit;

/**
 * AbstractValue is a class to help construct Matrix, Complex, and Vector but it does not extend java.lang.Number.
 * The Scalar class <i>does</i> extend Number, and implements the same interfaces from Value. 
 * <p>
 * Copyright (c) 2014 Delft University of Technology, Jaffalaan 5, 2628 BX Delft, the Netherlands. All rights reserved.
 * <p>
 * See for project information <a href="http://www.opentrafficsim.org/"> www.opentrafficsim.org</a>.
 * <p>
 * The OpenTrafficSim project is distributed under the following BSD-style license:<br>
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 * <ul>
 * <li>Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.</li>
 * <li>Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution.</li>
 * <li>Neither the name of Delft University of Technology, nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.</li>
 * </ul>
 * This software is provided by the copyright holders and contributors "as is" and any express or implied warranties,
 * including, but not limited to, the implied warranties of merchantability and fitness for a particular purpose are
 * disclaimed. In no event shall the copyright holder or contributors be liable for any direct, indirect, incidental,
 * special, exemplary, or consequential damages (including, but not limited to, procurement of substitute goods or
 * services; loss of use, data, or profits; or business interruption) however caused and on any theory of liability,
 * whether in contract, strict liability, or tort (including negligence or otherwise) arising in any way out of the use
 * of this software, even if advised of the possibility of such damage.
 * @version Jun 13, 2014 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 * @param <U> the unit of the values in the constructor and for display
 */
public abstract class AbstractValue<U extends Unit<U>> implements Value<U>, Serializable, MathFunctions
{
    /** */
    private static final long serialVersionUID = 20140615L;

    /** the unit of the value. */
    protected U unit;

    /**
     * @param unit the unit of the value
     */
    public AbstractValue(final U unit)
    {
        this.unit = unit;
    }

    /**
     * @see org.opentrafficsim.core.value.Value#getUnit()
     */
    public U getUnit()
    {
        return this.unit;
    }

    /**
     * @see org.opentrafficsim.core.value.Value#expressAsSIUnit(double)
     */
    public double expressAsSIUnit(final double value)
    {
        return ValueUtil.expressAsSIUnit(value, this.unit);
    }

    /**
     * @param value the value to convert in the specified unit for this scalar
     * @return the value in the unit as specified for this scalar
     */
    protected double expressAsSpecifiedUnit(final double value)
    {
        return ValueUtil.expressAsUnit(value, this.unit);
    }

    /**
     * @see org.opentrafficsim.core.value.Value#setDisplayUnit(org.opentrafficsim.core.unit.Unit)
     */
    public void setDisplayUnit(final U newUnit)
    {
        this.unit = newUnit;
    }

    /**
     * @see org.opentrafficsim.core.value.Value#isAbsolute()
     */
    public boolean isAbsolute()
    {
        return this instanceof Absolute;
    }

    /**
     * @see org.opentrafficsim.core.value.Value#isRelative()
     */
    public boolean isRelative()
    {
        return this instanceof Relative;
    }

}