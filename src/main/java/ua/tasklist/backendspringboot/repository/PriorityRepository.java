package ua.tasklist.backendspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.tasklist.backendspringboot.entity.Priority;

import java.util.List;

//принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    // получить все значения, сортировка по id ASC
    List<Priority> findAllByOrderByIdAsc();
}
