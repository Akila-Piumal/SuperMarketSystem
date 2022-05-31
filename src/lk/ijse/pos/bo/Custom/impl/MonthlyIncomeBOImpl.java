package lk.ijse.pos.bo.Custom.impl;

import lk.ijse.pos.bo.Custom.MonthlyIncomeBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.entity.Custom;

import java.sql.SQLException;
import java.util.ArrayList;

public class MonthlyIncomeBOImpl implements MonthlyIncomeBO {

    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public ArrayList<CustomDTO> getMonthlyIncomeDetails() throws SQLException, ClassNotFoundException {
        ArrayList<Custom> monthlyIncomeDetails = queryDAO.getMonthlyIncomeDetails();
        ArrayList<CustomDTO> AllIncomeDetails=new ArrayList<>();
        for (Custom monthlyIncomeDetail : monthlyIncomeDetails) {
            AllIncomeDetails.add(new CustomDTO(monthlyIncomeDetail.getYearAndMonth(),monthlyIncomeDetail.getOrderCount(),monthlyIncomeDetail.getTotal()));
        }
        return AllIncomeDetails;
    }
}
