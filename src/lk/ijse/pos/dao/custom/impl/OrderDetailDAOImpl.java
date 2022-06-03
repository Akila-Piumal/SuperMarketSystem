package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO orderdetail VALUES(?,?,?,?)",orderDetail.getOrderID(),orderDetail.getItemCode(),orderDetail.getQty(),orderDetail.getDiscount());
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE orderdetail SET OrderQTY=? ,Discount=? WHERE OrderID=? AND ItemCode=?",entity.getQty(),entity.getDiscount(),entity.getOrderID(),entity.getItemCode());
    }

    @Override
    public boolean delete(String orderID) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM orderdetail WHERE OrderID=?",orderID);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean deleteItemFromOrder(String orderID, String itemCode) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM orderdetail WHERE OrderID=? AND ItemCode=?",orderID,itemCode);
    }
}
