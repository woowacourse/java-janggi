package janggi.manager;

import janggi.common.ErrorMessage;
import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.dto.PositionDto;
import janggi.util.RecoveryUtil;
import janggi.view.Viewer;

public class JanggiGame {

    private final Viewer viewer;

    public JanggiGame(Viewer viewer) {
        this.viewer = viewer;
    }

    public void start(Board board) {
        Side turn = Side.CHO;

        turn = repeatGameTurns(board, turn);

        viewer.winner(turn);
    }

    private Side repeatGameTurns(Board board, Side turn) {
        while (board.hasGeneral()) {
            viewer.printBoard(board);
            viewer.printTurnInfo(turn);

            Side currentTurn = turn;

            RecoveryUtil.executeWithRetry(() -> commenceTurn(board, currentTurn));

            turn = turn.reverse();
        }
        return turn;
    }

    private void commenceTurn(Board board, Side turn) {
        Position selectedPosition = getSelectedPosition();
        Position targetPosition = getTargetPosition();

        if (!board.canMovePiece(turn, selectedPosition, targetPosition)) {
            throw new IllegalArgumentException(ErrorMessage.CANNOT_MOVE_PIECE.getMessage());
        }

        board.movePiece(selectedPosition, targetPosition);
    }

    private Position getSelectedPosition() {
        PositionDto positionDto = viewer.readPieceSelection();
        return Position.of(positionDto.row(), positionDto.column());
    }

    private Position getTargetPosition() {
        PositionDto positionDto = viewer.readMove();
        return Position.of(positionDto.row(), positionDto.column());
    }
}
