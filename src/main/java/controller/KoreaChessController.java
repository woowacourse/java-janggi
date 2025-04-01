package controller;

import domain.Board;
import domain.Game;
import domain.Player;
import domain.piece.Piece;
import domain.spatial.Position;
import java.util.List;
import service.GameInitializerService;
import service.GameLoadService;
import service.GameService;
import service.PieceService;
import view.InputView;
import view.OutputView;

public class KoreaChessController {

    private final OutputView outputView;
    private final InputView inputView;
    private final GameInitializerService gameInitializerService;
    private final GameLoadService gameLoadService;
    private final GameService gameService;
    private final PieceService pieceService;

    public KoreaChessController(final OutputView outputView, final InputView inputView,
                                final GameInitializerService gameInitializerService,
                                final GameLoadService gameLoadService,
                                final GameService gameService,
                                final PieceService pieceService) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.gameInitializerService = gameInitializerService;
        this.gameLoadService = gameLoadService;
        this.gameService = gameService;
        this.pieceService = pieceService;
    }

    public void run() {
        outputView.printGameStart();

        if (gameService.hasPlayingGame()) {
            handleExistingGame();
            return;
        }
        startNewGame();
    }

    private void handleExistingGame() {
        if (inputView.readGameSelection()) {
            String gameName = selectGame();
            loadSelectedGame(gameName);
            return;
        }
        startNewGame();
    }

    private String selectGame() {
        List<String> gameNames = gameService.findGameNameAll();
        outputView.printGameList(gameNames);
        String gameName = inputView.readGameName();

        if (!gameNames.contains(gameName)) {
            throw new IllegalArgumentException("잘못된 이름입니다. 다시 입력해 주세요.");
        }
        return gameName;
    }

    private void loadSelectedGame(final String gameName) {
        Game game = gameLoadService.loadGame(gameName);
        playGame(game);
    }

    private void startNewGame() {
        Game game = gameInitializerService.initializeGame();
        playGame(game);
    }

    private void playGame(final Game game) {
        Board board = game.getBoard();
        outputView.printBoard(board);

        Player han = board.getHanPlayer();
        Player cho = board.getChoPlayer();

        while (!board.isGameFinished() && !inputView.isGameTurnEnd()) {
            processTurn(game, han, board);
            if (board.isGameFinished()) {
                break;
            }
            processTurn(game, cho, board);
        }
        printGameResult(board);
    }

    private void processTurn(final Game game, final Player player, final Board board) {
        while (true) {
            try {
                Position start = parseToPosition(inputView.readMovingPiecePosition(player));
                Position target = parseToPosition(inputView.readTargetPiecePosition());
                Piece moved = board.moveAndCapture(player, start, target);

                pieceService.delete(game.getName(), player.getTeam(), target);
                pieceService.update(game.getName(), player.getTeam(), start, moved);

                outputView.printBoard(board);
                return;
            } catch (Exception e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private Position parseToPosition(final String input) {
        List<String> positionElements = List.of(input.split(","));
        int row = Integer.parseInt(positionElements.getFirst());
        int column = Integer.parseInt(positionElements.getLast());

        return new Position(row, column);
    }

    private void printGameResult(final Board board) {
        outputView.printGameResult(board, board.getGameResult());
    }
}
