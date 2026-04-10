package janggi.domain.game;

import janggi.domain.board.Position;
import janggi.domain.piece.Team;

public record MoveResult(
        Position from,
        Position to,
        boolean captured,
        Team currentTurnTeam,
        GameStatus gameStatus
) {
}
