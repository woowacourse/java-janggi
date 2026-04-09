package janggi.domain.state;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.board.Board;

public class ChoTurn extends Running {
    private static final Camp camp = Camp.CHO;

    @Override
    public GameState move(Position from, Position to, Board board) {
        validateCamp(from, board);
        board.movePiece(from, to);
        if (board.isOnlyGeneralOfCampAlive(camp)) {
            return new Checkmate(camp);
        }
        return new HanTurn();
    }

    @Override
    public Camp turn() {
        return camp;
    }

    @Override
    public void validateCamp(Position current, Board board) {
        if (!board.isSameCamp(current, camp)) {
            throw new IllegalArgumentException("자신의 기물만 선택할 수 있습니다.");
        }
    }
}
