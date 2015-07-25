package org.opentrafficsim.core.value.vfloat.scalar;

import org.opentrafficsim.core.unit.Unit;
import org.opentrafficsim.core.value.Absolute;
import org.opentrafficsim.core.value.Relative;
import org.opentrafficsim.core.value.ValueUtil;
import org.opentrafficsim.core.value.vfloat.FloatMathFunctions;

/**
 * MutableFloatScalar.
 * <p>
 * This file was generated by the OpenTrafficSim value classes generator, 26 jun, 2015
 * <p>
 * Copyright (c) 2015 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/docs/license.html">OpenTrafficSim License</a>.
 * <p>
 * $LastChangedDate$, @version $Revision$, by $Author$,
 * initial version 26 jun, 2015 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 * @param <U> Unit; the unit of this MutableFloatScalar
 */
public abstract class MutableFloatScalar<U extends Unit<U>> extends FloatScalar<U> implements
    FloatMathFunctions<FloatScalar<U>>
{
    /**  */
    private static final long serialVersionUID = 20150626L;

    /**
     * Construct a new MutableFloatScalar.
     * @param unit U; the unit of the new MutableFloatScalar
     */
    protected MutableFloatScalar(final U unit)
    {
        super(unit);
        // System.out.println("Created MutableFloatScalar");
    }

    /**
     * @param <U> Unit
     */
    public static class Abs<U extends Unit<U>> extends MutableFloatScalar<U> implements Absolute, Comparable<Abs<U>>
    {
        /**  */
        private static final long serialVersionUID = 20150626L;

        /**
         * Construct a new Absolute MutableFloatScalar.
         * @param value float; the value of the new Absolute MutableFloatScalar
         * @param unit U; the unit of the new Absolute MutableFloatScalar
         */
        public Abs(final float value, final U unit)
        {
            super(unit);
            // System.out.println("Created Abs");
            initialize(value);
        }

        /**
         * Construct a new Absolute MutableFloatScalar from an existing Absolute Immutable FloatScalar.
         * @param value FloatScalar.Abs&lt;U&gt;; the reference
         */
        public Abs(final FloatScalar.Abs<U> value)
        {
            super(value.getUnit());
            // System.out.println("Created Abs");
            initialize(value);
        }

        /**
         * Construct a new Absolute MutableFloatScalar from an existing Absolute MutableFloatScalar.
         * @param value MutableFloatScalar.Abs&lt;U&gt;; the reference
         */
        public Abs(final MutableFloatScalar.Abs<U> value)
        {
            super(value.getUnit());
            // System.out.println("Created Abs");
            initialize(value);
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> mutable()
        {
            return new MutableFloatScalar.Abs<U>(this);
        }

        /** {@inheritDoc} */
        @Override
        public final FloatScalar.Abs<U> immutable()
        {
            return new FloatScalar.Abs<U>(this);
        }

        /** {@inheritDoc} */
        @Override
        public final int compareTo(final Abs<U> o)
        {
            return new Float(getSI()).compareTo(o.getSI());
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> copy()
        {
            return new MutableFloatScalar.Abs<U>(this);
        }

        /**
         * Test if this FloatScalar.Abs&lt;U&gt; is less than a FloatScalar.Abs&lt;U&gt;.
         * @param o FloatScalar.Abs&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean lt(final FloatScalar.Abs<U> o)
        {
            return this.getSI() < o.getSI();
        }

        /**
         * Test if this FloatScalar.Abs&lt;U&gt; is less than or equal to a FloatScalar.Abs&lt;U&gt;.
         * @param o FloatScalar.Abs&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean le(final FloatScalar.Abs<U> o)
        {
            return this.getSI() <= o.getSI();
        }

        /**
         * Test if this FloatScalar.Abs&lt;U&gt; is greater than or equal to a FloatScalar.Abs&lt;U&gt;.
         * @param o FloatScalar.Abs&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean gt(final FloatScalar.Abs<U> o)
        {
            return this.getSI() > o.getSI();
        }

        /**
         * Test if this FloatScalar.Abs&lt;U&gt; is greater than a FloatScalar.Abs&lt;U&gt;.
         * @param o FloatScalar.Abs&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean ge(final FloatScalar.Abs<U> o)
        {
            return this.getSI() >= o.getSI();
        }

        /**
         * Test if this FloatScalar.Abs&lt;U&gt; is equal to a FloatScalar.Abs&lt;U&gt;.
         * @param o FloatScalar.Abs&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean eq(final FloatScalar.Abs<U> o)
        {
            return this.getSI() == o.getSI();
        }

        /**
         * Test if this FloatScalar.Abs&lt;U&gt; is not equal to a FloatScalar.Abs&lt;U&gt;.
         * @param o FloatScalar.Abs&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean ne(final FloatScalar.Abs<U> o)
        {
            return this.getSI() != o.getSI();
        }

        /**
         * Test if this FloatScalar.Abs&lt;U&gt; is less than a MutableFloatScalar.Abs&lt;U&gt;.
         * @param o MutableFloatScalar.Abs&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean lt(final MutableFloatScalar.Abs<U> o)
        {
            return this.getSI() < o.getSI();
        }

        /**
         * Test if this FloatScalar.Abs&lt;U&gt; is less than or equal to a MutableFloatScalar.Abs&lt;U&gt;.
         * @param o MutableFloatScalar.Abs&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean le(final MutableFloatScalar.Abs<U> o)
        {
            return this.getSI() <= o.getSI();
        }

        /**
         * Test if this FloatScalar.Abs&lt;U&gt; is greater than or equal to a MutableFloatScalar.Abs&lt;U&gt;.
         * @param o MutableFloatScalar.Abs&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean gt(final MutableFloatScalar.Abs<U> o)
        {
            return this.getSI() > o.getSI();
        }

        /**
         * Test if this FloatScalar.Abs&lt;U&gt; is greater than a MutableFloatScalar.Abs&lt;U&gt;.
         * @param o MutableFloatScalar.Abs&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean ge(final MutableFloatScalar.Abs<U> o)
        {
            return this.getSI() >= o.getSI();
        }

        /**
         * Test if this FloatScalar.Abs&lt;U&gt; is equal to a MutableFloatScalar.Abs&lt;U&gt;.
         * @param o MutableFloatScalar.Abs&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean eq(final MutableFloatScalar.Abs<U> o)
        {
            return this.getSI() == o.getSI();
        }

        /**
         * Test if this FloatScalar.Abs&lt;U&gt; is not equal to a MutableFloatScalar.Abs&lt;U&gt;.
         * @param o MutableFloatScalar.Abs&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean ne(final MutableFloatScalar.Abs<U> o)
        {
            return this.getSI() != o.getSI();
        }

        /**
         * Increment the value by the supplied value and return the result.
         * @param increment FloatScalar.Rel&lt;U&gt;; amount by which the value is incremented
         * @return MutableFloatScalar.Abs&lt;U&gt;
         */
        public final MutableFloatScalar.Abs<U> incrementBy(final FloatScalar.Rel<U> increment)
        {
            return (MutableFloatScalar.Abs<U>) incrementByImpl(increment);
        }

        /**
         * Decrement the value by the supplied value and return the result.
         * @param decrement FloatScalar.Rel&lt;U&gt;; amount by which the value is decremented
         * @return MutableFloatScalar.Abs&lt;U&gt;
         */
        public final MutableFloatScalar.Abs<U> decrementBy(final FloatScalar.Rel<U> decrement)
        {
            return (MutableFloatScalar.Abs<U>) decrementByImpl(decrement);
        }

        /**********************************************************************************/
        /********************************** MATH METHODS **********************************/
        /**********************************************************************************/

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> abs()
        {
            setValueSI(Math.abs(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> acos()
        {
            setValueSI((float) Math.acos(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> asin()
        {
            setValueSI((float) Math.asin(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> atan()
        {
            setValueSI((float) Math.atan(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> cbrt()
        {
            setValueSI((float) Math.cbrt(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> ceil()
        {
            setValueSI((float) Math.ceil(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> cos()
        {
            setValueSI((float) Math.cos(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> cosh()
        {
            setValueSI((float) Math.cosh(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> exp()
        {
            setValueSI((float) Math.exp(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> expm1()
        {
            setValueSI((float) Math.expm1(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> floor()
        {
            setValueSI((float) Math.floor(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> log()
        {
            setValueSI((float) Math.log(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> log10()
        {
            setValueSI((float) Math.log10(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> log1p()
        {
            setValueSI((float) Math.log1p(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> pow(final double x)
        {
            setValueSI((float) Math.pow(getSI(), x));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> rint()
        {
            setValueSI((float) Math.rint(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> round()
        {
            setValueSI(Math.round(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> signum()
        {
            setValueSI(Math.signum(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> sin()
        {
            setValueSI((float) Math.sin(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> sinh()
        {
            setValueSI((float) Math.sinh(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> sqrt()
        {
            setValueSI((float) Math.sqrt(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> tan()
        {
            setValueSI((float) Math.tan(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> tanh()
        {
            setValueSI((float) Math.tanh(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> toDegrees()
        {
            setValueSI((float) Math.toDegrees(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> toRadians()
        {
            setValueSI((float) Math.toRadians(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> inv()
        {
            setValueSI(1.0f / getSI());
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> multiplyBy(final float constant)
        {
            setValueSI(getSI() * constant);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Abs<U> divideBy(final float constant)
        {
            setValueSI(getSI() / constant);
            return this;
        }

    }

    /**
     * @param <U> Unit
     */
    public static class Rel<U extends Unit<U>> extends MutableFloatScalar<U> implements Relative, Comparable<Rel<U>>
    {
        /**  */
        private static final long serialVersionUID = 20150626L;

        /**
         * Construct a new Relative MutableFloatScalar.
         * @param value float; the value of the new Relative MutableFloatScalar
         * @param unit U; the unit of the new Relative MutableFloatScalar
         */
        public Rel(final float value, final U unit)
        {
            super(unit);
            // System.out.println("Created Rel");
            initialize(value);
        }

        /**
         * Construct a new Relative MutableFloatScalar from an existing Relative Immutable FloatScalar.
         * @param value FloatScalar.Rel&lt;U&gt;; the reference
         */
        public Rel(final FloatScalar.Rel<U> value)
        {
            super(value.getUnit());
            // System.out.println("Created Rel");
            initialize(value);
        }

        /**
         * Construct a new Relative MutableFloatScalar from an existing Relative MutableFloatScalar.
         * @param value MutableFloatScalar.Rel&lt;U&gt;; the reference
         */
        public Rel(final MutableFloatScalar.Rel<U> value)
        {
            super(value.getUnit());
            // System.out.println("Created Rel");
            initialize(value);
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> mutable()
        {
            return new MutableFloatScalar.Rel<U>(this);
        }

        /** {@inheritDoc} */
        @Override
        public final FloatScalar.Rel<U> immutable()
        {
            return new FloatScalar.Rel<U>(this);
        }

        /** {@inheritDoc} */
        @Override
        public final int compareTo(final Rel<U> o)
        {
            return new Float(getSI()).compareTo(o.getSI());
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> copy()
        {
            return new MutableFloatScalar.Rel<U>(this);
        }

        /**
         * Test if this FloatScalar.Rel&lt;U&gt; is less than a FloatScalar.Rel&lt;U&gt;.
         * @param o FloatScalar.Rel&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean lt(final FloatScalar.Rel<U> o)
        {
            return this.getSI() < o.getSI();
        }

        /**
         * Test if this FloatScalar.Rel&lt;U&gt; is less than or equal to a FloatScalar.Rel&lt;U&gt;.
         * @param o FloatScalar.Rel&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean le(final FloatScalar.Rel<U> o)
        {
            return this.getSI() <= o.getSI();
        }

        /**
         * Test if this FloatScalar.Rel&lt;U&gt; is greater than or equal to a FloatScalar.Rel&lt;U&gt;.
         * @param o FloatScalar.Rel&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean gt(final FloatScalar.Rel<U> o)
        {
            return this.getSI() > o.getSI();
        }

        /**
         * Test if this FloatScalar.Rel&lt;U&gt; is greater than a FloatScalar.Rel&lt;U&gt;.
         * @param o FloatScalar.Rel&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean ge(final FloatScalar.Rel<U> o)
        {
            return this.getSI() >= o.getSI();
        }

        /**
         * Test if this FloatScalar.Rel&lt;U&gt; is equal to a FloatScalar.Rel&lt;U&gt;.
         * @param o FloatScalar.Rel&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean eq(final FloatScalar.Rel<U> o)
        {
            return this.getSI() == o.getSI();
        }

        /**
         * Test if this FloatScalar.Rel&lt;U&gt; is not equal to a FloatScalar.Rel&lt;U&gt;.
         * @param o FloatScalar.Rel&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean ne(final FloatScalar.Rel<U> o)
        {
            return this.getSI() != o.getSI();
        }

        /**
         * Test if this FloatScalar.Rel&lt;U&gt; is less than a MutableFloatScalar.Rel&lt;U&gt;.
         * @param o MutableFloatScalar.Rel&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean lt(final MutableFloatScalar.Rel<U> o)
        {
            return this.getSI() < o.getSI();
        }

        /**
         * Test if this FloatScalar.Rel&lt;U&gt; is less than or equal to a MutableFloatScalar.Rel&lt;U&gt;.
         * @param o MutableFloatScalar.Rel&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean le(final MutableFloatScalar.Rel<U> o)
        {
            return this.getSI() <= o.getSI();
        }

        /**
         * Test if this FloatScalar.Rel&lt;U&gt; is greater than or equal to a MutableFloatScalar.Rel&lt;U&gt;.
         * @param o MutableFloatScalar.Rel&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean gt(final MutableFloatScalar.Rel<U> o)
        {
            return this.getSI() > o.getSI();
        }

        /**
         * Test if this FloatScalar.Rel&lt;U&gt; is greater than a MutableFloatScalar.Rel&lt;U&gt;.
         * @param o MutableFloatScalar.Rel&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean ge(final MutableFloatScalar.Rel<U> o)
        {
            return this.getSI() >= o.getSI();
        }

        /**
         * Test if this FloatScalar.Rel&lt;U&gt; is equal to a MutableFloatScalar.Rel&lt;U&gt;.
         * @param o MutableFloatScalar.Rel&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean eq(final MutableFloatScalar.Rel<U> o)
        {
            return this.getSI() == o.getSI();
        }

        /**
         * Test if this FloatScalar.Rel&lt;U&gt; is not equal to a MutableFloatScalar.Rel&lt;U&gt;.
         * @param o MutableFloatScalar.Rel&lt;U&gt;; the right hand side operand of the comparison
         * @return boolean
         */
        public final boolean ne(final MutableFloatScalar.Rel<U> o)
        {
            return this.getSI() != o.getSI();
        }

        /**
         * Increment the value by the supplied value and return the result.
         * @param increment FloatScalar.Rel&lt;U&gt;; amount by which the value is incremented
         * @return MutableFloatScalar.Rel&lt;U&gt;
         */
        public final MutableFloatScalar.Rel<U> incrementBy(final FloatScalar.Rel<U> increment)
        {
            return (MutableFloatScalar.Rel<U>) incrementByImpl(increment);
        }

        /**
         * Decrement the value by the supplied value and return the result.
         * @param decrement FloatScalar.Rel&lt;U&gt;; amount by which the value is decremented
         * @return MutableFloatScalar.Rel&lt;U&gt;
         */
        public final MutableFloatScalar.Rel<U> decrementBy(final FloatScalar.Rel<U> decrement)
        {
            return (MutableFloatScalar.Rel<U>) decrementByImpl(decrement);
        }

        /**********************************************************************************/
        /********************************** MATH METHODS **********************************/
        /**********************************************************************************/

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> abs()
        {
            setValueSI(Math.abs(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> acos()
        {
            setValueSI((float) Math.acos(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> asin()
        {
            setValueSI((float) Math.asin(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> atan()
        {
            setValueSI((float) Math.atan(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> cbrt()
        {
            setValueSI((float) Math.cbrt(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> ceil()
        {
            setValueSI((float) Math.ceil(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> cos()
        {
            setValueSI((float) Math.cos(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> cosh()
        {
            setValueSI((float) Math.cosh(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> exp()
        {
            setValueSI((float) Math.exp(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> expm1()
        {
            setValueSI((float) Math.expm1(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> floor()
        {
            setValueSI((float) Math.floor(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> log()
        {
            setValueSI((float) Math.log(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> log10()
        {
            setValueSI((float) Math.log10(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> log1p()
        {
            setValueSI((float) Math.log1p(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> pow(final double x)
        {
            setValueSI((float) Math.pow(getSI(), x));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> rint()
        {
            setValueSI((float) Math.rint(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> round()
        {
            setValueSI(Math.round(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> signum()
        {
            setValueSI(Math.signum(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> sin()
        {
            setValueSI((float) Math.sin(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> sinh()
        {
            setValueSI((float) Math.sinh(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> sqrt()
        {
            setValueSI((float) Math.sqrt(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> tan()
        {
            setValueSI((float) Math.tan(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> tanh()
        {
            setValueSI((float) Math.tanh(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> toDegrees()
        {
            setValueSI((float) Math.toDegrees(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> toRadians()
        {
            setValueSI((float) Math.toRadians(getSI()));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> inv()
        {
            setValueSI(1.0f / getSI());
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> multiplyBy(final float constant)
        {
            setValueSI(getSI() * constant);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableFloatScalar.Rel<U> divideBy(final float constant)
        {
            setValueSI(getSI() / constant);
            return this;
        }

    }

    /**
     * Make (immutable) FloatScalar equivalent for any type of MutableFloatScalar. <br>
     * The immutable version is created as a deep copy of this. Delayed copying is not worthwhile for a Scalar.
     * @return FloatScalar&lt;U&gt;; immutable version of this FloatScalar
     */
    public abstract FloatScalar<U> immutable();

    /**
     * Replace the stored value by the supplied value which is expressed in the standard SI unit.
     * @param valueSI float; the value to store (value must already be in the standard SI unit)
     */
    final void setSI(final float valueSI)
    {
        setValueSI(valueSI);
    }

    /**
     * Replace the stored value by the supplied value.
     * @param value FloatScalar&lt;U&gt;; the strongly typed value to store
     */
    final void set(final FloatScalar<U> value)
    {
        setValueSI(value.getSI());
    }

    /**
     * Replace the stored value by the supplied value which can be expressed in any compatible unit.
     * @param value float; the value to store
     * @param valueUnit U; the unit of the supplied value
     */
    final void setInUnit(final float value, final U valueUnit)
    {
        setValueSI((float) ValueUtil.expressAsSIUnit(value, valueUnit));
    }

    /**********************************************************************************/
    /******************************* NON-STATIC METHODS *******************************/
    /**********************************************************************************/

    /**
     * Increment the value in this MutableFloatScalar by the value in a FloatScalar.
     * @param increment FloatScalar&lt;U&gt;; the amount by which to increment the value in this MutableFloatScalar
     * @return MutableFloatScalar&lt;U&gt;; this modified MutableFloatScalar
     */
    protected final MutableFloatScalar<U> incrementBy(final FloatScalar<U> increment)
    {
        setValueSI(getSI() + increment.getSI());
        return this;
    }

    /**
     * Decrement the value in this MutableFloatScalar by the value in a FloatScalar.
     * @param decrement FloatScalar&lt;U&gt;; the amount by which to decrement the value in this MutableFloatScalar
     * @return MutableFloatScalar&lt;U&gt;; this modified MutableFloatScalar
     */
    protected final MutableFloatScalar<U> decrementBy(final FloatScalar<U> decrement)
    {
        setValueSI(getSI() - decrement.getSI());
        return this;
    }

    /**
     * Increment the value in this MutableFloatScalar by the value in a Relative FloatScalar. <br>
     * Only Relative values are allowed; adding an Absolute value to an Absolute value is not allowed. Adding an Absolute value
     * to an existing Relative value would require the result to become Absolute, which is a type change that is impossible. For
     * that operation use a static method.
     * @param rel FloatScalar.Rel&lt;U&gt;; the Relative FloatScalar
     * @return MutableFloatScalar&lt;U&gt;; this modified MutableFloatScalar
     */
    protected final MutableFloatScalar<U> incrementByImpl(final FloatScalar.Rel<U> rel)
    {
        setValueSI(getSI() + rel.getSI());
        return this;
    }

    /**
     * Decrement the value of this Relative FloatScalar from the value of this MutableFloatScalar. <br>
     * Only Relative values are allowed; subtracting an Absolute value from a Relative value is not allowed. Subtracting an
     * Absolute value from an existing Absolute value would require the result to become Relative, which is a type change that
     * is impossible. For that operation use a static method.
     * @param rel FloatScalar.Rel&lt;U&gt;; the Relative FloatScalar
     * @return MutableFloatScalar&lt;U&gt;; this modified MutableFloatScalar
     */
    protected final MutableFloatScalar<U> decrementByImpl(final FloatScalar.Rel<U> rel)
    {
        setValueSI(getSI() - rel.getSI());
        return this;
    }

}
