package controller;

import database.MysqlConnectionManager;
import java.util.List;
import java.util.function.Supplier;
import model.JanggiGame;
import model.board.Board;
import model.board.Country;
import model.board.HorseElephantStrategy;
import model.board.strategy.ElephantSetup;
import model.move.Move;
import model.position.Position;
import service.GameService;
import view.InputView;
import view.OutputView;

public class GameController {
    private static final int START_NEW_MODE = 1;
    private static final int START_CONTINUE_MODE = 2;
    private final MysqlConnectionManager manager;
    private final GameService gameService;

    public GameController(MysqlConnectionManager manager) {
        this.manager = manager;
        this.gameService = new GameService(manager);
    }

    public void start() {
        OutputView.printStartMode();
        int mode = retry(() -> {
            int num = InputView.readGameMode();
            validateMode(num);
            return num;
        });

        JanggiGame game = prepareGame(mode);

        OutputView.printBoard(game.board());

        while (game.isProgressing()) {
            playTurn(game);
        }

        endGamePhase(game.board());
    }

    private JanggiGame prepareGame(int mode) {
        if (mode == START_NEW_MODE) {
            HorseElephantStrategy choStrategy = askHorseSetup(Country.CHO);
            OutputView.printLine();
            HorseElephantStrategy hanStrategy = askHorseSetup(Country.HAN);
            return gameService.startNewGame(choStrategy, hanStrategy);
        }

        return gameService.continueGame(mode);
    }

    private void playTurn(JanggiGame game) {
        OutputView.printPositionCountry(game.turn());

        retry(() -> {
            Position from = selectStartPosition();
            game.board().checkTurn(from, game.turn());
            Position to = selectEndPosition();

            gameService.moveAndSave(game, new Move(from, to));
        });

        OutputView.printBoard(game.board());
    }

    private HorseElephantStrategy askHorseSetup(Country country) {
        OutputView.printArrangeCountry(country);
        OutputView.printArrangeList(ElephantSetup.arrangementList(), country);
        return retry(() -> {
            int num = InputView.readArrangement();
            return ElephantSetup.init(num);
        });
    }

    private Position selectStartPosition() {
        List<Integer> startList = InputView.readStartPosition();
        return Position.of(startList.get(0), startList.get(1));
    }

    private Position selectEndPosition() {
        List<Integer> startList = InputView.readEndPosition();
        return Position.of(startList.get(0), startList.get(1));
    }

    private void endGamePhase(Board board) {
        board.winnerCountry().ifPresent(OutputView::printWinner);
        OutputView.printScore(Country.CHO, board.sumScore(Country.CHO));
        OutputView.printScore(Country.HAN, board.sumScore(Country.HAN));
    }

    private void validateMode(int mode) {
        if (mode != START_NEW_MODE && mode != START_CONTINUE_MODE) {
            throw new IllegalArgumentException("[ERROR] 올바른 번호를 입력해 주세요.");
        }
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private void retry(Runnable callback) {
        while (true) {
            try {
                callback.run();
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }
}
