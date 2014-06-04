package org.opentrafficsim.core.unit;

/**
 * AreaUnit defines a number of common units for areas.
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
 * @version May 15, 2014 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @param <D> the distance unit type
 */
public class AreaUnit<D extends LengthUnit> extends Unit<AreaUnit<D>>
{
    /** */
    private static final long serialVersionUID = 20140603L;

    /** the unit of distance for the area unit, e.g., meter */
    private final D distanceUnit;

    /** m^2 */
    public static final AreaUnit<LengthUnit> SQUARE_METER = new AreaUnit<LengthUnit>(LengthUnit.METER,
            "AreaUnit.square_meter", "AreaUnit.m^2");

    /** km^2 */
    public static final AreaUnit<LengthUnit> SQUARE_KM = new AreaUnit<LengthUnit>(LengthUnit.KILOMETER,
            "AreaUnit.square_kilometer", "AreaUnit.km^2");

    /** are */
    public static final AreaUnit<LengthUnit> ARE = new AreaUnit<LengthUnit>("AreaUnit.are", "AreaUnit.a", SQUARE_METER,
            100);

    /** hectare */
    public static final AreaUnit<LengthUnit> HECTARE = new AreaUnit<LengthUnit>("AreaUnit.hectare", "AreaUnit.ha", ARE,
            100);

    /** mile^2 */
    public static final AreaUnit<LengthUnit> SQUARE_MILE = new AreaUnit<LengthUnit>(LengthUnit.MILE,
            "AreaUnit.square_mile", "AreaUnit.mi^2");

    /** ft^2 */
    public static final AreaUnit<LengthUnit> SQUARE_FOOT = new AreaUnit<LengthUnit>(LengthUnit.FOOT,
            "AreaUnit.square_foot", "AreaUnit.ft^2");

    /** in^2 */
    public static final AreaUnit<LengthUnit> SQUARE_INCH = new AreaUnit<LengthUnit>(LengthUnit.INCH,
            "AreaUnit.square_inch", "AreaUnit.in^2");

    /** yd^2 */
    public static final AreaUnit<LengthUnit> SQUARE_YARD = new AreaUnit<LengthUnit>(LengthUnit.YARD,
            "AreaUnit.square_yard", "AreaUnit.yd^2");

    /**
     * @param distanceUnit the unit of distance for the speed unit, e.g., meter
     * @param nameKey the key to the locale file for the long name of the unit
     * @param abbreviationKey the key to the locale file for the abbreviation of the unit
     */
    public AreaUnit(final D distanceUnit, final String nameKey, final String abbreviationKey)
    {
        super(nameKey, abbreviationKey, distanceUnit.getConversionFactorToStandardUnit()
                * distanceUnit.getConversionFactorToStandardUnit());
        this.distanceUnit = distanceUnit;
    }

    /**
     * This constructor constructs a unit out of another defined unit, e.g. an are is 100 m^2.
     * @param nameKey the key to the locale file for the long name of the unit
     * @param abbreviationKey the key to the locale file for the abbreviation of the unit
     * @param standardUnit the snatdard unit from which this unit is derived with a conversion factor
     * @param conversionFactorToStandardUnit multiply by this number to convert to the standard (e.g., SI) unit
     */
    public AreaUnit(final String nameKey, final String abbreviationKey, final AreaUnit<D> standardUnit,
            final double conversionFactorToStandardUnit)
    {
        super(nameKey, abbreviationKey, conversionFactorToStandardUnit);
        this.distanceUnit = standardUnit.getDistanceUnit();
    }

    /**
     * @see org.opentrafficsim.core.unit.Unit#getMultiplicationFactorTo(org.opentrafficsim.core.unit.Unit)
     */
    @Override
    public double getMultiplicationFactorTo(AreaUnit<D> unit)
    {
        return this.conversionFactorToStandardUnit / unit.getConversionFactorToStandardUnit();
    }

    /**
     * @return distanceUnit
     */
    public D getDistanceUnit()
    {
        return this.distanceUnit;
    }

}
