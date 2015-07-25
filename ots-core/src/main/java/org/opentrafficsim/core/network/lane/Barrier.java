package org.opentrafficsim.core.network.lane;

import org.opentrafficsim.core.network.NetworkException;
import org.opentrafficsim.core.unit.LengthUnit;
import org.opentrafficsim.core.value.vdouble.scalar.DoubleScalar;

/**
 * Typically, a barrier will have no lateral permeability. Sometimes, pedestrians can be given lateral permeability for the
 * barrier.
 * <p>
 * Copyright (c) 2013-2015 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * <p>
 * $LastChangedDate$, @version $Revision$, by $Author$,
 * initial version Oct 25, 2014 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 * @param <NODEID> the ID type of the Node, e.g., String.
 * @param <LINKID> the ID type of the Link, e.g., String.
 */
public abstract class Barrier<LINKID, NODEID> extends RoadMarkerAlong<LINKID, NODEID>
{
    /**
     * <b>Note:</b> LEFT is seen as a positive lateral direction, RIGHT as a negative lateral direction, with the direction from
     * the StartNode towards the EndNode as the longitudinal direction.
     * @param parentLink Cross Section Link to which the element belongs.
     * @param lateralCenterPosition the lateral start position compared to the linear geometry of the Cross Section Link.
     * @param beginWidth start width, positioned <i>symmetrically around</i> the lateral start position.
     * @param endWidth end width, positioned <i>symmetrically around</i> the lateral end position.
     * @throws NetworkException on network inconsistency
     */
    public Barrier(final CrossSectionLink<LINKID, NODEID> parentLink,
        final DoubleScalar.Rel<LengthUnit> lateralCenterPosition, final DoubleScalar.Rel<LengthUnit> beginWidth,
        final DoubleScalar.Rel<LengthUnit> endWidth) throws NetworkException
    {
        super(parentLink, lateralCenterPosition, beginWidth, endWidth);
    }

    /**
     * String ID class.
     * <p>
     * Copyright (c) 2013-2014 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. <br>
     * All rights reserved. <br>
     * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
     * <p>
     * @version Jul 22, 2015 <br>
     * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
     * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
     */
    public static class STR extends Barrier<String, String>
    {
        /**
         * <b>Note:</b> LEFT is seen as a positive lateral direction, RIGHT as a negative lateral direction, with the direction
         * from the StartNode towards the EndNode as the longitudinal direction.
         * @param parentLink Cross Section Link to which the element belongs.
         * @param lateralCenterPosition the lateral start position compared to the linear geometry of the Cross Section Link.
         * @param beginWidth start width, positioned <i>symmetrically around</i> the lateral start position.
         * @param endWidth end width, positioned <i>symmetrically around</i> the lateral end position.
         * @throws NetworkException on network inconsistency
         */
        public STR(final CrossSectionLink<String, String> parentLink,
            final DoubleScalar.Rel<LengthUnit> lateralCenterPosition, final DoubleScalar.Rel<LengthUnit> beginWidth,
            final DoubleScalar.Rel<LengthUnit> endWidth) throws NetworkException
        {
            super(parentLink, lateralCenterPosition, beginWidth, endWidth);
        }
    }

    /**
     * Integer ID class.
     * <p>
     * Copyright (c) 2013-2014 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. <br>
     * All rights reserved. <br>
     * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
     * <p>
     * @version Jul 22, 2015 <br>
     * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
     * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
     */
    public static class INT extends Barrier<Integer, Integer>
    {
        /**
         * <b>Note:</b> LEFT is seen as a positive lateral direction, RIGHT as a negative lateral direction, with the direction
         * from the StartNode towards the EndNode as the longitudinal direction.
         * @param parentLink Cross Section Link to which the element belongs.
         * @param lateralCenterPosition the lateral start position compared to the linear geometry of the Cross Section Link.
         * @param beginWidth start width, positioned <i>symmetrically around</i> the lateral start position.
         * @param endWidth end width, positioned <i>symmetrically around</i> the lateral end position.
         * @throws NetworkException on network inconsistency
         */
        public INT(final CrossSectionLink<Integer, Integer> parentLink,
            final DoubleScalar.Rel<LengthUnit> lateralCenterPosition, final DoubleScalar.Rel<LengthUnit> beginWidth,
            final DoubleScalar.Rel<LengthUnit> endWidth) throws NetworkException
        {
            super(parentLink, lateralCenterPosition, beginWidth, endWidth);
        }
    }
}
