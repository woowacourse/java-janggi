package janggi;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.dao.JanggiDao;
import janggi.dto.MoveDto;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.util.CommandParser;
import janggi.view.BoardView;
import janggi.view.SetupOption;
import java.util.List;

public class JanggiGame {

    private final BoardView boardView;
    private final JanggiDao janggiDao;
    private int gameId;

    public JanggiGame(final JanggiDao janggiDao) {
        this.boardView = new BoardView();
        this.janggiDao = janggiDao;
    }

    public void run() {
        final Board board = generateBoard();
        displayInitialBoard(board);
        Command command = Command.STOP;
        janggiDao.saveInitialGame(board.getSetupOption());
        this.gameId = janggiDao.findNotFinishedGameId();
        do {
            command = executeCommand(command, board);
        } while (!command.equals(Command.STOP) && !board.isGeneralDead());
        if (board.isGeneralDead()) {
            janggiDao.setGameFinished(gameId);
        }
        boardView.displayEnd(board);
    }

    private void displayInitialBoard(final Board board) {
        boardView.displayGame(board);
        boardView.displayScore(board.calculateScoreBoard());
        boardView.displayTurn(board);
    }

    private Board generateBoard() {
        try {
            boardView.displaySetupOption();
            final SetupOption setupOption = readSetupOption();
            return BoardGenerator.generate(setupOption, janggiDao, gameId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return generateBoard();
        }
    }

    private SetupOption readSetupOption() {
        try {
            return SetupOption.of(boardView.read());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return readSetupOption();
        }
    }

    private Command executeCommand(Command command, final Board board) {
        final String input = boardView.read();
        try {
            command = Command.of(input);
            moveUntilStop(command, board, input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return executeCommand(command, board);
        }
        return command;
    }

    private void moveUntilStop(final Command command, final Board board, final String input) {
        if (!command.equals(Command.STOP)) {
            final List<Integer> moveCommand = CommandParser.parseMoveCommand(input);
            final Position start = new Position(Row.of(moveCommand.get(0)), Column.of(moveCommand.get(1)));
            final Position end = new Position(Row.of(moveCommand.get(2)), Column.of(moveCommand.get(3)));
            move(board, start, end);
            janggiDao.saveHistory(new MoveDto(start, end), janggiDao.findNotFinishedGameId());
        }
    }

    private void move(final Board board, final Position start, final Position end) {
        try {
            board.move(start, end);
            boardView.displayGame(board);
            boardView.displayScore(board.calculateScoreBoard());
            if (!board.isGeneralDead()) {
                boardView.displayTurn(board);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
