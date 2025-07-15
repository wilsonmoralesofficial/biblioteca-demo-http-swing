package segurosgyt.wsoc.elements;

import segurosgyt.wsoc.ViewConstants;
import javax.swing.*;
import java.awt.*;

public class InitialFrame extends JFrame{

    public InitialFrame (){
        super(ViewConstants.tittleInitialFrame); // Crea una nueva ventana inicialmente invisible.
    }

    public void configureInitialWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Termina el programa al moemnto de cerra la ventana.
        setSize(700, 500); // Asigna las dimensiones de la ventana.
        setLocationRelativeTo(null); // Centra la ventana.
        setLayout(new BorderLayout());
    }
}
