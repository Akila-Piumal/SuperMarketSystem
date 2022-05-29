package lk.ijse.pos.bo;

import lk.ijse.pos.bo.Custom.impl.LoginBOImpl;
import lk.ijse.pos.bo.Custom.impl.ManageOrderBOImpl;
import lk.ijse.pos.bo.Custom.impl.PlaceOrderBOImpl;

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
        LOGIN,PLACEORDER,MANAGEORDER
    }

    public SuperBO getBO(BOTypes types){
        switch (types){
            case LOGIN:
                return new LoginBOImpl();
            case PLACEORDER:
                return new PlaceOrderBOImpl();
            case MANAGEORDER:
                return new ManageOrderBOImpl();
            default:
                return null;
        }
    }
}
