package lk.ijse.pos.bo.Custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageOrderBO extends SuperBO {
    ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> getEachCustomerOrders(String custID) throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> getOrderDetails(String orderID) throws SQLException, ClassNotFoundException;

    boolean updateItemDetails(String itemCode,int qty) throws SQLException, ClassNotFoundException;

    ItemDTO getItemDetails(String itemCode) throws SQLException, ClassNotFoundException;
}
