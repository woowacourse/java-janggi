import domain.board.Board;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.position.ElephantFormation;
import domain.position.Position;
import view.InputView;
import view.OutputView;

import java.util.Map;
import java.util.Optional;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public JanggiController(InputView inputView, OutputView outputView, JanggiService janggiService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void run() {
        boolean loadSaveBoard = inputView.askLoadSavedBoard();
        Board board = generateBoard(loadSaveBoard);
        Camp camp = loadTurn(loadSaveBoard);

        printBoard(board);

        playJanggi(board, camp);
    }

    private void playJanggi(Board board, Camp camp) {
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
            printScore(board);

            camp = turnCamp(camp);

            saveGame(board.getBoardStatus(), camp);

            Optional<Camp> winner = board.checkWinner();
            if(winner.isPresent()) {
                outputView.printWinner(winner.get());
                return;
            }
        }
    }

    private Board loadBoard() {
        return janggiService.loadBoard();
    }

    private Camp loadTurn(boolean loadSaveBoard) {
        return janggiService.loadTurn(loadSaveBoard);
    }

    private Board generateBoard(boolean loadSaveBoard) {
        if(loadSaveBoard) {
            return loadBoard();
        }
        Board board = new Board();
        int choElephantFormation = inputView.askElephantFormation(Camp.CHO);
        int hanElephantFormation = inputView.askElephantFormation(Camp.HAN);
        board.generatePiecesBy(Camp.CHO, mappingElephantFormation(choElephantFormation));
        board.generatePiecesBy(Camp.HAN, mappingElephantFormation(hanElephantFormation));
        return board;
    }

    private void saveGame(Map<Position, Piece> boardStatus, Camp camp) {
        janggiService.saveGame(boardStatus, camp);
        outputView.printSavedComplete();
    }

    private Camp turnCamp(Camp camp) {
        return camp.turnCamp();
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

    private void printScore(Board board) {
        outputView.printScoreByCamp(board.getScoreByCamp());
    }
}
