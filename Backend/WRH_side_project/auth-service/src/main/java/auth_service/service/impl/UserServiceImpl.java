package auth_service.service.impl;

import auth_service.model.dto.UserDto;
import auth_service.model.entity.User;
import auth_service.repository.UserRepositoryJDBC;
import auth_service.service.UserService;
import auth_service.util.Hash;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepositoryJDBC userRepositoryJDBC;

    @Override
    public List<UserDto> findAllUser() {
        return List.of();
    }

    @Override
    public UserDto findByUser(String username) {
        User user = userRepositoryJDBC.findUserByUsername(username).orElseThrow(() -> new RuntimeException("使用者不存在，查詢失敗"));
        UserDto userDto = objectMapper.convertValue(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto checkUser(UserDto userDto) {
        User user = userRepositoryJDBC.findUserByUsername(userDto.getUserName()).orElseThrow(() -> new RuntimeException("使用者不存在，登入失敗"));
        if (!Hash.getHash(userDto.getUserPassword(), user.getSalt()).equals(user.getUserPassword())) {
            throw new RuntimeException("密碼輸入錯誤，登入失敗");
        }
        return userDto;
    }

    @Override
    public UserDto ForgetUserByPassword(String username, String password) {
        User user = userRepositoryJDBC.findUserByUsername(username).orElseThrow(() -> new RuntimeException("使用者不存在，修改失敗"));
        String salt = Hash.getSalt();
        String newPassword = Hash.getHash(password, salt);
        user.setUserPassword(newPassword);
        user.setSalt(salt);
        UserDto userDto = objectMapper.convertValue(user, UserDto.class);
        return userDto;
    }

    @Override
    public void saveUser(UserDto userDto) {
        Optional<User> optUser = userRepositoryJDBC.findUserByUsername(userDto.getUserName());
        if (optUser.isPresent()) {
            throw new RuntimeException("此會員已存在，註冊失敗");
        }
        //做密碼雜湊
        String salt = Hash.getSalt();
        String password = Hash.getHash(userDto.getUserPassword(), salt);

        User user = objectMapper.convertValue(userDto, User.class);
        user.setUserPassword(password);
        user.setSalt(salt);
        user.setCreateAt(LocalDateTime.now());
        userRepositoryJDBC.save(user);
    }

    @Override
    public void deleteUser(String username) {
        userRepositoryJDBC.deleteUser(username).orElseThrow(() -> new RuntimeException("使用者不存在，刪除失敗"));
    }

    @Override
    public UserDto updateUserByPassword(String username, String oldPassword, String newPassword) {
        User user = userRepositoryJDBC.findUserByUsername(username).orElseThrow(() -> new RuntimeException("使用者不存在，修改失敗"));
        if (!Hash.getHash(oldPassword, user.getSalt()).equals(user.getUserPassword())) {
            throw new RuntimeException("舊密碼輸入錯誤，修改失敗");
        }
        String salt = Hash.getSalt();
        user.setUserPassword(Hash.getHash(newPassword, salt));
        user.setSalt(salt);
        userRepositoryJDBC.save(user);
        UserDto userDto = objectMapper.convertValue(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto updateUserByNickName(String username, String nickName) {
        User user = userRepositoryJDBC.findUserByUsername(username).orElseThrow(() -> new RuntimeException("使用者不存在，修改失敗"));
        user.setNickName(nickName);
        userRepositoryJDBC.save(user);
        UserDto userDto = objectMapper.convertValue(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto updateUserByEmail(String username, String email) {
        User user = userRepositoryJDBC.findUserByUsername(username).orElseThrow(() -> new RuntimeException("使用者不存在，修改失敗"));
        user.setEmail(email);
        userRepositoryJDBC.save(user);
        UserDto userDto = objectMapper.convertValue(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto updateUserByRole(String username, String role) {
        User user = userRepositoryJDBC.findUserByUsername(username).orElseThrow(() -> new RuntimeException("使用者不存在，修改失敗"));
        user.setUserRole(role);
        userRepositoryJDBC.save(user);
        UserDto userDto = objectMapper.convertValue(user, UserDto.class);
        return userDto;
    }
}
