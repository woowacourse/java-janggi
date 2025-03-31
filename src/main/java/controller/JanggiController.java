package controller;

import dao.JanggiTransactionManager;
import dao.dto.UpdatePieceRequest;
import domain.board.Board;
import domain.board.BoardLocation;
import domain.game.JanggiGame;
import domain.game.Turn;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.Map;
import view.ConsoleView;

public class JanggiController {

    private final ConsoleView consoleView;
    private final JanggiTransactionManager transactionManager;

    public JanggiController(ConsoleView consoleView, JanggiTransactionManager transactionManager) {
        this.consoleView = consoleView;
        this.transactionManager = transactionManager;
    }

    public void start() {
        Long janggiGameId = 1L; // 추후 사용자에게 입력받기
        JanggiGame janggiGame = transactionManager.findById(janggiGameId)
                .orElse(createJanggiGame());

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
                transactionManager.update(janggiGameId, janggiGame, new UpdatePieceRequest(current, destination));
            } catch (RuntimeException e) {
                consoleView.showMessage(e.getMessage());
            }
        }

        consoleView.showWinner(janggiGame.getTurn());
    }

    private JanggiGame createJanggiGame() {
        Map<BoardLocation, Piece> placements = consoleView.requestPlacements();
        Board board = Board.createWithPieces(placements);
        Turn turn = Turn.getStartingTurn();
        JanggiGame janggiGame = new JanggiGame(board, turn);
        transactionManager.create(janggiGame);
        return janggiGame;
    }
}
