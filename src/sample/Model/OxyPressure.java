package sample.Model;

import javafx.scene.control.Control;
import sample.Controller.Utils;

/**
 * This is oxygen pressure class which inherited input
 *
 * Created by Phan on 26/3/18.
 */
public class OxyPressure extends Input{
    public OxyPressure() {}

    public OxyPressure(Control controlAnchor) {
        super(controlAnchor);
    }

    @Override
    public String validate() {
        return Utils.getValidationString(
                Utils.validationCode(Utils.TYPE_OXY_PRESSURE, this.getValue()));
    }
}
