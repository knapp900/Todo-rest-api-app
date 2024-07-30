package by.ak.todo_restapi_app.repository;

import by.ak.todo_restapi_app.entity.Status;
import by.ak.todo_restapi_app.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    Optional<Task> findTaskByTasksListIdAndId(Long tasksListId,Long taskId);

    Page<Task> findAllByTasksListId(Long tasksListId, Pageable pageable);

    Page<Task> findByStatus(Status status, Long tasksListId, Pageable pageable);

}
