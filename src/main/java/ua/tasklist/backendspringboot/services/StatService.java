package ua.tasklist.backendspringboot.services;

import org.springframework.stereotype.Service;
import ua.tasklist.backendspringboot.entity.Stat;
import ua.tasklist.backendspringboot.repository.StatRepository;

import javax.transaction.Transactional;

@Service

// все методы класса должны выполниться без ошибки, чтобы транзакция завершилась
// если в методе возникнет исключение - все выполненные операции откатятся (Rollback)
@Transactional
public class StatService {

    private final StatRepository repository; // сервис имеет право обращаьтся к репозиторию (БД)

    public StatService(StatRepository repository) {
        this.repository = repository;
    }

    public Stat findById(Long id){
        return repository.findById(id).get();
    }
}
