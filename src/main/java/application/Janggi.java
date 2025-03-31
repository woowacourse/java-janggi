package application;

import application.persistence.BoardRepository;
import application.persistence.GameRepository;
import domain.Coordinate;
import domain.board.Board;
import domain.board.setting.ChoSettingUpStrategy;
import domain.board.setting.HanSettingUpStrategy;
import domain.game.Game;
import domain.piece.Country;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class Janggi {

    private final InputView inputView;
    private final OutputView outputView;
    private final BoardRepository boardRepository;
    private final GameRepository gameRepository;
    private Game game;

    public Janggi(
            InputView inputView, OutputView outputView,
            BoardRepository boardRepository, GameRepository gameRepository
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.boardRepository = boardRepository;
        this.gameRepository = gameRepository;
    }

    public void play() {
        List<Game> janggiGames = gameRepository.findAll();
        outputView.printAllGames(janggiGames);

        String gameName = inputView.readJoinGame();
        game = initGame(janggiGames, gameName);

        Board board = start();

        while (!isEndGame(board)) {
            takeTurn(board, this::movePiece);
            showScore(board);
            nextTurn();
        }
    }

    private Game initGame(List<Game> janggiGames, String gameName) {
        return janggiGames.stream()
                .filter(janggiGame -> janggiGame.getName().equals(gameName))
                .findFirst()
                .orElse(createGame(gameName));
    }

    private Game createGame(String gameName) {
        Game newGame = new Game(gameName, Country.CHO);
        gameRepository.save(newGame);
        return newGame;
    }

    private Board start() {
        Board stored = boardRepository.findAll();
        if (stored.isEmpty()) {
            return newGame();
        }
        return previousGame(stored);
    }

    private Board newGame() {
        Board board = settingUp();
        boardRepository.saveAll(board);
        gameRepository.save(game);
        outputView.printNewGameMessage();
        return board;
    }

    private Board previousGame(Board savedBoard) {
        game = gameRepository.findTurn();
        outputView.printPreviousGameMessage();
        return savedBoard;
    }

    public void takeTurn(Board board, Consumer<Board> consumer) {
        while (true) {
            try {
                consumer.accept(board);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Board settingUp() {
        HanSettingUpStrategy hanSettingUpStrategy = retryUntilValid(() ->
                HanSettingUpStrategy.selectStrategy(inputView.readSettingUp(Country.HAN)));

        ChoSettingUpStrategy choSettingUpStrategy = retryUntilValid(() ->
                ChoSettingUpStrategy.selectStrategy(inputView.readSettingUp(Country.CHO)));

        return new Board(choSettingUpStrategy, hanSettingUpStrategy);
    }

    private void movePiece(Board board) {
        outputView.printJanggiBoard(board);

        Coordinate from = retryUntilValid(() -> inputView.readMoveFrom(game.getCurrentName()));
        board.validateIsMyPiece(from, game.getCountry());
        Coordinate to = retryUntilValid(inputView::readMoveTo);

        board.movePiece(from, to);

        updateBoard(board);
    }

    private void updateBoard(Board board) {
        boardRepository.deleteAll();
        boardRepository.saveAll(board);
    }

    private boolean isEndGame(Board board) {
        boolean isChoGungDead = board.isChoGungDead();
        boolean isHanGungDead = board.isHanGungDead();
        if (isChoGungDead || isHanGungDead) {
            boardRepository.deleteAll();
            gameRepository.delete();
            outputView.printEndGame(isChoGungDead, isHanGungDead);
            return true;
        }
        return false;
    }

    private void showScore(Board board) {
        int hanScore = board.calculateHanScore();
        int choScore = board.calculateChoScore();

        outputView.printScore(hanScore, choScore);
    }

    private void nextTurn() {
        game.next();
        gameRepository.updateTurn(game);
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
}
