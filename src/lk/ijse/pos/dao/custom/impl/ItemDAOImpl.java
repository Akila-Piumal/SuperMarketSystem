package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM Item");
        ArrayList<Item> items=new ArrayList<>();
        while (resultSet.next()){
            items.add(new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getBigDecimal(4),resultSet.getInt(5)));
        }
        return items;
    }

    @Override
    public boolean save(Item dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Item SET Description=?, PackSize=?, UnitPrice=?,QtyOnHand=? WHERE ItemCode=?", entity.getDescription(), entity.getPackSize(), entity.getUnitPrice(),entity.getQtyOnHand(),entity.getItemCode());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT itemCode from item WHERE itemCode=?", code);
        return resultSet.next();
    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT * FROM item WHERE ItemCode=?", code);
        if (resultSet.next()){
            return new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getBigDecimal(4),resultSet.getInt(5));
        }
        return null;
    }
}
