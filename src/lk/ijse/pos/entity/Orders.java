package lk.ijse.pos.entity;

import java.time.LocalDate;

public class Orders {
    private String orderID;
    private LocalDate date;
    private String custID;

    public Orders() {
    }

    public Orders(String orderID, LocalDate date, String custID) {
        this.orderID = orderID;
        this.date = date;
        this.custID = custID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }
}
