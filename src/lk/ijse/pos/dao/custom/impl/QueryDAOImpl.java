package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.entity.Custom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<Custom> getOrderDetails(String orderID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select item.ItemCode,item.Description,item.UnitPrice,orderdetail.OrderQTY from orderdetail inner join item on orderdetail.ItemCode = item.ItemCode where orderdetail.OrderID=?", orderID);
        ArrayList<Custom> orderDetails=new ArrayList<>();
        while (resultSet.next()){
            orderDetails.add(new Custom(resultSet.getString(1),resultSet.getString(2),resultSet.getBigDecimal(3),resultSet.getInt(4)));
        }
        return orderDetails;
    }
}
