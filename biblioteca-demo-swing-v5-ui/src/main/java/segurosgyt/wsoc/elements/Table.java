package segurosgyt.wsoc.elements;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Table{

    public JScrollPane listScrollPane;
    public JTable listTable;
    public JPanel listPanel;

    public JPanel createTablePanel(String[][] Data ,String[] Columns){
        listPanel = new JPanel(new BorderLayout());
        DefaultTableModel tableModel = new DefaultTableModel(Data,Columns);
        listTable = new JTable(tableModel);
        listScrollPane = new JScrollPane(listTable);
        listPanel.add(listScrollPane);
        return listPanel;
    }

    public JPanel addPanelTable(String[][] Data , String[] Columns){
        listPanel = createTablePanel(Data ,Columns);
        return listPanel;
    }
}
