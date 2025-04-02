package controller;

import persistence.JanggiPersistenceManager;
import persistence.dto.UpdatePieceRequest;
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
    private final JanggiPersistenceManager janggiPersistenceManager;

    public JanggiController(ConsoleView consoleView, JanggiPersistenceManager janggiPersistenceManager) {
        this.consoleView = consoleView;
        this.janggiPersistenceManager = janggiPersistenceManager;
    }

    public void start() {
        Long janggiGameId = 1L; // 추후 사용자에게 입력받기
        JanggiGame janggiGame = prepareGame(janggiGameId);
        consoleView.showBoard(janggiGame.getBoard().getPieces());
        boolean isGameStopped = false;
        while (!isGameStopped) {
            try {
                consoleView.showScore(janggiGame.calculateScoreByTeam(Team.HAN), janggiGame.calculateScoreByTeam(Team.CHO));
                consoleView.showTurn(janggiGame.getTurn());
                BoardLocation current = consoleView.requestCurrent();
                BoardLocation destination = consoleView.requestDestination();

                janggiGame.process(current, destination);
                isGameStopped = janggiGame.isGameStopped();

                consoleView.showBoard(janggiGame.getBoard().getPieces());
                janggiPersistenceManager.update(janggiGameId, janggiGame, new UpdatePieceRequest(current, destination));
            } catch (RuntimeException e) {
                consoleView.showMessage(e.getMessage());
            }
        }

        consoleView.showWinner(janggiGame.getTurn());
    }

    private JanggiGame prepareGame(Long janggiGameId) {
        Optional<JanggiGame> janggiGame = janggiPersistenceManager.findById(janggiGameId);
        return janggiGame.orElseGet(this::createJanggiGame);
    }

    private JanggiGame createJanggiGame() {
        Map<BoardLocation, Piece> placements = consoleView.requestPlacements();
        Board board = Board.createWithPieces(placements);
        Turn turn = Turn.getStartingTurn();
        JanggiGame janggiGame = new JanggiGame(board, turn);
        janggiPersistenceManager.create(janggiGame);
        return janggiGame;
    }
}
