import domain.board.Board;
import domain.board.HorseElephantFormation;
import domain.place.piece.Side;
import domain.position.Position;
import factory.BoardFactory;
import parser.AnswerParser;
import parser.CommandParser;
import parser.NumberParser;
import parser.PositionParser;
import service.GameService;
import util.RetryHandler;
import view.InputView;
import view.OutputView;

public class Janggi {

    private final GameService gameService;
    private Side turn = Side.CHO;

    public Janggi(GameService gameService) {
        this.gameService = gameService;
    }

    public void run() {
        Board board = getBoard();
        play(board);

        OutputView.printWinner(turn.opposite());
    }

    private Board getBoard() {
        OutputView.printStartMenu();
        boolean answer = RetryHandler.retryInput(() -> AnswerParser.parse(InputView.readLine()));

        if (answer) {
            return getSaveBoard();
        }
        return getNewBoard();
    }

    private Board getSaveBoard() {
        OutputView.printSaveRoomList(gameService.findGameRoomAll());
        long roomId = RetryHandler.retryInput(() -> NumberParser.parse(InputView.readLine()));

        if (roomId == 0) {
            return getNewBoard();
        }
        turn = gameService.findGameStateByRoomId(roomId).currentSide();

        return new Board(gameService.findBoardByRoomId(roomId));
    }

    private Board getNewBoard() {
        HorseElephantFormation cho = getHorseElephantFormation(Side.CHO);
        HorseElephantFormation han = getHorseElephantFormation(Side.HAN);

        return BoardFactory.create(cho, han);
    }

    private void play(Board board) {
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
                executeMove(board);
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void executeMove(Board board) {
        Position from = getFrom(board);
        Position to = getTo();
        board.move(from, to, turn);
    }

    private Position getFrom(Board board) {
        while (true) {
            OutputView.printPieceMove(turn);
            String input = InputView.readLine();

            if (CommandParser.parse(input)) {
                saveGame(board);
                continue;
            }

            return PositionParser.parsePosition(input);
        }
    }

    private void saveGame(Board board) {
        OutputView.printGameName();
        String name = InputView.readLine();
        gameService.saveGame(board.getBoard(), name, turn);
        OutputView.printSaveComplete();
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
