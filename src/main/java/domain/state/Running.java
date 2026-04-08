package domain.state;

import domain.Board;
import domain.piece.Team;
import domain.position.Position;

public abstract class Running extends Started {
    private static final String GAME_DOSE_NOT_FINISHED = "게임이 아직 끝나지 않아 결과를 집계할 수 없습니다.";

    protected Running(long id, Board board, Team turn) {
        super(id, board, turn);
    }

    @Override
    public JanggiGame move(Position start, Position destination) {
        board.move(turn, start, destination);

        if (board.isAnyJangDead()) {
            return new Finished(id, board, turn.changeTeam());
        }

        if (board.isBikjang()) {
            return transitionOnBikjang();
        }
        return transitionOnNormal();
    }

    protected abstract JanggiGame transitionOnBikjang();

    protected abstract JanggiGame transitionOnNormal();

    @Override
    public Team judgeWinner() {
        throw new IllegalStateException(GAME_DOSE_NOT_FINISHED);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
