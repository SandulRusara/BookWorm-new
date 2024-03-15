package lk.ijse.bo.Custom.impl;


import lk.ijse.bo.Custom.AdminBo;
import lk.ijse.dao.DaoFactory;
import lk.ijse.dao.custom.AdminDao;
import lk.ijse.dto.AdminDto;
import lk.ijse.entity.Admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminBoImpl implements AdminBo {
    private AdminDao adminDao = (AdminDao) DaoFactory.getDaoFactory().getDao(DaoFactory.DataType.ADMIN);

    @Override
    public List<AdminDto> getAllAdmins() throws SQLException {
        List<Admin> entities = adminDao.loadAll();
        List<AdminDto> dtos = new ArrayList<>();
        for (Admin admin : entities){
            dtos.add(new AdminDto(admin.getName(), admin.getEmail(), admin.getUserName(), admin.getPassword()));
        }
        return  dtos;

    }

    @Override
    public AdminDto getAdminByEmail(String userName) throws SQLException {
        Admin admin = adminDao.get(userName);
        return new AdminDto(admin.getAdminId(), admin.getName(), admin.getEmail(),admin.getUserName(),admin.getPassword());
    }

    @Override
    public AdminDto getUserById(int id) throws SQLException {
        Admin admin = adminDao.getbyId(id);
        System.out.println(id);
        return new AdminDto(admin.getAdminId(), admin.getName(), admin.getEmail(), admin.getUserName(), admin.getPassword());
    }

    @Override
    public void updateAdmin(AdminDto dto) throws SQLException {
        adminDao.update(new Admin(dto.getAdminId(), dto.getName(), dto.getPassword(), dto.getUserName(), dto.getEmail()));

    }

    @Override
    public void saveAdmin(AdminDto adminDto) throws SQLException {
        adminDao.save(new Admin( adminDto.getEmail(), adminDto.getName(), adminDto.getPassword(), adminDto.getUserName() ));
    }

    @Override
    public void deleteAdmin(int id) throws SQLException {
        adminDao.delete(id);

    }

    @Override
    public AdminDto getAdminById(int adminId) throws SQLException {
        Admin admin = adminDao.getbyId(adminId);
        return new AdminDto(admin.getAdminId(), admin.getName(), admin.getEmail(), admin.getUserName(), admin.getPassword());
    }
}

