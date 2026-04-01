package mapper;

import domain.coordinate.Position;
import dto.PossibleMovesDto;

import java.util.List;

public class PossibleMovesMapper {

    public static PossibleMovesDto toDto(List<Position> possibleMoves) {
        return new PossibleMovesDto(possibleMoves, possibleMoves.size());
    }
}
