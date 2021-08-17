package ua.tasklist.backendspringboot.controller;

import lombok.AllArgsConstructor;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
