package domain.turn;


import domain.piece.Board;

public class CheckmateFinished extends Finished {

    public CheckmateFinished(Board board, TurnState turnState) {
        super(board, turnState);
    }

    @Override
    public boolean isFinishedByCheckmate() {
        return true;
    }

    @Override
    public GameState getGameState() {
        return GameState.FINISHED_CHECKMATE;
    }
}
