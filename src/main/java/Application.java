import board.Board;
import board.strategy.BoardCreateStrategy;
import board.strategy.MaSangMaSang;
import board.strategy.MaSangSangMa;
import board.strategy.SangMaMaSang;
import board.strategy.SangMaSangMa;
import java.util.Map;
import team.Team;
import view.InputView;
import view.InputView.CoordinatesPair;
import view.OutputView;

public class Application {

    private static final Map<Integer, BoardCreateStrategy> boardCreateStrategy = Map.of(
            1, new MaSangSangMa(),
            2, new MaSangMaSang(),
            3, new SangMaSangMa(),
            4, new SangMaMaSang()
    );

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        int hanTableSetting = inputView.readTableSetting(Team.HAN);
        int choTableSetting = inputView.readTableSetting(Team.CHO);
        Board board = Board.create(boardCreateStrategy.get(hanTableSetting), boardCreateStrategy.get(choTableSetting));
        outputView.printBoard(board.getPieces());

        while (true) {
            for (Team team : Team.values()) {
                CoordinatesPair coordinatesPair = inputView.readMoveCoordinate(team);
                board.move(coordinatesPair.departure(), coordinatesPair.arrival());
                outputView.printBoard(board.getPieces());
            }
        }
    }
}
