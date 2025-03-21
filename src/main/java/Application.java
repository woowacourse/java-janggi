import domain.Team;
import domain.board.Board;
import domain.board.strategy.BoardCreateStrategy;
import domain.board.strategy.MaSangMaSang;
import domain.board.strategy.MaSangSangMa;
import domain.board.strategy.SangMaMaSang;
import domain.board.strategy.SangMaSangMa;
import java.util.Map;
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
