package domain.turn;

import domain.piece.Board;

public class ScoreFinished extends Finished {

    public ScoreFinished(Board board, TurnState turnState) {
        super(board, turnState);
    }

    @Override
    public boolean isFinishedByCheckmate() {
        return false;
    }

    @Override
    public GameState getGameState() {
        return GameState.FINISHED_SCORE;
    }
}
