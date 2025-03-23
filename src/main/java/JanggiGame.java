import domain.Board;
import domain.BoardLocation;
import domain.Team;
import domain.TeamBoard;
import java.util.List;
import view.ConsoleView;

public class JanggiGame {

    private final ConsoleView consoleView;

    public JanggiGame(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public void start() {
        List<BoardLocation> hanHorses = List.of(new BoardLocation(2, 1), new BoardLocation(8, 1));
        List<BoardLocation> hanElephants = List.of(new BoardLocation(3, 1), new BoardLocation(7, 1));

        List<BoardLocation> choHorses = List.of(new BoardLocation(2, 10), new BoardLocation(8, 10));
        List<BoardLocation> choElephants = List.of(new BoardLocation(3, 10), new BoardLocation(7, 10));

        TeamBoard teamBoard = TeamBoard.createWithPieces(hanHorses, hanElephants, choHorses, choElephants);
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
