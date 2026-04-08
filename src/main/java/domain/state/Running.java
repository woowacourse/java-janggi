package domain.state;

import domain.Board;
import domain.piece.Team;

public abstract class Running extends Started {
    private static final String GAME_DOSE_NOT_FINISHED = "게임이 아직 끝나지 않아 결과를 집계할 수 없습니다.";

    protected Running(long id, Board board, Team turn) {
        super(id, board, turn);
    }

    @Override
    public Team judgeWinner() {
        throw new IllegalStateException(GAME_DOSE_NOT_FINISHED);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
