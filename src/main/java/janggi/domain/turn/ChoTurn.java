package janggi.domain.turn;

import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.PieceAttribute;

public class ChoTurn extends BaseTurn {
    private final int turn;

    public ChoTurn(Board board, int turn) {
        super(board);
        this.turn = turn;
    }

    @Override
    public TurnState move(Position start, Position end) {
        PieceAttribute pieceAttribute = board.move(start, end, Side.CHO);

        if(turn == MAX_TURN) {
            TurnAttribute turnAttribute = new TurnAttribute(Side.EMPTY, turn);
            return new TurnState(new FinishTurn(board, Side.EMPTY), turnAttribute, pieceAttribute);
        }

        if (board.isEndGame()) {
            TurnAttribute turnAttribute = new TurnAttribute(Side.EMPTY, turn);
            return new TurnState(new FinishTurn(board, Side.HAN), turnAttribute, pieceAttribute);
        }

        TurnAttribute turnAttribute = new TurnAttribute(Side.HAN, turn + 1);
        return new TurnState(new HanTurn(board, turn + 1), turnAttribute, pieceAttribute);
    }

    @Override
    public Side getCurrentSide() {
        return Side.CHO;
    }
}
