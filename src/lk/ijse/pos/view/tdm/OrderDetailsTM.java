package lk.ijse.pos.view.tdm;

import java.math.BigDecimal;

public class OrderDetailsTM {
    private String itemCode;
    private String description;
    private int qtyOnHand;
    private BigDecimal unitPrice;
    private double discount;
    private int qty;
    private double total;

    public OrderDetailsTM() {
    }

    public OrderDetailsTM(String itemCode, String description, int qtyOnHand, BigDecimal unitPrice, int qty) {
        this.itemCode = itemCode;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public OrderDetailsTM(String itemCode, String description, int qtyOnHand, BigDecimal unitPrice, double discount, int qty, double total) {
        this.itemCode = itemCode;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.qty = qty;
        this.total = total;
    }

    public OrderDetailsTM(String itemCode, String description, BigDecimal unitPrice, int qty) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetailsTM{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                ", qty=" + qty +
                ", total=" + total +
                '}';
    }
}
