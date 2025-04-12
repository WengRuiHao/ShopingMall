package auth_service.repository;

import auth_service.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryJDBC extends JpaRepository<User, Long> {

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
}
