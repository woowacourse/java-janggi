import domain.Team;
import domain.board.Board;
import domain.board.BoardBuilder;
import domain.board.maSangStrategy.MaSangStrategy;
import domain.board.maSangStrategy.MaSangMaSang;
import domain.board.maSangStrategy.MaSangSangMa;
import domain.board.maSangStrategy.SangMaMaSang;
import domain.board.maSangStrategy.SangMaSangMa;
import java.util.Map;
import view.InputView;
import view.InputView.CoordinatesPair;
import view.OutputView;

public class Application {

    private static final Map<Integer, MaSangStrategy> boardCreateStrategy = Map.of(
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
        Board board = createBoard(hanTableSetting, choTableSetting);
        outputView.printBoard(board.getPieces());

        while (true) {
            for (Team team : Team.values()) {
                CoordinatesPair coordinatesPair = inputView.readMoveCoordinate(team);
                board.move(coordinatesPair.departure(), coordinatesPair.arrival());
                outputView.printBoard(board.getPieces());
            }
        }
    }

    private static Board createBoard(final int hanTableSetting, final int choTableSetting) {
        MaSangStrategy hanBoardStrategy = boardCreateStrategy.get(hanTableSetting);
        MaSangStrategy choBoardStrategy = boardCreateStrategy.get(choTableSetting);

        return new BoardBuilder()
            .initTeam(Team.HAN, hanBoardStrategy)
            .initTeam(Team.CHO, choBoardStrategy)
            .build();
    }
}
