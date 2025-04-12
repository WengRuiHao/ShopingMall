package auth_service.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private String userName; // 使用者帳號

    private String userPassword; // 使用者Hash密碼

    private String nickName; // 暱稱

    private String gender; // 使用者性別

    private String email; // 電子郵件

    private String userRole; // 權限

    public UserDto(String userName, String userPassword, String gender, String email, String userRole) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.gender = gender;
        this.email = email;
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", nickName='" + nickName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
