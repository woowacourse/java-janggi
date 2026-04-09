package domain.state;

import domain.Board;
import domain.piece.Team;

public class Playing extends Running {
    public Playing(Board board, Team turn) {
        super(board, turn);
    }

    @Override
    protected JanggiGame transitionOnBikjang() {
        return new Bikjang(board, turn.changeTeam());
    }

    @Override
    protected JanggiGame transitionOnNormal() {
        return new Playing(board, turn.changeTeam());
    }

    @Override
    public JanggiGame pass() {
        return new Playing(board, turn.changeTeam());
    }
}
