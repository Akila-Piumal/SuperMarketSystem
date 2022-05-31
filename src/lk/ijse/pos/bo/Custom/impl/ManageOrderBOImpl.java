package lk.ijse.pos.bo.Custom.impl;

import lk.ijse.pos.bo.Custom.ManageOrderBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.entity.Custom;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrderBOImpl implements ManageOrderBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers=new ArrayList<>();
        for (Customer ent : customerDAO.getAll()) {
            allCustomers.add(new CustomerDTO(ent.getCustID(),ent.getCustTitle(),ent.getCustName(),ent.getCustAddress(),ent.getCity(),ent.getProvince(),ent.getPostalCode()));
        }
        return allCustomers;
    }

    @Override
    public ArrayList<OrderDTO> getEachCustomerOrders(String custID) throws SQLException, ClassNotFoundException {
        ArrayList<Orders> orders = orderDAO.getFromCustomerID(custID);
        ArrayList<OrderDTO> orderDTOS=new ArrayList<>();
        for (Orders order : orders) {
            orderDTOS.add(new OrderDTO(order.getOrderID(),order.getDate(),order.getCustID()));
        }
        return orderDTOS;
    }

    @Override
    public ArrayList<CustomDTO> getOrderDetails(String orderID) throws SQLException, ClassNotFoundException {
        ArrayList<Custom> all = queryDAO.getOrderDetails(orderID);
        ArrayList<CustomDTO> orderDetails=new ArrayList<>();
        for (Custom detail : all) {
            orderDetails.add(new CustomDTO(detail.getItemCode(),detail.getDescription(),detail.getUnitPrice(),detail.getQty()));
        }
        return orderDetails;
    }

    @Override
    public boolean updateItemDetails(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(itemCode);
        item.setQtyOnHand(qty+item.getQtyOnHand());
        return itemDAO.update(item);
    }

    @Override
    public ItemDTO getItemDetails(String itemCode) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(itemCode);
        return new ItemDTO(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand());
    }

}
