package sample.Controller;

/**
 * All static strings of the app will be defined here, in this class
 *
 * Created by Phan on 23/3/18.
 */
public class FinalString {
    /**
     * Option menu text
     */
    public final static String OPT_MOD = "MOD";
    public final static String OPT_PP = "PP";
    public final static String OPT_BEST_MIX = "BEST MIX";
    public final static String OPT_EAD = "EAD";
    public final static String OPT_EAD_2 = "EAD";
    public final static String OPT_PP_2 = "PP";

    public final static String TXT_PP_O2 = "Partial Pressure of O2 (ata)";
    public final static String TXT_FR_O2 = "Fraction of O2 (%)";
    public final static String TXT_MAX_DEPTH = "Maximum Depth (m)";
    public final static String TXT_PLANED_DEPTH = "Planed Depth (m)";

    public final static String PROMPT_PP_O2 = "1.1~1.6";
    public final static String PROMPT_FR_O2 = "22~50";
    public final static String PROMPT_MAX_DEPTH = "1~68";
    public final static String PROMPT_PLANED_DEPTH = "1~68";

    public final static String OUT_DEFAULT_RESULT = "Please choose a calculation ";
    public final static String OUT_ENTER_INPUTS = "Please enter inputs ";
    public final static String OUT_EMPTY_FIELDS = "Empty fields";
    public final static String OUT_NUM_VALUE_ONLY = "Please input number value";
    public final static String OUT_NEGATIVE_NUMBER = "Please input positive number";
    public final static String OUT_OXY_RANGE = "Please input numbers from 22 to 50 for Fraction of Oxygen";
    public final static String OUT_OXY_RANGE_TB = "Please input start and end for Fraction of Oxygen in range 18 to 50";
    public final static String OUT_OXY_PRESS_RANGE = "Please input numbers from 1.1 to 1.6 for Partial Pressure of Oxygen";
    public final static String OUT_DEPTH_RANGE = "Please input numbers from 1 to 68 for Depth";
    public final static String OUT_DEPTH_RANGE_TB = "Please input start and end for depth in range 3 to 70";
    public final static String OUT_NOT_SAFE_DIVE = "Warning! This is not a safe dive!";
    public final static String OUT_OXYGEN_START_BIGGER = "The start of Oxygen should be smaller than the end";
    public final static String OUT_DEPTH_START_BIGGER = "The start of Depth should be smaller than the end";

    public final static String DIALOG_TITLE_SIMPLE_CAL = "Simple Calculation Instruction";
    public final static String DIALOG_CONTENT_SIMPLE_CAL = "" +
            "Within the simple calculation function, there are four calculations (MOD, PP, BEST MIX, EAD) can be performed:" +
            "\n1. Choose a calculation type." +
            "\n2. Enter the positive integer values in the input boxes." +
            "\n3. Click 'Calculation' button for execute chosen calculation." +
            "\n4. The result is then displayed in a box at the top right corner of the form. The Cylinder on the left" +
            " side shows the Fraction of Oxygen and Nitrogen for every calculation." +
            "\n\n*Note: You can adjust the Cylinder to input the Fraction of Oxygen. However, the adjustment does not" +
            " " +
            "allow when the calculation is BEST MIX.";
    public final static String DIALOG_TITLE_TABLE = "Table Instruction";
    public final static String DIALOG_CONTENT_TABLE = "" +
            "Within the generating tables function (EAD or PP table), there are two tables can be generated:" +
            "\n1. Choose a type of table." +
            "\n2. Select the range of the Fraction of Oxygen." +
            "\n3. Select the range of depth." +
            "\n4. The table is then displayed below the 'Print Table' button." +
            "\n\n*Note: Spinner have been used to prevent users to type in these cells. Users will only select the\n" +
            "numbers using a spinner.\n" +
            "*Note: the Fraction of Oxygen and depth already have the minimum and maximum number in display.\n" +
            "In the left cell, users will select the smaller Fraction of Oxygen and depth, in the right cell, users " +
            "will select the bigger Fraction of Oxygen and depth. The Spinner will automatically stop when users try " +
            "to select a bigger number in the left cell.";
}
