package segurosgyt.wsoc.copyBookForm;


import segurosgyt.wsoc.ViewConstants;
import segurosgyt.wsoc.bookForm.BookForm;
//import segurosgyt.wsoc.businessData.bookListBusinessData.CopyBookListBusinessDataImpl;
//import segurosgyt.wsoc.businessData.interfacesBookListBusinessData.CopyBookListBusinessData;
import segurosgyt.wsoc.elements.Button;
import segurosgyt.wsoc.elements.Form;
import segurosgyt.wsoc.elements.InitialFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

public class CopyBookForm {

    private static final JPanel jPanelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
    public static InitialFrame initialWindow = new InitialFrame(); // Crea un frame
    private static final Button copyBookButtonForm =  new Button();
    private static final Form copyBookForm = new Form();
    private static final CopyBookRequest copyBookRequest = new CopyBookRequest();

    public static void createInitialViewCopyBook(){
        initialWindow.configureInitialWindow();
        createPanelButton();
        createFormCopyBook();
    }

    private static void createPanelButton(){
        jPanelButton.add(createSaveButtonPanel(actionButtonSaveCopyBook()));
        jPanelButton.add(createBackButtonPanel(actionButtonBackBook()));
        initialWindow.add(jPanelButton, BorderLayout.NORTH);
    }

    private static JButton createSaveButtonPanel(ActionListener actioSave){
        return copyBookButtonForm.createRightButtonPanel(ViewConstants.textButtonSaveData,actioSave);
    }

    private static JButton createBackButtonPanel(ActionListener actionBack){
        return copyBookButtonForm.createRightButtonPanel(ViewConstants.textButtonBack,actionBack);
    }
    private static ActionListener actionButtonSaveCopyBook(){
        return e -> {
            try {
                validateSaveDataCopyBook(copyBookRequest.saveCopyBook(copyBookForm.getFieldValues(), BookForm.currentBook).get());
                navigateToBookForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(initialWindow, ex.getMessage());
            }

        };
    }


    private static ActionListener actionButtonBackBook(){
        return e -> {
            navigateToBookForm();
        };
    }

    private static void navigateToBookForm() {
        removeCopyBooksForm();
        initialWindow.setVisible(false);
        copyBookForm.setFieldValues(initValuesFormCopyBooks());
        BookForm bookForm = new BookForm();
        bookForm.removeBooksForm();
        bookForm.createInitialView();
        bookForm.validateEditionMode(true, BookForm.currentBook);
        BookForm.InitialWindow.setVisible(true);
    }

    public static void createFormCopyBook(){
        copyBookForm.buildDynamicForm(ViewConstants.fieldsFormCopyBook,ConfigureGridBagConstraints());
        initialWindow.add(copyBookForm, BorderLayout.CENTER);
    }

    // Define cómo se colocan los componentes en la cuadricula.
    private static GridBagConstraints ConfigureGridBagConstraints(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    public static void showFormCopyBookPanel(boolean show){
        initialWindow.setVisible(show);
    }

    private static void validateSaveDataCopyBook(boolean saveSuccess){
        if (saveSuccess){
            JOptionPane.showMessageDialog(initialWindow, ViewConstants.textSaveSuccessDataMessageDialog);
        }else{
            JOptionPane.showMessageDialog(initialWindow, ViewConstants.textErrorToSaveCopyBooksMessageDialog);
        }
    }

    public static Map<String, String> initValuesFormCopyBooks(){
        Map<String, String> initValues = new LinkedHashMap<>();
        for (int i = 0 ; i < ViewConstants.fieldsFormCopyBook.length ; i++){
            initValues.put(ViewConstants.fieldsFormCopyBook[i],ViewConstants.blankText);
        }
        return initValues;
    }

    public static void removeCopyBooksForm(){
        jPanelButton.removeAll();
        copyBookForm.removeAll();
    }
}
