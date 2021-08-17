package ua.tasklist.backendspringboot.controller;

import lombok.AllArgsConstructor;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tasklist.backendspringboot.entity.Priority;
import ua.tasklist.backendspringboot.repository.PriorityRepository;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/priority")
public class PriorityController {
    private PriorityRepository priorityRepository;
    private static org.jboss.logging.Logger LOG = Logger.getLogger(PriorityController.class.getName());

    @GetMapping("/test")
    public List<Priority> test(){
        List<Priority> list = priorityRepository.findAll();
        LOG.info("Method test return list of priority");
        return list;
    }
}
