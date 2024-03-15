package lk.ijse.bo;

import lk.ijse.bo.Custom.impl.*;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){

    }
    public static BoFactory getBoFactory(){
        return (boFactory==null)?boFactory=new BoFactory():boFactory;
    }
    public enum BoTypes{
       ADMIN,BOOK,BRANCH,TRANSACTION,USER
    }
    public SuperBo getBoType(BoTypes boTypes){
        switch (boTypes){
            case BOOK:return new BookBoImpl();
            case USER:return new UserBoImpl();
            case ADMIN:return new AdminBoImpl();
            case BRANCH:return new BranchBoImpl();
            case TRANSACTION:return new TransactionBoImpl();
            default:return null;
        }

    }
}
