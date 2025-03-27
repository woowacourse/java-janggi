package controller;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.game.JanggiGame;
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
        Team turn = janggiGame.getTeam();
        consoleView.showBoard(janggiGame.getBoard().getPieces());

        while (true) { // TODO : 2단계 궁성 구현에서 종료 로직 구현
            try {
                consoleView.printTurn(turn);
                BoardLocation current = consoleView.requestCurrent();
                BoardLocation destination = consoleView.requestDestination();

                janggiGame.process(current, destination);
                consoleView.showBoard(janggiGame.getBoard().getPieces());
                turn = janggiGame.opposite(janggiGame.getTeam());
            } catch (RuntimeException e) {
                consoleView.printMessage(e.getMessage());
            }
        }
    }
    private JanggiGame createJanggiGame() {
        Map<BoardLocation, Piece> placements = consoleView.requestPlacements();
        Board board = Board.createWithPieces(placements);
        Team team1 = Team.getStartingTeam();
        return new JanggiGame(board, team1);
    }
}
