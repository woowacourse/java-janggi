package view;

import application.JanggiUseCase;
import domain.board.Board;
import domain.board.setting.ChoSettingUpStrategy;
import domain.board.setting.HanSettingUpStrategy;
import domain.game.Game;
import domain.piece.Country;
import domain.piece.coordiante.Coordinate;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class JanggiConsole {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiUseCase janggiUseCase;

    public JanggiConsole(InputView inputView, OutputView outputView, JanggiUseCase janggiUseCase) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiUseCase = janggiUseCase;
    }

    public void play() {
        loadGame();
        Board board = initBoard();
        playGame(board);
        endGame(board);
    }

    private void loadGame() {
        List<Game> games = janggiUseCase.findAllGames();
        outputView.printAllGames(games);

        String gameName = inputView.readJoinGame();
        janggiUseCase.loadGame(gameName);
    }

    private Board initBoard() {
        Board board = janggiUseCase.setUpBoard();
        if (board == null) {
            board = newBoard();
            outputView.printNewGameMessage();
        } else {
            outputView.printPreviousGameMessage();
        }
        return board;
    }

    private void playGame(Board board) {
        while (!janggiUseCase.isEndGame(board)) {
            takeTurn(board, this::movePiece);
            showScore(board);
            janggiUseCase.nextTurn();
        }
    }

    private void endGame(Board board) {
        outputView.printEndGame(
                board.isChoGungDead(),
                board.isHanGungDead()
        );
    }

    private Board newBoard() {
        HanSettingUpStrategy hanSetting = retryUntilValid(() ->
                HanSettingUpStrategy.selectStrategy(inputView.readSettingUp(Country.HAN)));

        ChoSettingUpStrategy choSetting = retryUntilValid(() ->
                ChoSettingUpStrategy.selectStrategy(inputView.readSettingUp(Country.CHO)));

        return janggiUseCase.newBoard(hanSetting, choSetting);
    }

    private void movePiece(Board board) {
        outputView.printJanggiBoard(board);

        Coordinate from = retryUntilValid(
                () -> inputView.readMoveFrom(janggiUseCase.getCurrentGame().getCurrentName()));
        Coordinate to = retryUntilValid(inputView::readMoveTo);

        janggiUseCase.movePiece(board, from, to);
    }

    private void showScore(Board board) {
        int hanScore = janggiUseCase.getHanScore(board);
        int choScore = janggiUseCase.getChoScore(board);
        outputView.printScore(hanScore, choScore);
    }

    private <T> T retryUntilValid(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void takeTurn(Board board, Consumer<Board> consumer) {
        while (true) {
            try {
                consumer.accept(board);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
