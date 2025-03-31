package application;

import application.persistence.GameRepository;
import application.persistence.PieceRepository;
import domain.Coordinate;
import domain.board.Board;
import domain.board.setting.ChoSettingUpStrategy;
import domain.board.setting.HanSettingUpStrategy;
import domain.game.Game;
import domain.piece.Country;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class Janggi {

    private final InputView inputView;
    private final OutputView outputView;
    private final PieceRepository pieceRepository;
    private final GameRepository gameRepository;
    private Game game;

    public Janggi(
            InputView inputView, OutputView outputView,
            PieceRepository pieceRepository, GameRepository gameRepository
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.pieceRepository = pieceRepository;
        this.gameRepository = gameRepository;
    }

    public void play() {
        List<Game> janggiGames = gameRepository.findAll();
        outputView.printAllGames(janggiGames);

        String gameName = inputView.readJoinGame();
        game = startGame(janggiGames, gameName);

        Board board = setUp();

        while (!isEndGame(board)) {
            takeTurn(board, this::movePiece);
            showScore(board);
            nextTurn();
        }
    }

    private Game startGame(List<Game> janggiGames, String gameName) {
        return janggiGames.stream()
                .filter(janggiGame -> janggiGame.getName().equals(gameName))
                .findFirst()
                .orElseGet(() -> createGame(gameName));
    }

    private Game createGame(String gameName) {
        Game newGame = new Game(gameName, Country.CHO);
        return gameRepository.save(newGame);
    }

    private Board setUp() {
        Map<Coordinate, Piece> stored = pieceRepository.findByGame(game);
        if (stored.isEmpty()) {
            return newBoard();
        }
        return previousBoard(stored);
    }

    private Board newBoard() {
        Board board = settingUp();
        System.out.println("gameid" + game.getId());
        pieceRepository.savePieces(board.getBoard(), game);
        outputView.printNewGameMessage();
        return board;
    }

    private Board previousBoard(Map<Coordinate, Piece> savedPieces) {
        outputView.printPreviousGameMessage();
        return new Board(savedPieces);
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
        pieceRepository.deleteByGame(game);
        pieceRepository.savePieces(board.getBoard(), game);
    }

    private boolean isEndGame(Board board) {
        boolean isChoGungDead = board.isChoGungDead();
        boolean isHanGungDead = board.isHanGungDead();
        if (isChoGungDead || isHanGungDead) {
            pieceRepository.deleteByGame(game);
            gameRepository.deleteGame(game);
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
        gameRepository.updateGame(game);
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
