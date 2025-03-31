package controller;

import dao.GameDao;
import dao.PieceDao;
import domain.board.Board;
import domain.board.BoardLocation;
import domain.game.JanggiGame;
import domain.game.Turn;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.Map;
import java.util.Optional;
import view.ConsoleView;

public class JanggiController {

    private final ConsoleView consoleView;
    private final GameDao gameDao;
    private final PieceDao pieceDao;

    private static final int GAME_ID = 1;

    public JanggiController(ConsoleView consoleView, GameDao gameDao, PieceDao pieceDao) {
        this.consoleView = consoleView;
        this.gameDao = gameDao;
        this.pieceDao = pieceDao;
    }

    public void start() {
        Optional<Board> board = pieceDao.findByAllAlivePieces();
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
        pieceDao.deleteBoard(destination);
        pieceDao.updateBoard(current, destination);
        gameDao.saveTurn(janggiGame.getTurn());
    }

    private JanggiGame initializeJanggiGame() {
        JanggiGame janggiGame = createJanggiGame();
        gameDao.insertGameTurn(Team.CHO);
        pieceDao.initializePieceIfNotExists(janggiGame.getBoard());
        return janggiGame;
    }

    private JanggiGame createJanggiGame() {
        Map<BoardLocation, Piece> placements = consoleView.requestPlacements();
        Board board = Board.createWithPieces(placements);
        Turn turn = Turn.getStartingTurn();
        return new JanggiGame(board, turn);
    }
}
