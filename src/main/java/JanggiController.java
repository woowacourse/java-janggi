import domain.Board;
import domain.Camp;
import domain.ElephantFormation;
import domain.Position;
import domain.piece.Piece;
import view.InputView;
import view.OutputView;

import java.util.Map;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;


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
            try{
                Position fromPosition = askFromPosition(camp, board);
                Position toPosition = inputView.askToPosition(camp);
                board.move(fromPosition, toPosition);
            } catch (IllegalArgumentException e) {
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
            Position fromPosition = inputView.askFromPosition(camp);
            if (!board.isPieceOfCamp(fromPosition, camp)) {
                outputView.printWrongChoice();
                continue;
            }
            return fromPosition;
        }
    }

    private void printBoard(Board board) {
        Map<Position, Piece> boardStatus = board.getBoardStatus();
        outputView.printBoardStatus(boardStatus);
    }

    private ElephantFormation mappingElephantFormation(int userInput) {
        return ElephantFormation.getFormationType(userInput);
    }
}
