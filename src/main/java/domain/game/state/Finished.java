package domain.game.state;

import common.exception.JanggiException;
import domain.game.Game;
import domain.player.Team;
import domain.position.Position;

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
}
