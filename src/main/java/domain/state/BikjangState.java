package domain.state;

import domain.game.JanggiGame;
import domain.setup.Command;
import io.OutputView;

public class BikjangState implements GameState {

    @Override
    public GameState handle(JanggiGame game, Command command) {
        if (command.isYes()) {
            return new EndGameState(GameResult.DRAW);
        }
        if (command.isNo()) {
            game.nextTurn();
            return new PlayingState();
        }
        throw new IllegalArgumentException("[ERROR] y 또는 n으로 입력해 주세요.");
    }

    @Override
    public void display(JanggiGame game, OutputView outputView) {
        outputView.printBoard(game.getBoard(), game.getTurn());
        outputView.printBikjangQuestion(game.getCurrentTeam());
    }
}
