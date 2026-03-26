package janggi.dto;

import java.util.List;

public record PositionDto(
        int row, int column
) {

    public static PositionDto from(List<Integer> position) {
        return new PositionDto(position.getFirst(), position.getLast());
    }
}
