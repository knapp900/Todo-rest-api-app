package by.ak.todo_restapi_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasksLists")
public class TasksList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tasksList_id")
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "taskslist_id", insertable = false, updatable = false)
    private List<Task> tasks;

    @Column(name = "dateTimeOfCreation")
    private LocalDateTime dateTimeOfCreation;

    @Column(name = "isCompleted")
    private boolean isComplete;

    @Column(name = "username")
    private String username;
}
