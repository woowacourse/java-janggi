import game.Board;
import game.Country;
import game.StartingPosition;
import game.Team;
import java.util.List;
import piece.StaticPieceInitializer;
import position.Position;
import position.PositionFile;
import position.PositionRank;
import view.InputView;
import view.OutputView;

public class JanggiApplication {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        JanggiApplication janggiApplication = new JanggiApplication();
        janggiApplication.run();
    }

    private void run() {
        StartingPosition startingCho = readStartingOption();
        StartingPosition startingHan = readStartingOption();

        Board board = initializeBoard(startingCho, startingHan);
        outputView.displayBoard(board);
        moveUntilFinished(board);
    }

    private void moveUntilFinished(Board board) {
        while (true) {
            outputView.printTurn(board.getCurrentTurnTeam());
            List<String> moveInfo = inputView.readMoveCommand();

            Position source = new Position(PositionFile.of(moveInfo.get(0)), PositionRank.of(moveInfo.get(1)));
            Position target = new Position(PositionFile.of(moveInfo.get(2)), PositionRank.of(moveInfo.get(3)));

            board.move(source, target);
            outputView.displayBoard(board);
        }
    }

    private Board initializeBoard(StartingPosition startingCho, StartingPosition startingHan) {
        Team cho = new Team(startingCho, new StaticPieceInitializer(), Country.CHO);
        Team han = new Team(startingHan, new StaticPieceInitializer(), Country.HAN);
        return new Board(cho, han);
    }

    private StartingPosition readStartingOption() {
        return StartingPosition.fromOption(inputView.getStartingPosition());
    }


}
