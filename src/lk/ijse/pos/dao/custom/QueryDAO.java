package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.entity.Custom;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<Custom> getOrderDetails(String orderID) throws SQLException, ClassNotFoundException;

    ArrayList<Custom> getDailyIncomeDetails() throws SQLException, ClassNotFoundException;

    ArrayList<Custom> getMonthlyIncomeDetails() throws SQLException, ClassNotFoundException;

    ArrayList<Custom> getAnnualIncomeDetails() throws SQLException, ClassNotFoundException;

    ArrayList<Custom> getMostMovableItemDetails() throws SQLException, ClassNotFoundException;

    ArrayList<Custom> getLeastMovableItemDetails() throws SQLException, ClassNotFoundException;
}
