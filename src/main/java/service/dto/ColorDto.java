package service.dto;

import java.util.List;

public record ColorDto (List<Row> rows){
    public record Row(List<String> colors){};
}
