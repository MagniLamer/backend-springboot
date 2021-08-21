package ua.tasklist.backendspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.tasklist.backendspringboot.entity.Category;
import ua.tasklist.backendspringboot.entity.Priority;

import java.util.List;

//принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    // получить все значения, сортировка по id ASC
    List<Priority> findAllByOrderByIdAsc();

    @Query("SELECT c FROM Priority c " +
            "WHERE (:title is null or :title = '' or lower(c.title) like lower(concat('%',:title,'%'))) " +
            "ORDER BY c.title asc")
    List<Priority> findByTitle(@Param("title") String title);

}
