package game.controller;

import game.domain.board.Board;
import game.domain.board.BoardLocation;
import game.domain.board.PieceExtractor;
import game.domain.board.PieceFinder;
import game.domain.piece.Piece;
import game.domain.piece.Team;
import game.view.ConsoleView;
import java.util.Map;

public class JanggiController {

    private final ConsoleView consoleView;

    public JanggiController(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public void start() {
        Map<BoardLocation, Piece> placements = consoleView.requestPlacements();

        Board board = Board.createWithPieces(placements);

        Team team = Team.getStartingTeam();
        consoleView.showBoard(board.getPieces());

        while (true) { // TODO : 2단계 궁성 구현에서 종료 로직 구현
            try {
                consoleView.printTurn(team);

                processGame(board, team);

                consoleView.showBoard(board.getPieces());
                team = team.opposite();
            } catch (RuntimeException e) {
                consoleView.printMessage(e.getMessage());
            }
        }
    }

    private void processGame(Board board, Team team) {
        BoardLocation current = consoleView.requestCurrent();
        BoardLocation destination = consoleView.requestDestination();

        Piece piece = board.getByLocationOrThrow(current);
        piece.validateEqualTeam(team);

        PieceExtractor pieceExtractor = board::extractPathPiece;
        PieceFinder pieceFinder = board::findByLocation;

        piece.validateMovable(current, destination, pieceExtractor, pieceFinder);

        board.occupy(current, destination);
    }
}
