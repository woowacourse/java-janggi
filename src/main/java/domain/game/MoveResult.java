package domain.game;

import domain.board.Team;
import domain.vo.Position;

public record MoveResult(
        Position from,
        Position to,
        boolean captured,
        Status status,
        Team nextTurn
) {
}
