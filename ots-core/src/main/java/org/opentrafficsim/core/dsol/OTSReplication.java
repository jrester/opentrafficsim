package org.opentrafficsim.core.dsol;

import javax.naming.NamingException;

import org.djunits.value.vdouble.scalar.Duration;
import org.djunits.value.vdouble.scalar.Time;
import org.opentrafficsim.core.perception.HistoryManager;
import org.opentrafficsim.core.perception.HistoryManagerDEVS;

import nl.tudelft.simulation.dsol.experiment.Experiment;
import nl.tudelft.simulation.dsol.experiment.Replication;
import nl.tudelft.simulation.dsol.simtime.SimTimeDoubleUnit;
import nl.tudelft.simulation.dsol.simulators.DEVSSimulatorInterface;

/**
 * <p>
 * Copyright (c) 2013-2018 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * <p>
 * @version $Revision$, $LastChangedDate$, by $Author$,
 *          initial version Aug 15, 2014 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 */
public class OTSReplication extends Replication<Time, Duration, SimTimeDoubleUnit>
{

    /** History manager. */
    private HistoryManager historyManager;

    /**
     * @param experiment Experiment&lt;Time,Duration,SimTimeDoubleUnit&gt;; Experiment
     * @throws NamingException when the context for the replication cannot be created
     */
    public OTSReplication(final Experiment<Time, Duration, SimTimeDoubleUnit> experiment) throws NamingException
    {
        super(experiment);
    }

    /**
     * Create a new OTSReplication.
     * @param id String; id of the new OTSReplication
     * @param startTime SimTimeDoubleUnit; the start time of the new OTSReplication
     * @param warmupPeriod Duration; the warmup period of the new OTSReplication
     * @param runLength Duration; the run length of the new OTSReplication
     * @param model OTSModelInterface; the model
     * @throws NamingException when the context for the replication cannot be created
     */
    public OTSReplication(final String id, final SimTimeDoubleUnit startTime, final Duration warmupPeriod,
            final Duration runLength, final OTSModelInterface model) throws NamingException
    {
        super(id, startTime, warmupPeriod, runLength, model);
    }

    /**
     * Returns the history manager. If none was set, one is created coupled to the simulator using 0s of history and 10s
     * clean-up time.
     * @param simulator DEVSSimulatorInterface.TimeDoubleUnit; simulator
     * @return HistoryManager; history manager
     */
    public HistoryManager getHistoryManager(final DEVSSimulatorInterface.TimeDoubleUnit simulator)
    {
        if (this.historyManager == null)
        {
            this.historyManager = new HistoryManagerDEVS(simulator, Duration.createSI(0.0), Duration.createSI(10.0));
        }
        return this.historyManager;
    }

    /**
     * Set history manager.
     * @param historyManager HistoryManager; history manager to set
     */
    public void setHistoryManager(final HistoryManager historyManager)
    {
        this.historyManager = historyManager;
    }

    /** */
    private static final long serialVersionUID = 20140815L;

    /** {@inheritDoc} */
    @Override
    public final String toString()
    {
        return "OTSReplication []";
    }
}
