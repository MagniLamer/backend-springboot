package ua.tasklist.backendspringboot.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.tasklist.backendspringboot.entity.Category;
import ua.tasklist.backendspringboot.entity.Priority;

import java.util.Date;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor

// возможные значения по которым будем искать
public class TaskSearchValues {
    private String title;
    private Integer completed;
    private Long priorityId;
    private Long categoryId;

    // постраничность
    private Integer pageNumber;
    private Integer pageSize;

    //сортировка
    private String sortColumn;
    private  String sortDirection;
}
