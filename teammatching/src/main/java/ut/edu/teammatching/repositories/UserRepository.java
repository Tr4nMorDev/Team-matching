package ut.edu.teammatching.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ut.edu.teammatching.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}   