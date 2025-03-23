import board.Board;
import board.create.strategy.MaSangMaSang;
import board.create.strategy.MaSangSangMa;
import board.create.strategy.SangMaMaSang;
import board.create.strategy.SangMaSangMa;
import board.create.strategy.TableSettingStrategy;
import java.util.Map;
import team.Team;
import view.InputView;
import view.InputView.CoordinatesPair;
import view.OutputView;

public class Application {

    private static final Map<Integer, TableSettingStrategy> boardCreateStrategy = Map.of(
            1, new MaSangSangMa(),
            2, new MaSangMaSang(),
            3, new SangMaSangMa(),
            4, new SangMaMaSang()
    );

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        Board board = initializeBoard();
        outputView.printBoard(board.getPieces());

        startGame(board);
    }

    private Board initializeBoard() {
        int hanTableSetting = inputView.readTableSetting(Team.HAN);
        int choTableSetting = inputView.readTableSetting(Team.CHO);
        return Board.create(
                boardCreateStrategy.get(hanTableSetting),
                boardCreateStrategy.get(choTableSetting)
        );
    }

    private void startGame(Board board) {
        while (true) {
            for (Team team : Team.values()) {
                playTurn(board, team);
            }
        }
    }

    private void playTurn(Board board, Team team) {
        CoordinatesPair coordinatesPair = inputView.readMoveCoordinate(team);
        board.move(coordinatesPair.departure(), coordinatesPair.arrival());
        outputView.printBoard(board.getPieces());
    }
}
