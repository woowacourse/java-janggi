package application;

import domain.board.SetUp;
import domain.game.Game;
import domain.piece.Camp;
import java.util.Optional;
import repository.GameRepository;
import view.InputView;
import view.OutputView;

public class GameStarter {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameRepository gameRepository;

    public GameStarter(InputView inputView, OutputView outputView, GameRepository gameRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
    }

    public Game start() {
        Optional<Game> inProgressGame = gameRepository.findInProgressGame();

        if (inProgressGame.isPresent()) {
            Game savedGame = inProgressGame.get();

            if (askContinue()) {
                return savedGame;
            }
        }

        return startNewGame();
    }

    private Game startNewGame() {
        outputView.printSetUpOptions();
        SetUp choSetUp = readSetUp(Camp.CHO);
        SetUp hanSetUp = readSetUp(Camp.HAN);

        Game game = Game.start(choSetUp, hanSetUp);
        gameRepository.save(game);

        return game;
    }

    private SetUp readSetUp(Camp camp) {
        while (true) {

            outputView.printSetUpPrompt(camp);
            try {
                return SetUpOption.from(inputView.readSetUpNumber());
            } catch (IllegalArgumentException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }

    private boolean askContinue() {
        while (true) {
            outputView.printResumePrompt();

            try {
                return inputView.readContinueAnswer();
            } catch (IllegalArgumentException exception) {
                outputView.printError(exception.getMessage());
            }
        }
    }
}
