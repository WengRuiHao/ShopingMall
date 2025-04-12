package auth_service.service;

import auth_service.model.dto.Login;
import auth_service.model.dto.Register;

import java.util.List;

public interface UserService {
    /**
     * 給管理者使用
     *
     * @return List<Register>
     */
    List<Register> findAllUser();

    /**
     * 搜尋使用者
     *
     * @param username
     * @return Register
     */
    Register findByUser(String username);

    /**
     * 檢查使用者登入
     * @param register
     * @return Login
     */
    Login checkUser(Register register);

    /**
     * 忘記密碼
     * @param username
     * @param password
     * @return Register
     */
    Register ForgetUserByPassword(String username, String password);

    /**
     * 新增使用者
     *
     * @param user
     * @return
     */
    void saveUser(Register user);

    /**
     * 刪除使用者
     *
     * @param username
     * @return
     */
    void deleteUser(String username);

    /**
     * 修改使用者密碼
     *
     * @param username
     * @param oldPassword
     * @param newPassword
     * @return Register
     */
    Register updateUserByPassword(String username, String oldPassword, String newPassword);

    /**
     * 修改使用者暱稱
     *
     * @param username
     * @return Register
     */
    Register updateUserByNickName(String username, String nickName);

    /**
     * 修改使用者的信箱
     *
     * @param username
     * @return Register
     */
    Register updateUserByEmail(String username, String email);

    /**
     * 新增使用者權限
     *
     * @param username
     * @return
     */
    Login addUserByRole(String username, String role);

    /**
     * 刪除使用者權限
     * @param username
     * @param role
     * @return
     */
    Login deleteUserByRole(String username,String role);
}
