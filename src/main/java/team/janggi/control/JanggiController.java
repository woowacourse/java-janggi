package team.janggi.control;

import team.janggi.domain.Position;
import team.janggi.exception.GameOverException;
import team.janggi.exception.PieceCanNotMoveException;
import team.janggi.repository.dto.BoardViewDTO;
import team.janggi.service.JanggiService;
import team.janggi.view.ConsoleInputView;
import team.janggi.view.ConsoleOutputView;
import team.janggi.view.Menu;

public class JanggiController {
    private final ConsoleOutputView consoleOutputView;
    private final ConsoleInputView consoleInputView;
    private final JanggiService janggiService;

    public JanggiController(JanggiService janggiService) {
        this.consoleOutputView = new ConsoleOutputView();
        this.consoleInputView = new ConsoleInputView();
        this.janggiService = janggiService;
    }

    public void run() {
        final long gameRoomId = getGameRoomId();

        do {
            doTurn(gameRoomId);
        } while (!janggiService.isGameOver(gameRoomId));

        consoleOutputView.printWinner(janggiService.getWinnerTeam(gameRoomId));
    }

    private long getGameRoomId() {
        final Menu menu = consoleInputView.readMenuChoice();

        if (menu == Menu.CREATE_ROOM) {
            return createGameRoom();
        }
        if (menu == Menu.JOIN_ROOM) {
            return readJoinGameRoomId();
        }
        if (menu == Menu.EXIT) {
            System.exit(0);
        }

        throw new IllegalStateException("지원하지 않는 메뉴입니다: " + menu);
    }

    private long createGameRoom() {
        return janggiService.createGameRoom(
                consoleInputView.readChoNormalSetup(),
                consoleInputView.readHanNormalSetup()
        );
    }

    private long readJoinGameRoomId() {
        boolean isValidGameRoomId;
        long readGameRoomId;

        do {
            readGameRoomId = consoleInputView.readGameRoomId();
            isValidGameRoomId = checkValidGameRoom(readGameRoomId);
        } while (!isValidGameRoomId);

        return readGameRoomId;
    }

    private boolean checkValidGameRoom(long gameRoomId) {
        try {
            janggiService.findGameRoom(gameRoomId);
            return true;
        } catch (Exception e) {
            consoleOutputView.printInvalidGameRoomIdMessage();
            return false;
        }
    }

    private void doTurn(long gameRoomId) {
        final BoardViewDTO boardView = janggiService.getBoardView(gameRoomId);
        consoleOutputView.print(boardView.boardStateReader(), gameRoomId, boardView.choScore(), boardView.hanScore());

        final Position from = consoleInputView.readMoveSource(boardView.currentTurn());
        final Position to = consoleInputView.readMoveDestination(boardView.currentTurn());

        try {
            janggiService.move(gameRoomId, boardView.currentTurn(), from, to);
        } catch (PieceCanNotMoveException e) {
            consoleOutputView.printWarningMessage(e.getMessage());
        } catch (GameOverException e) {
            exitGame();
        }
    }

    // 게임을 종료합니다.
    private void exitGame() {
        consoleOutputView.printExitMessage();
        System.exit(0);
    }
}
