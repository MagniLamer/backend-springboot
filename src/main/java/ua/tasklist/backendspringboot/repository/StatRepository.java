package ua.tasklist.backendspringboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.tasklist.backendspringboot.entity.Stat;

import java.util.List;

@Repository
public interface StatRepository extends CrudRepository<Stat, Long> {
}
