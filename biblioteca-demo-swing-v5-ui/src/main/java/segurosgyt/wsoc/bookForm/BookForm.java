package segurosgyt.wsoc.bookForm;

import segurosgyt.wsoc.ViewConstants;
import segurosgyt.wsoc.bookList.BookList;

import segurosgyt.wsoc.copyBookForm.CopyBookForm;
import segurosgyt.wsoc.elements.Button;
import segurosgyt.wsoc.elements.Form;
import segurosgyt.wsoc.elements.InitialFrame;
import segurosgyt.wsoc.elements.Table;
import segurosgyt.wsoc.models.Book;
import segurosgyt.wsoc.models.CopyBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookForm{

    public static boolean editMode = false;

    public static JPanel JPanelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

    //Contenedor de los Jpanes de formulario y la tabla de ejemplares de cada libro
    public static  JPanel auxJPanelContainer = new JPanel();

    //Contenedor de la tabla
    public static JPanel jPanelTable = new JPanel();

    //Contenedor del formulario
    private static JPanel JPanelForm = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 2));

    // Crea un frame
    public static InitialFrame InitialWindow = new InitialFrame();

    //Instancia de la clase Form que maneja la logica de los formularios
    public static Form booksForm = new Form();

    //Instancia de la clase button que maneja la logica de la creación de botones
    public static Button bookButtonForm =  new Button();

    public static String[][] dataJTableCopyBook = {};

    public static List<CopyBook> currentListCopyBook = new ArrayList<>();

    // Instancia del modelo book que tendra los datos de libro que se está mostrando actualmente en el formulario
    public static Book currentBook = new Book();

    //Instancia de la clase table que maneja la logica para la creación de tablas en el Frame.
    public static Table bookCopyTable = new Table();

    //private final BookListBusinessData bookListBussinessData = new BookListBusinessDataImpl();

    public static Book dataEditBookForm = null;

    private BookFormRequest bookFormRequest = new BookFormRequest();



    public void createInitialView(){
        InitialWindow.configureInitialWindow();
        createPanelButton();
        createFormBook();
    }

    private void createPanelButton(){
        JPanelButton.add(createSaveButtonPanel(ViewConstants.textButtonSaveData,actionButtonSaveBook()));
        JPanelButton.add(createBackButtonPanel(ViewConstants.textButtonBack,actionButtonBackBook()));
        InitialWindow.add(JPanelButton,BorderLayout.NORTH);
    }

    private static void createFormBook(){
        GridBagConstraints gbc = new GridBagConstraints(); //Tiene varias propiedades que se pueden modificar para controlar el diseño.
        // Configuración básica para GridBagConstraints
        gbc.insets = new Insets(4, 4, 4, 4); // Espaciado entre componentes
        gbc.anchor = GridBagConstraints.WEST;
        booksForm.buildDynamicForm(ViewConstants.fieldsFormBook,gbc);// Contruimos el formulario desde la instancia de la clase
        addFormToPanelForm();
    }

    private static void addFormToPanelForm(){
        JPanelForm.add(booksForm,BorderLayout.CENTER);
        JPanelForm.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        auxJPanelContainer.setLayout(new BoxLayout(auxJPanelContainer, BoxLayout.Y_AXIS));
        auxJPanelContainer.add(JPanelForm);
        auxJPanelContainer.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    public void validateEditionMode(boolean editionMode,Book dataBook){
        if (!editionMode) {
            editMode = false;
            booksForm.setFieldValues(initValuesBookForm());
        } else {
            editMode = true;
            initValuesEditBookForm(dataBook);
        }
        InitialWindow.add(auxJPanelContainer);
    }

    private void initValuesEditBookForm(Book dataBook){
        currentBook = dataBook;
        booksForm.setFieldValues(mapDataEditForm(dataBook));
        JPanelButton.add(createCopyButtonPanel(ViewConstants.textButtonCreateCopyBook,actionButtonCreateCopyBook()));
        initializeTableCopyBook(true);
    }

    public  void validateSaveDataBook(boolean saveSuccess){
        if (saveSuccess){
            JOptionPane.showMessageDialog(InitialWindow, ViewConstants.textSaveSuccessDataMessageDialog);
        }else{
            JOptionPane.showMessageDialog(InitialWindow, ViewConstants.textErrorToSaveDataMessageDialog);
        }
    }

    public static JButton createSaveButtonPanel(String textButton, ActionListener actioSave){
        return createCopyButtonPanel(textButton, actioSave);
    }

    public  ActionListener actionButtonSaveBook(){
        return e -> {
            try {
                Boolean res = bookFormRequest.SaveBook(currentBook,booksForm.getFieldValues(),editMode).get();
                validateSaveDataBook(res);
                InitialWindow.setVisible(false);
                manageDataUpdateBooksTable();
            }catch (Exception ex){
                JOptionPane.showMessageDialog(InitialWindow,ex);
            }
        };
    }

    public static JButton createBackButtonPanel(String textButton, ActionListener actionBack){
        return createCopyButtonPanel(textButton, actionBack);
    }

    public ActionListener actionButtonBackBook(){
        return e -> {
            InitialWindow.setVisible(false);
            removeBooksForm();
            manageDataUpdateBooksTable();
        };
    }

    public static Map<String, String> initValuesBookForm(){
        Map<String, String> initValues = new LinkedHashMap<>();
        for (int i = 0; i < ViewConstants.fieldsFormBook.length ; i++)
        {
            initValues.put(ViewConstants.fieldsFormBook[i],ViewConstants.blankText);
        }
        return initValues;
    }

    public static Map<String, String> mapDataEditForm(Book dataBookForm){
        Map<String, String> initialValue = new LinkedHashMap<>();
        initialValue.put("Título", dataBookForm.getTitle());
        initialValue.put("Autor", dataBookForm.getAutor());
        initialValue.put("Año Publicación", Integer.toString(dataBookForm.getPostYear()));
        return initialValue;
    }

    public static JButton createCopyButtonPanel(String textButton, ActionListener actionBack){
        return bookButtonForm.createRightButtonPanel(textButton,actionBack);
    }

    public ActionListener actionButtonCreateCopyBook(){
        return e -> {
            InitialWindow.setVisible(false);
            removeBooksForm();
            CopyBookForm.createInitialViewCopyBook();
            CopyBookForm.showFormCopyBookPanel(true);
        };
    }


    public static void manageDataUpdateBooksTable(){
        BookList bookList = new BookList();
        bookList.initViewBookList();
        bookList.showBookListPanel(true);
    }

    public static boolean editSelectedRow(int selectedRow, List<Book> booksTable){
        dataEditBookForm = booksTable.get(selectedRow);
        return dataEditBookForm != null;
    }

    public void initializeTableCopyBook(boolean getCopyBooksFromDataBase){
        if (getCopyBooksFromDataBase)dataJTableCopyBook = getCopyBooksAvailable();
        createBookCopyTable(dataJTableCopyBook);
    }

    private String[][] getCopyBooksAvailable(){
        try{
            currentListCopyBook =  bookFormRequest.getCopyBooks(currentBook.getIdBook()).get();
            return validateListCopyBook(currentListCopyBook);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(InitialWindow, "Error al consultar datos =>" +e.getMessage());
            return new String[][]{};
        }
    }

    private void createBookCopyTable(String[][]data){
        jPanelTable = new JPanel();
        jPanelTable = bookCopyTable.addPanelTable(data, ViewConstants.columnsCopyBook);
        auxJPanelContainer.add(jPanelTable);
    }

    private String[][] validateListCopyBook(List<CopyBook> currentCopyBooks){
        List<String[]> copyBooksDataString = new ArrayList<>();
        String[][] copyBooksString = {};
        if (currentCopyBooks == null)
        {
            JOptionPane.showMessageDialog(InitialWindow,ViewConstants.textErrorToGetCopyBooksMessageDialog);
        }else
        {
            iterateCurrentCopyBooks(currentCopyBooks,copyBooksDataString);
            copyBooksString = copyBooksDataString.toArray(new String[copyBooksDataString.size()][]);
        }
        return copyBooksString;
    }

    private static void iterateCurrentCopyBooks(List<CopyBook> currentCopyBooks,List<String[]> copyBooksDataString){
        for (CopyBook currentCopyBook : currentCopyBooks) {
            String[] stringBookData =
                    {
                            currentCopyBook.getIsbn(),
                            currentCopyBook.getPhisicalState(),
                            currentCopyBook.getCreationDate()
                    };
            copyBooksDataString.add(stringBookData);
        }
    }


    public void removeBooksForm(){
        JPanelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        booksForm = new Form();
        auxJPanelContainer = new JPanel();
        JPanelForm = new JPanel();
        JPanelForm.removeAll();
        auxJPanelContainer.removeAll();
        InitialWindow.removeAll();
        InitialWindow = new InitialFrame();
    }

}
