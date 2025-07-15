package segurosgyt.wsoc;

public class Inicializer {


    public static void main(String[] args) {
        try{
            BookController bookController = new BookController();
            bookController.createBookEndPoints();
        }catch (RuntimeException e)
        {
            throw new RuntimeException(e);
        }
    }

}
