package lk.ijse.pos.bo.Custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MostMovableItemsBO extends SuperBO {
    ArrayList<CustomDTO> getMostMovableItemDetails() throws SQLException, ClassNotFoundException;
}
