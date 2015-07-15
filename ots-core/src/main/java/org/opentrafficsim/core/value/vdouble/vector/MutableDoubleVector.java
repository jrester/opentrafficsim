package org.opentrafficsim.core.value.vdouble.vector;

import org.opentrafficsim.core.unit.Unit;
import org.opentrafficsim.core.value.Absolute;
import org.opentrafficsim.core.value.DenseData;
import org.opentrafficsim.core.value.Relative;
import org.opentrafficsim.core.value.SparseData;
import org.opentrafficsim.core.value.ValueException;
import org.opentrafficsim.core.value.ValueUtil;
import org.opentrafficsim.core.value.vdouble.DoubleMathFunctions;
import org.opentrafficsim.core.value.vdouble.DoubleMathFunctionsImpl;
import org.opentrafficsim.core.value.vdouble.scalar.DoubleScalar;

import cern.colt.matrix.tdouble.DoubleMatrix1D;
import cern.colt.matrix.tdouble.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.tdouble.impl.SparseDoubleMatrix1D;
import cern.jet.math.tdouble.DoubleFunctions;

/**
 * MutableDoubleVector.
 * <p>
 * This file was generated by the OpenTrafficSim value classes generator, 26 jun, 2015
 * <p>
 * Copyright (c) 2015 Delft University of Technology, PO Box 5, 2600 AA, Delft, the Netherlands. All rights reserved. <br>
 * BSD-style license. See <a href="http://opentrafficsim.org/node/13">OpenTrafficSim License</a>.
 * <p>
 * $LastChangedDate$, @version $Revision$, by $Author: pknoppers
 * $, initial version26 jun, 2015 <br>
 * @author <a href="http://www.tbm.tudelft.nl/averbraeck">Alexander Verbraeck</a>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 * @param <U> Unit; the unit of this MutableDoubleVector
 */
public abstract class MutableDoubleVector<U extends Unit<U>> extends DoubleVector<U> implements
        WriteDoubleVectorFunctions<U>, DoubleMathFunctions<MutableDoubleVector<U>>
{
    /**  */
    private static final long serialVersionUID = 20150626L;

    /**
     * Construct a new MutableDoubleVector.
     * @param unit U; the unit of the new MutableDoubleVector
     */
    protected MutableDoubleVector(final U unit)
    {
        super(unit);
        // System.out.println("Created MutableDoubleVector");
    }

    /** If set, any modification of the data must be preceded by replacing the data with a local copy. */
    private boolean copyOnWrite = false;

    /**
     * Retrieve the value of the copyOnWrite flag.
     * @return boolean
     */
    private boolean isCopyOnWrite()
    {
        return this.copyOnWrite;
    }

    /**
     * Change the copyOnWrite flag.
     * @param copyOnWrite boolean; the new value for the copyOnWrite flag
     */
    final void setCopyOnWrite(final boolean copyOnWrite)
    {
        this.copyOnWrite = copyOnWrite;
    }

    /** {@inheritDoc} */
    @Override
    public final void normalize() throws ValueException
    {
        double sum = zSum();
        if (0 == sum)
        {
            throw new ValueException("zSum is 0; cannot normalize");
        }
        checkCopyOnWrite();
        for (int i = 0; i < size(); i++)
        {
            safeSet(i, safeGet(i) / sum);
        }
    }

    /**
     * @param <U> Unit
     */
    public abstract static class Abs<U extends Unit<U>> extends MutableDoubleVector<U> implements Absolute
    {
        /**  */
        private static final long serialVersionUID = 20150626L;

        /**
         * Construct a new Absolute MutableDoubleVector.
         * @param unit U; the unit of the new Absolute MutableDoubleVector
         */
        protected Abs(final U unit)
        {
            super(unit);
            // System.out.println("Created Abs");
        }

        /**
         * @param <U> Unit
         */
        public static class Dense<U extends Unit<U>> extends Abs<U> implements DenseData
        {
            /**  */
            private static final long serialVersionUID = 20150626L;

            /**
             * Construct a new Absolute Dense MutableDoubleVector.
             * @param values double[]; the initial values of the entries in the new Absolute Dense MutableDoubleVector
             * @param unit U; the unit of the new Absolute Dense MutableDoubleVector
             * @throws ValueException when values is null
             */
            public Dense(final double[] values, final U unit) throws ValueException
            {
                super(unit);
                // System.out.println("Created Dense");
                initialize(values);
            }

            /**
             * Construct a new Absolute Dense MutableDoubleVector.
             * @param values DoubleScalar.Abs&lt;U&gt;[]; the initial values of the entries in the new Absolute Dense
             *            MutableDoubleVector
             * @throws ValueException when values has zero entries
             */
            public Dense(final DoubleScalar.Abs<U>[] values) throws ValueException
            {
                super(checkNonEmpty(values)[0].getUnit());
                // System.out.println("Created Dense");
                initialize(values);
            }

            /**
             * For package internal use only.
             * @param values DoubleMatrix1D; the initial values of the entries in the new Absolute Dense
             *            MutableDoubleVector
             * @param unit U; the unit of the new Absolute Dense MutableDoubleVector
             */
            protected Dense(final DoubleMatrix1D values, final U unit)
            {
                super(unit);
                // System.out.println("Created Dense");
                setCopyOnWrite(true);
                initialize(values); // shallow copy
            }

            /** {@inheritDoc} */
            @Override
            public final DoubleVector.Abs.Dense<U> immutable()
            {
                setCopyOnWrite(true);
                return new DoubleVector.Abs.Dense<U>(getVectorSI(), getUnit());
            }

            /** {@inheritDoc} */
            @Override
            public final MutableDoubleVector.Abs.Dense<U> mutable()
            {
                setCopyOnWrite(true);
                final MutableDoubleVector.Abs.Dense<U> result =
                        new MutableDoubleVector.Abs.Dense<U>(getVectorSI(), getUnit());
                result.setCopyOnWrite(true);
                return result;
            }

            /** {@inheritDoc} */
            @Override
            protected final DoubleMatrix1D createMatrix1D(final int size)
            {
                return new DenseDoubleMatrix1D(size);
            }

            /** {@inheritDoc} */
            @Override
            public final MutableDoubleVector.Abs.Dense<U> copy()
            {
                return mutable();
            }

        }

        /**
         * @param <U> Unit
         */
        public static class Sparse<U extends Unit<U>> extends Abs<U> implements SparseData
        {
            /**  */
            private static final long serialVersionUID = 20150626L;

            /**
             * Construct a new Absolute Sparse MutableDoubleVector.
             * @param values double[]; the initial values of the entries in the new Absolute Sparse MutableDoubleVector
             * @param unit U; the unit of the new Absolute Sparse MutableDoubleVector
             * @throws ValueException when values is null
             */
            public Sparse(final double[] values, final U unit) throws ValueException
            {
                super(unit);
                // System.out.println("Created Sparse");
                initialize(values);
            }

            /**
             * Construct a new Absolute Sparse MutableDoubleVector.
             * @param values DoubleScalar.Abs&lt;U&gt;[]; the initial values of the entries in the new Absolute Sparse
             *            MutableDoubleVector
             * @throws ValueException when values has zero entries
             */
            public Sparse(final DoubleScalar.Abs<U>[] values) throws ValueException
            {
                super(checkNonEmpty(values)[0].getUnit());
                // System.out.println("Created Sparse");
                initialize(values);
            }

            /**
             * For package internal use only.
             * @param values DoubleMatrix1D; the initial values of the entries in the new Absolute Sparse
             *            MutableDoubleVector
             * @param unit U; the unit of the new Absolute Sparse MutableDoubleVector
             */
            protected Sparse(final DoubleMatrix1D values, final U unit)
            {
                super(unit);
                // System.out.println("Created Sparse");
                setCopyOnWrite(true);
                initialize(values); // shallow copy
            }

            /** {@inheritDoc} */
            @Override
            public final DoubleVector.Abs.Sparse<U> immutable()
            {
                setCopyOnWrite(true);
                return new DoubleVector.Abs.Sparse<U>(getVectorSI(), getUnit());
            }

            /** {@inheritDoc} */
            @Override
            public final MutableDoubleVector.Abs.Sparse<U> mutable()
            {
                setCopyOnWrite(true);
                final MutableDoubleVector.Abs.Sparse<U> result =
                        new MutableDoubleVector.Abs.Sparse<U>(getVectorSI(), getUnit());
                result.setCopyOnWrite(true);
                return result;
            }

            /** {@inheritDoc} */
            @Override
            protected final DoubleMatrix1D createMatrix1D(final int size)
            {
                return new SparseDoubleMatrix1D(size);
            }

            /** {@inheritDoc} */
            @Override
            public final MutableDoubleVector.Abs.Sparse<U> copy()
            {
                return mutable();
            }

        }

        /** {@inheritDoc} */
        @Override
        public final DoubleScalar.Abs<U> get(final int index) throws ValueException
        {
            return new DoubleScalar.Abs<U>(getInUnit(index, getUnit()), getUnit());
        }

        /**
         * Increment the value by the supplied value and return the result.
         * @param increment DoubleVector.Rel&lt;U&gt;; amount by which the value is incremented
         * @return MutableDoubleVector.Abs&lt;U&gt;
         * @throws ValueException when the size of increment is not identical to the size of this
         */
        public final MutableDoubleVector.Abs<U> incrementBy(final DoubleVector.Rel<U> increment) throws ValueException
        {
            return (MutableDoubleVector.Abs<U>) incrementByImpl(increment);
        }

        /**
         * Decrement the value by the supplied value and return the result.
         * @param decrement DoubleVector.Rel&lt;U&gt;; amount by which the value is decremented
         * @return MutableDoubleVector.Abs&lt;U&gt;
         * @throws ValueException when the size of increment is not identical to the size of this
         */
        public final MutableDoubleVector.Abs<U> decrementBy(final DoubleVector.Rel<U> decrement) throws ValueException
        {
            return (MutableDoubleVector.Abs<U>) decrementByImpl(decrement);
        }

        /**********************************************************************************/
        /********************************** MATH METHODS **********************************/
        /**********************************************************************************/

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> abs()
        {
            assign(DoubleFunctions.abs);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> acos()
        {
            assign(DoubleFunctions.acos);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> asin()
        {
            assign(DoubleFunctions.asin);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> atan()
        {
            assign(DoubleFunctions.atan);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> cbrt()
        {
            assign(DoubleMathFunctionsImpl.cbrt);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> ceil()
        {
            assign(DoubleFunctions.ceil);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> cos()
        {
            assign(DoubleFunctions.cos);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> cosh()
        {
            assign(DoubleMathFunctionsImpl.cosh);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> exp()
        {
            assign(DoubleFunctions.exp);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> expm1()
        {
            assign(DoubleMathFunctionsImpl.expm1);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> floor()
        {
            assign(DoubleFunctions.floor);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> log()
        {
            assign(DoubleFunctions.log);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> log10()
        {
            assign(DoubleMathFunctionsImpl.log10);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> log1p()
        {
            assign(DoubleMathFunctionsImpl.log1p);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> pow(final double x)
        {
            assign(DoubleFunctions.pow(x));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> rint()
        {
            assign(DoubleFunctions.rint);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> round()
        {
            assign(DoubleMathFunctionsImpl.round);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> signum()
        {
            assign(DoubleMathFunctionsImpl.signum);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> sin()
        {
            assign(DoubleFunctions.sin);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> sinh()
        {
            assign(DoubleMathFunctionsImpl.sinh);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> sqrt()
        {
            assign(DoubleFunctions.sqrt);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> tan()
        {
            assign(DoubleFunctions.tan);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> tanh()
        {
            assign(DoubleMathFunctionsImpl.tanh);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> toDegrees()
        {
            assign(DoubleMathFunctionsImpl.toDegrees);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> toRadians()
        {
            assign(DoubleMathFunctionsImpl.toRadians);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> inv()
        {
            assign(DoubleFunctions.inv);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> multiplyBy(final double constant)
        {
            assign(DoubleFunctions.mult(constant));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Abs<U> divideBy(final double constant)
        {
            assign(DoubleFunctions.div(constant));
            return this;
        }

    }

    /**
     * @param <U> Unit
     */
    public abstract static class Rel<U extends Unit<U>> extends MutableDoubleVector<U> implements Relative
    {
        /**  */
        private static final long serialVersionUID = 20150626L;

        /**
         * Construct a new Relative MutableDoubleVector.
         * @param unit U; the unit of the new Relative MutableDoubleVector
         */
        protected Rel(final U unit)
        {
            super(unit);
            // System.out.println("Created Rel");
        }

        /**
         * @param <U> Unit
         */
        public static class Dense<U extends Unit<U>> extends Rel<U> implements DenseData
        {
            /**  */
            private static final long serialVersionUID = 20150626L;

            /**
             * Construct a new Relative Dense MutableDoubleVector.
             * @param values double[]; the initial values of the entries in the new Relative Dense MutableDoubleVector
             * @param unit U; the unit of the new Relative Dense MutableDoubleVector
             * @throws ValueException when values is null
             */
            public Dense(final double[] values, final U unit) throws ValueException
            {
                super(unit);
                // System.out.println("Created Dense");
                initialize(values);
            }

            /**
             * Construct a new Relative Dense MutableDoubleVector.
             * @param values DoubleScalar.Rel&lt;U&gt;[]; the initial values of the entries in the new Relative Dense
             *            MutableDoubleVector
             * @throws ValueException when values has zero entries
             */
            public Dense(final DoubleScalar.Rel<U>[] values) throws ValueException
            {
                super(checkNonEmpty(values)[0].getUnit());
                // System.out.println("Created Dense");
                initialize(values);
            }

            /**
             * For package internal use only.
             * @param values DoubleMatrix1D; the initial values of the entries in the new Relative Dense
             *            MutableDoubleVector
             * @param unit U; the unit of the new Relative Dense MutableDoubleVector
             */
            protected Dense(final DoubleMatrix1D values, final U unit)
            {
                super(unit);
                // System.out.println("Created Dense");
                setCopyOnWrite(true);
                initialize(values); // shallow copy
            }

            /** {@inheritDoc} */
            @Override
            public final DoubleVector.Rel.Dense<U> immutable()
            {
                setCopyOnWrite(true);
                return new DoubleVector.Rel.Dense<U>(getVectorSI(), getUnit());
            }

            /** {@inheritDoc} */
            @Override
            public final MutableDoubleVector.Rel.Dense<U> mutable()
            {
                setCopyOnWrite(true);
                final MutableDoubleVector.Rel.Dense<U> result =
                        new MutableDoubleVector.Rel.Dense<U>(getVectorSI(), getUnit());
                result.setCopyOnWrite(true);
                return result;
            }

            /** {@inheritDoc} */
            @Override
            protected final DoubleMatrix1D createMatrix1D(final int size)
            {
                return new DenseDoubleMatrix1D(size);
            }

            /** {@inheritDoc} */
            @Override
            public final MutableDoubleVector.Rel.Dense<U> copy()
            {
                return mutable();
            }

        }

        /**
         * @param <U> Unit
         */
        public static class Sparse<U extends Unit<U>> extends Rel<U> implements SparseData
        {
            /**  */
            private static final long serialVersionUID = 20150626L;

            /**
             * Construct a new Relative Sparse MutableDoubleVector.
             * @param values double[]; the initial values of the entries in the new Relative Sparse MutableDoubleVector
             * @param unit U; the unit of the new Relative Sparse MutableDoubleVector
             * @throws ValueException when values is null
             */
            public Sparse(final double[] values, final U unit) throws ValueException
            {
                super(unit);
                // System.out.println("Created Sparse");
                initialize(values);
            }

            /**
             * Construct a new Relative Sparse MutableDoubleVector.
             * @param values DoubleScalar.Rel&lt;U&gt;[]; the initial values of the entries in the new Relative Sparse
             *            MutableDoubleVector
             * @throws ValueException when values has zero entries
             */
            public Sparse(final DoubleScalar.Rel<U>[] values) throws ValueException
            {
                super(checkNonEmpty(values)[0].getUnit());
                // System.out.println("Created Sparse");
                initialize(values);
            }

            /**
             * For package internal use only.
             * @param values DoubleMatrix1D; the initial values of the entries in the new Relative Sparse
             *            MutableDoubleVector
             * @param unit U; the unit of the new Relative Sparse MutableDoubleVector
             */
            protected Sparse(final DoubleMatrix1D values, final U unit)
            {
                super(unit);
                // System.out.println("Created Sparse");
                setCopyOnWrite(true);
                initialize(values); // shallow copy
            }

            /** {@inheritDoc} */
            @Override
            public final DoubleVector.Rel.Sparse<U> immutable()
            {
                setCopyOnWrite(true);
                return new DoubleVector.Rel.Sparse<U>(getVectorSI(), getUnit());
            }

            /** {@inheritDoc} */
            @Override
            public final MutableDoubleVector.Rel.Sparse<U> mutable()
            {
                setCopyOnWrite(true);
                final MutableDoubleVector.Rel.Sparse<U> result =
                        new MutableDoubleVector.Rel.Sparse<U>(getVectorSI(), getUnit());
                result.setCopyOnWrite(true);
                return result;
            }

            /** {@inheritDoc} */
            @Override
            protected final DoubleMatrix1D createMatrix1D(final int size)
            {
                return new SparseDoubleMatrix1D(size);
            }

            /** {@inheritDoc} */
            @Override
            public final MutableDoubleVector.Rel.Sparse<U> copy()
            {
                return mutable();
            }

        }

        /** {@inheritDoc} */
        @Override
        public final DoubleScalar.Rel<U> get(final int index) throws ValueException
        {
            return new DoubleScalar.Rel<U>(getInUnit(index, getUnit()), getUnit());
        }

        /**
         * Increment the value by the supplied value and return the result.
         * @param increment DoubleVector.Rel&lt;U&gt;; amount by which the value is incremented
         * @return MutableDoubleVector.Rel&lt;U&gt;
         * @throws ValueException when the size of increment is not identical to the size of this
         */
        public final MutableDoubleVector.Rel<U> incrementBy(final DoubleVector.Rel<U> increment) throws ValueException
        {
            return (MutableDoubleVector.Rel<U>) incrementByImpl(increment);
        }

        /**
         * Decrement the value by the supplied value and return the result.
         * @param decrement DoubleVector.Rel&lt;U&gt;; amount by which the value is decremented
         * @return MutableDoubleVector.Rel&lt;U&gt;
         * @throws ValueException when the size of increment is not identical to the size of this
         */
        public final MutableDoubleVector.Rel<U> decrementBy(final DoubleVector.Rel<U> decrement) throws ValueException
        {
            return (MutableDoubleVector.Rel<U>) decrementByImpl(decrement);
        }

        /**********************************************************************************/
        /********************************** MATH METHODS **********************************/
        /**********************************************************************************/

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> abs()
        {
            assign(DoubleFunctions.abs);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> acos()
        {
            assign(DoubleFunctions.acos);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> asin()
        {
            assign(DoubleFunctions.asin);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> atan()
        {
            assign(DoubleFunctions.atan);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> cbrt()
        {
            assign(DoubleMathFunctionsImpl.cbrt);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> ceil()
        {
            assign(DoubleFunctions.ceil);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> cos()
        {
            assign(DoubleFunctions.cos);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> cosh()
        {
            assign(DoubleMathFunctionsImpl.cosh);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> exp()
        {
            assign(DoubleFunctions.exp);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> expm1()
        {
            assign(DoubleMathFunctionsImpl.expm1);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> floor()
        {
            assign(DoubleFunctions.floor);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> log()
        {
            assign(DoubleFunctions.log);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> log10()
        {
            assign(DoubleMathFunctionsImpl.log10);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> log1p()
        {
            assign(DoubleMathFunctionsImpl.log1p);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> pow(final double x)
        {
            assign(DoubleFunctions.pow(x));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> rint()
        {
            assign(DoubleFunctions.rint);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> round()
        {
            assign(DoubleMathFunctionsImpl.round);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> signum()
        {
            assign(DoubleMathFunctionsImpl.signum);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> sin()
        {
            assign(DoubleFunctions.sin);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> sinh()
        {
            assign(DoubleMathFunctionsImpl.sinh);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> sqrt()
        {
            assign(DoubleFunctions.sqrt);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> tan()
        {
            assign(DoubleFunctions.tan);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> tanh()
        {
            assign(DoubleMathFunctionsImpl.tanh);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> toDegrees()
        {
            assign(DoubleMathFunctionsImpl.toDegrees);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> toRadians()
        {
            assign(DoubleMathFunctionsImpl.toRadians);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> inv()
        {
            assign(DoubleFunctions.inv);
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> multiplyBy(final double constant)
        {
            assign(DoubleFunctions.mult(constant));
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public final MutableDoubleVector.Rel<U> divideBy(final double constant)
        {
            assign(DoubleFunctions.div(constant));
            return this;
        }

    }

    /**
     * Make (immutable) DoubleVector equivalent for any type of MutableDoubleVector.
     * @return DoubleVector&lt;U&gt;; immutable version of this DoubleVector
     */
    public abstract DoubleVector<U> immutable();

    /**
     * Check the copyOnWrite flag and, if it is set, make a deep copy of the data and clear the flag.
     */
    protected final void checkCopyOnWrite()
    {
        if (isCopyOnWrite())
        {
            // System.out.println("copyOnWrite is set: Copying data");
            deepCopyData();
            setCopyOnWrite(false);
        }
    }

    /** {@inheritDoc} */
    @Override
    public final void setSI(final int index, final double valueSI) throws ValueException
    {
        checkIndex(index);
        checkCopyOnWrite();
        safeSet(index, valueSI);
    }

    /** {@inheritDoc} */
    @Override
    public final void set(final int index, final DoubleScalar<U> value) throws ValueException
    {
        setSI(index, value.getSI());
    }

    /** {@inheritDoc} */
    @Override
    public final void setInUnit(final int index, final double value, final U valueUnit) throws ValueException
    {
        setSI(index, ValueUtil.expressAsSIUnit(value, valueUnit));
    }

    /**
     * Execute a function on a cell by cell basis.
     * @param d cern.colt.function.tdouble.DoubleFunction; the function to apply
     */
    public final void assign(final cern.colt.function.tdouble.DoubleFunction d)
    {
        checkCopyOnWrite();
        getVectorSI().assign(d);
    }

    /**********************************************************************************/
    /******************************* NON-STATIC METHODS *******************************/
    /**********************************************************************************/

    /**
     * Increment the values in this MutableDoubleVector by the corresponding values in a DoubleVector.
     * @param increment DoubleVector&lt;U&gt;; the values by which to increment the corresponding values in this
     *            MutableDoubleVector
     * @return MutableDoubleVector&lt;U&gt;; this modified MutableDoubleVector
     * @throws ValueException when the vectors do not have the same size
     */
    private MutableDoubleVector<U> incrementValueByValue(final DoubleVector<U> increment) throws ValueException
    {
        checkSizeAndCopyOnWrite(increment);
        for (int index = size(); --index >= 0;)
        {
            safeSet(index, safeGet(index) + increment.safeGet(index));
        }
        return this;
    }

    /**
     * Decrement the values in this MutableDoubleVector by the corresponding values in a DoubleVector.
     * @param decrement DoubleVector&lt;U&gt;; the values by which to decrement the corresponding values in this
     *            MutableDoubleVector
     * @return MutableDoubleVector&lt;U&gt;; this modified MutableDoubleVector
     * @throws ValueException when the vectors do not have the same size
     */
    private MutableDoubleVector<U> decrementValueByValue(final DoubleVector<U> decrement) throws ValueException
    {
        checkSizeAndCopyOnWrite(decrement);
        for (int index = size(); --index >= 0;)
        {
            safeSet(index, safeGet(index) - decrement.safeGet(index));
        }
        return this;
    }

    /**
     * Increment the values in this MutableDoubleVector by the corresponding values in a Relative DoubleVector. <br>
     * Only Relative values are allowed; adding an Absolute value to an Absolute value is not allowed. Adding an
     * Absolute value to an existing Relative value would require the result to become Absolute, which is a type change
     * that is impossible. For that operation use a static method.
     * @param rel DoubleVector.Rel&lt;U&gt;; the Relative DoubleVector
     * @return MutableDoubleVector&lt;U&gt;; this modified MutableDoubleVector
     * @throws ValueException when the vectors do not have the same size
     */
    protected final MutableDoubleVector<U> incrementByImpl(final DoubleVector.Rel<U> rel) throws ValueException
    {
        return incrementValueByValue(rel);
    }

    /**
     * Decrement the corresponding values of this Relative DoubleVector from the values of this MutableDoubleVector. <br>
     * Only Relative values are allowed; subtracting an Absolute value from a Relative value is not allowed. Subtracting
     * an Absolute value from an existing Absolute value would require the result to become Relative, which is a type
     * change that is impossible. For that operation use a static method.
     * @param rel DoubleVector.Rel&lt;U&gt;; the Relative DoubleVector
     * @return MutableDoubleVector&lt;U&gt;; this modified MutableDoubleVector
     * @throws ValueException when the vectors do not have the same size
     */
    protected final MutableDoubleVector<U> decrementByImpl(final DoubleVector.Rel<U> rel) throws ValueException
    {
        return decrementValueByValue(rel);
    }

    // FIXME It makes no sense to subtract an Absolute from a Relative
    /**
     * Decrement the values in this Relative MutableDoubleVector by the corresponding values in an Absolute
     * DoubleVector.
     * @param abs DoubleVector.Abs&lt;U&gt;; the Absolute DoubleVector
     * @return MutableDoubleVector.Rel&lt;U&gt;; this modified Relative MutableDoubleVector
     * @throws ValueException when the vectors do not have the same size
     */
    protected final MutableDoubleVector.Rel<U> decrementBy(final DoubleVector.Abs<U> abs) throws ValueException
    {
        return (MutableDoubleVector.Rel<U>) decrementValueByValue(abs);
    }

    /**
     * Scale the values in this MutableDoubleVector by the corresponding values in a DoubleVector.
     * @param factor DoubleVector&lt;?&gt;; contains the values by which to scale the corresponding values in this
     *            MutableDoubleVector
     * @throws ValueException when the vectors do not have the same size
     */
    protected final void scaleValueByValue(final DoubleVector<?> factor) throws ValueException
    {
        checkSizeAndCopyOnWrite(factor);
        for (int index = size(); --index >= 0;)
        {
            safeSet(index, safeGet(index) * factor.safeGet(index));
        }
    }

    /**
     * Scale the values in this MutableDoubleVector by the corresponding values in a double array.
     * @param factor double[]; contains the values by which to scale the corresponding values in this
     *            MutableDoubleVector
     * @return MutableDoubleVector&lt;U&gt;; this modified MutableDoubleVector
     * @throws ValueException when the vector and the array do not have the same size
     */
    protected final MutableDoubleVector<U> scaleValueByValue(final double[] factor) throws ValueException
    {
        checkSizeAndCopyOnWrite(factor);
        for (int index = size(); --index >= 0;)
        {
            safeSet(index, safeGet(index) * factor[index]);
        }
        return this;
    }

    /**
     * Check sizes and copy the data if the copyOnWrite flag is set.
     * @param other DoubleVector&lt;?&gt;; partner for the size check
     * @throws ValueException when the vectors do not have the same size
     */
    private void checkSizeAndCopyOnWrite(final DoubleVector<?> other) throws ValueException
    {
        checkSize(other);
        checkCopyOnWrite();
    }

    /**
     * Check sizes and copy the data if the copyOnWrite flag is set.
     * @param other double[]; partner for the size check
     * @throws ValueException when the vectors do not have the same size
     */
    private void checkSizeAndCopyOnWrite(final double[] other) throws ValueException
    {
        checkSize(other);
        checkCopyOnWrite();
    }

}
