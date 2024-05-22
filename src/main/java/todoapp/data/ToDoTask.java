package todoapp.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoTask {
    private BigInteger id;
    private String text;
    private boolean completed;
}