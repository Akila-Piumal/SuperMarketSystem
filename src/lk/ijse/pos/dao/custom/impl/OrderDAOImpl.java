package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Orders order) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO orders VALUES(?,?,?)",order.getOrderID(),order.getDate(),order.getCustID());
    }

    @Override
    public boolean update(Orders dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String orderID) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM orders WHERE OrderID=?",orderID);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT OrderID from orders ORDER BY OrderID DESC LIMIT 1");
        if (resultSet.next()) {
            return String.format("OID-%03d", (Integer.parseInt(resultSet.getString("OrderID").replace("OID-", "")) + 1));
        }
        return "OID-001";
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT OrderID FROM orders WHERE OrderID=?", id).next();
    }

    @Override
    public Orders search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Orders> getFromCustomerID(String custID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM orders WHERE CustID=?", custID);
        ArrayList<Orders> orders=new ArrayList<>();
        while (resultSet.next()){
            orders.add(new Orders(resultSet.getString(1), LocalDate.parse(resultSet.getString(2)),resultSet.getString(3)));
        }
        return orders;
    }
}
