package lk.ijse.pos.bo.Custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageOrderBO extends SuperBO {
    ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> getEachCustomerOrders(String custID) throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> getOrderDetails(String orderID) throws SQLException, ClassNotFoundException;

    boolean updateItemDetails(String itemCode, int qty) throws SQLException, ClassNotFoundException;

    ItemDTO getItemDetails(String itemCode) throws SQLException, ClassNotFoundException;

    boolean deleteItemFromOrder(String orderID, String itemID) throws SQLException, ClassNotFoundException;

    boolean updateOrderDetails(OrderDetailsDTO dto) throws SQLException, ClassNotFoundException;

    boolean removeOrderAndOrderDetails(String orderID) throws SQLException, ClassNotFoundException;
}
