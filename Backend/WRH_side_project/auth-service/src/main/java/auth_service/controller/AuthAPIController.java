package auth_service.controller;

import auth_service.model.dto.Login;
import auth_service.model.dto.Register;
import auth_service.response.ApiResponse;
import auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Auth")
public class AuthAPIController {

    @Autowired
    private UserService userService;

    @DeleteMapping
    public ResponseEntity<ApiResponse<Register>> deleteUser(@RequestBody Register register){
        userService.deleteUser(register.getUserName());
        return ResponseEntity.ok(ApiResponse.success("刪除成功",null));
    }

    @PutMapping("/Role")
    public ResponseEntity<ApiResponse<Login>> addUserByRole(@RequestBody Register register){
        Login login=userService.addUserByRole(register.getUserName(), register.getUserRole());
        return ResponseEntity.ok(ApiResponse.success("新增成功",login));
    }

    @DeleteMapping("/Role")
    public ResponseEntity<ApiResponse<Login>> deleteUserByRole(@RequestBody Register register){
        Login login=userService.deleteUserByRole(register.getUserName(),register.getUserRole());
        return ResponseEntity.ok(ApiResponse.success("刪除成功",login));
    }
}
