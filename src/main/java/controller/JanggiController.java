package controller;

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

    public JanggiController(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public void start() {
        JanggiGame janggiGame = createJanggiGame();
        consoleView.showBoard(janggiGame.getBoard().getPieces());
        boolean isGameStopped = false;
        while (!isGameStopped) {
            try {
                consoleView.showScore(janggiGame.getTotalScore(Team.HAN), janggiGame.getTotalScore(Team.CHO));
                consoleView.showTurn(janggiGame.getTurn());
                BoardLocation current = consoleView.requestCurrent();
                BoardLocation destination = consoleView.requestDestination();

                janggiGame.process(current, destination);
                isGameStopped = janggiGame.isGameStopped();

                consoleView.showBoard(janggiGame.getBoard().getPieces());
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
        return new JanggiGame(board, turn);
    }
}
