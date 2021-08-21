package ua.tasklist.backendspringboot.search;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

// возможные значения по которым будем искать
public class PrioritySearchValues {
    public String title;
}
