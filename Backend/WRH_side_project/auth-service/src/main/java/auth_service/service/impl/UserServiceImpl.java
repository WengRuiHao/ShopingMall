package auth_service.service.impl;

import auth_service.exception.ConflictException;
import auth_service.exception.NotFoundException;
import auth_service.model.dto.Login;
import auth_service.model.dto.Register;
import auth_service.model.entity.Role;
import auth_service.model.entity.User;
import auth_service.model.entity.UserRole;
import auth_service.repository.RoleRepository;
import auth_service.repository.UserRepository;
import auth_service.service.UserService;
import auth_service.util.Hash;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Register> findAllUser() {
        return List.of();
    }

    @Override
    public Register findByUser(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("使用者不存在，查詢失敗!"));
        Register register = objectMapper.convertValue(user, Register.class);
        return register;
    }

    @Override
    public Login checkUser(Register register) {
        User user = userRepository.findUserByUsername(register.getUserName()).orElseThrow(() -> new NotFoundException("使用者不存在，登入失敗!"));
        if (!Hash.getHash(register.getUserPassword(), user.getSalt()).equals(user.getUserPassword())) {
            throw new NotFoundException("密碼輸入錯誤，登入失敗!");
        }
        Set<String> role=userRepository.findRoleByUser(user).stream().collect(Collectors.toSet());
        Login login=new Login();
        login.setUserName(user.getUserName());
        login.setUserPassword(user.getUserPassword());
        login.setUserRole(role);
        return login;
    }

    @Override
    public Register ForgetUserByPassword(String username, String password) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("使用者不存在，修改失敗"));
        String salt = Hash.getSalt();
        String newPassword = Hash.getHash(password, salt);
        user.setUserPassword(newPassword);
        user.setSalt(salt);
        Register register = objectMapper.convertValue(user, Register.class);
        return register;
    }

    @Override
    public void saveUser(Register register) {
        Optional<User> optUser = userRepository.findUserByUsername(register.getUserName());
        if (optUser.isPresent()) {
            throw new ConflictException("此會員已存在，註冊失敗!");
        }
        //做密碼雜湊
        String salt = Hash.getSalt();
        String password = Hash.getHash(register.getUserPassword(), salt);

        User user=new User();
        user.setUserName(register.getUserName());
        user.setUserPassword(password);
        user.setSalt(salt);
        user.setGender(register.getGender());
        user.setEmail(register.getEmail());
        user.setCreateAt(LocalDateTime.now());

        Role role=roleRepository.findRole(register.getUserRole()).orElseThrow(()->new NotFoundException("角色不存在，新增失敗!"));
        UserRole userRole=new UserRole();
        userRole.setUserName(user);
        userRole.setUserRole(role);
        userRole.setCreateAt(LocalDateTime.now());
        user.getUserRoles().add(userRole);
        System.out.println(user.toString());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteUser(username).orElseThrow(() -> new NotFoundException("使用者不存在，刪除失敗!"));
    }

    @Override
    public Register updateUserByPassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("使用者不存在，修改失敗!"));
        if (!Hash.getHash(oldPassword, user.getSalt()).equals(user.getUserPassword())) {
            throw new NotFoundException("舊密碼輸入錯誤，修改失敗!");
        }
        String salt = Hash.getSalt();
        user.setUserPassword(Hash.getHash(newPassword, salt));
        user.setSalt(salt);
        userRepository.save(user);
        Register register = objectMapper.convertValue(user, Register.class);
        return register;
    }

    @Override
    public Register updateUserByNickName(String username, String nickName) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("使用者不存在，修改失敗!"));
        user.setNickName(nickName);
        userRepository.save(user);
        Register register = objectMapper.convertValue(user, Register.class);
        return register;
    }

    @Override
    public Register updateUserByEmail(String username, String email) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("使用者不存在，修改失敗!"));
        user.setEmail(email);
        userRepository.save(user);
        Register register = objectMapper.convertValue(user, Register.class);
        return register;
    }

    @Override
    public Login addUserByRole(String username, String role) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("使用者不存在，新增失敗!"));
        Role map=roleRepository.findRole(role).orElseThrow(()->new NotFoundException("角色不存在，新增失敗!"));

        UserRole userRole=new UserRole();
        userRole.setUserName(user);
        userRole.setUserRole(map);
        userRole.setCreateAt(LocalDateTime.now());
        user.getUserRoles().add(userRole)   ;
        userRepository.save(user);
        Set<String> role1=userRepository.findRoleByUser(user).stream().collect(Collectors.toSet());
        Login login = new Login(user.getUserName(),user.getUserPassword(),role1);
        return login;
    }

    @Override
    public Login deleteUserByRole(String username, String role) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new NotFoundException("使用者不存在，刪除失敗!"));
        Role map=roleRepository.findRole(role).orElseThrow(()->new NotFoundException("角色不存在，刪除失敗!"));

        int mount=userRepository.deleteRolesByUser(user,map);
        if(mount==0){
            throw new NotFoundException("刪除失敗!");
        }

        Set<String> role1=userRepository.findRoleByUser(user).stream().collect(Collectors.toSet());
        Login login=new Login(user.getUserName(),user.getUserPassword(),role1);
        return login;
    }
}
