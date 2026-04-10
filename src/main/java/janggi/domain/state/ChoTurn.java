package janggi.domain.state;

import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.space.Destinations;
import janggi.domain.space.Position;

public class ChoTurn extends  GameState {

    public ChoTurn(Board board) {
        super(board);
    }

    @Override
    public Destinations selectSource(Position source) {
        validateAlly(source);
        return getBoard().findDestinations(source);
    }

    @Override
    public GameState move(Position source, Position target) {
        validateAlly(source);
        Board nextBoard = getBoard().movePiece(source, target);
        if (nextBoard.isKingDead(Side.HAN)) {
            return new Finished(nextBoard, Side.CHO);
        }
        return new HanTurn(nextBoard);
    }

    private void validateAlly(Position position) {
        Piece piece = getBoard().getPiece(position);
        if (!piece.isAlly(Side.CHO)) {
            throw new IllegalArgumentException("초나라 차례입니다. 초나라 기물만 움직일 수 있습니다.");
        }
    }

    @Override
    public Side getCurrentSide() {
        return Side.CHO;
    }

    @Override
    public Side getWinnerSide() {
        throw new IllegalStateException("아직 게임이 끝나지 않았습니다.");
    }
}
