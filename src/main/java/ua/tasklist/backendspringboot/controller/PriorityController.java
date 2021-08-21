package ua.tasklist.backendspringboot.controller;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tasklist.backendspringboot.entity.Priority;
import ua.tasklist.backendspringboot.repository.PriorityRepository;
import ua.tasklist.backendspringboot.search.PrioritySearchValues;
import ua.tasklist.backendspringboot.services.PriorityService;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor // внедрение через конструктор экземпляра priorityService
@RestController
@RequestMapping("/priority")
public class PriorityController {
    private PriorityService priorityService;
    private  static Logger logger = Logger.getLogger(CategoryController.class.getName());


    @GetMapping("/all")
    public List<Priority> findAll() {
        logger.info("Method finds all category");
        return priorityService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority) { // @RequestBody преобразовывает обьект в JSON и обратно

        // проверка на обязательные параметры
        if (priority.getId() != null && priority.getId() != 0) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("incorrect param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение color
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }

        logger.info("Added new category in table Category");
        return ResponseEntity.ok(priorityService.add(priority));
    }

    @PutMapping("/update")
    public ResponseEntity<Priority> update(@RequestBody Priority priority) {

        if ((priority.getId() == null) || (priority.getId() == 0)) {
            // id нужно передавать обязательно -> обновляется обьект по id
            return new ResponseEntity("missed param: ", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение color
        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }

        logger.info("Update category with id = " + priority.getId() + " in table Category");
        return ResponseEntity.ok(priorityService.update(priority));
    }

    // параметр id передается не в body запросаб а в самом URL
    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> findById(@PathVariable Long id) {

        Priority priority = null;

        try {
            priority = priorityService.findById(id);
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        logger.info("Find priority by id = " + id);
        return ResponseEntity.ok(priority);
    }

    // параметр id передается не в body запросаб а в самом URL
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        // можно и без try - catch
        try {
            priorityService.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues) {
        logger.info("Method searches priority by title-------------------------------------------------------------------\n\n ");
        // если вместо текста будет null или пусто - вернутся все категории
        return ResponseEntity.ok(priorityService.findByTitle(prioritySearchValues.getTitle()));
    }

}
