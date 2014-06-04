package org.opentrafficsim.core.unit;

/**
 * The units of electric potential (voltage).
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
 * @param <M> the mass unit type (e.g, kg)
 * @param <L> the length unit type (e.g., m)
 * @param <EC> the electric current unit type (e.g., A)
 * @param <T> the time unit type (e.g., s)
 */
public class ElectricPotentialUnit<M extends MassUnit, L extends LengthUnit, EC extends ElectricCurrentUnit, T extends TimeUnit>
        extends Unit<ElectricPotentialUnit<M, L, EC, T>>
{
    /** */
    private static final long serialVersionUID = 20140604L;

    /** the unit of mass for the electric potential difference (voltage) unit, e.g., kilogram */
    private final M massUnit;

    /** the unit of length for the electric potential difference (voltage) unit, e.g., meters */
    private final L lengthUnit;

    /** the unit of electric current for the electric potential difference (voltage) unit, e.g., Ampere */
    private final EC electricCurrentUnit;

    /** the unit of time for the electric potential difference (voltage) unit, e.g., second */
    private final T timeUnit;

    /** Volt */
    public static final ElectricPotentialUnit<MassUnit, LengthUnit, ElectricCurrentUnit, TimeUnit> VOLT =
            new ElectricPotentialUnit<MassUnit, LengthUnit, ElectricCurrentUnit, TimeUnit>(MassUnit.KILOGRAM,
                    LengthUnit.METER, ElectricCurrentUnit.AMPERE, TimeUnit.SECOND, "ElectricPotentialUnit.volt",
                    "ElectricPotentialUnit.V");

    /** millivolt */
    public static final ElectricPotentialUnit<MassUnit, LengthUnit, ElectricCurrentUnit, TimeUnit> MILLIVOLT =
            new ElectricPotentialUnit<MassUnit, LengthUnit, ElectricCurrentUnit, TimeUnit>("ElectricPotentialUnit.millivolt",
                    "ElectricPotentialUnit.mV", VOLT, 0.001);

    /** kilovolt */
    public static final ElectricPotentialUnit<MassUnit, LengthUnit, ElectricCurrentUnit, TimeUnit> KILOVOLT =
            new ElectricPotentialUnit<MassUnit, LengthUnit, ElectricCurrentUnit, TimeUnit>("ElectricPotentialUnit.kilovolt",
                    "ElectricPotentialUnit.kV", VOLT, 1000.0);

    /**
     * @param massUnit the unit of mass for the electric potential difference (voltage) unit, e.g., kilogram
     * @param lengthUnit the unit of length for the electric potential difference (voltage) unit, e.g., meter
     * @param electricCurrentUnit the unit of electric current for the electric potential difference (voltage) unit,
     *            e.g., Ampere
     * @param timeUnit the unit of time for the electric potential difference (voltage) unit, e.g., second
     * @param nameKey the key to the locale file for the long name of the unit
     * @param abbreviationKey the key to the locale file for the abbreviation of the unit
     */
    public ElectricPotentialUnit(final M massUnit, final L lengthUnit, final EC electricCurrentUnit, final T timeUnit,
            final String nameKey, final String abbreviationKey)
    {
        super(nameKey, abbreviationKey, massUnit.getConversionFactorToStandardUnit()
                * lengthUnit.getConversionFactorToStandardUnit()
                * lengthUnit.getConversionFactorToStandardUnit()
                / (electricCurrentUnit.getConversionFactorToStandardUnit() * Math.pow(
                        timeUnit.getConversionFactorToStandardUnit(), 3.0)));
        this.massUnit = massUnit;
        this.lengthUnit = lengthUnit;
        this.electricCurrentUnit = electricCurrentUnit;
        this.timeUnit = timeUnit;
    }

    /**
     * @param powerUnit the unit of power for the electric potential difference (voltage) unit, e.g., Watt
     * @param electricCurrentUnit the unit of electric current for the electric potential difference (voltage) unit,
     *            e.g., Ampere
     * @param nameKey the key to the locale file for the long name of the unit
     * @param abbreviationKey the key to the locale file for the abbreviation of the unit
     */
    public <P extends PowerUnit<M, L, T>> ElectricPotentialUnit(final P powerUnit, final EC electricCurrentUnit,
            final String nameKey, final String abbreviationKey)
    {
        super(nameKey, abbreviationKey, powerUnit.getConversionFactorToStandardUnit()
                / electricCurrentUnit.getConversionFactorToStandardUnit());
        this.massUnit = powerUnit.getMassUnit();
        this.lengthUnit = powerUnit.getLengthUnit();
        this.electricCurrentUnit = electricCurrentUnit;
        this.timeUnit = powerUnit.getTimeUnit();
    }

    /**
     * @param nameKey the key to the locale file for the long name of the unit
     * @param abbreviationKey the key to the locale file for the abbreviation of the unit
     * @param referenceUnit the unit to convert from
     * @param conversionFactorToReferenceUnit multiply by this number to convert from the reference unit
     */
    public ElectricPotentialUnit(final String nameKey, final String abbreviationKey,
            final ElectricPotentialUnit<M, L, EC, T> referenceUnit, final double conversionFactorToReferenceUnit)
    {
        super(nameKey, abbreviationKey, referenceUnit, conversionFactorToReferenceUnit);
        this.massUnit = referenceUnit.getMassUnit();
        this.lengthUnit = referenceUnit.getLengthUnit();
        this.electricCurrentUnit = referenceUnit.getElectricCurrentUnit();
        this.timeUnit = referenceUnit.getTimeUnit();
    }

    /**
     * @return massUnit
     */
    public M getMassUnit()
    {
        return this.massUnit;
    }

    /**
     * @return lengthUnit
     */
    public L getLengthUnit()
    {
        return this.lengthUnit;
    }

    /**
     * @return electricCurrentUnit
     */
    public EC getElectricCurrentUnit()
    {
        return this.electricCurrentUnit;
    }

    /**
     * @return timeUnit
     */
    public T getTimeUnit()
    {
        return this.timeUnit;
    }

}
