import domain.Board;
import domain.Camp;
import domain.InvalidMoveException;
import domain.Position;
import dto.BoardStatusDto;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Board board = generateBoard();

        playJanggi(board);
    }

    private Board generateBoard() {
        Board board = new Board();
        int choElephantFormation = inputView.askElephantFormation(Camp.CHO);
        int hanElephantFormation = inputView.askElephantFormation(Camp.HAN);
        board.generatePiecesBy(Camp.CHO, choElephantFormation);
        board.generatePiecesBy(Camp.HAN, hanElephantFormation);
        return board;
    }

    private void playJanggi(Board board) {
        Camp camp = Camp.CHO;
        printBoard(board);
        while (!board.isGameOver()) {
            try {
                Position fromPosition = askFromPosition(camp, board);
                Position toPosition = askToPosition(camp);
                board.move(fromPosition, toPosition);
                printBoard(board);
                camp = camp.turnCamp();
            } catch (InvalidMoveException e) {
                outputView.printErrorMessage(e);
            }
        }
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
}
