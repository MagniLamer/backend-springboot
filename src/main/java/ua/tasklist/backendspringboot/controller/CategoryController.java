package ua.tasklist.backendspringboot.controller;

import lombok.AllArgsConstructor;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tasklist.backendspringboot.entity.Category;
import ua.tasklist.backendspringboot.repository.CategoryRepository;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryRepository categoryRepository;
    private static Logger LOG = Logger.getLogger(CategoryController.class.getName());

    @GetMapping("/test")
    public List<Category> test(){
        List<Category> list =categoryRepository.findAll();
        LOG.info("Method find all category");
        return list;
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category){ // @RequestBody преобразовывает обьект в JSON и обратно
        // проверка на обязательные параметры
        if (category.getId() != null && category.getId() != 0) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("incorrect param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        LOG.info("Added new category in table Category");
        return ResponseEntity.ok(categoryRepository.save(category));
    }


    @PutMapping("/update")
    public ResponseEntity<Category> update(@RequestBody Category category){

        if ((category.getId() == null) || (category.getId() == 0)) {
            // id нужно передавать обязательно -> обновляется обьект по id
            return new ResponseEntity("missed param: ", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        LOG.info("Update category with id = " + category.getId() + " in table Category");
        return ResponseEntity.ok(categoryRepository.save(category));
    }

}
