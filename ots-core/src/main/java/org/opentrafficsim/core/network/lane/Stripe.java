package org.opentrafficsim.core.network.lane;

import java.util.Set;

import org.opentrafficsim.core.gtu.GTUType;
import org.opentrafficsim.core.network.LateralDirectionality;
import org.opentrafficsim.core.network.NetworkException;
import org.opentrafficsim.core.unit.LengthUnit;
import org.opentrafficsim.core.value.vdouble.scalar.DoubleScalar;

/**
 * Longitudinal road stripes.
 * <p>
 * Copyright (c) 2013-2015 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
 * <p>
 * $LastChangedDate$, @version $Revision$, by $Author$,
 * initial version Oct 25, 2014 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 * @param <NODEID> the ID type of the Node, e.g., String.
 * @param <LINKID> the ID type of the Link, e.g., String.
 */
public class Stripe<LINKID, NODEID> extends RoadMarkerAlong<LINKID, NODEID>
{
    /**
     * <b>Note:</b> LEFT is seen as a positive lateral direction, RIGHT as a negative lateral direction, with the direction from
     * the StartNode towards the EndNode as the longitudinal direction.
     * @param parentLink Cross Section Link to which the element belongs
     * @param lateralCenterPosition the lateral start position compared to the linear geometry of the Cross Section Link
     * @param width positioned <i>symmetrically around</i> the center line given by the lateralCenterPosition.
     * @throws NetworkException on network topology problems
     */
    public Stripe(final CrossSectionLink<LINKID, NODEID> parentLink,
        final DoubleScalar.Rel<LengthUnit> lateralCenterPosition, final DoubleScalar.Rel<LengthUnit> width)
        throws NetworkException
    {
        super(parentLink, lateralCenterPosition, width, width);
    }

    /**
     * Helper constructor that immediately provides permeability for a number of GTU classes.<br>
     * <b>Note:</b> LEFT is seen as a positive lateral direction, RIGHT as a negative lateral direction, with the direction from
     * the StartNode towards the EndNode as the longitudinal direction.
     * @param parentLink Cross Section Link to which the element belongs
     * @param lateralCenterPosition the lateral start position compared to the linear geometry of the Cross Section Link
     * @param width positioned <i>symmetrically around</i> the center line given by the lateralCenterPosition
     * @param gtuTypes the GTU types for which the permeability is defined
     * @param permeable one of the enums of Stripe.Permeable to define the permeability
     * @throws NetworkException on network topology problems
     */
    public Stripe(final CrossSectionLink<LINKID, NODEID> parentLink,
        final DoubleScalar.Rel<LengthUnit> lateralCenterPosition, final DoubleScalar.Rel<LengthUnit> width,
        final Set<GTUType<?>> gtuTypes, final Permeable permeable) throws NetworkException
    {
        super(parentLink, lateralCenterPosition, width, width);
        for (GTUType<?> gtuType : gtuTypes)
        {
            addPermeability(gtuType, permeable);
        }
    }

    /**
     * Helper constructor that immediately provides permeability for all GTU classes.<br>
     * <b>Note:</b> LEFT is seen as a positive lateral direction, RIGHT as a negative lateral direction, with the direction from
     * the StartNode towards the EndNode as the longitudinal direction.
     * @param parentLink Cross Section Link to which the element belongs
     * @param lateralCenterPosition the lateral start position compared to the linear geometry of the Cross Section Link
     * @param width positioned <i>symmetrically around</i> the center line given by the lateralCenterPosition
     * @param permeable one of the enums of Stripe.Permeable to define the permeability
     * @throws NetworkException on network topology problems
     */
    public Stripe(final CrossSectionLink<LINKID, NODEID> parentLink,
        final DoubleScalar.Rel<LengthUnit> lateralCenterPosition, final DoubleScalar.Rel<LengthUnit> width,
        final Permeable permeable) throws NetworkException
    {
        super(parentLink, lateralCenterPosition, width, width);
        addPermeability(GTUType.ALL, permeable);
    }

    /**
     * @param gtuType GTU type to add permeability for.
     * @param permeable direction(s) to add compared to the direction of the design line.
     */
    public final void addPermeability(final GTUType<?> gtuType, final Permeable permeable)
    {
        if (permeable.equals(Permeable.LEFT) || permeable.equals(Permeable.BOTH))
        {
            addPermeability(gtuType, LateralDirectionality.LEFT);
        }
        if (permeable.equals(Permeable.RIGHT) || permeable.equals(Permeable.BOTH))
        {
            addPermeability(gtuType, LateralDirectionality.RIGHT);
        }
    }

    /** the types of permeability of a stripe. */
    public enum Permeable
    {
        /** Permeable in the positive lateral direction compared to the design line direction. */
        LEFT,
        /** Permeable in the negative lateral direction compared to the design line direction. */
        RIGHT,
        /** Permeable in both directions. */
        BOTH;
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("checkstyle:designforextension")
    public String toString()
    {
        return String.format("Stripe offset %.2fm..%.2fm, width %.2fm..%.2fm", getDesignLineOffsetAtBegin().getSI(),
            getDesignLineOffsetAtEnd().getSI(), getBeginWidth().getSI(), getEndWidth().getSI());
    }

    /**
     * String ID class.
     * <p>
     * Copyright (c) 2013-2014 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. <br>
     * All rights reserved. <br>
     * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
     * <p>
     * @version Jul 22, 2015 <br>
     * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
     * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
     */
    public static class STR extends Stripe<String, String>
    {
        /**
         * <b>Note:</b> LEFT is seen as a positive lateral direction, RIGHT as a negative lateral direction, with the direction
         * from the StartNode towards the EndNode as the longitudinal direction.
         * @param parentLink Cross Section Link to which the element belongs
         * @param lateralCenterPosition the lateral start position compared to the linear geometry of the Cross Section Link
         * @param width positioned <i>symmetrically around</i> the center line given by the lateralCenterPosition.
         * @throws NetworkException on network topology problems
         */
        public STR(final CrossSectionLink<String, String> parentLink,
            final DoubleScalar.Rel<LengthUnit> lateralCenterPosition, final DoubleScalar.Rel<LengthUnit> width)
            throws NetworkException
        {
            super(parentLink, lateralCenterPosition, width);
        }

        /**
         * Helper constructor that immediately provides permeability for a number of GTU classes.<br>
         * <b>Note:</b> LEFT is seen as a positive lateral direction, RIGHT as a negative lateral direction, with the direction
         * from the StartNode towards the EndNode as the longitudinal direction.
         * @param parentLink Cross Section Link to which the element belongs
         * @param lateralCenterPosition the lateral start position compared to the linear geometry of the Cross Section Link
         * @param width positioned <i>symmetrically around</i> the center line given by the lateralCenterPosition
         * @param gtuTypes the GTU types for which the permeability is defined
         * @param permeable one of the enums of Stripe.Permeable to define the permeability
         * @throws NetworkException on network topology problems
         */
        public STR(final CrossSectionLink<String, String> parentLink,
            final DoubleScalar.Rel<LengthUnit> lateralCenterPosition, final DoubleScalar.Rel<LengthUnit> width,
            final Set<GTUType<?>> gtuTypes, final Permeable permeable) throws NetworkException
        {
            super(parentLink, lateralCenterPosition, width, gtuTypes, permeable);
        }

        /**
         * Helper constructor that immediately provides permeability for all GTU classes.<br>
         * <b>Note:</b> LEFT is seen as a positive lateral direction, RIGHT as a negative lateral direction, with the direction
         * from the StartNode towards the EndNode as the longitudinal direction.
         * @param parentLink Cross Section Link to which the element belongs
         * @param lateralCenterPosition the lateral start position compared to the linear geometry of the Cross Section Link
         * @param width positioned <i>symmetrically around</i> the center line given by the lateralCenterPosition
         * @param permeable one of the enums of Stripe.Permeable to define the permeability
         * @throws NetworkException on network topology problems
         */
        public STR(final CrossSectionLink<String, String> parentLink,
            final DoubleScalar.Rel<LengthUnit> lateralCenterPosition, final DoubleScalar.Rel<LengthUnit> width,
            final Permeable permeable) throws NetworkException
        {
            super(parentLink, lateralCenterPosition, width, permeable);
        }
    }

    /**
     * Integer ID class.
     * <p>
     * Copyright (c) 2013-2014 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. <br>
     * All rights reserved. <br>
     * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
     * <p>
     * @version Jul 22, 2015 <br>
     * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
     * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
     */
    public static class INT extends Stripe<Integer, Integer>
    {
        /**
         * <b>Note:</b> LEFT is seen as a positive lateral direction, RIGHT as a negative lateral direction, with the direction
         * from the StartNode towards the EndNode as the longitudinal direction.
         * @param parentLink Cross Section Link to which the element belongs
         * @param lateralCenterPosition the lateral start position compared to the linear geometry of the Cross Section Link
         * @param width positioned <i>symmetrically around</i> the center line given by the lateralCenterPosition.
         * @throws NetworkException on network topology problems
         */
        public INT(final CrossSectionLink<Integer, Integer> parentLink,
            final DoubleScalar.Rel<LengthUnit> lateralCenterPosition, final DoubleScalar.Rel<LengthUnit> width)
            throws NetworkException
        {
            super(parentLink, lateralCenterPosition, width);
        }

        /**
         * Helper constructor that immediately provides permeability for a number of GTU classes.<br>
         * <b>Note:</b> LEFT is seen as a positive lateral direction, RIGHT as a negative lateral direction, with the direction
         * from the StartNode towards the EndNode as the longitudinal direction.
         * @param parentLink Cross Section Link to which the element belongs
         * @param lateralCenterPosition the lateral start position compared to the linear geometry of the Cross Section Link
         * @param width positioned <i>symmetrically around</i> the center line given by the lateralCenterPosition
         * @param gtuTypes the GTU types for which the permeability is defined
         * @param permeable one of the enums of Stripe.Permeable to define the permeability
         * @throws NetworkException on network topology problems
         */
        public INT(final CrossSectionLink<Integer, Integer> parentLink,
            final DoubleScalar.Rel<LengthUnit> lateralCenterPosition, final DoubleScalar.Rel<LengthUnit> width,
            final Set<GTUType<?>> gtuTypes, final Permeable permeable) throws NetworkException
        {
            super(parentLink, lateralCenterPosition, width, gtuTypes, permeable);
        }

        /**
         * Helper constructor that immediately provides permeability for all GTU classes.<br>
         * <b>Note:</b> LEFT is seen as a positive lateral direction, RIGHT as a negative lateral direction, with the direction
         * from the StartNode towards the EndNode as the longitudinal direction.
         * @param parentLink Cross Section Link to which the element belongs
         * @param lateralCenterPosition the lateral start position compared to the linear geometry of the Cross Section Link
         * @param width positioned <i>symmetrically around</i> the center line given by the lateralCenterPosition
         * @param permeable one of the enums of Stripe.Permeable to define the permeability
         * @throws NetworkException on network topology problems
         */
        public INT(final CrossSectionLink<Integer, Integer> parentLink,
            final DoubleScalar.Rel<LengthUnit> lateralCenterPosition, final DoubleScalar.Rel<LengthUnit> width,
            final Permeable permeable) throws NetworkException
        {
            super(parentLink, lateralCenterPosition, width, permeable);
        }
    }

}
