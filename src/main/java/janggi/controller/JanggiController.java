package janggi.controller;

import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import janggi.repository.GameRepository;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final GameRepository gameRepository;

    public JanggiController(InputView inputView, OutputView outputView, GameRepository gameRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRepository = gameRepository;
    }

    public void run() {

        Optional<Integer> activeGameId = gameRepository.findActiveGameId();

        if (activeGameId.isPresent() && inputView.readContinueGame()) {
            int gameId = activeGameId.get();
            Map<Point, Piece> board = gameRepository.loadPieces(gameId);
            Side turn = gameRepository.loadTurn(gameId);
            play(Game.loadGame(board, turn), gameId);
            return;
        }

        activeGameId.ifPresent(gameRepository::finish);

        BoardSetUp choBoardSetUp = retry(() -> inputView.readBoardSetup(Side.CHO));
        BoardSetUp hanBoardSetUp = retry(() -> inputView.readBoardSetup(Side.HAN));
        Game game = Game.createGame(choBoardSetUp, hanBoardSetUp);
        int gameId = gameRepository.saveGame(game.getTurn(), game.getBoard());
        play(game, gameId);
    }

    private void play(Game game, int gameId) {
        while (true) {
            Map<Point, Piece> board = game.getBoard();
            outputView.printBoard(board);
            outputView.printSide(game.getTurn());

            Side side = selectMove(game, board);

            gameRepository.updateGame(gameId, game.getTurn(), game.getBoard());

            outputView.printScore(Side.CHO, game.getScore(Side.CHO));
            outputView.printScore(Side.HAN, game.getScore(Side.HAN));

            if (side != Side.NONE) {
                outputView.printBoard(game.getBoard());
                outputView.printGameResult(side);
                break;
            }
        }
    }

    private Side selectMove(Game game, Map<Point, Piece> board) {
        while (true) {
            Point from = retry(() -> printPath(inputView.readPoint(), game, board));
            Optional<Point> to = retry(() -> inputView.readDestination());

            if (to.isEmpty()) {
                continue;
            }

            return retry(() -> move(game, from, to.get()));
        }
    }

    private Side move(Game game, Point from, Point to) {
        return game.move(from, to);
    }

    private Point printPath(Point from, Game game, Map<Point, Piece> board) {
        Set<Point> destinations = game.destinations(from);
        outputView.printBoardWithPath(board, destinations);
        return from;
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}
