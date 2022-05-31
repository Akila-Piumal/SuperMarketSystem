package lk.ijse.pos.view.tdm;

import java.time.LocalDate;

public class IncomeTM {
    private LocalDate date;
    private int orderCount;
    private double income;

    public IncomeTM() {
    }

    public IncomeTM(LocalDate date, int orderCount, double income) {
        this.date = date;
        this.orderCount = orderCount;
        this.income = income;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "IncomeTM{" +
                "date=" + date +
                ", orderCount=" + orderCount +
                ", income=" + income +
                '}';
    }
}
