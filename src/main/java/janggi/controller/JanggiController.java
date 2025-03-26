package janggi.controller;

import janggi.domain.Board;
import janggi.domain.BoardFactory;
import janggi.domain.Turn;
import janggi.domain.piece.HorseSide;
import janggi.domain.piece.Position;
import janggi.domain.piece.Team;
import janggi.view.InputConverter;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Turn turn;

    public JanggiController(final InputView inputView, final OutputView outputView, final Turn turn) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.turn = turn;
    }

    public void startJanggi() {
        Board board = getInitializedBoardByInput();

        while (true) {
            Team nowTeam = turn.next();
            outputView.printBoard(board);
            String pieceMovement = inputView.readPieceMovement(nowTeam);
            movePieceByPieceMovement(nowTeam, pieceMovement, board);
        }
    }

    private Board getInitializedBoardByInput() {
        String blueHorseSide = inputView.readHorsePosition(Team.BLUE);
        String redHorseSide = inputView.readHorsePosition(Team.RED);
        return BoardFactory.getInitializedBoard(
                getPositionSide(InputConverter.extractLeftHorseSide(blueHorseSide)),
                getPositionSide(InputConverter.extractRightHorseSide(blueHorseSide)),
                getPositionSide(InputConverter.extractLeftHorseSide(redHorseSide)),
                getPositionSide(InputConverter.extractRightHorseSide(redHorseSide))
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

    private void movePieceByPieceMovement(final Team team, final String pieceMovement, final Board board) {
        Position beforePosition = getBeforePosition(pieceMovement);
        Position afterPosition = getAfterPosition(pieceMovement);
        board.movePiece(team, beforePosition, afterPosition);
    }

    private Position getBeforePosition(final String pieceMovement) {
        int parsedInt = Integer.parseInt(pieceMovement.split(" ")[0]);
        return new Position(parsedInt / 10, parsedInt % 10);
    }

    private Position getAfterPosition(final String pieceMovement) {
        int parsedInt = Integer.parseInt(pieceMovement.split(" ")[1]);
        return new Position(parsedInt / 10, parsedInt % 10);
    }
}
