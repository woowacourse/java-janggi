package janggi.domain.state;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.board.Board;

public class HanTurn implements GameState {
    private static final Camp camp = Camp.HAN;

    @Override
    public GameState move(Position from, Position to, Board board) {
        validateCamp(from, board);
        board.movePiece(from, to);
        if (board.isOnlyGeneralOfCampAlive(camp)) {
            return new Checkmate(Camp.CHO);
        }
        return new ChoTurn();
    }

    @Override
    public boolean isOngoing() {
        return true;
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

    @Override
    public String getStateType() {
        return "HAN TURN";
    }

    @Override
    public GameState giveUp() {
        return new GiveUp(camp);
    }

    @Override
    public GameState draw() {
        return new Draw(camp);
    }
}
