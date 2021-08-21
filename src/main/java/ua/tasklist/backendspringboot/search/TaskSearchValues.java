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
    public String title;
    public Integer completed;
    public Long priorityId;
    public Long categoryId;
}
