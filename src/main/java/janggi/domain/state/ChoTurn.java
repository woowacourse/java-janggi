package janggi.domain.state;

import janggi.domain.Camp;
import janggi.domain.JanggiPosition;
import janggi.domain.board.Board;

public class ChoTurn implements GameState {
    private static final Camp camp = Camp.CHO;

    @Override
    public GameState move(JanggiPosition from, JanggiPosition to, Board board) {
        validateCamp(from, board);
        board.movePiece(from, to);
        if (board.isOnlyGeneralOfCampAlive(camp)) {
            return new Checkmate(Camp.HAN);
        }
        return new HanTurn();
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
    public void validateCamp(JanggiPosition current, Board board) {
        if (!board.isSameCamp(current, camp)) {
            throw new IllegalArgumentException("자신의 기물만 선택할 수 있습니다.");
        }
    }

    @Override
    public String getStateType() {
        return "CHO TURN";
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
