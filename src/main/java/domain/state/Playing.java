package domain.state;

import domain.Board;
import domain.piece.Team;

public class Playing extends Running {
    public Playing(Board board, Team turn) {
        super(board, turn);
    }

    @Override
    protected JanggiGame transitionOnBikjang() {
        return new Bikjang(Board.mutableCopyOf(board), turn.changeTeam());
    }

    @Override
    protected JanggiGame transitionOnNormal() {
        return new Playing(Board.mutableCopyOf(board), turn.changeTeam());
    }

    @Override
    public JanggiGame pass() {
        return new Playing(Board.mutableCopyOf(board), turn.changeTeam());
    }

    @Override
    public State getState() {
        return State.PLAYING;
    }
}
