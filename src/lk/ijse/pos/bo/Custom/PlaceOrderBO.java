package lk.ijse.pos.bo.Custom;

import lk.ijse.pos.bo.SuperBO;

import java.sql.SQLException;

public interface PlaceOrderBO extends SuperBO {
    String generateNewOrderID() throws SQLException, ClassNotFoundException;
}
