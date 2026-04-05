package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.PieceAttribute;

public class HanTurn extends BaseTurn {
    private final int turn;

    public HanTurn(Board board, int turn) {
        super(board);
        this.turn = turn;
    }

    @Override
    public TurnState move(Position start, Position end) {
        PieceAttribute pieceAttribute = board.move(start, end, Side.HAN);

        if(turn == MAX_TURN) {
            TurnAttribute turnAttribute = new TurnAttribute(Side.EMPTY, turn + 1);
            return new TurnState(new FinishTurn(board, Side.EMPTY), turnAttribute, pieceAttribute);
        }

        if (board.isEndGame()) {
            TurnAttribute turnAttribute = new TurnAttribute(Side.EMPTY, turn + 1);
            return new TurnState(new FinishTurn(board, Side.HAN), turnAttribute, pieceAttribute);
        }

        TurnAttribute turnAttribute = new TurnAttribute(Side.HAN, turn + 1);
        return new TurnState(new ChoTurn(board, turn + 1), turnAttribute, pieceAttribute);
    }

    @Override
    public Side getCurrentSide() {
        return Side.HAN;
    }
}
