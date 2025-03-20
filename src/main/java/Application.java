import board.Board;
import board.BoardFactory;
import piece.TeamType;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        OutputView.printIntroduce();
        final BoardFactory boardFactory = new BoardFactory();
        final Board board = boardFactory.generateBoard();
        TeamType type = TeamType.RED;
        while (true) {
            type = changeTeamType(type);
            OutputView.printBoard(board, type);

            break;
        }
    }

    private static TeamType changeTeamType(final TeamType teamType) {
        return TeamType.changeTeamType(teamType);
    }
}
