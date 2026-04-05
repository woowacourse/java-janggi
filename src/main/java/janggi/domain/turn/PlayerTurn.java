package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.SideScore;
import janggi.domain.board.Board;
import janggi.domain.piece.PieceAttribute;
import java.util.List;

public interface PlayerTurn {
    TurnState move(Position start, Position end);

    boolean isFinished();

    List<List<PieceAttribute>> getCurrentBoard();

    Side getCurrentSide();

    Side getWinnerSide();

    SideScore getCurrentScore();

    int getCurrentTurn();

    static PlayerTurn from(Board board, int turn, Side side) {
        if(side.equals(Side.CHO)) {
            return new ChoTurn(board, turn);
        }
        return new HanTurn(board, turn);
    }

    static PlayerTurn init(Board board) {
        return new ChoTurn(board, 0);
    }
}
