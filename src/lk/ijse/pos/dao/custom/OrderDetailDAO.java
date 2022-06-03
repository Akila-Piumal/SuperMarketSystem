package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.entity.OrderDetail;

import java.sql.SQLException;

public interface OrderDetailDAO extends CrudDAO<OrderDetail,String> {
    boolean deleteItemFromOrder(String orderID, String itemCode) throws SQLException, ClassNotFoundException;
}
