package lk.ijse.pos.bo.Custom.impl;

import lk.ijse.pos.bo.Custom.ManageOrderBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrderBOImpl implements ManageOrderBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

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
}
