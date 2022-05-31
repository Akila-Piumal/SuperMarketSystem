package lk.ijse.pos.bo.Custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AnnualIncomeBO extends SuperBO {
    ArrayList<CustomDTO> getAnnualIncomeDetails() throws SQLException, ClassNotFoundException;
}
