package ua.tasklist.backendspringboot.controller;

import lombok.AllArgsConstructor;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tasklist.backendspringboot.entity.Category;
import ua.tasklist.backendspringboot.repository.CategoryRepository;
import ua.tasklist.backendspringboot.search.CategorySearchValues;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryRepository categoryRepository;
    private  static Logger logger = Logger.getLogger(CategoryController.class.getName());

    @GetMapping("/all")
    public List<Category> findAll() {
        logger.info("Method finds all category");
        return categoryRepository.findAllByOrderByTitleAsc();
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category) { // @RequestBody преобразовывает обьект в JSON и обратно
        // проверка на обязательные параметры
        if (category.getId() != null && category.getId() != 0) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("incorrect param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        logger.info("Added new category in table Category");
        return ResponseEntity.ok(categoryRepository.save(category));
    }


    @PutMapping("/update")
    public ResponseEntity<Category> update(@RequestBody Category category) {

        if ((category.getId() == null) || (category.getId() == 0)) {
            // id нужно передавать обязательно -> обновляется обьект по id
            return new ResponseEntity("missed param: ", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        logger.info("Update category with id = " + category.getId() + " in table Category");
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        Category category = null;

        try {
            category = categoryRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        logger.info("Find category by id = " + id);
        return ResponseEntity.ok(category);
    }

    // параметр id передается не в body запросаб а в самом URL
    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        // можно и без try - catch
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return new ResponseEntity("id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    // поиск по любым параметрам CategorySearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search (@RequestBody CategorySearchValues categorySearchValues){
        logger.info("Method searches category by title ");
        // если вместо текста будет null или пусто - вернутся все категории
        return ResponseEntity.ok(categoryRepository.findByTitle(categorySearchValues.getTitle()));
    }
}
