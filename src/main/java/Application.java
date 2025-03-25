import domain.Coordinate;
import domain.Team;
import domain.board.Board;
import domain.board.BoardBuilder;
import domain.board.maSangStrategy.MaSangMaSang;
import domain.board.maSangStrategy.MaSangSangMa;
import domain.board.maSangStrategy.MaSangStrategy;
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

    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        Board board = loadBoard();
        outputView.printBoard(board.getPieces());

        processGame(board);
    }

    private static Board loadBoard() {
        int hanTableSetting = inputView.readTableSetting(Team.HAN);
        int choTableSetting = inputView.readTableSetting(Team.CHO);

        return createBoard(hanTableSetting, choTableSetting);
    }

    private static Board createBoard(final int hanTableSetting, final int choTableSetting) {
        MaSangStrategy hanBoardStrategy = boardCreateStrategy.get(hanTableSetting);
        MaSangStrategy choBoardStrategy = boardCreateStrategy.get(choTableSetting);

        return new BoardBuilder()
            .initTeam(Team.HAN, hanBoardStrategy)
            .initTeam(Team.CHO, choBoardStrategy)
            .build();
    }

    private static void processGame(final Board board) {
        int index = 0;
        while (true) {
            Team team = Team.values()[index++ % 2];
            try {
                CoordinatesPair coordinatesPair = inputView.readMoveCoordinate(team);
                checkDepartureIsMyPiece(team, board, coordinatesPair.departure());

                movePiece(board, coordinatesPair);

                outputView.printBoard(board.getPieces());
            } catch (IllegalArgumentException e) {
                outputView.printException(e);
            }
        }
    }

    private static void movePiece(final Board board, final CoordinatesPair coordinatesPair) {
        final var departure = coordinatesPair.departure();
        final var arrival = coordinatesPair.arrival();

        board.move(departure, arrival);
    }

    private static void checkDepartureIsMyPiece(Team team, final Board board,
        final Coordinate departure) {
        boolean selectsMyTeam = board.findAt(departure)
            .map(p -> p.isTeam(team))
            .orElse(true);
        if (!selectsMyTeam) {
            throw new IllegalArgumentException("같은 팀 기물만 선택할 수 있습니다.");
        }
    }
}
