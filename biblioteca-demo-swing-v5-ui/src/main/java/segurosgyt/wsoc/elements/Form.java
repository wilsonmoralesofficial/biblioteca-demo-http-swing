package segurosgyt.wsoc.elements;


import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Form extends JPanel {

    private final Map<String, JTextField> fieldsMap = new LinkedHashMap<>();

    public Form(){
    }

    public GridBagConstraints configureGridBagConstraints(){
        GridBagConstraints gbc = new GridBagConstraints();//Tiene varias propiedades que se pueden
        // modificar para controlar el diseño
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes
        gbc.anchor = GridBagConstraints.WEST; //Controla cómo se alinea un componente dentro de
        // su celda si el componente es más pequeño que el espacio que tiene asignado
        return gbc;
    }
    public void buildDynamicForm(String[] fieldNames,GridBagConstraints gbc){
        int row = 0;
        for (String fieldName : fieldNames) {
            // Crea la etiqueta descriptiva del campo
            createLabelField(fieldName,gbc,row);
            // Crea Campo de texto (JTextField)
            createTextField(fieldName,gbc,row);
            row++; // Siguiente fila para el siguiente campo
        }
    }

    public void createLabelField(String fieldName, GridBagConstraints gbc, int row){
        JLabel label = new JLabel(fieldName + ":");
        gbc.gridx = 0; // Columna 0 para las etiquetas
        gbc.gridy = row; // Fila actual
        gbc.weightx = 0; // No se expande horizontalmente
        gbc.fill = GridBagConstraints.NONE; // No rellena espacio
        add(label, gbc);
    }

    public void createTextField(String fieldName,GridBagConstraints gbc, int row){
        JTextField textField = new JTextField(10); // Ancho inicial de 20 columnas
        fieldsMap.put(fieldName, textField); // Guardamos la referencia al JTextField
        gbc.gridx = 1; // Columna 1 para los campos de texto
        gbc.gridy = row; // Fila actual
        gbc.weightx = 1.0; // Se expande horizontalmente para ocupar espacio disponible
        gbc.fill = GridBagConstraints.HORIZONTAL; // Rellena el espacio horizontalmente
        add(textField, gbc);
    }

    public Map<String, String> getFieldValues() {
        Map<String, String> values = new LinkedHashMap<>();
        for (Map.Entry<String, JTextField> entry : fieldsMap.entrySet()) {
            values.put(entry.getKey(), entry.getValue().getText());
        }
        return values;
    }

    public void setFieldValues(Map<String, String> initialValues) {
        for (Map.Entry<String, String> entry : initialValues.entrySet()) {
            JTextField textField = fieldsMap.get(entry.getKey());
            if (textField != null) {
                textField.setText(entry.getValue());
            }
        }
    }
}
