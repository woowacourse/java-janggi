package janggi.controller;

import janggi.domain.Board;
import janggi.domain.BoardFactory;
import janggi.domain.HorseSide;
import janggi.domain.piece.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.sql.SQLException;
import service.JanggiService;

public class JanggiController {

    public static final String SAVED_MENU_SELECTION = "1";
    public static final String NEW_GAME_MENU_SELECTION = "2";
    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiService janggiService;

    public static final int FIRST_INPUT_START_INDEX = 0;
    public static final int FIRST_INPUT_END_INDEX = 2;
    public static final int SECOND_INPUT_START_INDEX = 2;
    public static final int SECOND_INPUT_END_INDEX = 4;

    public JanggiController(
        InputView inputView,
        OutputView outputView,
        JanggiService janggiService
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiService = janggiService;
    }

    public void startJanggi() throws SQLException {
        Board board = loadOrCreateBoard();
        boolean quitGame = false;
        outputView.printBoard(board);
        while (board.isGameNotEnd() && !quitGame) {
            outputView.printTurn(board.getTurn());
            movePieceByInput(board);
            outputView.printBoard(board);
            quitGame = getGameQuitInput();
        }
        endJanggi(board);
    }

    private Board loadOrCreateBoard() throws SQLException {
        String loadOrCreate = inputView.getLoadOrCreate();
        if (loadOrCreate.equals(SAVED_MENU_SELECTION)) {
            return janggiService.loadBoard();
        }
        if (loadOrCreate.equals(NEW_GAME_MENU_SELECTION)) {
            return getInitializedBoardByInput();
        }
        throw new IllegalArgumentException("1, 2 만 입력 가능합니다");
    }

    private Board getInitializedBoardByInput() {
        String blueHorsePosition = inputView.getBlueHorsePosition();
        String redHorsePosition = inputView.getRedHorsePosition();
        return BoardFactory.getInitializedBoard(
            getPositionSide(
                blueHorsePosition.substring(FIRST_INPUT_START_INDEX, FIRST_INPUT_END_INDEX)),
            getPositionSide(
                blueHorsePosition.substring(SECOND_INPUT_START_INDEX, SECOND_INPUT_END_INDEX)),
            getPositionSide(
                redHorsePosition.substring(FIRST_INPUT_START_INDEX, FIRST_INPUT_END_INDEX)),
            getPositionSide(
                redHorsePosition.substring(SECOND_INPUT_START_INDEX, SECOND_INPUT_END_INDEX))
        );
    }

    private void movePieceByInput(Board board) {
        String pieceMovement = inputView.getPieceMovement();
        try {
            movePieceByPieceMovement(pieceMovement, board);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean getGameQuitInput() {
        String gameQuitInput = inputView.getGameQuitInput();
        return gameQuitInput.equals("Y");
    }

    private void endJanggi(Board board) throws SQLException {
        if (board.isGameEnd()) {
            outputView.printWinner(board);
            return;
        }
        janggiService.saveBoard(board);
        outputView.printSaved();
    }

    private HorseSide getPositionSide(final String position) {
        if (position.equals("마상")) {
            return HorseSide.LEFT;
        }
        if (position.equals("상마")) {
            return HorseSide.RIGHT;
        }
        throw new IllegalArgumentException("올바르지 않은 입력입니다.");
    }

    private void movePieceByPieceMovement(final String pieceMovement, final Board board) {
        Position beforePosition = getBeforePosition(pieceMovement);
        Position afterPosition = getAfterPosition(pieceMovement);
        board.movePiece(beforePosition, afterPosition);
    }

    private Position getBeforePosition(String pieceMovement) {
        int parsedInt = Integer.parseInt(pieceMovement.split(" ")[0]);
        return new Position(parsedInt / 10, parsedInt % 10);
    }

    private Position getAfterPosition(String pieceMovement) {
        int parsedInt = Integer.parseInt(pieceMovement.split(" ")[1]);
        return new Position(parsedInt / 10, parsedInt % 10);
    }
}
