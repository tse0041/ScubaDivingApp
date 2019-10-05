package sample.Model;

import javafx.scene.control.Control;
import sample.Controller.Utils;

/**
 * This is depth class which inherited input
 *
 * Created by Phan on 26/3/18.
 */
public class Depth extends Input{
    public Depth() {}

    public Depth(Control controlAnchor) {
        super(controlAnchor);
    }

    public Depth(String v) {
        this.setValue(v);
    }

    @Override
    public String validate() {
        return Utils.getValidationString(
                Utils.validationCode(Utils.TYPE_DEPTH, this.getValue()));
    }
}
