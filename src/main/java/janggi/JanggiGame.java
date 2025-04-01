package janggi;

import janggi.board.Board;
import janggi.board.BoardGenerator;
import janggi.dao.GameDao;
import janggi.dao.MoveDao;
import janggi.dto.MoveDto;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.util.CommandParser;
import janggi.view.JanggiView;
import janggi.view.SetupOption;
import java.util.List;

public class JanggiGame {

    private final JanggiView janggiView;
    private final GameDao gameDao;
    private final MoveDao moveDao;
    private int gameId;

    public JanggiGame(final GameDao gameDao, final MoveDao moveDao) {
        this.janggiView = new JanggiView();
        this.gameDao = gameDao;
        this.moveDao = moveDao;
    }

    public void run() {
        final Board board = generateBoard();
        displayInitialBoard(board);
        Command command = Command.STOP;
        do {
            command = executeCommand(command, board);
        } while (!command.equals(Command.STOP) && !board.isGeneralDead());
        if (board.isGeneralDead()) {
            gameDao.setGameFinished(gameId);
        }
        janggiView.displayEnd(board);
    }

    private void displayInitialBoard(final Board board) {
        janggiView.displayGame(board);
        janggiView.displayScore(board.calculateScoreBoard());
        janggiView.displayTurn(board);
    }

    private Board generateBoard() {
        try {
            return findBoard();
        } catch (IllegalArgumentException e) {
            janggiView.displayError("보드를 생성하지 못했습니다.");
            return generateBoard();
        }
    }

    private Board findBoard() {
        janggiView.displaySetupOption();
        final SetupOption setupOption = readSetupOption();
        if (setupOption == SetupOption.EXIST_SETUP) {
            return findUnfinishedBoard();
        }
        final Board board = BoardGenerator.generateOriginalSetup(setupOption);
        gameDao.saveInitialGame(board.getSetupOption());
        this.gameId = gameDao.findRecentNotFinishedGameId();
        return board;
    }

    private Board findUnfinishedBoard() {
        if (!gameDao.existNotFinishedGame()) {
            janggiView.displayError("게임 기록이 없습니다.");
            return generateBoard();
        }
        final List<Integer> notFinishedGameIds = gameDao.findNotFinishedGameIds();
        janggiView.displayUnfinishedGame(notFinishedGameIds);
        this.gameId = readUnfinishedGame(notFinishedGameIds);
        return BoardGenerator.generateExistSetup(gameDao.findGameSetup(gameId),
                moveDao.selectAllHistory(gameId));
    }

    private int readUnfinishedGame(final List<Integer> notFinishedGameIds) {
        final String input = janggiView.read();
        try {
            final int id = Integer.parseInt(input);
            if (!notFinishedGameIds.contains(id)) {
                janggiView.displayError("목록에 있는 숫자를 입력해주세요.");
                return readUnfinishedGame(notFinishedGameIds);
            }
            return id;
        } catch (IllegalArgumentException e) {
            janggiView.displayError("숫자 형식으로 입력해주세요.");
            return readUnfinishedGame(notFinishedGameIds);
        }
    }

    private SetupOption readSetupOption() {
        try {
            return SetupOption.of(janggiView.read());
        } catch (IllegalArgumentException e) {
            janggiView.displayError("옵션을 선택하지 못했습니다.");
            return readSetupOption();
        }
    }

    private Command executeCommand(Command command, final Board board) {
        final String input = janggiView.read();
        try {
            command = Command.of(input);
            moveIfNotStop(command, board, input);
        } catch (IllegalArgumentException e) {
            janggiView.displayError("명령을 실행하지 못했습니다.");
            return executeCommand(command, board);
        }
        return command;
    }

    private void moveIfNotStop(final Command command, final Board board, final String input) {
        if (!command.equals(Command.STOP)) {
            final List<Integer> moveCommand = CommandParser.parseMoveCommand(input);
            final Position start = new Position(Row.of(moveCommand.get(0)), Column.of(moveCommand.get(1)));
            final Position end = new Position(Row.of(moveCommand.get(2)), Column.of(moveCommand.get(3)));
            move(board, start, end);
            moveDao.saveHistory(new MoveDto(start, end), gameId);
        }
    }

    private void move(final Board board, final Position start, final Position end) {
        try {
            board.move(start, end);
            janggiView.displayGame(board);
            janggiView.displayScore(board.calculateScoreBoard());
            if (!board.isGeneralDead()) {
                janggiView.displayTurn(board);
            }
        } catch (IllegalArgumentException e) {
            janggiView.displayError("기물을 이동시키지 못했습니다.");
        }
    }
}
