package domain.game.state;

import common.JanggiException;
import domain.game.Game;
import domain.player.Team;
import domain.position.Position;
import java.util.Set;

public class Finished extends GameState {

    private final Team winner;

    public Finished(Game game, Team winner) {
        super(game);
        this.winner = winner;
    }

    @Override
    public void move(Position source, Position destination) {
        throw new JanggiException("게임이 이미 종료되었습니다.");
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public Team getCurrentTeam() {
        throw new JanggiException("게임이 이미 종료되었습니다.");
    }

    @Override
    public Team getWinner() {
        return winner;
    }

    @Override
    public Set<Position> selectPiece(Position source) {
        throw new JanggiException("게임이 이미 종료되었습니다.");
    }
}
