package domain.turn;

import domain.Board;

public class ScoreFinished extends Finished {

    public ScoreFinished(Board board, TurnState turnState) {
        super(board, turnState);
    }

    @Override
    public boolean isFinishedByCheckmate() {
        return false;
    }
}
