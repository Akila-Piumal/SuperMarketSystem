package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.entity.Custom;

import javax.swing.text.DateFormatter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<Custom> getOrderDetails(String orderID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select item.ItemCode,item.Description,item.UnitPrice,orderdetail.OrderQTY from orderdetail inner join item on orderdetail.ItemCode = item.ItemCode where orderdetail.OrderID=?", orderID);
        ArrayList<Custom> orderDetails=new ArrayList<>();
        while (resultSet.next()){
            orderDetails.add(new Custom(resultSet.getString(1),resultSet.getString(2),resultSet.getBigDecimal(3),resultSet.getInt(4)));
        }
        return orderDetails;
    }

    @Override
    public ArrayList<Custom> getDailyIncomeDetails() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT o.OrderDate ,count(OD.OrderID) , SUM((unitPrice*OrderQTY)-Discount) from orders o RIGHT JOIN orderdetail OD ON o.OrderID = OD.OrderID LEFT JOIN item i on OD.ItemCode = i.ItemCode GROUP BY o.OrderDate");
        ArrayList<Custom> incomeDetails=new ArrayList<>();
        while (resultSet.next()){
            incomeDetails.add(new Custom(LocalDate.parse(resultSet.getString(1)),resultSet.getInt(2),resultSet.getDouble(3)));
        }
        return incomeDetails;
    }

    @Override
    public ArrayList<Custom> getMonthlyIncomeDetails() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT date_format(o.OrderDate,'%Y-%M') ,count(OD.OrderID) , SUM((unitPrice*OrderQTY)-Discount) from orders o RIGHT JOIN orderdetail OD ON o.OrderID = OD.OrderID LEFT JOIN item i on OD.ItemCode = i.ItemCode GROUP BY year(OrderDate),month(OrderDate) order by year(o.OrderDate),month(OrderDate)");
        ArrayList<Custom> incomeDetails=new ArrayList<>();
        while (resultSet.next()){
            incomeDetails.add(new Custom(resultSet.getString(1),resultSet.getInt(2),resultSet.getDouble(3)));
        }
        return incomeDetails;
    }

    @Override
    public ArrayList<Custom> getAnnualIncomeDetails() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT year(OrderDate ) ,count(OD.OrderID) , SUM((unitPrice*OrderQTY)-Discount) from orders o RIGHT JOIN orderdetail OD ON o.OrderID = OD.OrderID LEFT JOIN item i on OD.ItemCode = i.ItemCode GROUP BY year(OrderDate) order by year(o.OrderDate)");
        ArrayList<Custom> incomeDetails=new ArrayList<>();
        while (resultSet.next()){
            incomeDetails.add(new Custom(resultSet.getString(1),resultSet.getInt(2),resultSet.getDouble(3)));
        }
        return incomeDetails;
    }
}
