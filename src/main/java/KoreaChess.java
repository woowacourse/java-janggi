import domain.Board;
import domain.Player;
import domain.Team;
import domain.piece.Pieces;
import domain.spatial.Position;
import domain.strategy.SettingUp;
import domain.strategy.SettingUpInitializer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class KoreaChess {

    private final OutputView outputView;
    private final InputView inputView;

    public KoreaChess(final OutputView outputView, final InputView inputView) {
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run() {
        outputView.printGameStart();

        Player han = new Player(Team.HAN);
        Player cho = new Player(Team.CHO);
        Board board = initializeGame(han, cho);

        while (!board.isFinish()) {
            processTurn(han, board);
            if (board.isFinish()) {
                break;
            }
            processTurn(cho, board);
        }
        printWinner(board);
    }

    private Board initializeGame(final Player han, final Player cho) {
        Board board = createBoard(han, cho);
        outputView.printBoard(board);
        return board;
    }

    private Board createBoard(final Player han, final Player cho) {
        Map<Player, Pieces> board = new HashMap<>();
        board.put(han, createPiecesByPlayer(han));
        board.put(cho, createPiecesByPlayer(cho));

        return new Board(board);
    }

    private Pieces createPiecesByPlayer(final Player player) {
        int command = inputView.readSettingUpStrategyCommand(player);
        SettingUpInitializer strategy = SettingUp.findStrategyByCommand(command);
        return strategy.initPieces(player);
    }

    private void processTurn(final Player player, final Board board) {
        Position movingHanPosition = parseToPosition(inputView.readMovingPiecePosition(player));
        Position targetHanPosition = parseToPosition(inputView.readTargetPiecePosition());
        board.moveAndCapture(player, movingHanPosition, targetHanPosition);
        outputView.printBoard(board);
    }

    private Position parseToPosition(final String input) {
        List<String> positionElements = List.of(input.split(","));
        int row = Integer.parseInt(positionElements.getFirst());
        int column = Integer.parseInt(positionElements.getLast());

        return new Position(row, column);
    }

    private void printWinner(final Board board) {
        Player winner = board.getWinner();
        outputView.printWinner(winner);
    }
}
