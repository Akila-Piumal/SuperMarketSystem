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
    public boolean save(Item item) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO item VALUES(?,?,?,?,?)",item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand());
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Item SET Description=?, PackSize=?, UnitPrice=?,QtyOnHand=? WHERE ItemCode=?", entity.getDescription(), entity.getPackSize(), entity.getUnitPrice(),entity.getQtyOnHand(),entity.getItemCode());
    }

    @Override
    public boolean delete(String itemCode) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM item WHERE itemCode=?",itemCode);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT itemCode FROM item ORDER BY itemCode DESC LIMIT 1");
        return resultSet.next() ? String.format("I%03d", (Integer.parseInt(resultSet.getString("itemCode").replace("I", "")) + 1)) : "I001";
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

    @Override
    public boolean updateQtyOnHand(String itemCode, int qtyOnHand) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE item SET QtyOnHand=? WHERE itemCode=?", qtyOnHand, itemCode);
    }
}
