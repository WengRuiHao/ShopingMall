package auth_service.controller;

import auth_service.exception.ConflictException;
import auth_service.exception.NotFoundException;
import auth_service.model.dto.Login;
import auth_service.model.dto.Register;
import auth_service.response.ApiResponse;
import auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Login")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/Register")
    public ResponseEntity<ApiResponse<Register>> register(@RequestBody Register register){
        userService.saveUser(register);
        return ResponseEntity.ok(ApiResponse.success("註冊成功", register));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Login>> Login(@RequestBody Register register){
        Login login=userService.checkUser(register);
        return ResponseEntity.ok(ApiResponse.success("登入成功",login));
    }

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


    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflict(ConflictException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(409, ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(404, ex.getMessage()));
    }
}
