package org.opentrafficsim.graphs;

import javax.swing.JFrame;

/**
 * <p>
 * Copyright (c) 2013-2015 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * <p>
 * $LastChangedDate$, @version $Revision$, by $Author$,
 * initial version 8 dec. 2014 <br>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 */
public interface MultipleViewerChart
{
    /**
     * Create/add a viewer for the data of the graph.
     * @return JFrame; the JFrame that holds the visualization of the data
     */
    JFrame addViewer();
}
