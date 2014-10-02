package org.opentrafficsim.core.value.vfloat.vector;

import org.opentrafficsim.core.unit.Unit;
import org.opentrafficsim.core.value.ValueException;
import org.opentrafficsim.core.value.vfloat.scalar.FloatScalar;

/**
 * Methods that modify the data stored in a vector.
 * <p>
 * Copyright (c) 2013-2014 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
 * <p>
 * @version Sep 1, 2014 <br>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 * @param <U> Unit of the vector
 */
public interface WriteFloatVectorFunctions<U extends Unit<U>>
{
    /**
     * Replace the value at index by the supplied value which is expressed in the standard SI unit.
     * @param index int; index of the value to replace
     * @param valueSI float; the value to store (expressed in the standard SI unit)
     * @throws ValueException when index &lt; 0 or index &gt;= size()
     */
    void setSI(int index, float valueSI) throws ValueException;

    /**
     * Replace the value at index by the supplied value which is in a compatible unit.
     * @param index int; index of the value to replace
     * @param value FloatScalar&lt;U&gt;; the strongly typed value to store
     * @throws ValueException when index &lt; 0 or index &gt;= size()
     */
    void set(int index, FloatScalar<U> value) throws ValueException;

    /**
     * Replace the value at index by the supplied value which is expressed in a supplied (compatible) unit.
     * @param index int; index of the value to replace
     * @param value float; the value to store (which is expressed in valueUnit)
     * @param valueUnit U; unit of the supplied value
     * @throws ValueException when index &lt; 0 or index &gt;= size()
     */
    void setInUnit(int index, float value, U valueUnit) throws ValueException;

    /**
     * normalize the vector, i.e. scale the values to make the sum equal to 1.
     * @throws ValueException when the sum of the values is zero and normalization is not possible
     */
    void normalize() throws ValueException;

}
