package auth_service.repository;

import auth_service.model.entity.Role;
import auth_service.model.entity.User;
import auth_service.model.entity.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 透過username搜尋使用者
     * @param username
     * @return User
     */
    @Query("select u from User u where u.userName=:username")
    Optional<User> findUserByUsername(@Param("username") String username);

    /**
     * 透過username刪除使用者
     * @param username
     * @return User
     */
    @Transactional
    @Modifying
    @Query("delete from User u where u.userName=:username")
    Optional<User> deleteUser(@Param("username") String username);

    /**
     * 搜尋使用者有哪些權限
     * @param user
     * @return
     */
    @Query("select ur.userRole.Role from UserRole ur where ur.userName=:user")
    List<String> findRoleByUser(@Param("user")User user);

    @Transactional
    @Modifying
    @Query("delete from UserRole ur where ur.userName = :user and ur.userRole=:role")
    int deleteRolesByUser(@Param("user") User user, @Param("role") Role role);
}
