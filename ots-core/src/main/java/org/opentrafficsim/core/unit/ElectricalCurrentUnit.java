package org.opentrafficsim.core.unit;

import static org.opentrafficsim.core.unit.unitsystem.UnitSystem.*;

import org.opentrafficsim.core.unit.unitsystem.UnitSystem;

/**
 * Standard units for electrical current.
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
 */
public class ElectricalCurrentUnit extends Unit<ElectricalCurrentUnit>
{
    /** */
    private static final long serialVersionUID = 20140607L;

    /** Ampere */
    public static final ElectricalCurrentUnit AMPERE = new ElectricalCurrentUnit("ElectricalCurrentUnit.ampere",
            "ElectricalCurrentUnit.A", SI_BASE);

    /** nanoampere */
    public static final ElectricalCurrentUnit NANOAMPERE = new ElectricalCurrentUnit(
            "ElectricalCurrentUnit.nanoampere", "ElectricalCurrentUnit.nA", SI_BASE, AMPERE, 1.0E-9);

    /** microampere */
    public static final ElectricalCurrentUnit MICROAMPERE = new ElectricalCurrentUnit(
            "ElectricalCurrentUnit.microampere", "ElectricalCurrentUnit.muA", SI_BASE, AMPERE, 1.0E-6);

    /** milliampere */
    public static final ElectricalCurrentUnit MILLIAMPERE = new ElectricalCurrentUnit(
            "ElectricalCurrentUnit.milliampere", "ElectricalCurrentUnit.mA", SI_BASE, AMPERE, 0.001);

    /** kiloampere */
    public static final ElectricalCurrentUnit KILOAMPERE = new ElectricalCurrentUnit(
            "ElectricalCurrentUnit.kiloampere", "ElectricalCurrentUnit.kA", SI_BASE, AMPERE, 1000.0);

    /** statampere (GCS ESU) */
    public static final ElectricalCurrentUnit STATAMPERE = new ElectricalCurrentUnit(
            "ElectricalCurrentUnit.statampere", "ElectricalCurrentUnit.statA", CGS_ESU, AMPERE, 3.335641E-10);
    
    /** abampere (GCS EMU) */
    public static final ElectricalCurrentUnit ABAMPERE = new ElectricalCurrentUnit(
            "ElectricalCurrentUnit.abampere", "ElectricalCurrentUnit.abA", CGS_EMU, AMPERE, 10.0);
    
    /**
     * @param nameKey the key to the locale file for the long name of the unit
     * @param abbreviationKey the key to the locale file for the abbreviation of the unit
     * @param unitSystem the unit system, e.g. SI or Imperial
     */
    public ElectricalCurrentUnit(final String nameKey, final String abbreviationKey, final UnitSystem unitSystem)
    {
        super(nameKey, abbreviationKey, unitSystem);
    }

    /**
     * @param nameKey the key to the locale file for the long name of the unit
     * @param abbreviationKey the key to the locale file for the abbreviation of the unit
     * @param unitSystem the unit system, e.g. SI or Imperial
     * @param referenceUnit the unit to convert to
     * @param conversionFactorToReferenceUnit multiply a value in this unit by the factor to convert to the given
     *            reference unit
     */
    public ElectricalCurrentUnit(String nameKey, String abbreviationKey, final UnitSystem unitSystem,
            ElectricalCurrentUnit referenceUnit, double conversionFactorToReferenceUnit)
    {
        super(nameKey, abbreviationKey, unitSystem, referenceUnit, conversionFactorToReferenceUnit);
    }

    /**
     * @see org.opentrafficsim.core.unit.Unit#getStandardUnit()
     */
    @Override
    public ElectricalCurrentUnit getStandardUnit()
    {
        return AMPERE;
    }

}
