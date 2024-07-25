package by.ak.todo_restapi_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "taskslist_id")
    private TasksList tasksListId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "importance")
    @Enumerated(EnumType.STRING)
    private Importance importance;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "createdDateTime")
    private LocalDateTime createdDateTime;

    @Column(name = "lastModifiedDateTime")
    private LocalDateTime lastModifiedDateTime;
}
