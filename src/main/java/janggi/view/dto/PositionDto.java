package janggi.view.dto;

import janggi.domain.position.Position;
import java.util.List;

public record PositionDto(
        int row,
        int column
) {

    public static PositionDto from(int row, int column) {
        return new PositionDto(row, column);
    }



    public static List<PositionDto> fromPositions(List<Position> positions) {
        return positions.stream()
                .map(position -> new PositionDto(position.row().row(), position.column().column()))
                .toList();
    }

}
