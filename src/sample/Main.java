package sample;

import javafx.application.Application;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import sample.Controller.*;
import sample.Model.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Execution class
 * <p>
 * Created by Phan on 14/3/18.
 */
public class Main extends Application {
    private Stage primaryStage = null;
    private Parent root = null;

    private Oxygen oxygen = null;
    private OxyPressure oxyPressure = null;
    private Depth depth = null;

    private TextField tvValue1 = null;
    private TextField tvValue2 = null;

    private Spinner spiO2Start = null;
    private Spinner spiO2End = null;
    private Spinner spiDepthStart = null;
    private Spinner spiDepthEnd = null;

    private TextArea txtResult = null;

    private Text txtLabel1 = null;
    private Text txtLabel2 = null;

    private ToggleGroup tgSimpleCal = null;
    private ToggleGroup tgTableCal = null;

    private RadioButton rbMOD = null;
    private RadioButton rbPP = null;
    private RadioButton rbBMix = null;
    private RadioButton rbEAD = null;
    private RadioButton rbEAD2 = null;
    private RadioButton rbPP2 = null;

    private Slider sDOxygen = null;
    private Slider sDNitrogen = null;

    private VBox vBOxygen = null;
    private VBox vBNitrogen = null;

    private ProgressBar pBCylinder = null;

    private GridPane gpTable = null;

    private ScrollPane spTable = null;

    private Button btnCal = null;
    private Button btnCal2 = null;

    private ImageView imgInfo1 = null;
    private ImageView imgInfo2 = null;
    private ImageView imgCylinder = null;

    private Label lbOxygen = null;
    private Label lbNitrogen = null;

    /**
     * These variable below store newest values for oxygen and depth (table calculation). These value are used for
     * re-assign whenever the limits occur.
     */
    private int currO2Start = 0;
    private int currO2End = 0;
    private int currDepthStart = 0;
    private int currDepthEnd = 0;

    /**
     * This variable stores number of table columns for every time users change the ranges of oxygen fraction
     */
    private int numColumn = 0;

    @Override
    public void start(Stage primaryStage) {
        try {
            initMainScreen(primaryStage);
        } catch (Exception e) {
            System.out.print("Unable to init main screen");
        }
    }

    private void initMainScreen(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Scuba Diving Calculator");
        root = FXMLLoader.load(getClass().getResource("FXML/main_screen.fxml"));
        Scene sc = new Scene(root, 800, 700);
        primaryStage.setScene(sc);
        primaryStage.show();
        primaryStage.setResizable(false);

        //get all views ids
        getIdFromXML();

        initSimpleCal();
        initTableCal();

        setListeners();
    }

    /**
     * This is the simple function for initializing simple calculation
     */
    private void initSimpleCal() {
        //set image buttons
        Image image = new Image(FinalUri.URI_INFO_IMG);
        imgInfo1.setImage(image);
        imgInfo2.setImage(image);

        //output result
        txtResult.setStyle(FinalStyle.TXT_RESULT_DEFAULT);
        txtResult.setText(FinalString.OUT_DEFAULT_RESULT);
        txtResult.setEditable(false);

        //simple calculation radio buttons
        tgSimpleCal = new ToggleGroup();
        rbMOD.setToggleGroup(tgSimpleCal);
        rbPP.setToggleGroup(tgSimpleCal);
        rbBMix.setToggleGroup(tgSimpleCal);
        rbEAD.setToggleGroup(tgSimpleCal);

        //input textfields
        tvValue1.setEditable(false);
        tvValue2.setEditable(false);

        //execute button
        btnCal.setDisable(true);

        //cylinder style
        Image cylinder = new Image(FinalUri.URI_CYLINDER_IMG);
        imgCylinder.setImage(cylinder);
        pBCylinder.getStylesheets().add(FinalUri.URI_CYLINDER_CSS);
    }

    /**
     * This is the simple function for initializing table calculation
     */
    private void initTableCal() {
        //grouping radio buttons for table
        tgTableCal = new ToggleGroup();
        rbEAD2.setToggleGroup(tgTableCal);
        rbPP2.setToggleGroup(tgTableCal);

        //execute button
        btnCal2.setDisable(true);

        //spinners including oxygen start, end; depth start, end
        spiO2Start.setEditable(false);
        spiO2End.setEditable(false);
        spiDepthStart.setEditable(false);
        spiDepthEnd.setEditable(false);

        //set style
        spiO2Start.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        spiO2End.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        spiDepthStart.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        spiDepthEnd.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);

        //set value ranges and initial values
        spiO2Start.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                Utils.MIN_OXY_TB, Utils.MAX_OXY_TB, Utils.MIN_OXY_TB));
        spiO2End.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                Utils.MIN_OXY_TB, Utils.MAX_OXY_TB, Utils.MAX_OXY_TB));
        spiDepthStart.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                Utils.MIN_DEPTH_TB, Utils.MAX_DEPTH_TB, Utils.MIN_DEPTH_TB));
        spiDepthEnd.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                Utils.MIN_DEPTH_TB, Utils.MAX_DEPTH_TB, Utils.MAX_DEPTH_TB));

        //assign current variables as initial values
        currO2Start = Utils.MIN_OXY_TB;
        currO2End = Utils.MAX_OXY_TB;
        currDepthStart = Utils.MIN_DEPTH_TB;
        currDepthEnd = Utils.MAX_DEPTH_TB;
    }

    /**
     * This method start when users click simple calculation button
     */
    private void executeSimpleCal() {
        //init components
        String resultStr = "";
        Formula formula = null;

        //set default state for related nodes
        txtResult.setStyle(FinalStyle.TXT_RESULT_DEFAULT);

        //get current options then calculating based on that option
        String currentOpt = ((RadioButton) tgSimpleCal.getSelectedToggle()).getText();
        switch (currentOpt) {
            case FinalString.OPT_MOD:
                formula = new Formula(Formula.TYPE_MOD,
                        oxygen, oxyPressure, depth);
                resultStr = "The Maximum Operating Depth is %1$sm with: " +
                        "\nThe Partial Pressure of Oxygen is " + ((TextField) oxyPressure.getControlAnchor()).getText() + "ata" +
                        "\nThe Fraction of Oxygen is " + ((TextField) oxygen.getControlAnchor()).getText() + "%%";
                break;
            case FinalString.OPT_PP:
                formula = new Formula(Formula.TYPE_PP,
                        oxygen, oxyPressure, depth);

                resultStr = "The Partial Pressure of Oxygen is %1$sata with: " +
                        "\nThe Maximum Operating Depth is " + ((TextField) depth.getControlAnchor()).getText() + "m" +
                        "\nThe Fraction of Oxygen is " + ((TextField) oxygen.getControlAnchor()).getText() + "%%";
                break;
            case FinalString.OPT_BEST_MIX:
                formula = new Formula(Formula.TYPE_BEST_MIX,
                        oxygen, oxyPressure, depth);
                resultStr = "The Best Mix is %1$s%% with: " +
                        "\nThe Maximum Operating Depth is " + ((TextField) depth.getControlAnchor()).getText() + "m" +
                        "\nThe Partial Pressure of Oxygen is " + ((TextField) oxyPressure.getControlAnchor()).getText() + "ata";
                break;
            case FinalString.OPT_EAD:
                formula = new Formula(Formula.TYPE_EAD,
                        oxygen, oxyPressure, depth);
                resultStr = "The Equivalent Air Depth is %1$sm with: " +
                        "\nThe Planned Depth is " + ((TextField) depth.getControlAnchor()).getText() + "m" +
                        "\nThe Fraction of Oxygen is " + ((TextField) oxygen.getControlAnchor()).getText() + "%%";
                break;
        }

        double result = formula.execute();

        if (currentOpt.equals(FinalString.OPT_PP)
                && result >= Utils.MAX_OXY_PRESSURE) {
            resultStr = FinalString.OUT_NOT_SAFE_DIVE + "\n" + resultStr;
            txtResult.setStyle(FinalStyle.TXT_RESULT_ERROR);
        }

        int tmp = 0;
        //change cylinder if simple calculation is best mix
        if (currentOpt.equals(FinalString.OPT_BEST_MIX)) {
            //change cylinder value
            tmp = (int) result;
        } else {
            tmp = (int) (Double.parseDouble(oxygen.getValue()));
        }

        // show text result
        String temp = String.format(resultStr, result);
        txtResult.setText(temp);

        sDOxygen.setValue(tmp);
        sDNitrogen.setValue(100 - tmp);
        pBCylinder.setProgress(tmp / 100f);
        lbNitrogen.setText((100 - tmp) + "% N2");
        lbOxygen.setText(tmp + "% O2");
    }

    /**
     * This method start when user click calculate button for table calculation
     */
    private void executeTableCal() {
        //clear gridview
        spTable.setContent(null);

        //parse value from inputs to integer
        int oxygenStart = Integer.valueOf(spiO2Start.getValue().toString());
        int oxygenEnd = Integer.valueOf(spiO2End.getValue().toString());
        int depthStart = Integer.valueOf(spiDepthStart.getValue().toString());
        int depthEnd = Integer.valueOf(spiDepthEnd.getValue().toString());

        // get number of columns
        numColumn = 1;
        for (int oxygen = oxygenStart; oxygen <= oxygenEnd; oxygen++) {
            numColumn++;
        }

        //init table
        gpTable = new GridPane();
        //setting size for the table
        gpTable.setMinSize(758, 293);
        gpTable.setMaxSize(798, Integer.MAX_VALUE);
        //enable divider lines
        gpTable.gridLinesVisibleProperty().setValue(true);

        //check current radio button
        String currentOpt = ((RadioButton) tgTableCal.getSelectedToggle()).getId();
        Formula formula = new Formula();
        switch (currentOpt) {
            case "rbEAD2":
                formula.setFormulaType(Formula.TYPE_EAD);
                Label a = getCellStyle(FinalString.OPT_EAD_2);
                a.setTextFill(Color.web(FinalColor.TABLE_BLUE));
                gpTable.add(a, 0, 0);
                break;
            case "rbPP2":
                formula.setFormulaType(Formula.TYPE_PP);
                Label b = getCellStyle(FinalString.OPT_PP_2);
                b.setTextFill(Color.web(FinalColor.TABLE_BLUE));
                gpTable.add(b, 0, 0);
                break;
        }

        // looping to retrieve table data
        int count1 = 0;
        for (int oxygen = oxygenStart; oxygen <= oxygenEnd; oxygen++) {
            Label a = getCellStyle(oxygen + "%");
            a.setTextFill(Color.web(FinalColor.TABLE_BLUE));
            gpTable.add(a, count1 + 1, 0);
            int count2 = 0;
            for (int depth = depthStart; depth <= depthEnd; depth += 3) {
                if (count1 == 1) {
                    Label b = getCellStyle(depth + "m");
                    b.setTextFill(Color.web(FinalColor.TABLE_BLUE));
                    gpTable.add(b, 0, count2 + 1);
                }
                // check the output
                formula.setOxygen(new Oxygen(oxygen + ""));
                formula.setDepth(new Depth(depth + ""));
                Double output = formula.execute();
                if (depth > Utils.MAX_DEPTH_TB
                        || oxygen > Utils.MAX_OXY_TB
                        || (output > Utils.MAX_OXY_PRESSURE && currentOpt.equals("rbPP2"))
                        || (output < Utils.MIN_DEPTH && currentOpt.equals("rbEAD2"))) {
                    gpTable.add(getCellStyle("-"), count1 + 1, count2 + 1);
                } else {
                    gpTable.add(getCellStyle(output + ""), count1 + 1, count2 + 1);
                }
                count2++;
            }
            count1++;
        }

        //show table
        spTable.setContent(gpTable);
    }

    /**
     * Mapping fxml id to java objects
     */
    private void getIdFromXML() {
        tvValue1 = (TextField) root.lookup("#tvValue1");
        tvValue2 = (TextField) root.lookup("#tvValue2");

        txtResult = (TextArea) root.lookup("#txtResult");

        spiO2Start = (Spinner) root.lookup("#spiO2Start");
        spiO2End = (Spinner) root.lookup("#spiO2End");
        spiDepthStart = (Spinner) root.lookup("#spiDepthStart");
        spiDepthEnd = (Spinner) root.lookup("#spiDepthEnd");

        txtLabel1 = (Text) root.lookup("#txtLabel1");
        txtLabel2 = (Text) root.lookup("#txtLabel2");

        rbMOD = (RadioButton) root.lookup("#rbMOD");
        rbPP = (RadioButton) root.lookup("#rbPP");
        rbBMix = (RadioButton) root.lookup("#rbBMix");
        rbEAD = (RadioButton) root.lookup("#rbEAD");

        rbEAD2 = (RadioButton) root.lookup("#rbEAD2");
        rbPP2 = (RadioButton) root.lookup("#rbPP2");

        btnCal = (Button) root.lookup("#btnCal");
        btnCal2 = (Button) root.lookup("#btnCal2");

        imgInfo1 = (ImageView) root.lookup("#imgInfo1");
        imgInfo2 = (ImageView) root.lookup("#imgInfo2");
        imgCylinder = (ImageView) root.lookup("#imgCylinder");

        spTable = (ScrollPane) root.lookup("#spTable");

        sDOxygen = (Slider) root.lookup("#sDOxygen");
        sDNitrogen = (Slider) root.lookup("#sDNitrogen");

        vBOxygen = (VBox) root.lookup("#vBOxygen");
        vBNitrogen = (VBox) root.lookup("#vBNitrogen");

        lbOxygen = (Label) root.lookup("#lbOxygen");
        lbNitrogen = (Label) root.lookup("#lbNitrogen");

        pBCylinder = (ProgressBar) root.lookup("#pBCylinder");
    }

    /**
     * This method places all listeners to theirs appropriate nodes
     */
    public void setListeners() {
        //set listener for oxygen spinners
        ChangeListener spinnerO2Listener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    currO2Start = (int) spiO2Start.getValueFactory().getValue();
                    currO2End = (int) spiO2End.getValueFactory().getValue();
                } catch (Exception e) {
                    //do nothing
                    if (observable instanceof ReadOnlyObjectWrapper) {
                        ((Spinner) (((ReadOnlyObjectWrapper) observable).getBean()))
                                .getEditor().setText(oldValue + "");
                    }
                }

                if (currO2Start >= currO2End) {
                    ((Spinner) (((ReadOnlyObjectWrapper) observable).getBean()))
                            .getValueFactory().setValue(oldValue);
                }
            }
        };

        //set listener for depth spinners
        ChangeListener spinnerDepthListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    currDepthStart = (int) spiDepthStart.getValueFactory().getValue();
                    currDepthEnd = (int) spiDepthEnd.getValueFactory().getValue();
                } catch (Exception e) {
                    //do nothing
                }

                if (currDepthStart >= currDepthEnd) {
                    ((Spinner) (((ReadOnlyObjectWrapper) observable).getBean()))
                            .getValueFactory().setValue(oldValue);
                }
            }
        };

        //add listeners to their components
        spiO2Start.valueProperty().addListener(spinnerO2Listener);
        spiO2End.valueProperty().addListener(spinnerO2Listener);
        spiDepthStart.valueProperty().addListener(spinnerDepthListener);
        spiDepthEnd.valueProperty().addListener(spinnerDepthListener);

        //set listener for group of radio buttons
        tgSimpleCal.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                txtResult.setText(FinalString.OUT_ENTER_INPUTS);
                // enable cylinder
                vBOxygen.setDisable(false);
                vBNitrogen.setDisable(false);
                sDNitrogen.setValue(100 - Utils.MIN_OXY);
                sDOxygen.setValue(0.0f);
                pBCylinder.setProgress(0.0f);
                lbOxygen.setText("0% O2");
                lbNitrogen.setText("100% N2");
                // enable inputs
                tvValue1.setEditable(true);
                tvValue2.setEditable(true);
                btnCal.setDisable(false);
                // reset to default
                tvValue1.setText("");
                tvValue2.setText("");
                txtResult.setStyle(FinalStyle.TXT_RESULT_DEFAULT);
                //init components
                oxygen = null;
                oxyPressure = null;
                depth = null;

                if (newValue == rbMOD) {
                    txtLabel1.setText(FinalString.TXT_PP_O2);
                    txtLabel2.setText(FinalString.TXT_FR_O2);
                    tvValue1.setPromptText(FinalString.PROMPT_PP_O2);
                    tvValue2.setPromptText(FinalString.PROMPT_FR_O2);

                    oxyPressure = new OxyPressure(tvValue1);
                    oxygen = new Oxygen(tvValue2);
                } else if (newValue == rbPP) {
                    txtLabel1.setText(FinalString.TXT_MAX_DEPTH);
                    txtLabel2.setText(FinalString.TXT_FR_O2);
                    tvValue1.setPromptText(FinalString.PROMPT_MAX_DEPTH);
                    tvValue2.setPromptText(FinalString.PROMPT_FR_O2);

                    depth = new Depth(tvValue1);
                    oxygen = new Oxygen(tvValue2);
                } else if (newValue == rbBMix) {
                    txtLabel1.setText(FinalString.TXT_MAX_DEPTH);
                    txtLabel2.setText(FinalString.TXT_PP_O2);
                    tvValue1.setPromptText(FinalString.PROMPT_MAX_DEPTH);
                    tvValue2.setPromptText(FinalString.PROMPT_PP_O2);

                    vBNitrogen.setDisable(true);
                    vBOxygen.setDisable(true);

                    depth = new Depth(tvValue1);
                    oxyPressure = new OxyPressure(tvValue2);
                } else {
                    txtLabel1.setText(FinalString.TXT_PLANED_DEPTH);
                    txtLabel2.setText(FinalString.TXT_FR_O2);
                    tvValue1.setPromptText(FinalString.PROMPT_PLANED_DEPTH);
                    tvValue2.setPromptText(FinalString.PROMPT_FR_O2);

                    depth = new Depth(tvValue1);
                    oxygen = new Oxygen(tvValue2);
                }
            }
        });

        //set listener for group of radio buttons
        tgTableCal.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                // enable elements
                btnCal2.setDisable(false);
            }
        });

        //set listener for simple calculation button
        btnCal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Set<String> messages = new HashSet<>();
                messages.add((oxygen != null) ? oxygen.validate() : "");
                messages.add((oxyPressure != null) ? oxyPressure.validate() : "");
                messages.add((depth != null) ? depth.validate() : "");

                String resultStr = "";

                for (String m : messages) {
                    if (!m.equals("")) {
                        if (resultStr.equals("")) {
                            resultStr = resultStr + m;
                        } else {
                            resultStr = resultStr + "\n" + m;
                        }
                    }
                }

                // check empty fields first
                if (tvValue1.getText().equals("")
                        && tvValue2.getText().equals("")) {
                    //do nothing
                } else if (!tvValue1.getText().equals("")
                        && !tvValue2.getText().equals("")) {
                    try {
                        Double.parseDouble(tvValue1.getText());
                        Double.parseDouble(tvValue2.getText());
                        if (messages.size() == 2 ||
                                messages.size() == 3) {
                            resultStr = FinalString.OUT_NOT_SAFE_DIVE + "\n" + resultStr;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                //check if having error or not
                if (resultStr.equals("")) {
                    executeSimpleCal();
                } else {
                    //show error output
                    txtResult.setStyle(FinalStyle.TXT_RESULT_ERROR);
                    txtResult.setText(resultStr);
                }
            }
        });

        //set listener for table calculation button
        btnCal2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                executeTableCal();
            }
        });

        //define slider listener
        ChangeListener sliderListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                Slider currSlider = (Slider) ((DoublePropertyBase) ov).getBean();
                TextField tvOxygen = (TextField) oxygen.getControlAnchor();
                if (tvOxygen == null)
                    return;
                if (currSlider.equals(sDOxygen)) {
                    pBCylinder.setProgress(newVal.doubleValue() / 100.0f);
                    sDNitrogen.setValue(100.0f - newVal.doubleValue());
                    tvOxygen.setText(newVal.intValue() + "");

                    lbOxygen.setText(newVal.intValue() + "% O2");
                    lbNitrogen.setText((100 - newVal.intValue()) + "% N2");
                } else {
                    pBCylinder.setProgress((100.0f - newVal.doubleValue()) / 100.0f);
                    sDOxygen.setValue(100.0f - newVal.doubleValue());
                    tvOxygen.setText((100 - newVal.intValue()) + "");

                    lbOxygen.setText((100 - newVal.intValue()) + "% O2");
                    lbNitrogen.setText(newVal.intValue() + "% N2");
                }


            }
        };

        //set listener for sliders
        sDOxygen.valueProperty().addListener(sliderListener);
        sDNitrogen.valueProperty().addListener(sliderListener);

        //set clickable for info buttons
        imgInfo1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showInfoDialog(FinalString.DIALOG_TITLE_SIMPLE_CAL,
                        FinalString.DIALOG_CONTENT_SIMPLE_CAL);
            }
        });

        imgInfo2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showInfoDialog(FinalString.DIALOG_TITLE_TABLE,
                        FinalString.DIALOG_CONTENT_TABLE);
            }
        });
    }

    /**
     * This method is for calculating size of cells, font sizes of cells in table numColumn is more than 27, font size
     * will be 11 numColumn is below 27, font size will be 13 </>
     *
     * @param value of every cell
     *
     * @return styled label object
     */
    private Label getCellStyle(String value) {
        Label a = new Label(value);
        a.setPrefHeight(30.0);
        a.setPrefWidth(798.0 - ((numColumn + 1) * 0.5f) / numColumn);
        if (numColumn > 27) {
            a.setFont(Font.font("Arial", FontWeight.NORMAL, 11));
        } else {
            a.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        }
        a.setAlignment(Pos.CENTER);

        return a;
    }

    private void showInfoDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
