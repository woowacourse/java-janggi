package domain.state;

import domain.board.Board;
import domain.game.JanggiGame;
import domain.piece.Team;
import domain.setup.Command;
import io.OutputView;

public class BikjangState implements GameState {

    @Override
    public GameState handle(JanggiGame game, Command command) {
        if (command.isYes()) {
            Board board = game.getBoard().orElseThrow();
            GameResult result = GameResult.fromScore(
                    board.calculateScore(Team.HAN),
                    board.calculateScore(Team.CHO)
            );
            return new EndGameState(result);
        }
        if (command.isNo()) {
            game.nextTurn();
            return new PlayingState();
        }
        throw new IllegalArgumentException("[ERROR] y 또는 n으로 입력해 주세요.");
    }

    @Override
    public void display(JanggiGame game, OutputView outputView) {
        outputView.printBoard(game.getBoard().orElseThrow(), game.getTurn());
        outputView.printBikjangQuestion(game.getCurrentTeam());
    }

    @Override
    public GameStateName stateName() {
        return GameStateName.BIKJANG;
    }
}
