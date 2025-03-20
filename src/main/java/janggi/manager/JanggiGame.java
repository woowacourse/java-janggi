package janggi.manager;

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
        while (board.hasGeneral(turn.reverse())) {
            viewer.printBoard(board);
            viewer.printTurnInfo(turn);

            Side finalTurn = turn;
            Position position = RecoveryUtil.executeWithRetry(() -> choosePiece(board, finalTurn));
            RecoveryUtil.executeWithRetry(() -> movePiece(board, position));

            turn = turn.reverse();
        }
        return turn;
    }

    private Position choosePiece(Board board, Side turn) {
        PositionDto positionDto = viewer.readPieceSelection();
        Position position = Position.of(positionDto.row(), positionDto.column());
        board.checkMoveablePiece(turn, position);

        return position;
    }

    private void movePiece(Board board, Position currentPosition) {
        PositionDto positionDto = viewer.readMove(board.getPiece(currentPosition));

        Position targetPosition = Position.of(positionDto.row(), positionDto.column());

        board.move(currentPosition, targetPosition);
    }
}
