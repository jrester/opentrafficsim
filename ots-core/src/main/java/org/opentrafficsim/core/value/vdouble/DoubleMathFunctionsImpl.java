package org.opentrafficsim.core.value.vdouble;

import cern.colt.function.tdouble.DoubleFunction;

/**
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
 * @version Jun 18, 2014 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 */
public class DoubleMathFunctionsImpl
{
    /**
     * This class should never be instantiated.
     */
    private DoubleMathFunctionsImpl()
    {
        // Prevent instantiation of this class
    }

    /**
     * Function that returns <tt>Math.cbrt(a)</tt>.
     */
    public static final DoubleFunction cbrt = new DoubleFunction()
    {
        @Override
        public final double apply(final double a)
        {
            return Math.cbrt(a);
        }
    };

    /**
     * Function that returns <tt>Math.cosh(x)</tt>.
     */
    public static final DoubleFunction cosh = new DoubleFunction()
    {
        @Override
        public final double apply(final double a)
        {
            return Math.cosh(a);
        }
    };

    /**
     * Function that returns <tt>Math.expm1(x)</tt>.
     */
    public static final DoubleFunction expm1 = new DoubleFunction()
    {
        @Override
        public final double apply(final double a)
        {
            return Math.expm1(a);
        }
    };

    /**
     * Function that returns <tt>Math.log10(x)</tt>.
     */
    public static final DoubleFunction log10 = new DoubleFunction()
    {
        @Override
        public final double apply(final double a)
        {
            return Math.log10(a);
        }
    };

    /**
     * Function that returns <tt>Math.log1p(x)</tt>.
     */
    public static final DoubleFunction log1p = new DoubleFunction()
    {
        @Override
        public final double apply(final double a)
        {
            return Math.log1p(a);
        }
    };

    /**
     * Function that returns <tt>Math.round(x)</tt>.
     */
    public static final DoubleFunction round = new DoubleFunction()
    {
        @Override
        public final double apply(final double a)
        {
            return Math.round(a);
        }
    };

    /**
     * Function that returns <tt>Math.signum(x)</tt>.
     */
    public static final DoubleFunction signum = new DoubleFunction()
    {
        @Override
        public final double apply(final double a)
        {
            return Math.signum(a);
        }
    };

    /**
     * Function that returns <tt>Math.sinh(x)</tt>.
     */
    public static final DoubleFunction sinh = new DoubleFunction()
    {
        @Override
        public final double apply(final double a)
        {
            return Math.sinh(a);
        }
    };

    /**
     * Function that returns <tt>Math.tanh(x)</tt>.
     */
    public static final DoubleFunction tanh = new DoubleFunction()
    {
        @Override
        public final double apply(final double a)
        {
            return Math.tanh(a);
        }
    };

    /**
     * Function that returns <tt>Math.toDegrees(x)</tt>.
     */
    public static final DoubleFunction toDegrees = new DoubleFunction()
    {
        @Override
        public final double apply(final double a)
        {
            return Math.toDegrees(a);
        }
    };

    /**
     * Function that returns <tt>Math.toRadians(x)</tt>.
     */
    public static final DoubleFunction toRadians = new DoubleFunction()
    {
        @Override
        public final double apply(final double a)
        {
            return Math.toRadians(a);
        }
    };

}
