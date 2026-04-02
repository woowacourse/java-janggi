package mapper;

import domain.coordinate.Position;
import dto.PositionDto;
import dto.PossibleMovesDto;

import java.util.ArrayList;
import java.util.List;

public class PossibleMovesMapper {

    public static PossibleMovesDto toDto(List<Position> possibleMoves) {
        List<PositionDto> possibleMovesView = new ArrayList<>();

        for (Position position : possibleMoves) {
            possibleMovesView.add(toPositionDto(position));
        }

        return new PossibleMovesDto(possibleMovesView, possibleMoves.size());
    }

    private static PositionDto toPositionDto(Position position) {
        return new PositionDto(position.col(), position.row());
    }
}
