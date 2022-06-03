package lk.ijse.pos.bo.Custom.impl;

import lk.ijse.pos.bo.Custom.ManageOrderBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.dao.custom.*;
import lk.ijse.pos.dto.*;
import lk.ijse.pos.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrderBOImpl implements ManageOrderBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);

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
            orderDetails.add(new CustomDTO(detail.getItemCode(),detail.getDescription(),detail.getQtyOnHand(),detail.getUnitPrice(),detail.getQty()));
        }
        return orderDetails;
    }

    @Override
    public boolean updateItemDetails(String itemCode, int qtyOnHand) throws SQLException, ClassNotFoundException {
        return itemDAO.updateQtyOnHand(itemCode,qtyOnHand);
    }

    @Override
    public ItemDTO getItemDetails(String itemCode) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(itemCode);
        return new ItemDTO(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand());
    }

    @Override
    public boolean deleteItemFromOrder(String orderID, String itemCode) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.deleteItemFromOrder(orderID,itemCode);
    }

    @Override
    public boolean updateOrderDetails(OrderDetailsDTO dto) throws SQLException, ClassNotFoundException {
        return orderDetailDAO.update(new OrderDetail(dto.getOrderID(),dto.getItemCode(),dto.getQty(),dto.getDiscount()));
    }

    @Override
    public boolean removeOrderAndOrderDetails(String orderID) throws SQLException, ClassNotFoundException {
        if (orderDetailDAO.delete(orderID)) {
            if (orderDAO.delete(orderID)) {
                return true;
            }
        }
        return false;
    }

}
