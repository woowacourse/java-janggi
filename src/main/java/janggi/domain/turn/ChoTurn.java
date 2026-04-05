package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.PieceAttribute;

public class ChoTurn extends BaseTurn {
    public ChoTurn(Board board, int turn) {
        super(board, turn);
    }

    @Override
    public TurnState move(Position start, Position end) {
        PieceAttribute pieceAttribute = board.move(start, end, Side.CHO);

        if(turn == MAX_TURN) {
            return new TurnState(new FinishTurn(board, turn + 1, Side.EMPTY), pieceAttribute);
        }

        if (board.isEndGame()) {
            return new TurnState(new FinishTurn(board, turn + 1, Side.HAN), pieceAttribute);
        }

        return new TurnState(new HanTurn(board, turn + 1), pieceAttribute);
    }

    @Override
    public Side getCurrentSide() {
        return Side.CHO;
    }
}
