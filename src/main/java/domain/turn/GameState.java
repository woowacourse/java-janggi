package domain.turn;

import domain.piece.Board;
import java.util.function.BiFunction;

public enum GameState {
    FINISHED_SCORE(ScoreFinished::new),
    FINISHED_CHECKMATE(CheckmateFinished::new),
    IN_PROGRESS(Playing::new),
    ;

    GameState(BiFunction<Board, TurnState, Turn> creator) {
        this.creator = creator;
    }

    private final BiFunction<Board, TurnState, Turn> creator;

    public Turn createTurn(Board board, TurnState turnState) {
        return creator.apply(board, turnState);
    }
}
