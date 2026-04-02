package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.PieceAttribute;

public class HanTurn extends BaseTurn {
    public HanTurn(Board board, int turn) {
        super(board, turn);
    }

    @Override
    public TurnState move(Position start, Position end) {
        PieceAttribute pieceAttribute = board.move(start, end, Side.HAN);

        if(turn == MAX_TURN) {
            return new TurnState(new FinishTurn(board, Side.EMPTY, turn), pieceAttribute);
        }

        if (board.isEndGame()) {
            return new TurnState(new FinishTurn(board, Side.HAN, turn), pieceAttribute);
        }
        return new TurnState(new ChoTurn(board, turn + 1), pieceAttribute);
    }

    @Override
    public Side getCurrentSide() {
        return Side.HAN;
    }
}
