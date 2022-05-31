package lk.ijse.pos.bo.Custom.impl;

import lk.ijse.pos.bo.Custom.DailyIncomeBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.entity.Custom;

import java.sql.SQLException;
import java.util.ArrayList;

public class DailyIncomeBOImpl implements DailyIncomeBO {
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public ArrayList<CustomDTO> getDailyIncomeDetails() throws SQLException, ClassNotFoundException {
        ArrayList<Custom> dailyIncomeDetails = queryDAO.getDailyIncomeDetails();
        ArrayList<CustomDTO> allDetails=new ArrayList<>();
        for (Custom detail : dailyIncomeDetails) {
            allDetails.add(new CustomDTO(detail.getDate(),detail.getOrderCount(),detail.getTotal()));
        }
        return allDetails;
    }
}
