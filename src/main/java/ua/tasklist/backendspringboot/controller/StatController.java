package ua.tasklist.backendspringboot.controller;

import lombok.AllArgsConstructor;
import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.tasklist.backendspringboot.entity.Stat;
import ua.tasklist.backendspringboot.repository.StatRepository;
import ua.tasklist.backendspringboot.services.StatService;

@AllArgsConstructor
@RestController
public class StatController {
    private StatService statService;// сервис для доступа к данным(напрямую к репозиторию)
    private static Logger LOG = Logger.getLogger(StatController.class.getName());

    private final Long defaultId = 1L;

    @GetMapping("/stat")
    public ResponseEntity<Stat> findById() {
        LOG.info("Method finds all stat");
        return ResponseEntity.ok(statService.findById(defaultId));
    }

}
