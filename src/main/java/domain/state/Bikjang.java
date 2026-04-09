package domain.state;

import domain.Board;
import domain.piece.Team;

public class Bikjang extends Running {
    public Bikjang(Board board, Team turn) {
        super(board, turn);
    }

    @Override
    protected JanggiGame transitionOnBikjang() {
        return new Finished(board, turn.changeTeam());
    }

    @Override
    protected JanggiGame transitionOnNormal() {
        return new Playing(board, turn.changeTeam());
    }

    @Override
    public JanggiGame pass() {
        return new Finished(board, turn.changeTeam());
    }
}
