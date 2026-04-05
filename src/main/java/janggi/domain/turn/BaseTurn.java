package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.SideScore;
import janggi.domain.board.Board;
import janggi.domain.piece.PieceAttribute;
import java.util.List;

public abstract class BaseTurn implements PlayerTurn {
    protected static final int MAX_TURN = 200;

    protected final Board board;
    protected final int turn;

    public BaseTurn(Board board, int turn) {
        this.board = board;
        this.turn = turn;
    }

    @Override
    public TurnState move(Position start, Position end) {
        PieceAttribute pieceAttribute = board.move(start, end, getCurrentSide());

        if(turn == MAX_TURN) {
            return new TurnState(new FinishTurn(board, turn + 1, Side.EMPTY), pieceAttribute);
        }

        if (board.isEndGame()) {
            return new TurnState(new FinishTurn(board, turn + 1, Side.HAN), pieceAttribute);
        }

        return new TurnState(nextTurn(), pieceAttribute);
    }


    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public List<List<PieceAttribute>> getCurrentBoard() {
        return board.getCurrentBoard();
    }

    @Override
    public Side getWinnerSide() {
        return board.getHighestScoreSide();
    }

    @Override
    public SideScore getCurrentScore() {
        return board.getScore();
    }

    @Override
    public int getCurrentTurn() {
        return turn;
    }

    protected abstract PlayerTurn nextTurn();
}
