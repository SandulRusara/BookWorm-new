package lk.ijse.dao;



import lk.ijse.dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private DaoFactory(){

    }
    public static DaoFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory=new DaoFactory():daoFactory;
    }
    public enum DataType{
        ADMIN,USER,BOOK,BRANCH,TRANSACTION
    }
    public SuperDao getDao(DataType type ){
        switch (type){
            case BRANCH:return new BranchDaoImpl();
            case TRANSACTION:return new TransactionDaoImpl();
            case USER:return new UserDaoImpl();
            case BOOK:return new BookDaoImpl();
            case ADMIN:return new AdminDaoImpl();
            default:return null;
        }
    }
}
