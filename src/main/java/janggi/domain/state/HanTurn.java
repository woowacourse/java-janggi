package janggi.domain.state;

import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.space.Destinations;
import janggi.domain.space.Position;

public class HanTurn extends GameState {

    public HanTurn(Board board) {
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
        if (!nextBoard.isKingDead(Side.CHO)) {
            return new Finished(nextBoard, Side.HAN);
        }
        return new ChoTurn(nextBoard);
    }

    private void validateAlly(Position position) {
        Piece piece = getBoard().getPiece(position);
        if (!piece.isAlly(Side.HAN)) {
            throw new IllegalArgumentException("초나라 차례입니다. 초나라 기물만 움직일 수 있습니다.");
        }
    }

    @Override
    public Side getCurrentSide() {
        return Side.HAN;
    }

    @Override
    public Side getWinnerSide() {
        throw new IllegalStateException("아직 게임이 끝나지 않았습니다.");
    }
}
