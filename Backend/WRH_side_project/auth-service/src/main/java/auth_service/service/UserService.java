package auth_service.service;

import auth_service.model.dto.UserDto;

import java.util.List;

public interface UserService {
    /**
     * 給管理者使用
     *
     * @return
     */
    List<UserDto> findAllUser();

    /**
     * 搜尋使用者
     *
     * @param username
     * @return
     */
    UserDto findByUser(String username);

    /**
     * 檢查使用者登入
     * @param userDto
     * @return
     */
    UserDto checkUser(UserDto userDto);

    /**
     * 忘記密碼
     * @param username
     * @param password
     * @return
     */
    UserDto ForgetUserByPassword(String username,String password);

    /**
     * 新增使用者
     *
     * @param user
     * @return
     */
    void saveUser(UserDto user);

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
     * @return
     */
    UserDto updateUserByPassword(String username, String oldPassword, String newPassword);

    /**
     * 修改使用者暱稱
     *
     * @param username
     * @return
     */
    UserDto updateUserByNickName(String username, String nickName);

    /**
     * 修改使用者的信箱
     *
     * @param username
     * @return
     */
    UserDto updateUserByEmail(String username, String email);

    /**
     * 修改使用者權限
     *
     * @param username
     * @return
     */
    UserDto updateUserByRole(String username, String role);
}
