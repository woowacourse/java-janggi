package controller;

import dao.GameDao;
import domain.board.Board;
import domain.board.BoardLocation;
import domain.game.JanggiGame;
import domain.game.Turn;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.Map;
import java.util.Optional;
import service.PieceService;
import view.ConsoleView;

public class JanggiController {

    private final ConsoleView consoleView;
    private final GameDao gameDao;
    private final PieceService pieceService;

    private static final int GAME_ID = 1;

    public JanggiController(ConsoleView consoleView, GameDao gameDao, PieceService pieceService) {
        this.consoleView = consoleView;
        this.gameDao = gameDao;
        this.pieceService = pieceService;
    }

    public void start() {
        Optional<Board> board = pieceService.findInitializeBoard();
        Optional<Turn> turn = gameDao.findTurnByGameId(GAME_ID);
        JanggiGame janggiGame;
        if (board.isEmpty() || turn.isEmpty()) {
            janggiGame = initializeJanggiGame();
        } else {
            janggiGame = new JanggiGame(board.get(), turn.get());
        }

        consoleView.showBoard(janggiGame.getBoard().getPieces());
        boolean isGameStopped = false;
        while (!isGameStopped) {
            try {
                consoleView.showScore(janggiGame.getTotalScore(Team.HAN), janggiGame.getTotalScore(Team.CHO));
                consoleView.showTurn(janggiGame.getTurn());
                BoardLocation current = consoleView.requestCurrent();
                BoardLocation destination = consoleView.requestDestination();

                janggiGame.process(current, destination);
                processOneTurn(destination, current, janggiGame);
                isGameStopped = janggiGame.isGameStopped();
                consoleView.showBoard(janggiGame.getBoard().getPieces());
            } catch (RuntimeException e) {
                consoleView.showMessage(e.getMessage());
            }
        }
        consoleView.showWinner(janggiGame.getTurn());
    }

    private void processOneTurn(BoardLocation destination, BoardLocation current, JanggiGame janggiGame) {
        pieceService.pieceMove(current, destination);
        gameDao.saveTurn(janggiGame.getTurn());
    }

    private JanggiGame initializeJanggiGame() {
        JanggiGame janggiGame = createJanggiGame();
        gameDao.insertGameTurn(Team.CHO);
        pieceService.initializePieceIfNotExists(janggiGame);
        return janggiGame;
    }

    private JanggiGame createJanggiGame() {
        Map<BoardLocation, Piece> placements = consoleView.requestPlacements();
        Board board = Board.createWithPieces(placements);
        Turn turn = Turn.getStartingTurn();
        return new JanggiGame(board, turn);
    }
}
