import domain.BoardLocation;
import domain.Team;
import domain.Board;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import view.ConsoleView;

public class JanggiGame {

    private final ConsoleView consoleView;

    public JanggiGame(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public void start() {
        Map<BoardLocation, Piece> placements = consoleView.requestPlacements();

        Board board = Board.createWithPieces(placements);

        Team team = Team.getStartingTeam();
        consoleView.showBoard(board.getPieces());
        while (true) {
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

        Piece piece = board.findByLocation(current);

        piece.validateEqualTeam(team);
        piece.validateMovable(current, destination);

        List<BoardLocation> allPath = piece.createAllPath(current, destination);
        List<Piece> pathPiece = board.extractPathPiece(allPath);
        piece.validateArrival(pathPiece);

        Piece destinationPiece = board.getByLocation(destination);
        piece.validateOccupiable(destinationPiece);

        board.removeIfHas(destination);
        board.occupy(current, destination);
    }
}
