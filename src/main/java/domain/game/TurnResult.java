package domain.game;

import domain.board.MoveResult;
import domain.piece.TeamColor;
import java.util.Optional;

public record TurnResult(MoveResult moveResult, Optional<TeamColor> winner) {

    public static TurnResult inProgress(MoveResult moveResult) {
        return new TurnResult(moveResult, Optional.empty());
    }

    public static TurnResult finished(MoveResult moveResult, TeamColor winner) {
        return new TurnResult(moveResult, Optional.of(winner));
    }
}
