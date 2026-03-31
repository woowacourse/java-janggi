import domain.board.Board;
import domain.board.HorseElephantFormation;
import domain.place.piece.Side;
import domain.position.Position;
import factory.BoardFactory;
import parser.PositionParser;
import service.BoardService;
import view.InputView;
import view.OutputView;

public class Janggi {

    private final BoardService boardService;
    private Side turn = Side.CHO;

    public Janggi(BoardService boardService){
        this.boardService = boardService;
    }

    public void run() {
        Board board = getBoard();
        turn(board);

        OutputView.printWinner(turn.opposite());
    }

    private Board getBoard() {
        HorseElephantFormation cho = getHorseElephantFormation(Side.CHO);
        HorseElephantFormation han = getHorseElephantFormation(Side.HAN);

        return BoardFactory.create(cho, han);
    }

    private void turn(Board board) {
        while (board.isAliveGeneral(turn)) {
            processTurn(board);
            turn = turn.opposite();
        }
    }

    private HorseElephantFormation getHorseElephantFormation(Side side) {
        OutputView.printHorseElephantFormation(side);
        String input = InputView.readLine();
        return HorseElephantFormation.from(input);
    }

    private void processTurn(Board board) {
        OutputView.printBoard(board.getFormatBoard(), board.getSideBoard());
        printScore(board);

        executeTurn(board);
        printCheckIfNeeded(board);
    }

    private void executeTurn(Board board) {
        while (true) {
            try {
                move(board);
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void move(Board board) {
        Position from = getFrom();
        Position to = getTo();
        board.move(from, to, turn);
    }

    private Position getFrom() {
        OutputView.printPieceMove(turn);
        return PositionParser.parsePosition(InputView.readLine());
    }

    private Position getTo() {
        OutputView.printPositionMove(turn);
        return PositionParser.parsePosition(InputView.readLine());
    }

    private void printCheckIfNeeded(Board board) {
        if (board.isCheck(turn)) {
            OutputView.printCheck(turn.opposite());
        }
    }

    private void printScore(Board board) {
        int choScore = board.getSideScore(Side.CHO);
        int hanScore = board.getSideScore(Side.HAN);

        OutputView.printScore(Side.CHO, choScore, Side.HAN, hanScore);
    }
}
