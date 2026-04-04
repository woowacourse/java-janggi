import domain.Board;
import domain.Camp;
import domain.ElephantFormation;
import domain.InvalidMoveException;
import domain.Position;
import dto.BoardStatusDto;
import java.util.HashMap;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    private final Map<Integer, ElephantFormation> INPUT_FORMATION_MAPPER = new HashMap<>(Map.of(
            1, ElephantFormation.RIGHT,
            2, ElephantFormation.INNER,
            3, ElephantFormation.LEFT,
            4, ElephantFormation.OUTER
    ));

    JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Board board = generateBoard();
        printBoard(board);

        playJanggi(board);
    }

    private Board generateBoard() {
        Board board = new Board();
        int choElephantFormation = inputView.askElephantFormation(Camp.CHO);
        int hanElephantFormation = inputView.askElephantFormation(Camp.HAN);
        board.generatePiecesBy(Camp.CHO, mappingElephantFormation(choElephantFormation));
        board.generatePiecesBy(Camp.HAN, mappingElephantFormation(hanElephantFormation));
        return board;
    }

    private void playJanggi(Board board) {
        Camp camp = Camp.CHO;
        while (true) {
            try {
                Position fromPosition = askFromPosition(camp, board);
                Position toPosition = askToPosition(camp);
                board.move(fromPosition, toPosition);
            } catch (InvalidMoveException e) {
                outputView.printErrorMessage(e);
                continue;
            }
            printBoard(board);
            camp = turnCamp(camp);

            //Todo: 사이클2 왕이 잡히면, 게임이 종료
        }
    }

    private Camp turnCamp(Camp camp) {
        if (camp.equals(Camp.CHO)) {
            return Camp.HAN;
        }
        return Camp.CHO;
    }

    private Position askFromPosition(Camp camp, Board board) {
        while (true) {
            Position fromPosition = inputView.readFromPosition(camp);
            if (!board.isPieceOfCamp(fromPosition, camp)) {
                outputView.printWrongChoice();
                continue;
            }
            return fromPosition;
        }
    }

    private Position askToPosition(Camp camp) {
        return inputView.readToPosition(camp);
    }

    private void printBoard(Board board) {
        BoardStatusDto boardStatus = board.getBoardStatus();
        outputView.printBoardStatus(boardStatus);
    }

    private ElephantFormation mappingElephantFormation(int userInput) {
        return INPUT_FORMATION_MAPPER.get(userInput);
    }
}
