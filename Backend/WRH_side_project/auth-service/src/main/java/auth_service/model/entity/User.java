package auth_service.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId",length = 50,nullable = false)
    private Long userId; // 使用者id

    @Column(name="username",length = 150,nullable = false)
    private String userName; // 使用者帳號

    @Column(name = "password",length = 150,nullable = false)
    private String userPassword; // 使用者Hash密碼

    @Column(name="salt",length = 150,nullable = false)
    private String salt;  // 隨機鹽

    @Column(name="nick_Name",length = 150,nullable = true)
    private String nickName; // 暱稱

    @Column(name = "sex",length = 100,nullable = false)
    private String gender; // 使用者性別

    @Column(name = "email",length = 100,nullable = false)
    private String email; // 電子郵件

    @Column(name = "create_At",length = 150,nullable = false, columnDefinition = "TIMESTAMP(0)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt; // 創建時間

    @Column(name = "role",length = 150,nullable = false)
    private String userRole; // 權限

//    @OneToOne(cascade = CascadeType.ALL)
//    @Column(name = "vactorPath",length = 150,nullable = true)
//    @JoinColumn(name = "vactorPath")
//    private String vactorPath; //個人圖片路徑

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", salt='" + salt + '\'' +
                ", nickName='" + nickName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", createAt=" + createAt +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
