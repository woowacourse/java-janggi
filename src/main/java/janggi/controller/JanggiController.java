package janggi.controller;

import janggi.domain.Board;
import janggi.domain.BoardFactory;
import janggi.domain.piece.Position;
import janggi.domain.piece.PositionSide;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void startJanggi() {
        String blueHorsePosition = inputView.getBlueHorsePosition();
        String redHorsePosition = inputView.getRedHorsePosition();
        PositionSide blueLeftHorsePosition = getPositionSide(blueHorsePosition.substring(0, 2));
        PositionSide blueRightHorsePosition = getPositionSide(blueHorsePosition.substring(2, 4));
        PositionSide redLeftHorsePosition = getPositionSide(redHorsePosition.substring(0, 2));
        PositionSide redRightHorsePosition = getPositionSide(redHorsePosition.substring(2, 4));

        Board board = BoardFactory.getInitializedBoard(
                blueLeftHorsePosition, blueRightHorsePosition, redLeftHorsePosition, redRightHorsePosition
        );
        while(true) {
            outputView.printBoard(board);
            String pieceMovement = inputView.getPieceMovement();
            Position beforePosition = getBeforePosition(pieceMovement);
            Position afterPosition = getAfterPosition(pieceMovement);
            board.movePiece(beforePosition, afterPosition);
        }
    }

    private Position getBeforePosition(String pieceMovement) {
         int parsedInt = Integer.parseInt(pieceMovement.split(" ")[0]);
         return new Position(parsedInt/10, parsedInt%10);
    }

    private Position getAfterPosition(String pieceMovement) {
        int parsedInt = Integer.parseInt(pieceMovement.split(" ")[1]);
        return new Position(parsedInt/10, parsedInt%10);
    }


    private PositionSide getPositionSide(final String position) {
        if (position.equals("마상")) {
            return PositionSide.LEFT;
        }
        if (position.equals("상마")) {
            return PositionSide.RIGHT;
        }
        throw new IllegalArgumentException("올바르지 않은 입력입니다.");
    }
}
