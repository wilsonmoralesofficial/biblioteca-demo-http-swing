package segurosgyt.wsoc.models;

public class CopyBook {

    private int idCopyBook;
    private int idBook;
    private String isbn;
    private String phisicalState;
    private String entryDate;
    private String creationDate;
    private String changeDate;
    private String createdBy;
    private String changedBy;

    public int getIdCopyBook() {
        return idCopyBook;
    }

    public int getIdBook() {
        return idBook;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPhisicalState() {
        return phisicalState;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setIdCopyBook(int idCopyBook) {
        this.idCopyBook = idCopyBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPhisicalState(String phisicalState) {
        this.phisicalState = phisicalState;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
}
