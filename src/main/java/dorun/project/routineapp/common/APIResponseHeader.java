package dorun.project.routineapp.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class APIResponseHeader {
    private int code;
    private String message;
}
