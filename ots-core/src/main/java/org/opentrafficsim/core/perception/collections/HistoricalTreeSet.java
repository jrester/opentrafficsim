package org.opentrafficsim.core.perception.collections;

import java.util.Collection;
import java.util.TreeSet;

import org.djunits.value.vdouble.scalar.Time;
import org.opentrafficsim.core.perception.HistoryManager;

/**
 * TreeSet-valued historical state. The current tree set is always maintained, and past states of the tree set are obtained by
 * applying the events between now and the requested time in reverse.<br>
 * <br>
 * The {@code Iterator} returned by this class does not support the {@code remove()} method. Any returned subset is
 * unmodifiable.
 * <p>
 * Copyright (c) 2013-2017 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
 * <p>
 * @version $Revision$, $LastChangedDate$, by $Author$, initial version 3 feb. 2018 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 * @author <a href="http://www.transport.citg.tudelft.nl">Wouter Schakel</a>
 * @param <E> element type
 */
public class HistoricalTreeSet<E> extends AbstractHistoricalNavigableSet<E, TreeSet<E>>
{

    /**
     * Constructor.
     * @param historyManager HistoryManager; history manager
     */
    public HistoricalTreeSet(final HistoryManager historyManager)
    {
        super(historyManager, new TreeSet<>());
    }
    
    /**
     * Constructor.
     * @param historyManager HistoryManager; history manager
     * @param c Collection; initial collection
     */
    public HistoricalTreeSet(final HistoryManager historyManager, final Collection<? extends E> c)
    {
        super(historyManager, new TreeSet<>(c));
    }

    /** {@inheritDoc} */
    @Override
    public TreeSet<E> get()
    {
        return fill(new TreeSet<E>());
    }

    /** {@inheritDoc} */
    @Override
    public TreeSet<E> get(final Time time)
    {
        return fill(time, new TreeSet<E>());
    }
    
    /** {@inheritDoc} */
    @Override
    public String toString()
    {
        return "HistoricalTreeSet [current=" + getCollection() + "]";
    }

}
