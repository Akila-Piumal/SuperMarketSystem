package lk.ijse.pos.bo.Custom.impl;

import lk.ijse.pos.bo.Custom.AnnualIncomeBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.entity.Custom;

import java.sql.SQLException;
import java.util.ArrayList;

public class AnnualIncomeBOImpl implements AnnualIncomeBO {

    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public ArrayList<CustomDTO> getAnnualIncomeDetails() throws SQLException, ClassNotFoundException {
        ArrayList<Custom> annualIncomeDetails = queryDAO.getAnnualIncomeDetails();
        ArrayList<CustomDTO> allDetails=new ArrayList<>();
        for (Custom detail : annualIncomeDetails) {
            allDetails.add(new CustomDTO(detail.getYearAndMonth(),detail.getOrderCount(),detail.getTotal()));
        }
        return allDetails;
    }
}
