package org.opentrafficsim.road.gtu.lane.tactical.lmrs;

import org.opentrafficsim.core.gtu.behavioralcharacteristics.BehavioralCharacteristics;
import org.opentrafficsim.core.gtu.behavioralcharacteristics.ParameterException;
import org.opentrafficsim.core.gtu.plan.operational.OperationalPlanException;
import org.opentrafficsim.road.gtu.lane.perception.LanePerception;

/**
 * Interface for voluntary incentives.
 * <p>
 * Copyright (c) 2013-2016 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
 * <p>
 * @version $Revision$, $LastChangedDate$, by $Author$, initial version Apr 13, 2016 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 * @author <a href="http://www.transport.citg.tudelft.nl">Wouter Schakel</a>
 */
public interface VoluntaryIncentive
{

    /**
     * Determines level of lane change desire for a lane change incentive.
     * @param behavioralCharacteristics behavioral characteristics
     * @param perception perception
     * @param mandatoryDesire level of mandatory desire at current time
     * @param voluntaryDesire level of voluntary desire at current time, of voluntary incentives calculated before
     * @return level of lane change desire for this incentive
     * @throws ParameterException if a parameter is not given or out of bounds
     * @throws OperationalPlanException in case of a perception exception
     */
    Desire determineDesire(final BehavioralCharacteristics behavioralCharacteristics, final LanePerception perception,
        Desire mandatoryDesire, Desire voluntaryDesire) throws ParameterException, OperationalPlanException;

}
