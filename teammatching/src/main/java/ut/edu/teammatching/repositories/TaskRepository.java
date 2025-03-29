package ut.edu.teammatching.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ut.edu.teammatching.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
