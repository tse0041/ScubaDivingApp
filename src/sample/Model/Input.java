package sample.Model;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;

/**
 * The abstract class for all types of Textfield in the app
 *
 * Created by Phan on 4/4/18.
 *
 * @attr controlAnchor is the control object (Textfield) of the input
 * @attr value is what inside the controlAnchor
 *
 */
public abstract class Input {
    private Control controlAnchor = null;
    private String value = null;

    public Input() {}

    public Input(Control controlAnchor) {
        this.controlAnchor = controlAnchor;
    }

    public String getValue() {
        if(controlAnchor != null) {
            this.value = ((TextField) controlAnchor).getText();
        }
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Control getControlAnchor() {
        return controlAnchor;
    }

    public void setControlAnchor(Control controlAnchor) {
        this.controlAnchor = controlAnchor;
    }

    /**
     * This is the class for validate the object's value by itself
     * @return
     */
    public abstract String validate();
}
