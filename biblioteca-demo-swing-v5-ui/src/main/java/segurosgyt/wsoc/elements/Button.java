package segurosgyt.wsoc.elements;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Button {

    public JButton button;

    public JButton createRightButtonPanel(String buttonText, ActionListener actionListener){

        button = new JButton(buttonText);
        button.addActionListener(actionListener);
        return button;
    }
}
