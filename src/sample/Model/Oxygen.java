package sample.Model;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import sample.Controller.Utils;

/**
 * This is oxygen class which inherited input
 *
 * Created by Phan on 26/3/18.
 */
public class Oxygen extends Input {
    public Oxygen() {}

    public Oxygen(Control controlAnchor) {
        super(controlAnchor);
    }

    public Oxygen(String v) {
        this.setValue(v);
    }

    @Override
    public String validate() {
        return Utils.getValidationString(
                Utils.validationCode(Utils.TYPE_OXY, this.getValue()));
    }
}
