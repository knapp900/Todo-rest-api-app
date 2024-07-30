package by.ak.todo_restapi_app.repository;

import by.ak.todo_restapi_app.entity.TasksList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TasksListRepository extends JpaRepository<TasksList,Long> {

    Optional<TasksList> findAllByUsernameAndId(String username,Long id);
    Page<TasksList> findAllByUsername(String username, Pageable pageable);
    Page<TasksList> findAllByUsernameAndIsCompleteFalse(String username, Pageable pageable);

    Page<TasksList> findAllByUsernameAndIsCompleteTrue(String username, Pageable pageable);
}
