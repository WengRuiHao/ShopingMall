package auth_service.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Login {
    private String userName;
    private String userPassword;
    private Set<String> userRole;

    public Login(String userName, String userPassword, Set<String> userRole) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }
}
