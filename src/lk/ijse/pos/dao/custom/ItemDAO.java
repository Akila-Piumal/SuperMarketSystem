package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.entity.Item;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item,String> {
    boolean updateQtyOnHand(String itemCode,int qtyOnHand) throws SQLException, ClassNotFoundException;
}
