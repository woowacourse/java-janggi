package domain.state;

import domain.Board;
import domain.piece.Team;
import domain.position.Position;

public class Bikjang extends Running {
    public Bikjang(long id, Board board, Team turn) {
        super(id, board, turn);
    }

    @Override
    public JanggiGame move(Position start, Position destination) {
        board.move(turn, start, destination);
        if (board.isAnyJangDead()) {
            return new Finished(id, board, turn.changeTeam());
        }
        if (board.isBikjang()) {
            return new Finished(id, board, turn.changeTeam());
        }
        return new Playing(id, board, turn.changeTeam());
    }

    @Override
    public JanggiGame pass() {
        return new Finished(id, board, turn.changeTeam());
    }
}
