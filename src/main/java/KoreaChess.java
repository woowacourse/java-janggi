import domain.Board;
import domain.Player;
import domain.SetUp;
import domain.Team;
import domain.piece.PieceInitializer;
import domain.piece.Pieces;
import domain.piece.Position;
import java.util.HashMap;
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
        Player han = new Player(inputView.getName(Team.HAN), Team.HAN);
        Player cho = new Player(inputView.getName(Team.CHO), Team.CHO);

        SetUp setUp = inputView.readSetUp();
        Board board = createBoard(han, cho, setUp);

        outputView.printGameStart();
        outputView.printBoard(board);

        while (!board.isFinish()) {
            processTurn(han, board);
            if (board.isFinish()) {
                break;
            }
            processTurn(cho, board);
        }

        Player winner = board.getWinner();
        outputView.printWinner(winner);
    }

    private void processTurn(final Player player, final Board board) {
        Position movingPosition = inputView.readMovingPiecePosition(player);
        Position targetPosition = inputView.readTargetPiecePosition();
        board.move(player, movingPosition, targetPosition);
        outputView.printBoard(board);
    }

    private Board createBoard(final Player han, final Player cho, final SetUp setUp) {
        Pieces hanPieces = new Pieces(PieceInitializer.createTeamPieces(Team.HAN, setUp));
        Pieces choPieces = new Pieces(PieceInitializer.createTeamPieces(Team.CHO, setUp));

        Map<Player, Pieces> board = new HashMap<>();
        board.put(han, hanPieces);
        board.put(cho, choPieces);

        return new Board(board);
    }
}
