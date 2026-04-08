package domain.state;

import domain.Board;
import domain.piece.Team;

public class Bikjang extends Running {
    public Bikjang(long id, Board board, Team turn) {
        super(id, board, turn);
    }

    @Override
    protected JanggiGame transitionOnBikjang() {
        return new Finished(id, board, turn.changeTeam());
    }

    @Override
    protected JanggiGame transitionOnNormal() {
        return new Playing(id, board, turn.changeTeam());
    }

    @Override
    public JanggiGame pass() {
        return new Finished(id, board, turn.changeTeam());
    }
}
