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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/Register")
    public ResponseEntity<ApiResponse<Register>> register(@RequestBody Register register){
        userService.saveUser(register);
        return ResponseEntity.ok(ApiResponse.success("註冊成功", register));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Login>> Login(@RequestBody Register register){
        Login login=userService.checkUser(register);
        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(register.getUserName(),register.getUserPassword());
        authToken.setDetails(login);

        Authentication authentication =authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(ApiResponse.success("登入成功",login));
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
