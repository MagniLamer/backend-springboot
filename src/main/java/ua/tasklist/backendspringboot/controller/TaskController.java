package ua.tasklist.backendspringboot.controller;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tasklist.backendspringboot.entity.Task;
import ua.tasklist.backendspringboot.repository.TaskRepository;
import ua.tasklist.backendspringboot.search.TaskSearchValues;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/task")
public class TaskController {
    private TaskRepository taskRepository;
    private static Logger logger = Logger.getLogger(TaskController.class.getName());

    @GetMapping("/all")
    public List<Task> findAll() {
        logger.info("Method finds all task");
        return taskRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody Task task) { // @RequestBody преобразовывает обьект в JSON и обратно
        // проверка на обязательные параметры
        if (task.getId() != null && task.getId() != 0) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("incorrect param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        logger.info("Added new task in table Task");
        return ResponseEntity.ok(taskRepository.save(task));
    }


    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody Task task) {

        if ((task.getId() == null) || (task.getId() == 0)) {
            // id нужно передавать обязательно -> обновляется обьект по id
            return new ResponseEntity("missed param: ", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        logger.info("Update task with id = " + task.getId() + " in table Task");
        return ResponseEntity.ok(taskRepository.save(task));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        Task task = null;

        try {
            task = taskRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        logger.info("Find task by id = " + id);
        return ResponseEntity.ok(task);
    }

    // параметр id передается не в body запросаб а в самом URL
    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        // можно и без try - catch
        try {
            taskRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    // поиск по любым параметрам TaskSearchValues
    @PostMapping("/search")
    public ResponseEntity<Page<Task>> search(@RequestBody TaskSearchValues taskSearchValues) {
        logger.info("Method searches task by param-------------------- ");

        //избегаем NullPointerException
        String title = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;
        Integer completed = taskSearchValues.getCompleted() != null ? taskSearchValues.getCompleted() : null;
        Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;

        String sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : null;
        String sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortDirection() : null;

        Integer pageNumber = taskSearchValues.getPageNumber() != null ? taskSearchValues.getPageNumber() : null;
        Integer pageSize = taskSearchValues.getPageSize() != null ? taskSearchValues.getPageSize() : null;

        Sort.Direction direction = (sortDirection == null || sortDirection.trim().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;

        // подставляем все значения
        //обьект сортировки
        Sort sort = Sort.by(direction, sortColumn );

        //обьект постраничности
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page result = taskRepository.findByParams(title, completed, priorityId, categoryId, pageRequest);
        // если вместо параметров будет null или пусто - вернутся все категории
        return ResponseEntity.ok(result);
    }
}
