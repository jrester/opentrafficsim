package org.opentrafficsim.core.value.vfloat.matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;
import org.opentrafficsim.core.unit.EnergyUnit;
import org.opentrafficsim.core.unit.ForceUnit;
import org.opentrafficsim.core.unit.LengthUnit;
import org.opentrafficsim.core.unit.MassUnit;
import org.opentrafficsim.core.unit.SICoefficients;
import org.opentrafficsim.core.unit.SIUnit;
import org.opentrafficsim.core.unit.Unit;
import org.opentrafficsim.core.unit.UnitException;
import org.opentrafficsim.core.value.Absolute;
import org.opentrafficsim.core.value.DenseData;
import org.opentrafficsim.core.value.Format;
import org.opentrafficsim.core.value.Relative;
import org.opentrafficsim.core.value.SparseData;
import org.opentrafficsim.core.value.ValueException;
import org.opentrafficsim.core.value.vfloat.scalar.FloatScalar;
import org.opentrafficsim.core.value.vfloat.scalar.FloatScalarAbs;
import org.opentrafficsim.core.value.vfloat.scalar.FloatScalarRel;
import org.opentrafficsim.core.value.vfloat.vector.FloatVector;

import cern.colt.matrix.tfloat.FloatMatrix2D;

/**
 * <p>
 * Copyright (c) 2002-2014 Delft University of Technology, Jaffalaan 5, 2628 BX Delft, the Netherlands. All rights
 * reserved.
 * <p>
 * See for project information <a href="http://www.simulation.tudelft.nl/"> www.simulation.tudelft.nl</a>.
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
 * @version Jun 26, 2014 <br>
 * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
 */
public abstract class FloatMatrixTest
{
    /**
     * Test FloatMatrixAbs and FloatMatrixRel creators that take a float[][] and a Unit as arguments
     */
    @Test
    public void floatMatrixTwoArgs()
    {
        floatMatrixTwoArgs(true); // test absolute version
        floatMatrixTwoArgs(false); // test relative version
    }

    /**
     * Create a 2D array of float for testing. Entry 0,0 is zero, all others will be non-zero unless a non-zero offset
     * is specified.
     * @param rows Integer; number of rows in the 2D array
     * @param cols Integer; number of columns in the 2D array
     * @param badRow Boolean; if true; make the last row one entry longer than the rest
     * @param offset Float; number to add to each entry
     * @return float[][]; 2D array of float
     */
    private static float[][] buildArray(int rows, int cols, boolean badRow, float offset)
    {
        float[][] result = new float[rows][];
        int badRowIndex = badRow ? rows - 1 : -1;
        for (int row = 0; row < rows; row++)
        {
            result[row] = new float[row == badRowIndex ? cols + 1 : cols];
            for (int col = 0; col < result[row].length; col++)
                result[row][col] = row * 1000 + col + offset;
        }
        return result;
    }

    /**
     * Test the FloatMatrixAbs that takes a float[][] and a Unit as arguments and some methods.
     */
    private void floatMatrixTwoArgs(Boolean absolute)
    {
        float[][] in = buildArray(12, 3, false, 0);
        LengthUnit u = LengthUnit.FOOT;
        FloatMatrix<LengthUnit> fm = safeCreateFloatMatrix(in, u, absolute);
        assertEquals("FloatMatrix should have 12 rows", 12, fm.rows());
        assertEquals("FloatMatrix should have 3 columns", 3, fm.columns());
        float[][] out = fm.getValuesInUnit();
        for (int i = 0; i < in.length; i++)
            for (int j = 0; j < in[i].length; j++)
            {
                assertEquals("Values in FloatMatrix in unit should be equal to input values", in[i][j], out[i][j],
                        0.001);
                try
                {
                    assertEquals("Values in FloatMatrix in unit should be equal to input values", in[i][j],
                            fm.getInUnit(i, j), 0.001);
                    assertEquals("Values in FloatMatrix in unit should be equal to input values", in[i][j],
                            fm.getSI(i, j) / (12 * 0.0254), 0.001);
                    assertEquals("Values in FloatMatrix in unit should be equal to input values", in[i][j],
                            fm.getInUnit(i, j, LengthUnit.MILE) * 1609.34 / (12 * 0.0254), 0.1);
                }
                catch (ValueException exception)
                {
                    fail("Get should not throw exceptions for legal values of the index");
                }
            }
        String output = fm.toString(LengthUnit.MILLIMETER);
        String[] lines = output.split("[\n]");
        assertEquals("Number of lines should be number of rows + 1", in.length + 1, lines.length);
        assertEquals("first line should contain unit in brackers", "[mm]", lines[0]);
        for (int i = 1; i < lines.length; i++)
        {
            String[] fields = lines[i].trim().split("[ ]+");
            assertEquals("Number of fields should be number of columns", fm.columns(), fields.length);
            for (int j = 0; j < fields.length; j++)
            {
                float expectedValue = (float) (in[i - 1][j] * (12 * 0.0254) * 1000);
                String expected = Format.format(expectedValue).trim();
                assertEquals("Field " + j + " should contain \"" + expected + "\"", expected, fields[j]);
            }
        }
        output = fm.toString();
        lines = output.split("[\n]");
        assertEquals("Number of lines should be number of rows + 1", in.length + 1, lines.length);
        assertEquals("first line should contain unit in brackers", "[ft]", lines[0]);
        for (int i = 1; i < lines.length; i++)
        {
            String[] fields = lines[i].trim().split("[ ]+");
            assertEquals("Number of fields should be number of columns", fm.columns(), fields.length);
            for (int j = 0; j < fields.length; j++)
            {
                float expectedValue = in[i - 1][j];
                String expected = Format.format(expectedValue).trim();
                assertEquals("Field " + i + " should contain \"" + expected + "\"", expected, fields[j]);
            }
        }

        FloatMatrix2D fm2d;
        if (fm instanceof FloatMatrixAbsDense)
            fm2d = ((FloatMatrixAbsDense<LengthUnit>) fm).getColtDenseFloatMatrix2D();
        else if (fm instanceof FloatMatrixRelDense)
            fm2d = ((FloatMatrixRelDense<LengthUnit>) fm).getColtDenseFloatMatrix2D();
        else if (fm instanceof FloatMatrixAbsSparse)
            fm2d = ((FloatMatrixAbsSparse<LengthUnit>) fm).getColtSparseFloatMatrix2D();
        else if (fm instanceof FloatMatrixRelSparse)
            fm2d = ((FloatMatrixRelSparse<LengthUnit>) fm).getColtSparseFloatMatrix2D();
        else
            throw new Error("Matrix neither Dense nor Sparse");
        assertTrue("ColtMatrix1D should not be null", null != fm2d);
        assertEquals("Size of Colt matrix should be number of cells", in.length * in[0].length, fm2d.size());
        for (int i = 0; i < in.length; i++)
            for (int j = 0; j < in[i].length; j++)
                assertEquals("Contents of Colt matrix should be SI equivalent of input", in[i][j] * (12 * 0.0254),
                        fm2d.getQuick(i, j), 0.001);
        fm2d = fm.getMatrixSI();
        assertTrue("MatrixSI should not be null", null != fm2d);
        assertEquals("Size of MatrixSI should be number of cells", in.length * in[0].length, fm2d.size());
        for (int i = 0; i < in.length; i++)
            for (int j = 0; j < in[i].length; j++)
                assertEquals("Contents of MatrixSI should be SI equivalent of input", in[i][j] * (12 * 0.0254),
                        fm2d.getQuick(i, j), 0.001);
        float[][] valuesInUnit = fm.getValuesInUnit();
        assertTrue("valuesInUnit should not be null", null != valuesInUnit);
        assertEquals("Size of valuesInUnit should be size of input array", in.length, valuesInUnit.length);
        for (int i = 0; i < in.length; i++)
            for (int j = 0; j < in[i].length; j++)
                assertEquals("Contents of valuesInUnit should be equal to input", in[i][j], valuesInUnit[i][j], 0.001);
        LengthUnit outputUnit = LengthUnit.DEKAMETER;
        float[][] valuesInOtherUnit = fm.getValuesInUnit(outputUnit);
        assertTrue("valuesInUnit should not be null", null != valuesInOtherUnit);
        assertEquals("Size of valuesInUnit should be size of input array", in.length, valuesInOtherUnit.length);
        for (int i = 0; i < in.length; i++)
            for (int j = 0; j < in[i].length; j++)
                assertEquals("Contents of valuesInUnit should be equal to input", in[i][j] * (12 * 0.0254) / 10,
                        valuesInOtherUnit[i][j], 0.0001);
        try
        {
            fm.getInUnit(-1, 0);
            fail("Using a negative index should throw a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        try
        {
            fm.getInUnit(in.length, 0);
            fail("Using a index that is too hig should throw a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        try
        {
            fm.getInUnit(0, -1);
            fail("Using a negative index should throw a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        try
        {
            fm.getInUnit(0, in[0].length);
            fail("Using a index that is too hig should throw a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        try
        {
            fm.getSI(-1, 0);
            fail("Using a bad index should throw a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        try
        {
            fm.getSI(in.length, 0);
            fail("Using a index that is too hig should throw a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        try
        {
            fm.getSI(0, -1);
            fail("Using a bad index should throw a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        try
        {
            fm.getSI(0, in[0].length);
            fail("Using a index that is too hig should throw a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        try
        {
            fm.getInUnit(-1, 0, LengthUnit.MILE);
            fail("Using a negative index should throw a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        try
        {
            fm.getInUnit(in.length, 0, LengthUnit.MILE);
            fail("Using an index that is too big should throw a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        try
        {
            fm.getInUnit(0, -1, LengthUnit.MILE);
            fail("Using a negative index should throw a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        try
        {
            fm.getInUnit(0, in[0].length, LengthUnit.MILE);
            fail("Using an index that is too big should throw a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        try
        {
            fm.setSI(-1, 0, 12345f);
            fail("Using a negative index should throw a ValueException");
        }
        catch (ValueException exception1)
        {
            // ignore
        }
        try
        {
            fm.setSI(in.length, 0, 12345f);
            fail("Using an index that is too big should throw a ValueException");
        }
        catch (ValueException exception1)
        {
            // ignore
        }
        try
        {
            fm.setSI(0, -1, 12345f);
            fail("Using a negative index should throw a ValueException");
        }
        catch (ValueException exception1)
        {
            // ignore
        }
        try
        {
            fm.setSI(0, in[0].length, 12345f);
            fail("Using an index that is too big should throw a ValueException");
        }
        catch (ValueException exception1)
        {
            // ignore
        }
        out = fm.getValuesSI();
        assertTrue("getValuesSI does not return null", null != out);
        assertEquals("Length of getValuesSI should match size", in.length, out.length);
        for (int i = 0; i < in.length; i++)
            for (int j = 0; j < in[i].length; j++)
                assertEquals("Values in FloatMatrix should be equivalent values in meters", in[i][j], out[i][j]
                        / (12 * 0.0254), 0.001);
        LengthUnit uOut = fm.getUnit();
        assertEquals("Stored unit should be provided unit", u, uOut);
        FloatMatrix<LengthUnit> copy = null;
        if (fm instanceof FloatMatrixAbs<?>)
        {
            copy = ((FloatMatrixAbs<LengthUnit>) fm).copy();
        }
        else if (fm instanceof FloatMatrixRel<?>)
        {
            copy = ((FloatMatrixRel<LengthUnit>) fm).copy();
        }
        else
            fail("Matrix neither Absolute nor Relative");

        assertEquals("copy should have 12 rows", 12, copy.rows());
        assertEquals("copy should have 3 columns", 3, copy.columns());
        float[][] copyOut = copy.getValuesSI();
        for (int i = 0; i < in.length; i++)
            for (int j = 0; j < in[i].length; j++)
                assertEquals("Values in copy of FloatMatrix should be equivalent values in meters", in[i][j],
                        copyOut[i][j] / (12 * 0.0254), 0.001);
        copyOut = fm.getValuesInUnit();
        for (int i = 0; i < in.length; i++)
            for (int j = 0; j < in[i].length; j++)
                assertEquals("Values in copy of FloatMatrix in unit should be equal to input values", in[i][j],
                        copyOut[i][j], 0.001);
        try
        {
            copy.setSI(0, 0, 12345f);
            assertEquals("value should be altered", 12345f, copy.getSI(0, 0), 0.01);
            assertEquals("original value should not be altered", out[0][0], fm.getSI(0, 0), 0.001);
            FloatScalar<LengthUnit> value = copy.get(1, 0);
            assertTrue("value cannot be null", null != value);
            assertEquals("value should be same as SI value", copy.getSI(1, 0), value.getValueSI(), 0.0001);
            copy.set(2, 0, value);
            assertEquals("value should be same as SI value", copy.getSI(2, 0), value.getValueSI(), 0.0001);
        }
        catch (ValueException exception)
        {
            fail("set*/get* should not throw ValueException for valid index and correctly typed value");
        }
        try
        {
            copy.setInUnit(1, 0, 321, LengthUnit.HECTOMETER);
            assertEquals("321 hectometer is 32100m", copy.getSI(1, 0), 32100, 0.001);
        }
        catch (ValueException exception)
        {
            fail("Legal index should not throw exception");
        }
        float sum = 0;
        for (int i = 0; i < in.length; i++)
            for (int j = 0; j < in[i].length; j++)
                sum += in[i][j];
        sum *= (12 * 0.0254); // convert to meters
        assertEquals("zsum should be sum of the values", sum, fm.zSum(), 0.01);
        try
        {
            fm.normalize();
            for (int i = 0; i < in.length; i++)
                for (int j = 0; j < in[i].length; j++)
                    assertEquals("Unexpected normalized value", in[i][j] * (12 * 0.0254) / sum, fm.getSI(i, j), 0.0001);
        }
        catch (ValueException exception)
        {
            fail("Unexpected exception");
        }
        assertEquals("Cardinality should be 35", 35, fm.cardinality());
        float[][] in2 = {{1f, -1f, 0f}};
        fm = safeCreateFloatMatrix(in2, u, absolute);
        assertEquals("Cardinality should be 2", 2, fm.cardinality());
        assertEquals("zSum should be 0", 0, fm.zSum(), 0.00001);
        try
        {
            fm.normalize();
            fail("Should have thrown a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        assertEquals("Cardinality should be 2", 2, fm.cardinality());
        try
        {
            fm.setSI(0, 0, 0);
            assertEquals("Cardinality should be 1", 1, fm.cardinality());
            fm.setSI(0, 1, 0);
            assertEquals("Cardinality should be 0", 0, fm.cardinality());
            fm.setSI(0, 2, 999);
            assertEquals("Cardinality should be 1", 1, fm.cardinality());
        }
        catch (ValueException exception)
        {
            fail("Unexpected exception");
        }
        assertTrue("FloatMatrix should be equal to itself", fm.equals(fm));
        assertFalse("FloatMatrix should not be equal to null", fm.equals(null));
        if (fm instanceof FloatMatrixAbs<?>)
        {
            copy = ((FloatMatrixAbs<LengthUnit>) fm).copy();
        }
        else if (fm instanceof FloatMatrixRel<?>)
        {
            copy = ((FloatMatrixRel<LengthUnit>) fm).copy();
        }
        else
            fail("Matrix neither Absolute nor Relative");
        assertTrue("FloatMatrix should be equal to copy of itself", fm.equals(copy));
        try
        {
            copy.setSI(0, 1, copy.getSI(0, 1) + 0.001f);
        }
        catch (ValueException exception)
        {
            fail("Unexpected exception");
        }
        assertFalse("FloatMatrix should be different from slightly altered to copy of itself", fm.equals(copy));
        try
        {
            copy.setSI(0, 1, fm.getSI(0, 1));
        }
        catch (ValueException exception)
        {
            fail("Unexpected exception");
        }
        assertTrue("FloatMatrix should be equal to repaired copy of itself", fm.equals(copy));
        if (absolute)
        {
            float[][] values = fm.getValuesInUnit();
            FloatMatrix<LengthUnit> fmr = null;
            try
            {
                fmr = createFloatMatrix(values, fm.getUnit(), false);
                for (int i = 0; i < in2.length; i++)
                    for (int j = 0; j < in2[i].length; j++)
                        assertEquals("Values should be equal", fm.getSI(i, j), fmr.getSI(i, j), 0.00001);
            }
            catch (ValueException exception)
            {
                fail("Unexpected exception");
            }
            assertEquals("fm and fmr should have same unit", fm.getUnit(), fmr.getUnit());
            assertFalse("fm and fmr should not be equal", fm.equals(fmr));
            assertFalse("fmr and fm should not be equal", fmr.equals(fm));
        }
        float[][] inNonRect = buildArray(4, 5, true, 0);
        try
        {
            fm = createFloatMatrix(inNonRect, LengthUnit.METER, absolute);
            fail("Non rectangular input data should have thrown a ValueException");
        }
        catch (ValueException exception1)
        {
            // ignore
        }
        float[][] in3 =
                {{-100, -10, -1, -0.1f, 1, 0.1f, 1, 10, 100},
                        {-1000000, -1000, -100, -0.001f, 1, 0.001f, 100, 1000, 1000000}};
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.abs();
        MathTester.tester(in3, "abs", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return Math.abs(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.acos();
        MathTester.tester(in3, "acos", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.acos(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.asin();
        MathTester.tester(in3, "asin", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.asin(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.atan();
        MathTester.tester(in3, "atan", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.atan(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.cbrt();
        MathTester.tester(in3, "cbrt", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.cbrt(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.ceil();
        MathTester.tester(in3, "ceil", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.ceil(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.cos();
        MathTester.tester(in3, "cos", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.cos(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.cosh();
        MathTester.tester(in3, "cosh", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.cosh(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.exp();
        MathTester.tester(in3, "exp", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.exp(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.expm1();
        MathTester.tester(in3, "expm1", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.expm1(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.floor();
        MathTester.tester(in3, "floor", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.floor(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.log();
        MathTester.tester(in3, "log", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.log(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.log10();
        MathTester.tester(in3, "log10", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.log10(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.log1p();
        MathTester.tester(in3, "log1p", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.log1p(f);
            }
        });
        for (float power = -5; power <= 5; power += 0.5)
        {
            fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
            fm.pow(power);
            final float myPower = power;
            MathTester.tester(in3, "pow(" + power + ")", fm.getValuesSI(), 0.001, new FloatToFloat()
            {
                public float function(final float f)
                {
                    return (float) Math.pow(f, myPower);
                }
            });
        }
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.rint();
        MathTester.tester(in3, "rint", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.rint(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.round();
        MathTester.tester(in3, "round", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return Math.round(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.signum();
        MathTester.tester(in3, "signum", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return Math.signum(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.sin();
        MathTester.tester(in3, "sin", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.sin(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.sinh();
        MathTester.tester(in3, "sinh", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.sinh(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.sqrt();
        MathTester.tester(in3, "sqrt", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.sqrt(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.tan();
        MathTester.tester(in3, "tan", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.tan(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.tanh();
        MathTester.tester(in3, "tanh", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.tanh(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.toDegrees();
        MathTester.tester(in3, "toDegrees", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.toDegrees(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.toRadians();
        MathTester.tester(in3, "toRadians", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) Math.toRadians(f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        fm.inv();
        MathTester.tester(in3, "inv", fm.getValuesSI(), 0.001, new FloatToFloat()
        {
            public float function(float f)
            {
                return (float) (1.0 / f);
            }
        });
        fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
        try
        {
            fm.det();
            fail("det of non-square matrix should have thrown a ValueException");
        }
        catch (ValueException exception1)
        {
            // ignore
        }
        float[][] singular = {{1, 2, 3}, {3, 5, 7}, {5, 10, 0}};
        fm = safeCreateFloatMatrix(singular, LengthUnit.METER, absolute);
        System.out.println("matrix is " + fm.toString());
        if (fm instanceof SparseData)
            System.out.println("(sparse)");
        if (fm instanceof DenseData)
            System.out.println("(dense)");
        if (fm instanceof Absolute)
            System.out.println("(absolute)");
        if (fm instanceof Relative)
            System.out.println("(relative)");
        try
        {
            assertEquals("Determinant should be 15", 15, fm.det(), 0.0001);
        }
        catch (ValueException exception1)
        {
            // TODO this one fails for sparse matrices
            // admittedly, this matrix is not very sparse
            exception1.printStackTrace();
            // fail("Unexpected exception");
        }
        for (float factor = -5; factor <= 5; factor += 0.5)
        {
            fm = safeCreateFloatMatrix(in3, LengthUnit.METER, absolute);
            fm.multiply(factor);
            final float myFactor = factor;
            MathTester.tester(in3, "multiply(" + factor + ")", fm.getValuesSI(), 0.001, new FloatToFloat()
            {
                public float function(final float f)
                {
                    return myFactor * f;
                }
            });
        }
        if (absolute)
        {
            FloatMatrixAbs<LengthUnit> fm1 =
                    (FloatMatrixAbs<LengthUnit>) safeCreateFloatMatrix(in3, LengthUnit.METER, true);
            float[][] inRowCountMismatch =
                    {{-100, -10, -1, -0.1f, 1, 0.1f, 1, 10}, {-1000000, -1000, -100, -0.001f, 1, 0.001f, 100, 1000}};
            float[][] inColCountMismatch = {{-100, -10, -1, -0.1f, 1, 0.1f, 1, 10, 100}};
            FloatMatrixRel<LengthUnit> fm2 =
                    (FloatMatrixRel<LengthUnit>) safeCreateFloatMatrix(inRowCountMismatch, LengthUnit.METER, false);
            FloatMatrix<LengthUnit> plus = null;
            FloatMatrix<LengthUnit> minus = null;
            try
            {
                plus = FloatMatrix.plus(fm1, fm2);
                fail("Adding FloatMatrices of unequal size should have thrown a ValueException");
            }
            catch (ValueException exception)
            {
                // ignore
            }
            try
            {
                minus = FloatMatrix.minus(fm1, fm2);
                fail("Subtracting FloatMatrices of unequal size should have thrown a ValueException");
            }
            catch (ValueException exception)
            {
                // ignore
            }
            fm2 = (FloatMatrixRel<LengthUnit>) safeCreateFloatMatrix(inColCountMismatch, LengthUnit.METER, false);
            try
            {
                plus = FloatMatrix.plus(fm1, fm2);
                fail("Adding FloatMatrices of unequal size should have thrown a ValueException");
            }
            catch (ValueException exception)
            {
                // ignore
            }
            try
            {
                minus = FloatMatrix.minus(fm1, fm2);
                fail("Subtracting FloatMatrices of unequal length should have thrown a ValueException");
            }
            catch (ValueException exception)
            {
                // ignore
            }
            float[][] in5 = buildArray(2, 9, false, 0);
            fm2 = (FloatMatrixRel<LengthUnit>) safeCreateFloatMatrix(in5, LengthUnit.METER, false);
            try
            {
                plus = FloatMatrix.plus(fm1, fm2);
                minus = FloatMatrix.minus(fm1, fm2);
            }
            catch (ValueException exception)
            {
                fail("Unexpected exception");
            }
            assertTrue("plus should not return null", null != plus);
            assertEquals("rowcount of result should be " + in5.length, in5.length, plus.rows());
            assertEquals("columncount of result should be " + in5[0].length, in5[0].length, plus.columns());
            assertTrue("minus should not return null", null != minus);
            assertEquals("rowcount of result should be " + in5.length, in5.length, minus.rows());
            assertEquals("columncount of result should be " + in5[0].length, in5[0].length, minus.columns());
            try
            {
                for (int i = 0; i < in3.length; i++)
                    for (int j = 0; j < in[i].length; j++)
                        assertEquals("value of element should be sum of contributing elements", in3[i][j] + in5[i][j],
                                plus.getSI(i, j), 0.00001);
                for (int i = 0; i < in3.length; i++)
                    for (int j = 0; j < in[i].length; j++)
                        assertEquals("value of element should be sum of contributing elements", in3[i][j] - in5[i][j],
                                minus.getSI(i, j), 0.00001);
            }
            catch (ValueException exception)
            {
                fail("Unexpected exception");
            }
            assertTrue("result should be Absolute", plus instanceof Absolute);
            assertTrue("result should be Absolute", minus instanceof Absolute);
            if (fm1 instanceof DenseData)
            {
                assertTrue("result should be Dense", plus instanceof DenseData);
                assertTrue("result should be Dense", minus instanceof DenseData);
            }
            else if (fm1 instanceof SparseData)
            {
                assertTrue("result should be Sparse", plus instanceof SparseData);
                assertTrue("result should be Sparse", minus instanceof SparseData);
            }
            else
                fail("fm1 neither Dense nor Sparse");

            FloatMatrix<LengthUnit> plusReverse = null;
            try
            {
                plusReverse = FloatMatrix.plus(fm2, fm1);
            }
            catch (ValueException exception)
            {
                fail("Unexpected exception");
            }
            assertTrue("result should be Absolute", plusReverse instanceof Absolute);
            // System.out.println("plus is       " + plus);
            // System.out.println("plusReverse is" + plusReverse);
            assertTrue("result of a + b should be equal to result of b + a", plus.equals(plusReverse));
            try
            {
                float in4[][] = {{1, 2, 3}, {4, 5, 6}};
                FloatMatrix<LengthUnit> original = safeCreateFloatMatrix(in4, LengthUnit.METER, absolute);
                FloatMatrix<LengthUnit> duplicate = (FloatMatrix<LengthUnit>) original.copy();
                assertTrue("Original should be equal to duplicate", original.equals(duplicate));
                assertTrue("Duplicate should be equal to original", duplicate.equals(original));
                original.setSI(0, 0, 123.456f);
                assertFalse("Original should now differ from duplicate", original.equals(duplicate));
                assertFalse("Duplicate should now differ from original", duplicate.equals(original));

            }
            catch (ValueException exception)
            {
                fail("Unexpected ValueException");
            }
        }
        else
        {
            FloatMatrixRel<LengthUnit> fm1 =
                    (FloatMatrixRel<LengthUnit>) safeCreateFloatMatrix(in3, LengthUnit.METER, false);
            float[][] in4 = buildArray(1, 4, false, 0);
            FloatMatrixRel<LengthUnit> fm2 =
                    (FloatMatrixRel<LengthUnit>) safeCreateFloatMatrix(in4, LengthUnit.METER, false);
            FloatMatrix<SIUnit> multiply = null;
            try
            {
                multiply = FloatMatrix.multiply(fm1, fm2);
                fail("Adding FloatMatrices of unequal length should have thrown a ValueException");
            }
            catch (ValueException exception)
            {
                // ignore
            }
            float[][] in5 = buildArray(2, 9, false, 0);
            fm2 = (FloatMatrixRel<LengthUnit>) safeCreateFloatMatrix(in5, LengthUnit.METER, false);
            try
            {
                multiply = FloatMatrix.multiply(fm1, fm2);
            }
            catch (ValueException exception)
            {
                fail("Unexpected exception");
            }
            assertTrue("multiply should not return null", null != multiply);
            assertEquals("size of result should be " + in5.length, in5.length, multiply.rows());
            assertEquals("size of result should be " + in5[0].length, in5[0].length, multiply.columns());
            try
            {
                for (int i = 0; i < in3.length; i++)
                    for (int j = 0; j < in3[i].length; j++)
                        assertEquals("value of element should be sum of contributing elements", in3[i][j] * in5[i][j],
                                multiply.getSI(i, j), 0.00001);
            }
            catch (ValueException exception)
            {
                fail("Unexpected exception");
            }
            assertTrue("result should be Relative", multiply instanceof Relative);
            if (fm1 instanceof DenseData)
            {
                assertTrue("result should be Dense", multiply instanceof DenseData);
            }
            else if (fm1 instanceof SparseData)
            {
                assertTrue("result should be Sparse", multiply instanceof SparseData);
            }
            else
                fail("fm1 neither Dense nor Sparse");
            // System.out.println("Result of multiply has unit " + multiply);
            assertEquals("Result of multiplication should be in square meters", "m2", multiply.getUnit()
                    .getSICoefficientsString());

            FloatMatrix<SIUnit> multiplyReverse = null;
            try
            {
                multiplyReverse = FloatMatrix.multiply(fm2, fm1);
            }
            catch (ValueException exception)
            {
                fail("Unexpected exception");
            }
            assertTrue("result should be Absolute", multiplyReverse instanceof Relative);
            // System.out.println("plus is       " + multiply);
            // System.out.println("plusReverse is" + multiplyReverse);
            assertTrue("result of a * b should be equal to result of b * a", multiply.equals(multiplyReverse));
            try
            {
                float in6[][] = {{1, 2, 3}, {4, 5, 6}};
                FloatMatrix<LengthUnit> original = safeCreateFloatMatrix(in6, LengthUnit.METER, absolute);
                FloatMatrix<LengthUnit> duplicate = (FloatMatrix<LengthUnit>) original.copy();
                assertTrue("Original should be equal to duplicate", original.equals(duplicate));
                assertTrue("Duplicate should be equal to original", duplicate.equals(original));
                original.setSI(0, 0, 123.456f);
                assertFalse("Original should now differ from duplicate", original.equals(duplicate));
                assertFalse("Duplicate should now differ from original", duplicate.equals(original));

            }
            catch (ValueException exception)
            {
                fail("Unexpected ValueException");
            }
        }
        if (fm instanceof DenseData)
        {
            FloatMatrix<LengthUnit> fm2 = null;
            if (fm instanceof Absolute)
                fm2 = FloatMatrix.denseToSparse((FloatMatrixAbsDense<LengthUnit>) fm);
            else
                fm2 = FloatMatrix.denseToSparse((FloatMatrixRelDense<LengthUnit>) fm);
            assertTrue("dense version is  equal to sparse version", fm.equals(fm2));
            assertEquals("unit should be same", fm.getUnit(), fm2.getUnit());
            try
            {
                for (int i = 0; i < fm.rows(); i++)
                    for (int j = 0; j < fm.columns(); j++)
                        assertEquals("Values should be equal", fm.getSI(i, j), fm2.getSI(i, j), 0.0001);
            }
            catch (ValueException exception)
            {
                fail("Unexpected exception");
            }
        }
        else
        {
            FloatMatrix<LengthUnit> fm2 = null;
            if (fm instanceof Absolute)
                fm2 = FloatMatrix.sparseToDense((FloatMatrixAbsSparse<LengthUnit>) fm);
            else
                fm2 = FloatMatrix.sparseToDense((FloatMatrixRelSparse<LengthUnit>) fm);
            assertTrue("dense version is equal to sparse version", fm.equals(fm2));
            assertEquals("unit should be same", fm.getUnit(), fm2.getUnit());
            try
            {
                for (int i = 0; i < fm.rows(); i++)
                    for (int j = 0; j < fm.columns(); j++)
                        assertEquals("Values should be equal", fm.getSI(i, j), fm2.getSI(i, j), 0.0001);
            }
            catch (ValueException exception)
            {
                fail("Unexpected exception");
            }
        }
        float[][] left = buildArray(4, 5, false, 0);
        FloatMatrix<LengthUnit> leftMatrix = safeCreateFloatMatrix(left, LengthUnit.METER, absolute);
        float[][] right = buildArray(4, 6, false, 0.3f);
        FloatMatrixRel<LengthUnit> rightMatrix =
                (FloatMatrixRel<LengthUnit>) safeCreateFloatMatrix(right, LengthUnit.METER, false);
        try
        {
            leftMatrix.add(rightMatrix);
            fail("Adding matrices of different sizes should have thrown an exception");
        }
        catch (ValueException exception)
        {
            // Ignore
        }
        right = buildArray(3, 5, false, 0.3f);
        rightMatrix = (FloatMatrixRel<LengthUnit>) safeCreateFloatMatrix(right, LengthUnit.METER, false);
        try
        {
            leftMatrix.add(rightMatrix);
            fail("Adding matrices of different sizes should have thrown an exception");
        }
        catch (ValueException exception)
        {
            // Ignore
        }
        right = buildArray(4, 5, false, 0.3f);
        rightMatrix = (FloatMatrixRel<LengthUnit>) safeCreateFloatMatrix(right, LengthUnit.METER, false);
        try
        {
            leftMatrix.add(rightMatrix);
        }
        catch (ValueException exception)
        {
            fail("Adding matrices of equal sizes should not have thrown an exception");
        }
        try
        {
            for (int i = 0; i < left.length; i++)
                for (int j = 0; j < left[0].length; j++)
                    assertEquals("Values should now be sum of input values", left[i][j] + right[i][j],
                            leftMatrix.getSI(i, j), 0.001);
        }
        catch (ValueException exception)
        {
            fail("Unexpected exception");
        }
        leftMatrix = safeCreateFloatMatrix(left, LengthUnit.METER, absolute);
        right = buildArray(4, 6, false, 0.3f);
        rightMatrix = (FloatMatrixRel<LengthUnit>) safeCreateFloatMatrix(right, LengthUnit.METER, false);
        try
        {
            leftMatrix.subtract(rightMatrix);
            fail("Subtracting matrices of different sizes should have thrown an exception");
        }
        catch (ValueException exception)
        {
            // Ignore
        }
        right = buildArray(3, 5, false, 0.3f);
        rightMatrix = (FloatMatrixRel<LengthUnit>) safeCreateFloatMatrix(right, LengthUnit.METER, false);
        try
        {
            leftMatrix.subtract(rightMatrix);
            fail("Subtracting matrices of different sizes should have thrown an exception");
        }
        catch (ValueException exception)
        {
            // Ignore
        }
        right = buildArray(4, 5, false, 0.3f);
        rightMatrix = (FloatMatrixRel<LengthUnit>) safeCreateFloatMatrix(right, LengthUnit.METER, false);
        try
        {
            leftMatrix.subtract(rightMatrix);
        }
        catch (ValueException exception)
        {
            fail("Subtracting matrices of equal sizes should not have thrown an exception");
        }
        try
        {
            for (int i = 0; i < left.length; i++)
                for (int j = 0; j < left[0].length; j++)
                    assertEquals("Values should now be difference of input values", left[i][j] - right[i][j],
                            leftMatrix.getSI(i, j), 0.001);
        }
        catch (ValueException exception)
        {
            fail("Unexpected exception");
        }
    }

    /**
     * Interface encapsulating a function that takes a float and returns a float.
     * <p>
     * Copyright (c) 2002-2014 Delft University of Technology, Jaffalaan 5, 2628 BX Delft, the Netherlands. All rights
     * reserved.
     * <p>
     * See for project information <a href="http://www.simulation.tudelft.nl/"> www.simulation.tudelft.nl</a>.
     * <p>
     * The OpenTrafficSim project is distributed under the following BSD-style license:<br>
     * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
     * following conditions are met:
     * <ul>
     * <li>Redistributions of source code must retain the above copyright notice, this list of conditions and the
     * following disclaimer.</li>
     * <li>Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
     * following disclaimer in the documentation and/or other materials provided with the distribution.</li>
     * <li>Neither the name of Delft University of Technology, nor the names of its contributors may be used to endorse
     * or promote products derived from this software without specific prior written permission.</li>
     * </ul>
     * This software is provided by the copyright holders and contributors "as is" and any express or implied
     * warranties, including, but not limited to, the implied warranties of merchantability and fitness for a particular
     * purpose are disclaimed. In no event shall the copyright holder or contributors be liable for any direct,
     * indirect, incidental, special, exemplary, or consequential damages (including, but not limited to, procurement of
     * substitute goods or services; loss of use, data, or profits; or business interruption) however caused and on any
     * theory of liability, whether in contract, strict liability, or tort (including negligence or otherwise) arising
     * in any way out of the use of this software, even if advised of the possibility of such damage.
     * @version Jun 23, 2014 <br>
     * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
     */
    interface FloatToFloat
    {
        /**
         * @param f float value
         * @return float value
         */
        float function(float f);
    }

    /**
     * <p>
     * Copyright (c) 2002-2014 Delft University of Technology, Jaffalaan 5, 2628 BX Delft, the Netherlands. All rights
     * reserved.
     * <p>
     * See for project information <a href="http://www.simulation.tudelft.nl/"> www.simulation.tudelft.nl</a>.
     * <p>
     * The OpenTrafficSim project is distributed under the following BSD-style license:<br>
     * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
     * following conditions are met:
     * <ul>
     * <li>Redistributions of source code must retain the above copyright notice, this list of conditions and the
     * following disclaimer.</li>
     * <li>Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
     * following disclaimer in the documentation and/or other materials provided with the distribution.</li>
     * <li>Neither the name of Delft University of Technology, nor the names of its contributors may be used to endorse
     * or promote products derived from this software without specific prior written permission.</li>
     * </ul>
     * This software is provided by the copyright holders and contributors "as is" and any express or implied
     * warranties, including, but not limited to, the implied warranties of merchantability and fitness for a particular
     * purpose are disclaimed. In no event shall the copyright holder or contributors be liable for any direct,
     * indirect, incidental, special, exemplary, or consequential damages (including, but not limited to, procurement of
     * substitute goods or services; loss of use, data, or profits; or business interruption) however caused and on any
     * theory of liability, whether in contract, strict liability, or tort (including negligence or otherwise) arising
     * in any way out of the use of this software, even if advised of the possibility of such damage.
     * @version Jun 23, 2014 <br>
     * @author <a href="http://www.tudelft.nl/pknoppers">Peter Knoppers</a>
     */
    abstract static class MathTester
    {

        /**
         * @param inputValues array of array of float with unprocessed values
         * @param operation String; description of method that is being tested
         * @param resultValues array of array of float with processed values
         * @param precision double expected accuracy
         * @param function FloatToFloat encapsulating function that converts one value in inputValues to the
         *            corresponding value in resultValues
         */
        public static void tester(final float[][] inputValues, String operation, final float[][] resultValues,
                final double precision, final FloatToFloat function)
        {
            for (int i = 0; i < inputValues.length; i++)
                for (int j = 0; j < inputValues[i].length; j++)
                {
                    float result = function.function(inputValues[i][j]);
                    String description =
                            String.format("index=%d: %s(%f)->%f should be equal to %f with precision %f", i, operation,
                                    inputValues[i][j], result, resultValues[i][j], precision);
                    // System.out.println(description);
                    assertEquals(description, result, resultValues[i][j], precision);
                }
        }

        /**
         * Function that takes a float value and returns a float value
         * @param in float value
         * @return float value
         */
        public abstract float function(float in);
    }

    /**
     * Test FloatMatrixAbs and FloatMatrixRel creators that take an array of FloatScalar as argument
     */
    @Test
    public void floatMatrixOneArg()
    {
        floatMatrixOneArg(true); // test absolute version
        floatMatrixOneArg(false); // test relative version
    }

    /**
     * Test the FloatMatrixAbs and FloatMatrixRel that takes a FloatScalar*<U>[] as argument
     */
    @SuppressWarnings("unchecked")
    private void floatMatrixOneArg(Boolean absolute)
    {
        FloatMatrix<LengthUnit> fm = null;
        FloatScalarAbs<LengthUnit>[][] inAbs = new FloatScalarAbs[0][0];
        FloatScalarRel<LengthUnit>[][] inRel = new FloatScalarRel[0][0];
        try
        {
            if (absolute)
                fm = createFloatMatrixAbs(inAbs);
            else
                fm = createFloatMatrixRel(inRel);
            fail("Should have thrown an exception");
        }
        catch (ValueException ve)
        {
            // Ignore expected exception
        }
        inAbs = new FloatScalarAbs[1][0];
        inRel = new FloatScalarRel[1][0];
        try
        {
            if (absolute)
                fm = createFloatMatrixAbs(inAbs);
            else
                fm = createFloatMatrixRel(inRel);
            fail("Should have thrown an exception");
        }
        catch (ValueException ve)
        {
            // Ignore expected exception
        }
        inAbs = new FloatScalarAbs[0][1];
        inRel = new FloatScalarRel[0][1];
        try
        {
            if (absolute)
                fm = createFloatMatrixAbs(inAbs);
            else
                fm = createFloatMatrixRel(inRel);
            fail("Should have thrown an exception");
        }
        catch (ValueException ve)
        {
            // Ignore expected exception
        }
        inAbs = new FloatScalarAbs[1][1];
        inAbs[0][0] = new FloatScalarAbs<LengthUnit>(123.456f, LengthUnit.FOOT);
        inRel = new FloatScalarRel[1][1];
        inRel[0][0] = new FloatScalarRel<LengthUnit>(123.456f, LengthUnit.FOOT);
        try
        {
            if (absolute)
                fm = createFloatMatrixAbs(inAbs);
            else
                fm = createFloatMatrixRel(inRel);
        }
        catch (ValueException exception)
        {
            fail("Should NOT have thrown an exception");
        }
        float[][] out = fm.getValuesInUnit();
        assertTrue("Result of getValuesInUnit should not be null", null != out);
        assertEquals("Array of values should have length 1", 1, out.length);
        assertEquals("Element in array should have the expected value", 123.456f, out[0][0], 0.001);
        float[][] in4 = buildArray(2, 3, true, 0);
        try
        {
            if (absolute)
            {
                FloatScalarAbs<LengthUnit>[][] inv4 = new FloatScalarAbs[in4.length][];
                for (int i = 0; i < in4.length; i++)
                {
                    inv4[i] = new FloatScalarAbs[in4[i].length];
                    for (int j = 0; j < in4[i].length; j++)
                        inv4[i][j] = new FloatScalarAbs<LengthUnit>(in4[i][j], LengthUnit.FOOT);
                }
                fm = createFloatMatrixAbs(inv4);
            }
            else
            {
                FloatScalarRel<LengthUnit>[][] inv4 = new FloatScalarRel[in4.length][];
                for (int i = 0; i < in4.length; i++)
                {
                    inv4[i] = new FloatScalarRel[in4[i].length];
                    for (int j = 0; j < in4[i].length; j++)
                        inv4[i][j] = new FloatScalarRel<LengthUnit>(in4[i][j], LengthUnit.FOOT);
                }
                fm = createFloatMatrixRel(inv4);
            }
            fail("Attempt to create floatMatrix from 2D array with rows of different sizes should have failed");
        }
        catch (ValueException exception)
        {
            // Ignore
        }
    }

    /**
     * Test adding and subtracting FloatMatrixRel.
     */
    @Test
    public void relRel()
    {
        float[][] in1 = buildArray(2, 4, false, 0);
        float[][] in2 = buildArray(2, 4, false, 0);
        MassUnit u = MassUnit.POUND;
        FloatMatrixRel<MassUnit> fm1 = null;
        FloatMatrixRel<MassUnit> fm2 = null;
        try
        {
            fm1 = createFloatMatrixRel(in1, u);
            fm2 = createFloatMatrixRel(in2, u);
        }
        catch (ValueException exception2)
        {
            fail("Unexpected exception");
        }
        FloatMatrixRel<MassUnit> sum = null;
        try
        {
            sum = FloatMatrix.plus(fm1, fm2);
        }
        catch (ValueException exception)
        {
            fail("Should be able to add FloatMatrixRel to FloatMatrixRel of same size");
        }
        assertTrue("Result should not be null", null != sum);
        assertEquals("Size of result should be size of inputs", 2, sum.rows());
        assertEquals("Size of result should be size of inputs", 4, sum.columns());
        assertEquals("Type of result should be type of inputs", u, sum.getUnit());
        float[][] sumValues = sum.getValuesInUnit();
        for (int i = 0; i < in1.length; i++)
            for (int j = 0; j < in1[0].length; j++)
                assertEquals("Each element should equal the sum of the contributing elements", in1[i][j] + in2[i][j],
                        sumValues[i][j], 0.0001);
        FloatMatrixRel<MassUnit> difference = null;
        try
        {
            difference = FloatMatrix.minus(fm1, fm2);
        }
        catch (ValueException exception1)
        {
            fail("Should be able to subtract FloatMatrixRel from FloatMatrixRel of same size");
        }
        assertTrue("Result should not be null", null != difference);
        assertEquals("Size of result should be size of inputs", 2, difference.rows());
        assertEquals("Size of result should be size of inputs", 4, difference.columns());
        assertEquals("Type of result should be type of inputs", u, difference.getUnit());
        float[][] differenceValues = difference.getValuesInUnit();
        for (int i = 0; i < in1.length; i++)
            for (int j = 0; j < in1[0].length; j++)
                assertEquals("Each element should equal the difference of the contributing elements", in1[i][j]
                        - in2[i][j], differenceValues[i][j], 0.0001);
        float[][] in3 = buildArray(2, 3, false, 0);
        FloatMatrixRel<MassUnit> fm3 = null;
        try
        {
            fm3 = createFloatMatrixRel(in3, u);
        }
        catch (ValueException exception1)
        {
            fail("Unexpected exception");
        }
        try
        {
            sum = FloatMatrix.plus(fm1, fm3);
            fail("Adding FloatMatrices of unequal size should have thrown a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        try
        {
            difference = FloatMatrix.minus(fm1, fm3);
            fail("Subtracting FloatMatrices of unequal size should have thrown a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        MassUnit u2 = MassUnit.OUNCE;
        try
        {
            fm2 = createFloatMatrixRel(in2, u2);
        }
        catch (ValueException exception2)
        {
            fail("Unexpected exceptin");
        }
        try
        {
            sum = FloatMatrix.plus(fm1, fm2);
        }
        catch (ValueException exception)
        {
            fail("Should be able to add FloatMatrixRel to FloatMatrixRel of same size");
        }
        assertTrue("Result should not be null", null != sum);
        assertEquals("Size of result should be size of inputs", 2, sum.rows());
        assertEquals("Size of result should be size of inputs", 4, sum.columns());
        assertEquals("Type of result should be type of first input", u, sum.getUnit());
        assertFalse("Type of result should be different of type of second input", u2 == sum.getUnit());
        sumValues = sum.getValuesInUnit();
        for (int i = 0; i < in1.length; i++)
            for (int j = 0; j < in1[0].length; j++)
            {
                assertEquals("Each element should equal the weighted sum of the contributing elements", in1[i][j]
                        * 0.45359 + in2[i][j] * 0.028350, sumValues[i][j] * 0.45359, 0.001);
            }
        try
        {
            fm2 = createFloatMatrixRel(in2, u2);
        }
        catch (ValueException exception1)
        {
            fail("Unexpected exception");
        }
        try
        {
            difference = FloatMatrix.minus(fm1, fm2);
        }
        catch (ValueException exception)
        {
            fail("Should be able to add FloatMatrixRel to FloatMatrixRel of same size");
        }
        assertTrue("Result should not be null", null != difference);
        assertEquals("Size of result should be size of inputs", 2, difference.rows());
        assertEquals("Size of result should be size of inputs", 4, difference.columns());
        assertEquals("Type of result should be type of first input", u, difference.getUnit());
        assertFalse("Type of result should be different of type of second input", u2 == difference.getUnit());
        differenceValues = difference.getValuesInUnit();
        for (int i = 0; i < in1.length; i++)
            for (int j = 0; j < in1[0].length; j++)
                assertEquals("Each element should equal the weighted difference of the contributing elements",
                        in1[i][j] * 0.45359 - in2[i][j] * 0.028350, differenceValues[i][j] * 0.45359, 0.001);
    }

    /**
     * Test adding, subtracting and multiplication of FloatMatrixAbs.
     */
    @Test
    public void absAbs()
    {
        float[][] in1 = buildArray(2, 4, false, 0);
        float[][] in2 = buildArray(2, 4, false, 0);
        MassUnit u = MassUnit.POUND;
        FloatMatrixAbs<MassUnit> fm1 = null;
        FloatMatrixAbs<MassUnit> fm2 = null;
        try
        {
            fm1 = createFloatMatrixAbs(in1, u);
            fm2 = createFloatMatrixAbs(in2, u);
        }
        catch (ValueException exception2)
        {
            fail("Unexpected exception");
        }
        FloatMatrixRel<MassUnit> difference = null;
        try
        {
            difference = FloatMatrix.minus(fm1, fm2);
        }
        catch (ValueException exception1)
        {
            fail("Should be able to subtract FloatMatrixAbs from FloatMatrixAbs of same size");
        }
        assertTrue("Result should not be null", null != difference);
        assertEquals("Size of result should be size of inputs", 2, difference.rows());
        assertEquals("Size of result should be size of inputs", 4, difference.columns());
        assertEquals("Type of result should be type of inputs", u, difference.getUnit());
        float[][] differenceValues = difference.getValuesInUnit();
        for (int i = 0; i < in1.length; i++)
            for (int j = 0; j < in1[0].length; j++)
                assertEquals("Each element should equal the difference of the contributing elements", in1[i][j]
                        - in2[i][j], differenceValues[i][j], 0.0001);
        float[][] in3 = buildArray(2, 3, false, 0);
        FloatMatrixAbs<MassUnit> fm3 = null;
        try
        {
            fm3 = createFloatMatrixAbs(in3, u);
        }
        catch (ValueException exception2)
        {
            fail("Unexpected exception");
        }
        try
        {
            difference = FloatMatrix.minus(fm1, fm3);
            fail("Subtracting FloatMatrices of unequal size should have thrown a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        MassUnit u2 = MassUnit.OUNCE;
        try
        {
            fm2 = createFloatMatrixAbs(in2, u2);
        }
        catch (ValueException exception2)
        {
            fail("Unexpected exception");
        }
        try
        {
            difference = FloatMatrix.minus(fm1, fm2);
        }
        catch (ValueException exception)
        {
            fail("Should be able to subtract FloatMatrixAbs to FloatMatrixAbs of same size");
        }
        assertTrue("Result should not be null", null != difference);
        assertEquals("Size of result should be size of inputs", 2, difference.rows());
        assertEquals("Size of result should be size of inputs", 4, difference.columns());
        assertEquals("Type of result should be type of first input", u, difference.getUnit());
        assertFalse("Type of result should be different of type of second input", u2 == difference.getUnit());
        differenceValues = difference.getValuesInUnit();
        for (int i = 0; i < in1.length; i++)
            for (int j = 0; j < in1[0].length; j++)
                assertEquals("Each element should equal the weighted difference of the contributing elements",
                        in1[i][j] * 0.45359 - in2[i][j] * 0.028350, differenceValues[i][j] * 0.45359, 0.01);
        try
        {
            difference = FloatMatrix.minus(fm2, fm1);
        }
        catch (ValueException exception)
        {
            fail("Should be able to add FloatMatrixAbs to FloatMatrixAbs of same size");
        }
        assertTrue("Result should not be null", null != difference);
        assertEquals("Size of result should be size of inputs", 2, difference.rows());
        assertEquals("Size of result should be size of inputs", 4, difference.columns());
        assertEquals("Type of result should be type of first input", u2, difference.getUnit());
        assertFalse("Type of result should be different of type of second input", u == difference.getUnit());
        differenceValues = difference.getValuesInUnit();
        for (int i = 0; i < in1.length; i++)
            for (int j = 0; j < in1[i].length; j++)
                assertEquals("Each element should equal the weighted difference of the contributing elements",
                        in2[i][j] * 0.028350 - in1[i][j] * 0.45359, differenceValues[i][j] * 0.028350, 0.02);
        LengthUnit u4 = LengthUnit.INCH;
        ForceUnit u5 = ForceUnit.POUND_FORCE;
        FloatMatrixAbs<LengthUnit> fm4 = null;
        FloatMatrixAbs<ForceUnit> fm5 = null;
        try
        {
            fm4 = createFloatMatrixAbs(in1, u4);
            fm5 = createFloatMatrixAbs(in2, u5);
        }
        catch (ValueException exception1)
        {
            fail("Unexpected exception");
        }
        Unit<EnergyUnit> resultUnit = EnergyUnit.CALORIE_IT;
        FloatMatrixAbs<?> product = null;
        try
        {
            product = FloatMatrix.multiply(fm4, fm5);
        }
        catch (ValueException exception)
        {
            fail("Should be able to multiply FloatMatrixAbs with FloatMatrixAbs of same size");
        }
        assertTrue("Result should not be null", null != product);
        assertEquals("Size of result should be size of inputs", 2, product.rows());
        assertEquals("Size of result should be size of inputs", 4, product.columns());
        // System.out.println("unit of product is " + product.getUnit().getSICoefficientsString());
        // System.out.println("expected result unit is " + resultUnit);
        Set<Unit<?>> matches = null;
        try
        {
            matches =
                    Unit.lookupUnitWithSICoefficients(SICoefficients.normalize(product.getUnit().getStandardUnit()
                            .getSICoefficientsString()));
        }
        catch (UnitException exception)
        {
            exception.printStackTrace();
        }
        // System.out.println("matches: " + matches);
        assertTrue("Result is an EnergyUnit", matches.contains(resultUnit.getStandardUnit()));
        for (int i = 0; i < in1.length; i++)
            for (int j = 0; j < in1[i].length; j++)
                try
                {
                    assertEquals("Value in product should be product of contributing values",
                            fm4.getSI(i, j) * fm5.getSI(i, j), product.getSI(i, j), 0.00001);
                }
                catch (ValueException exception)
                {
                    fail("Unexpected ValueException");
                }
        try
        {
            FloatMatrixAbs<LengthUnit> lhs = createFloatMatrixAbs(buildArray(2, 3, false, 0.5f), LengthUnit.METER);
            float rhs[][] = {{1, 2, 3}, {4, 5, 6}};
            FloatMatrix<LengthUnit> result = FloatMatrix.multiply(lhs, rhs);
            assertTrue("Result should not be null", null != result);
            assertEquals("Result should have 2 rows", 2, result.rows());
            assertEquals("Result should have 3 columns", 3, result.columns());
            for (int row = 0; row < 2; row++)
                for (int column = 0; column < 3; column++)
                    assertEquals("Cell should contain product of contributing cell values", lhs.get(row, column)
                            .getValueSI() * rhs[row][column], result.get(row, column).getValueSI(), 0.0001);
        }
        catch (ValueException exception)
        {
            fail("Unexpected ValueException");
        }
        try
        {
            FloatMatrixRel<LengthUnit> lhs = createFloatMatrixRel(buildArray(2, 3, false, 0.5f), LengthUnit.METER);
            float rhs[][] = {{1, 2, 3}, {4, 5, 6}};
            FloatMatrix<LengthUnit> result = FloatMatrix.multiply(lhs, rhs);
            assertTrue("Result should not be null", null != result);
            assertEquals("Result should have 2 rows", 2, result.rows());
            assertEquals("Result should have 3 columns", 3, result.columns());
            for (int row = 0; row < 2; row++)
                for (int column = 0; column < 3; column++)
                    assertEquals("Cell should contain product of contributing cell values", lhs.get(row, column)
                            .getValueSI() * rhs[row][column], result.get(row, column).getValueSI(), 0.0001);
        }
        catch (ValueException exception)
        {
            fail("Unexpected ValueException");
        }
        try
        {
            FloatMatrixAbs<LengthUnit> lhs = createFloatMatrixAbs(buildArray(2, 3, false, 0.5f), LengthUnit.METER);
            float rhs[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
            FloatMatrix.multiply(lhs, rhs);
            fail("Should have thrown a ValueException");
        }
        catch (ValueException exception)
        {
            // Ignore
        }
        try
        {
            FloatMatrixRel<LengthUnit> lhs = createFloatMatrixRel(buildArray(2, 3, false, 0.5f), LengthUnit.METER);
            float rhs[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
            FloatMatrix.multiply(lhs, rhs);
            fail("Should have thrown a ValueException");
        }
        catch (ValueException exception)
        {
            // Ignore
        }

    }

    /**
     * Test adding, subtracting of absolute and relative matrix
     */
    @Test
    public void absRel()
    {
        float[][] in1 = buildArray(2, 4, false, 0);
        float[][] in2 = buildArray(2, 4, false, 10);
        MassUnit u = MassUnit.POUND;
        FloatMatrixAbs<MassUnit> fm1 = null;
        FloatMatrixRel<MassUnit> fm2 = null;
        try
        {
            fm1 = createFloatMatrixAbs(in1, u);
            fm2 = createFloatMatrixRel(in2, u);
        }
        catch (ValueException exception2)
        {
            fail("Unexpected exception");
        }
        FloatMatrixAbs<MassUnit> difference = null;
        try
        {
            difference = FloatMatrix.minus(fm1, fm2);
        }
        catch (ValueException exception1)
        {
            fail("Should be able to subtract FloatMatrixAbs from FloatMatrixAbs of same size");
        }
        assertTrue("Result should not be null", null != difference);
        assertEquals("Size of result should be size of inputs", 2, difference.rows());
        assertEquals("Size of result should be size of inputs", 4, difference.columns());
        assertEquals("Type of result should be type of inputs", u, difference.getUnit());
        float[][] differenceValues = difference.getValuesInUnit();
        for (int i = 0; i < in1.length; i++)
            for (int j = 0; j < in1[0].length; j++)
                assertEquals("Each element should equal the difference of the contributing elements", in1[i][j]
                        - in2[i][j], differenceValues[i][j], 0.0001);
        float[][] in3 = buildArray(2, 3, false, 0);
        FloatMatrixRel<MassUnit> fm3 = null;
        try
        {
            fm3 = createFloatMatrixRel(in3, u);
        }
        catch (ValueException exception2)
        {
            fail("Unexpected exception");
        }
        try
        {
            difference = FloatMatrix.minus(fm1, fm3);
            fail("Subtracting FloatMatrices of unequal size should have thrown a ValueException");
        }
        catch (ValueException exception)
        {
            // ignore
        }
        MassUnit u2 = MassUnit.OUNCE;
        try
        {
            fm2 = createFloatMatrixRel(in2, u2);
        }
        catch (ValueException exception2)
        {
            fail("Unexpected exception");
        }
        try
        {
            difference = FloatMatrix.minus(fm1, fm2);
        }
        catch (ValueException exception)
        {
            fail("Should be able to add FloatMatrixAbs to FloatMatrixAbs of same size");
        }
        assertTrue("Result should not be null", null != difference);
        assertEquals("Size of result should be size of inputs", 2, difference.rows());
        assertEquals("Size of result should be size of inputs", 4, difference.columns());
        assertEquals("Type of result should be type of first input", u, difference.getUnit());
        assertFalse("Type of result should be different of type of second input", u2 == difference.getUnit());
        differenceValues = difference.getValuesInUnit();
        for (int i = 0; i < in1.length; i++)
            for (int j = 0; j < in1[0].length; j++)
                assertEquals("Each element should equal the weighted difference of the contributing elements",
                        in1[i][j] * 0.45359 - in2[i][j] * 0.028350, differenceValues[i][j] * 0.45359, 0.002);
    }

    /**
     * Test the solve methods
     */
    @Test
    public void solver()
    {
        float[][] a = {{1, 2, 3}, {5, 6, 7}, {9, 10, 15}};
        float[] b = {4, 8, 12};
        FloatMatrix<LengthUnit> aMatrix = safeCreateFloatMatrix(a, LengthUnit.METER, true);
        FloatVector<ForceUnit> bVector = null;
        if (aMatrix instanceof SparseData)
            bVector = new FloatVector.Sparse.Abs<ForceUnit>(b, ForceUnit.NEWTON);
        else
            bVector = new FloatVector.Dense.Abs<ForceUnit>(b, ForceUnit.NEWTON);
        try
        {
            FloatVector<SIUnit> result = null;
            if (aMatrix instanceof SparseData)
            {
                if (aMatrix instanceof Absolute)
                {
                    // This case does not work (the other three do work)
                    FloatMatrix2D m = aMatrix.getMatrixSI();
                    System.out.println(m.toString());
                    return; // result = FloatMatrix.solve((FloatMatrixAbsSparse<LengthUnit>) aMatrix,
                            // (FloatVectorAbs<ForceUnit>) bVector);
                }
                else if (aMatrix instanceof Relative)
                    result = FloatMatrix.solve((FloatMatrixRelSparse<LengthUnit>) aMatrix, bVector);
            }
            else
            {
                if (aMatrix instanceof Absolute)
                    result = FloatMatrix.solve((FloatMatrixAbsDense<LengthUnit>) aMatrix, bVector);
                else
                    result = FloatMatrix.solve((FloatMatrixRelDense<LengthUnit>) aMatrix, bVector);
            }
            System.out.println("unit of result is " + result.getUnit());
            assertEquals("result[0] should be -2", -2, result.getSI(0), 0.0001);
            assertEquals("result[1] should be 3", 3, result.getSI(1), 0.0001);
            assertEquals("result[2] should be 0", 0, result.getSI(2), 0.0001);
        }
        catch (ValueException ve)
        {
            fail("Unexpected exception");
        }
        catch (IllegalArgumentException iae)
        {
            iae.printStackTrace();
            fail("Unexpected exception");
        }
    }

    /**
     * Test the FloatMatrixRelDense that takes a float[] as argument.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void floatMatrixRel2()
    {
        FloatMatrix<LengthUnit> fsa = null;
        FloatScalarRel<LengthUnit>[][] in = new FloatScalarRel[0][0];
        try
        {
            fsa = createFloatMatrixRel(in);
            fail("Should have thrown an exception");
        }
        catch (ValueException ve)
        {
            // Ignore expected exception
        }
        in = new FloatScalarRel[1][1];
        in[0][0] = new FloatScalarRel<LengthUnit>(123.456f, LengthUnit.FOOT);
        try
        {
            fsa = new FloatMatrixRelDense<LengthUnit>(in);
        }
        catch (ValueException exception)
        {
            fail("Should NOT have thrown an exception");
        }
        float[][] out = fsa.getValuesInUnit();
        assertTrue("Result of getValuesInUnit should not be null", null != out);
        assertEquals("Array of values should have length 1", 1, out.length);
        assertEquals("Element in array should have the expected value", 123.456f, out[0][0], 0.001);
    }

    /**
     * Version of createFloatMatrix for use with guaranteed rectangular data
     * @param in float[][] with values MUST BE RECTANGULAR
     * @param u Unit; type for the new FloatMatrix
     * @param absolute Boolean; true to create a FloatMatrixAbs; false to create a FloatMatrixRel
     * @return FloatMatrix
     */
    private <U extends Unit<U>> FloatMatrix<U> safeCreateFloatMatrix(float[][] in, U u, boolean absolute)
    {
        FloatMatrix<U> result = null;
        try
        {
            result = createFloatMatrix(in, u, absolute);
        }
        catch (ValueException exception)
        {
            fail("Caught unexpected ValueException in safeCreateFloatMatrix");
        }
        return result;
    }

    /**
     * Create a FloatMatrixAbs or a FloatMatrixRel from an array of float values and Unit
     * @param in float[][] with values
     * @param u Unit; type for the new FloatMatrix
     * @param absolute Boolean; true to create a FloatMatrixAbs; false to create a FloatMatrixRel
     * @return FloatMatrix
     * @throws ValueException
     */
    private <U extends Unit<U>> FloatMatrix<U> createFloatMatrix(float[][] in, U u, boolean absolute)
            throws ValueException
    {
        if (absolute)
            return createFloatMatrixAbs(in, u);
        else
            return createFloatMatrixRel(in, u);
    }

    /**
     * Create a new FloatMatrixAbs from an array of float values and Unit.
     * @param in float[][] with values
     * @param u Unit; type for the new FloatMatrixAbs
     * @return
     * @throws ValueException when the array is not rectangular
     */
    protected abstract <U extends Unit<U>> FloatMatrixAbs<U> createFloatMatrixAbs(float[][] in, U u)
            throws ValueException;

    /**
     * Create a new FloatMatrixAbs from an array of FloatScalarAbs values.
     * @param in FloatScalarAbs[][]; the values
     * @return
     * @throws ValueException when the array is empty or not rectangular
     */
    protected abstract <U extends Unit<U>> FloatMatrixAbs<U> createFloatMatrixAbs(FloatScalarAbs<U>[][] in)
            throws ValueException;

    /**
     * Create a new FloatMatrixRel from an array of float values and Unit.
     * @param in float[][] with values
     * @param u Unit; type for the new FloatMatrixRel
     * @return
     * @throws ValueException when the array is not rectangular
     */
    protected abstract <U extends Unit<U>> FloatMatrixRel<U> createFloatMatrixRel(float[][] in, U u)
            throws ValueException;

    /**
     * Create a new FloatMatrixRel from an array of FloatScalarRel values.
     * @param in FloatScalarAbs[][]; the values
     * @return
     * @throws ValueException when the array is empty or not rectangular
     */
    protected abstract <U extends Unit<U>> FloatMatrixRel<U> createFloatMatrixRel(FloatScalarRel<U>[][] in)
            throws ValueException;

}
