package controller;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.game.JanggiGame;
import domain.game.Turn;
import domain.piece.Piece;
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
        boolean isSurrender = false;
        while (!isSurrender) {
            try {
                consoleView.printTurn(janggiGame.getTurn());
                BoardLocation current = consoleView.requestCurrent();
                BoardLocation destination = consoleView.requestDestination();

                janggiGame.process(current, destination);

                consoleView.showBoard(janggiGame.getBoard().getPieces());
                isSurrender = consoleView.requestSurrender();
            } catch (RuntimeException e) {
                consoleView.printMessage(e.getMessage());
            }
        }
    }

    private JanggiGame createJanggiGame() {
        Map<BoardLocation, Piece> placements = consoleView.requestPlacements();
        Board board = Board.createWithPieces(placements);
        Turn turn = Turn.getStartingTurn();
        return new JanggiGame(board, turn);
    }
}
