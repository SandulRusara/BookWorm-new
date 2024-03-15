package lk.ijse.bo.Custom;

import lk.ijse.bo.SuperBo;
import lk.ijse.dto.AdminDto;

import java.sql.SQLException;
import java.util.List;

public interface AdminBo extends SuperBo {

    List<AdminDto> getAllAdmins() throws SQLException;

    AdminDto getAdminByEmail(String userName) throws  SQLException;

    AdminDto getUserById(int id) throws SQLException;

    void updateAdmin(AdminDto dto) throws SQLException;

    void saveAdmin(AdminDto adminDto) throws SQLException;

    void deleteAdmin(int id) throws SQLException;

    AdminDto getAdminById(int adminId) throws SQLException;
}
