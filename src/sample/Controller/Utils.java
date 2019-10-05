package sample.Controller;

import javafx.scene.control.TableView;
import javafx.scene.text.Text;

/**
 * The collections of all static functions
 * <p>
 * Created by Phan on 23/3/18.
 */
public class Utils {
    public static final int TYPE_OXY = 1;
    public static final int TYPE_OXY_PRESSURE = 2;
    public static final int TYPE_DEPTH = 3;

    /**
     * Limitation of ranges
     */
    public static final double MAX_OXY = 50;
    public static final double MIN_OXY = 22;
    public static final int MIN_OXY_TB = 18;
    public static final int MAX_OXY_TB = 50;
    public static final double MAX_OXY_PRESSURE = 1.6;
    public static final double MIN_OXY_PRESSURE = 1.1;
    public static final double MAX_DEPTH = 68;
    public static final double MIN_DEPTH = 1;
    public static final int MIN_DEPTH_TB = 3;
    public static final int MAX_DEPTH_TB = 70;

    /**
     * This is the function for get error code by the value of Input object
     *
     * @param type of the object which could be Oxygen, Depth and Oxygen Pressure
     * @param src  is the target for validation
     *
     * @return error code
     */
    public static int validationCode(int type, String src) {
        if (src.trim().equals("")) {
            //return -1;
            src = "0";
        }

        double a = 0;
        try {
            a = Double.parseDouble(src);
            if (type == TYPE_OXY
                    && (a < MIN_OXY || a > MAX_OXY)) {
                return -4;
            } else if (type == TYPE_OXY_PRESSURE
                    && (a < MIN_OXY_PRESSURE || a > MAX_OXY_PRESSURE)) {
                return -5;
            } else if (type == TYPE_DEPTH
                    && (a < MIN_DEPTH || a > MAX_DEPTH)) {
                return -6;
            } else {
                return 1;
            }
        } catch (Exception e) {
            if (type == TYPE_OXY) {
                return -4;
            } else if (type == TYPE_OXY_PRESSURE) {
                return -5;
            } else if (type == TYPE_DEPTH) {
                return -6;
            } else {
                return 1;
            }
        }
    }

    public static int validateCodeForTables(int type, String a, String b) {
        double c = 0;
        double d = 0;
        try {
            c = Double.parseDouble(a);
            d = Double.parseDouble(b);

            if (type == TYPE_OXY) {
                if (c < MIN_OXY_TB || c > MAX_OXY_TB ||
                        d < MIN_OXY_TB || d > MAX_OXY_TB) {
                    return -7;
                } else {
                    if (c > d) {
                        return -9;
                    } else {
                        return 1;
                    }
                }
            } else {
                if (c < MIN_DEPTH_TB || c > MAX_DEPTH_TB ||
                        d < MIN_DEPTH_TB || d > MAX_DEPTH_TB) {
                    return -8;
                } else {
                    if (c > d) {
                        return -10;
                    } else {
                        return 1;
                    }
                }
            }
        } catch (Exception e) {
            if (type == TYPE_OXY) {
                return -7;
            } else {
                return -8;
            }
        }
    }

    /**
     * Using the error code for producing error message
     *
     * @param code is error code from function {@link #validationCode(int, String)}
     *
     * @return error message
     */
    public static String getValidationString(int code) {
        String result = "";
        switch (code) {
            case -1:
                result = FinalString.OUT_EMPTY_FIELDS;
                break;
            case -2:
                result = FinalString.OUT_NUM_VALUE_ONLY;
                break;
            case -3:
                result = FinalString.OUT_NEGATIVE_NUMBER;
                break;
            case -4:
                result = FinalString.OUT_OXY_RANGE;
                break;
            case -5:
                result = FinalString.OUT_OXY_PRESS_RANGE;
                break;
            case -6:
                result = FinalString.OUT_DEPTH_RANGE;
                break;
            case -7:
                result = FinalString.OUT_OXY_RANGE_TB;
                break;
            case -8:
                result = FinalString.OUT_DEPTH_RANGE_TB;
                break;
            case -9:
                result = FinalString.OUT_OXYGEN_START_BIGGER;
                break;
            case -10:
                result = FinalString.OUT_DEPTH_START_BIGGER;
                break;
        }

        return result;
    }
}
