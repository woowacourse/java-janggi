package domain.game.state;

import common.exception.JanggiException;
import domain.game.Game;
import domain.position.Position;

public class Finished extends GameState {
    public Finished(Game game) {
        super(game);
    }

    @Override
    public void move(Position source, Position destination) {
        throw new JanggiException("게임이 종료되어 이동이 불가합니다.");
    }
}
