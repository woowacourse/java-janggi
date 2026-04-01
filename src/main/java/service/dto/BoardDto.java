package service.dto;

import java.util.List;


public record BoardDto (List<Row> rows) {
    public record Row(List<String> pieces) {};
}
