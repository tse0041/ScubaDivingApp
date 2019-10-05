package sample.Model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is the testing class for Formula class
 * This class will test all possible cases which related to Formula class in the test case document
 * <p>
 * Created by Phan on 10/4/18.
 */
public class FormulaTest {
    private Formula fo = new Formula();
    private Oxygen o = new Oxygen();
    private OxyPressure op = new OxyPressure();
    private Depth d = new Depth();
    private int size = 7;
    private int size1 = 10;
    /**
     * Desired input data collections (based on test case document)
     */
    private double[] arrInPP = {1.1, 1.28, 1.35, 1.39, 1.47, 1.51, 1.59};
    private double[] arrInPP1 = {1.1, 1.13, 1.19, 1.28, 1.35, 1.39, 1.44, 1.47, 1.51, 1.59};
    private double[] arrInOO = {22, 25, 28, 33, 36, 37, 40};
    private double[] arrInOO1 = {22, 25, 28, 33, 36, 37, 40, 42, 47, 50};
    private double[] arrInDp = {3, 6, 9, 18, 27, 54, 63};
    private double[] arrInDp1 = {5, 9, 17, 28, 33, 36, 40, 51, 62, 68};
    private double[] arrInDp2 = {5, 9, 17, 28, 33, 36, 40, 63, 65, 68};

    /**
     * Expected output data collections (based on test case document)
     */
    private double[] arrOuDp = {40.0, 41.2, 38.2, 32.1, 30.8, 30.8, 29.8};
    private double[] arrOuDp1 = {4.8, 8.0, 14.6, 22.2, 24.8, 26.7, 28, 43.6, 40.3, 39.4};
    private double[] arrOuOO1 = {73.3, 59.5, 44.1, 33.7, 31.4, 30.2, 28.8, 24.1, 21, 20.4};
    private double[] arrOuPP = {0.3, 0.4, 0.5, 0.9, 1.3, 2.4, 2.9};

    /**
     * Random inputs for EAD & PP tables in ranges:
     *  Fraction of Oxygen (18 - 34)
     *  Depth (3 - 40)
     */
    private int[] arrInOOTable = {18, 20, 29, 34};
    private int[] arrInDpTable = {3, 15, 27, 30};

    /**
     * Expected results for EAD table based on the inputs above
     */
    private double[] arrOuEADTable = {3.5, 15.3, 23.3, 23.4};

    /**
     * Expected results for PP table based on the inputs above
     */
    private double[] arrOuPPTable = {0.2, 0.5, 1.1, 1.4};


    /**
     * This is the function for test case SDCA01-6
     *
     * @throws Exception
     */
    @Test
    public void testSimpleMOD() throws Exception {
        fo.setFormulaType(Formula.TYPE_MOD);
        for (int i = 0; i < size; i++) {
            o.setValue(String.valueOf(arrInOO[i]));
            op.setValue(String.valueOf(arrInPP[i]));
            fo.setOxygen(o);
            fo.setOxyPressure(op);
            assertEquals(String.valueOf(arrOuDp[i]), String.valueOf(fo.execute()));
        }
    }

    /**
     * This is the function for test case SDCA02-6
     *
     * @throws Exception
     */
    @Test
    public void testSimplePP() throws Exception {
        fo.setFormulaType(Formula.TYPE_PP);
        // test in range
        for (int i = 0; i < size; i++) {
            o.setValue(String.valueOf(arrInOO[i]));
            d.setValue(String.valueOf(arrInDp[i]));
            fo.setOxygen(o);
            fo.setDepth(d);
            assertEquals(String.valueOf(arrOuPP[i]), String.valueOf(fo.execute()));
        }
    }

    /**
     * This is the function for test case SDCA03-1
     *
     * @throws Exception
     */
    @Test
    public void testSimpleBestMix() throws Exception {
        fo.setFormulaType(Formula.TYPE_BEST_MIX);
        for (int i = 0; i < size1; i++) {
            op.setValue(String.valueOf(arrInPP1[i]));
            d.setValue(String.valueOf(arrInDp1[i]));
            fo.setOxyPressure(op);
            fo.setDepth(d);
            assertEquals(String.valueOf(arrOuOO1[i]), String.valueOf(fo.execute()));
        }
    }

    /**
     * This is the function for test case SDCA04-1
     *
     * @throws Exception
     */
    @Test
    public void testSimpleEAD() throws Exception {
        fo.setFormulaType(Formula.TYPE_EAD);
        for (int i = 0; i < size1; i++) {
            o.setValue(String.valueOf(arrInOO1[i]));
            d.setValue(String.valueOf(arrInDp2[i]));
            fo.setOxygen(o);
            fo.setDepth(d);
            assertEquals(String.valueOf(arrOuDp1[i]), String.valueOf(fo.execute()));
        }
    }

    /**
     * This is the function for test case SDCA05-1
     *  Fraction of Oxygen (18 - 34)
     *  Depth (3 - 40)
     * @throws Exception
     */
    @Test
    public void testTableEAD() throws Exception {
        int oxygen1 = 18;
        int oxygen2 = 34;
        int depth1 = 3;
        int depth2 = 40;

        double[][] result = new double[oxygen2 + 1][depth2 + 1];
        for(int i = oxygen1; i <= oxygen2; i++) {
            for(int j = depth1; j <= depth2; j+=3) {
                Formula fom = new Formula();
                fom.setFormulaType(Formula.TYPE_EAD);
                fom.setOxygen(new Oxygen(i + ""));
                fom.setDepth(new Depth(j + ""));
                result[i][j] = fom.execute();
            }
        }

        for(int i = 0; i < arrInOOTable.length; i++) {
            assertEquals(String.valueOf(arrOuEADTable[i]),
                    String.valueOf(result[arrInOOTable[i]][arrInDpTable[i]]));
        }
    }

    /**
     * This is the function for test case SDCA06-1
     *  Fraction of Oxygen (18 - 34)
     *  Depth (3 - 40)
     * @throws Exception
     */
    @Test
    public void testTablePP() throws Exception {
        int oxygen1 = 18;
        int oxygen2 = 34;
        int depth1 = 3;
        int depth2 = 40;

        double[][] result = new double[oxygen2 + 1][depth2 + 1];
        for(int i = oxygen1; i <= oxygen2; i++) {
            for(int j = depth1; j <= depth2; j+=3) {
                Formula fom = new Formula();
                fom.setFormulaType(Formula.TYPE_PP);
                fom.setOxygen(new Oxygen(i + ""));
                fom.setDepth(new Depth(j + ""));
                result[i][j] = fom.execute();
            }
        }

        for(int i = 0; i < arrInOOTable.length; i++) {
            assertEquals(String.valueOf(arrOuPPTable[i]),
                    String.valueOf(result[arrInOOTable[i]][arrInDpTable[i]]));
        }
    }
}