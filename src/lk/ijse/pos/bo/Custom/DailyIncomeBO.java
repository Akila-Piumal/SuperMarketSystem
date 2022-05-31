package lk.ijse.pos.bo.Custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DailyIncomeBO extends SuperBO {
    ArrayList<CustomDTO> getDailyIncomeDetails() throws SQLException, ClassNotFoundException;
}
