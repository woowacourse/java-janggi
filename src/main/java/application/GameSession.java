package application;

import domain.game.Game;
import domain.piece.Camp;
import repository.GameRepository;
import view.GameCommand;
import view.InputView;
import view.OutputView;

public class GameSession {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameRepository gameRepository;

    public GameSession(InputView inputView, OutputView outputView, GameRepository gameRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
    }

    public void run(Game game) {
        outputView.printBoard(game.board());

        while (!game.isFinished()) {
            executeTurn(game);
        }

        outputView.printWinner(game.currentTurn());
    }

    private void executeTurn(Game game) {
        outputView.printTurnPrompt(game.currentTurn());

        try {
            GameCommand command = inputView.readCommand();
            command.execute(game);

            gameRepository.save(game);

            outputView.printBoard(game.board());
            outputView.printScore(game.scoreOf(Camp.CHO), game.scoreOf(Camp.HAN));
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
        }
    }
}
