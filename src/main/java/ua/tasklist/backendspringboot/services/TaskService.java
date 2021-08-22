package ua.tasklist.backendspringboot.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.tasklist.backendspringboot.entity.Task;
import ua.tasklist.backendspringboot.repository.TaskRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service

// все методы класса должны выполниться без ошибки, чтобы транзакция завершилась
// если в методе возникнет исключение - все выполненные операции откатятся (Rollback)
@Transactional
public class TaskService {

    private final TaskRepository repository; // сервис имеет право обращаьтся к репозиторию (БД)

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }


    public List<Task> findAll() {
        return repository.findAll();
    }

    public Task add(Task task) {
        return repository.save(task); // метод save обновляет или создает новый объект, если его не было
    }

    public Task update(Task task) {
        return repository.save(task); // метод save обновляет или создает новый объект, если его не было
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public Page findByParams(String text, Integer completed, Long priorityId, Long categoryId, PageRequest paging) {
        return repository.findByParams(text, completed, priorityId, categoryId, paging);
    }

    public Task findById(Long id) {
        return repository.findById(id).get(); // т.к. возвращается Optional - нужно получить объект методом get()
    }


}

