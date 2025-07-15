package segurosgyt.wsoc.bookList;

import segurosgyt.wsoc.ViewConstants;
import segurosgyt.wsoc.bookForm.BookForm;
import segurosgyt.wsoc.elements.Button;
import segurosgyt.wsoc.elements.InitialFrame;
import segurosgyt.wsoc.elements.Table;
import segurosgyt.wsoc.models.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BookList {

    private final InitialFrame InitialWindow = new InitialFrame();

    private JPanel JPanelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

    private final Button bookButton =  new Button();

    private final Table bookTable = new Table();

    private String[][] dataJTable = {};

    private List<Book> currentListBook = new ArrayList<>();

    private final BookForm bookForm = new BookForm();

    private BookListRequest bookListRequest = new BookListRequest();


    public void initViewBookList(){
        InitialWindow.configureInitialWindow();
        createInitialView(true);
    }

    public void createInitialView(boolean getBooksFromDataBase){
        createButtonsTable();
        createBookTable(getBooksFromDataBase);
    }

    private void createButtonsTable(){
        JPanelButton.add(createBookButtonRight(actionButtonCreateBook()));
        JPanelButton.add(editBookButtonRight());
        JPanelButton.add(deleteBookButtonRight());
        InitialWindow.add(JPanelButton, BorderLayout.NORTH);
    }

    private JButton createBookButtonRight(ActionListener actionListenerButtonCreateRight){
        return bookButton.createRightButtonPanel(ViewConstants.textButtonCreateBook,actionListenerButtonCreateRight);
    }

    public ActionListener actionButtonCreateBook(){
        return e -> {
            InitialWindow.setVisible(false);
            bookForm.removeBooksForm();
            bookForm.createInitialView();
            bookForm.validateEditionMode(false,new Book());
            removeBooksTable();
            BookForm.InitialWindow.setVisible(true);
        };
    }

    private JButton editBookButtonRight(){
        return bookButton.createRightButtonPanel(ViewConstants.textButtonEditBook,actionButtonEditBook());
    }

    public ActionListener actionButtonEditBook(){
        return e -> {
            if (bookTable.listTable.getSelectedRow() != -1)
            {
                bookForm.removeBooksForm();
                bookForm.createInitialView();
                bookForm.validateEditionMode(BookForm.editSelectedRow(bookTable.listTable.getSelectedRow(),currentListBook),currentListBook.get(bookTable.listTable.getSelectedRow()));
                BookForm.InitialWindow.setVisible(true);
                showBookListPanel(false);
            }else{
                JOptionPane.showMessageDialog(InitialWindow, ViewConstants.textSelectBookToEditMessageDialog);
            }
        };
    }
    private JButton deleteBookButtonRight(){
        return bookButton.createRightButtonPanel(ViewConstants.textButtonDeleteBook,actionButtonDeleteBook());
    }

    public  ActionListener actionButtonDeleteBook(){
        return e -> {
            try{
                if (bookTable.listTable.getSelectedRow() != -1){
                    if (bookListRequest.deleteBook(currentListBook.get(bookTable.listTable.getSelectedRow())).get()){
                        removeBooksTable();
                        createInitialView(true);
                        JOptionPane.showMessageDialog(InitialWindow, ViewConstants.textDeleteBookSuccessMessageDialog);
                    }else {
                        JOptionPane.showMessageDialog(InitialWindow, ViewConstants.textDeleteBookErrorMessageDialog);
                    }
                }else {
                    JOptionPane.showMessageDialog(InitialWindow, ViewConstants.textSelectABookToDeleteMessageDialog);
                }
            } catch (Exception ex) {
                System.out.println("Error ==> actionButtonDeleteBook ==> " + ex);
                JOptionPane.showMessageDialog(InitialWindow, "Ocurrio un error inesperado al eliminar el libro.");
            }
        };
    }

    private void createBookTable(boolean getBooksFromDataBase){
        dataJTable = getBooksFromDataBase ?getBooksAvailable():dataJTable;
        InitialWindow.add(bookTable.addPanelTable(dataJTable, ViewConstants.columnsBook));
        InitialWindow.setVisible(true);
    }

    public void showBookListPanel(boolean show){
        InitialWindow.setVisible(show);
    }

    public void removeBooksTable(){
        InitialWindow.remove(JPanelButton);
        JPanelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        InitialWindow.remove(bookTable.listPanel);
    }

    private String[][] getBooksAvailable(){
        try {
            currentListBook = bookListRequest.getBooks().get();
            return validateListBook(currentListBook);
        }catch (Exception e){
            JOptionPane.showMessageDialog(InitialWindow, "Error al consultar datos =>" +e.getMessage());
            return new String[][]{};
        }
    }

    private String[][] validateListBook(List<Book> currentBooks){
        List<String[]> booksDataString = new ArrayList<>();
        String[][] booksString = {};
        if (currentBooks == null){
            JOptionPane.showMessageDialog(InitialWindow, ViewConstants.textErrorGetBooksMessageDialog);
        }else {
            iterateCurrentBooks(currentBooks,booksDataString);
            booksString = booksDataString.toArray(new String[booksDataString.size()][]);
        }
        return booksString;
    }

    private void iterateCurrentBooks(List<Book> currentBooks,List<String[]> booksDataString){
        for (Book currentBook : currentBooks) {
            String[] stringBookData =
                    {currentBook.getTitle(), currentBook.getAutor(),
                            Integer.toString(currentBook.getPostYear())};
            booksDataString.add(stringBookData);
        }
    }

}
