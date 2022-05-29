package lk.ijse.pos.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDetailsDTO {
    private String orderID;
    private String itemCode;
    private int qty;
    private double discount;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(String orderID, String itemCode, int qty, double discount) {
        this.orderID = orderID;
        this.itemCode = itemCode;
        this.qty = qty;
        this.discount = discount;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
