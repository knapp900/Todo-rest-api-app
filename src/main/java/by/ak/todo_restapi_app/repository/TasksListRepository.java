package by.ak.todo_restapi_app.repository;

import by.ak.todo_restapi_app.entity.TasksList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TasksListRepository extends JpaRepository<TasksList,Long> {

    Optional<TasksList> findAllByUser_UsernameAndId(String username, Long id);
    Page<TasksList> findAllByUser_Username(String username, Pageable pageable);
    Page<TasksList> findAllByUser_UsernameAndIsCompleteFalse(String username, Pageable pageable);

    Page<TasksList> findAllByUser_UsernameAndIsCompleteTrue(String username, Pageable pageable);
}
