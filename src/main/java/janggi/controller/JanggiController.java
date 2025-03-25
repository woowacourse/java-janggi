package janggi.controller;

import janggi.domain.Board;
import janggi.domain.BoardFactory;
import janggi.domain.piece.HorseSide;
import janggi.domain.piece.Position;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiController {
    public static final int FIRST_INPUT_START_INDEX = 0;
    public static final int FIRST_INPUT_END_INDEX = 2;
    public static final int SECOND_INPUT_START_INDEX = 2;
    public static final int SECOND_INPUT_END_INDEX = 4;
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void startJanggi() {
        Board board = getInitializedBoardByInput();

        while (true) {
            outputView.printBoard(board);
            String pieceMovement = inputView.getPieceMovement();
            movePieceByPieceMovement(pieceMovement, board);
        }
    }

    private Board getInitializedBoardByInput() {
        String blueHorsePosition = inputView.getBlueHorsePosition();
        String redHorsePosition = inputView.getRedHorsePosition();
        return BoardFactory.getInitializedBoard(
                getPositionSide(blueHorsePosition.substring(FIRST_INPUT_START_INDEX, FIRST_INPUT_END_INDEX)),
                getPositionSide(blueHorsePosition.substring(SECOND_INPUT_START_INDEX, SECOND_INPUT_END_INDEX)),
                getPositionSide(redHorsePosition.substring(FIRST_INPUT_START_INDEX, FIRST_INPUT_END_INDEX)),
                getPositionSide(redHorsePosition.substring(SECOND_INPUT_START_INDEX, SECOND_INPUT_END_INDEX))
        );
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
