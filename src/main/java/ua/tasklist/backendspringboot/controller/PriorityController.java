package ua.tasklist.backendspringboot.controller;

import lombok.AllArgsConstructor;
import org.jboss.logging.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tasklist.backendspringboot.entity.Category;
import ua.tasklist.backendspringboot.entity.Priority;
import ua.tasklist.backendspringboot.repository.PriorityRepository;
import ua.tasklist.backendspringboot.search.CategorySearchValues;
import ua.tasklist.backendspringboot.search.PrioritySearchValues;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor // внедрение через конструктор экземпляра priorityRepository
@RestController
@RequestMapping("/priority")
public class PriorityController {
    private PriorityRepository priorityRepository;
    private static org.jboss.logging.Logger LOG = Logger.getLogger(PriorityController.class.getName());

    @GetMapping("/all")
    public List<Priority> findAll() {
        LOG.info("Method finds all category");
        return priorityRepository.findAllByOrderByIdAsc();
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

        LOG.info("Added new category in table Category");
        return ResponseEntity.ok(priorityRepository.save(priority));
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

        LOG.info("Update category with id = " + priority.getId() + " in table Category");
        return ResponseEntity.ok(priorityRepository.save(priority));
    }

    // параметр id передается не в body запросаб а в самом URL
    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> findById(@PathVariable Long id) {

        Priority priority = null;

        try {
            priority = priorityRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        LOG.info("Find priority by id = " + id);
        return ResponseEntity.ok(priority);
    }

    // параметр id передается не в body запросаб а в самом URL
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        // можно и без try - catch
        try {
            priorityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search (@RequestBody PrioritySearchValues prioritySearchValues){
        LOG.info("Method searches priority by title ");
        // если вместо текста будет null или пусто - вернутся все категории
        return ResponseEntity.ok(priorityRepository.findByTitle(prioritySearchValues.getTitle()));
    }

}
