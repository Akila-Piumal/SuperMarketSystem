package lk.ijse.pos.bo.Custom.impl;

import lk.ijse.pos.bo.Custom.MostMovableItemsBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.entity.Custom;

import java.sql.SQLException;
import java.util.ArrayList;

public class MostMovableItemsBOImpl implements MostMovableItemsBO {

    private final QueryDAO queryDAO= (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

    @Override
    public ArrayList<CustomDTO> getMostMovableItemDetails() throws SQLException, ClassNotFoundException {
        ArrayList<Custom> mostMovableItemDetails = queryDAO.getMostMovableItemDetails();
        ArrayList<CustomDTO> itemDetails=new ArrayList<>();
        for (Custom detail : mostMovableItemDetails) {
            itemDetails.add(new CustomDTO(detail.getItemCode(),detail.getDescription(),detail.getUnitPrice(),detail.getQtyOnHand(),detail.getOrderCount(),detail.getTotal()));
        }
        return itemDetails;
    }
}
