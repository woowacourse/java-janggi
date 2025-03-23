import domain.Board;
import domain.BoardLocation;
import domain.Team;
import domain.TeamBoard;
import domain.piece.Piece;
import java.util.Map;
import view.ConsoleView;

public class JanggiGame {

    private final ConsoleView consoleView;

    public JanggiGame(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public void start() {
        Map<BoardLocation, Piece> placements = consoleView.requestPlacements();

        TeamBoard teamBoard = TeamBoard.createWithPieces(placements);
        Board board = new Board(teamBoard);

        Team team = Team.getStartingTeam();
        consoleView.showBoard();
        while (true) {
            consoleView.printTurn(team);

            BoardLocation current = consoleView.requestCurrent();
            BoardLocation destination = consoleView.requestDestination();
            board.movePiece(team, current, destination);

            consoleView.printResult(team);
            team = team.opposite();
        }
    }
}
