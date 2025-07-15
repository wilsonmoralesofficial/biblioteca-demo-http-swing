package segurosgyt.wsoc.models;

public class Book {

    private int idBook;
    private int version;
    private String title;
    private String autor;
    private int postYear;

    public int getIdBook() {
        return idBook;
    }

    public int getVersion() {return version;}

    public String getTitle() {
        return title;
    }

    public String getAutor() {
        return autor;
    }

    public int getPostYear() {
        return postYear;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public void setVersion(int version) {this.version = version;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setPostYear(int postYear) {
        this.postYear = postYear;
    }
}
