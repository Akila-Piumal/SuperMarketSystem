package lk.ijse.pos.bo.Custom.impl;

import lk.ijse.pos.bo.Custom.ManageOrderBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrderBOImpl implements ManageOrderBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers=new ArrayList<>();
        for (Customer ent : customerDAO.getAll()) {
            allCustomers.add(new CustomerDTO(ent.getCustID(),ent.getCustTitle(),ent.getCustName(),ent.getCustAddress(),ent.getCity(),ent.getProvince(),ent.getPostalCode()));
        }
        return allCustomers;
    }
}
