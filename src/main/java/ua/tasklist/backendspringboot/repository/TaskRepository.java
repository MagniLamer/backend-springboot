package ua.tasklist.backendspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.tasklist.backendspringboot.entity.Category;
import ua.tasklist.backendspringboot.entity.Priority;
import ua.tasklist.backendspringboot.entity.Task;

import java.util.List;

//принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // учитываем, что параметр может быть null или пустым
    @Query("SELECT p FROM Task p where " +
            "(:title is null or :title = '' or lower(p.title) like lower(concat('%', :title,'%'))) and" +
            "(:completed is null or p.completed=:completed) and " +
            "(:priorityId is null or p.priority.id=:priorityId) and " +
            "(:categoryId is null or p.category.id=:categoryId)"
    )
    List<Task> findByParams(@Param("title") String title,
                            @Param("completed") Integer completed,
                            @Param("priorityId") Long priorityId,
                            @Param("categoryId") Long categoryId);

}
