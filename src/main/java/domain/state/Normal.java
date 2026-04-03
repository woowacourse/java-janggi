package domain.state;

import domain.Board;
import domain.piece.Team;
import domain.position.Position;

public class Normal extends Running {
    protected Normal(Board board, Team turn) {
        super(board, turn);
    }

    @Override
    public JanggiGame move(Position start, Position destination) {
        board.move(turn, start, destination);
        if (board.isAnyJangDead()) {
            return new Finished(board, turn.changeTeam());
        }
        if (board.isBikjang()) {
            return new Bikjang(board, turn.changeTeam());
        }
        return new Normal(board, turn.changeTeam());
    }

    @Override
    public JanggiGame pass() {
        return new Normal(board, turn.changeTeam());
    }
}
