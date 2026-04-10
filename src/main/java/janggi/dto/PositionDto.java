package janggi.dto;

import janggi.domain.position.Position;
import java.util.List;

public record PositionDto(
        int row,
        int column
) {

    public static PositionDto from(List<Integer> rowAndColumn) {
        return new PositionDto(rowAndColumn.getFirst(), rowAndColumn.getLast());
    }

    public static PositionDto from(Position position) {
        return new PositionDto(position.row().row(), position.column().column());
    }

    public static List<PositionDto> fromPositions(List<Position> positions) {
        return positions.stream()
                .map(position -> new PositionDto(position.row().row(), position.column().column()))
                .toList();
    }

}
