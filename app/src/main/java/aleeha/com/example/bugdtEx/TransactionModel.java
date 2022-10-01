package aleeha.com.example.bugdtEx;
import java.time.LocalDateTime;

public class TransactionModel {
    private String description, expenseField, date, time;
    private int transAmount,position;
    private boolean moneyAdded;
    private int id;

    public TransactionModel(int id, String expenseField, int transAmount,String date, String time, String description,boolean moneyAdded,int position) {
        this.id = id;
        this.description = description;
        this.expenseField = expenseField;
        this.date = date;
        this.time = time;
        this.transAmount = transAmount;
        this.moneyAdded = moneyAdded;
        this.position = position;
    }



    public TransactionModel() {
        description = "";
        expenseField = "Others";
        transAmount = 0;
        date = "";
        time = "";
        position=-2;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpenseField() {
        return expenseField;
    }

    public void setExpenseField(String expenseField) {
        this.expenseField = expenseField;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(int transAmount) {
        this.transAmount = transAmount;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isMoneyAdded() {
        return moneyAdded;
    }

    public void setMoneyAdded(boolean moneyAdded) {
        this.moneyAdded = moneyAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "TransactionModel{" +
                "description='" + description + '\'' +
                ", expenseField='" + expenseField + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", transAmount=" + transAmount +
                ", position=" + position +
                ", moneyAdded=" + moneyAdded +
                ", id=" + id +
                '}';
    }
}
