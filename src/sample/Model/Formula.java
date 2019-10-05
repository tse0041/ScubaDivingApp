package sample.Model;

/**
 * This class is the collections of all calculation types
 *
 * Created by Phan on 26/3/18.
 */
public class Formula {
    /**
     * The below final int variables are define flags for every calculation
     */
    public static final int TYPE_MOD = 1;
    public static final int TYPE_PP = 2;
    public static final int TYPE_BEST_MIX = 3;
    public static final int TYPE_EAD = 4;

    /**
     *  The flag of current calculation
     */
    private int formulaType = 0;

    /**
     * Basic objects for calculations
     */
    private Oxygen oxygen = null;
    private OxyPressure oxyPressure = null;
    private Depth depth = null;

    public Formula(int formulaType, Oxygen oxygen, OxyPressure oxyPressure, Depth depth) {
        this.oxygen = oxygen;
        this.oxyPressure = oxyPressure;
        this.depth = depth;
        this.formulaType = formulaType;
    }

    public Formula() {
    }

    public int getFormulaType() {
        return formulaType;
    }

    public void setFormulaType(int formulaType) {
        this.formulaType = formulaType;
    }

    public Oxygen getOxygen() {
        return oxygen;
    }

    public void setOxygen(Oxygen oxygen) {
        this.oxygen = oxygen;
    }

    public OxyPressure getOxyPressure() {
        return oxyPressure;
    }

    public void setOxyPressure(OxyPressure oxyPressure) {
        this.oxyPressure = oxyPressure;
    }

    public Depth getDepth() {
        return depth;
    }

    public void setDepth(Depth depth) {
        this.depth = depth;
    }

    /**
     * The calculation for simple MOD
     *
     * @return maximum depth in meter (by using {@link #transPressureToDepth(double)})
     */
    private double calSimpleMOD() {
        double per = parseToRightMeasure(oxygen);
        double pp = Double.parseDouble(oxyPressure.getValue());
        return transPressureToDepth(pp / per);
    }

    /**
     * The calculation for simple PP
     *
     * @return partial pressure in ata
     */
    private double calSimplePP() {
        double per = parseToRightMeasure(oxygen);
        double d = parseToRightMeasure(depth);
        return per * d;
    }

    /**
     * The calculation for simple Best Mix
     *
     * @return best mix of Oxygen in percentage (by using {@link #parseToRightMeasure(Object)})
     */
    private double calSimpleBestMix() {
        double pp = Double.parseDouble(oxyPressure.getValue());
        double d = parseToRightMeasure(depth);
        return (pp / d) * 100;
    }

    /**
     * The calculation for simple EAD
     *
     * @return depth in meter (by using {@link #transPressureToDepth(double)})
     */
    private double calSimpleEAD() {
        double per = parseToRightMeasure(oxygen);
        double d = parseToRightMeasure(depth);
        return transPressureToDepth(((1 - per) * d) / 0.79);
    }

    /**
     * This function is for formatting meter to ata
     *
     * @param depth in meter
     * @return the value in ata
     */
    public static double transDepthToPressure(double depth) {
        double pressure = depth / 10 + 1;
        return pressure;
    }

    /**
     * This function is for formatting ata to meter
     *
     * @param pressure in ata
     * @return the value in meter
     */
    public static double transPressureToDepth(double pressure) {
        double depth = (pressure - 1) * 10;
        return depth;
    }

    /**
     * This function is for formatting the right measure of outputs for user to understand easily
     *
     * @param obj is flexible object which could be Oxygen or Depth
     * @return number is percentage (1-100) if the input is Oxygen
     *         number is value in meter (> 1) if the input is Depth
     */
    public static double parseToRightMeasure(Object obj) {
        if(obj instanceof Oxygen) {
            Oxygen o = (Oxygen) obj;
            double per = Double.parseDouble(o.getValue());
            if(per > 1) {
                return per / 100;
            } else {
                return per;
            }
        }

        if(obj instanceof Depth) {
            Depth d = (Depth) obj;
            double dp = Double.parseDouble(d.getValue());
            return transDepthToPressure(dp);
        }

        return 0;
    }

    public double execute() {
        double result = 0;
        switch (this.formulaType) {
            case TYPE_MOD:
                result = calSimpleMOD();
                break;
            case TYPE_PP:
                result = calSimplePP();
                break;
            case TYPE_BEST_MIX:
                result = calSimpleBestMix();
                break;
            case TYPE_EAD:
                result = calSimpleEAD();
                break;
        }

        return (double) Math.round(result * 10) / 10;
    }
}
