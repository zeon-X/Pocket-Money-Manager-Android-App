package aleeha.com.example.bugdtEx;

public class LendBorrowModel {

    private int id;
    private String fieldName;
    private int amount;
    private String personName,date,returnDate,note;
    private boolean status;
    private int idInTransaction;

    //constructor||  , int idInTransaction
    public LendBorrowModel(int id, String fieldName, int amount, String personName, String date, String returnDate, String note, boolean status ) {
        this.id = id;
        this.fieldName = fieldName;
        this.amount = amount;
        this.personName = personName;
        this.date = date;
        this.returnDate = returnDate;
        this.note = note;
        this.status = status;
        //this.idInTransaction=idInTransaction;
    }

    //getter-setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    //to-String || for printing purpose
    @Override
    public String toString() {
        return "LendBorrowModel{" +
                "id=" + id +
                ", fieldName='" + fieldName + '\'' +
                ", amount=" + amount +
                ", personName='" + personName + '\'' +
                ", date='" + date + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", note='" + note + '\'' +
                ", status=" + status +
                '}';
    }
}
