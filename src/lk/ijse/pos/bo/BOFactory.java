package lk.ijse.pos.bo;

import lk.ijse.pos.bo.Custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        if (boFactory==null){
            boFactory=new BOFactory();
        }
        return boFactory;
    }

    public enum BOTypes{
        LOGIN,PLACEORDER,MANAGEORDER,MANAGEITEM,DAILYINCOME,MONTHLYINCOME
    }

    public SuperBO getBO(BOTypes types){
        switch (types){
            case LOGIN:
                return new LoginBOImpl();
            case PLACEORDER:
                return new PlaceOrderBOImpl();
            case MANAGEORDER:
                return new ManageOrderBOImpl();
            case MANAGEITEM:
                return new ManageItemBOImpl();
            case DAILYINCOME:
                return new DailyIncomeBOImpl();
            case MONTHLYINCOME:
                return new MonthlyIncomeBOImpl();
            default:
                return null;
        }
    }
}
