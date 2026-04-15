package domain.state;

import domain.game.JanggiGame;
import domain.setup.Command;

public class PlayingState implements GameState {
    @Override
    public GameState handle(JanggiGame game, Command command) {
        game.move(command.toCoordinate());
        if (game.isFinished()) {
            return new FinishState(game.createGameResult());
        }
        game.nextTurn();
        return this;
    }

    @Override
    public GamePhase phase() {
        return GamePhase.PLAYING;
    }
}
