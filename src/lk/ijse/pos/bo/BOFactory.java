package lk.ijse.pos.bo;

import lk.ijse.pos.bo.Custom.impl.LoginBOImpl;

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
        LOGIN
    }

    public SuperBO getBO(BOTypes types){
        switch (types){
            case LOGIN:
                return new LoginBOImpl();
            default:
                return null;
        }
    }
}
