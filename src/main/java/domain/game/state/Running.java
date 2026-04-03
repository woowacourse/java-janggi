package domain.game.state;

import common.JanggiException;
import domain.game.Game;
import domain.player.Team;
import domain.position.Position;
import java.util.Set;

public abstract class Running extends GameState {
    public Running(Game game) {
        super(game);
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public Team getWinner() {
        throw new JanggiException("게임이 아직 종료되지 않았습니다.");
    }

    @Override
    public void move(Position source, Position destination) {
        validateTurn(source);
        game.movePiece(source, destination);
        if (game.isJangCaught()) {
            finishGame();
            return;
        }
        changeTurn();
    }

    @Override
    public Set<Position> selectPiece(Position source) {
        validateTurn(source);
        return game.findMovablePositions(source);
    }

    protected abstract void validateTurn(Position source);

    protected abstract void changeTurn();

    protected abstract void finishGame();
}
