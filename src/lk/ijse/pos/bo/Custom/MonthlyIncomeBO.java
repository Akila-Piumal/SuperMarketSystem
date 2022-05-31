package lk.ijse.pos.bo.Custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MonthlyIncomeBO extends SuperBO {
    ArrayList<CustomDTO> getMonthlyIncomeDetails() throws SQLException, ClassNotFoundException;
}
