package lk.ijse.pos.bo.Custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LeastMovableItemsBO extends SuperBO {
    ArrayList<CustomDTO> getLeastMovableItemsDetails() throws SQLException, ClassNotFoundException;
}
