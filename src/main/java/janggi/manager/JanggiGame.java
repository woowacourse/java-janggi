package janggi.manager;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.dto.PositionDto;
import janggi.view.Viewer;

public class JanggiGame {

    private final Viewer viewer;

    public JanggiGame(Viewer viewer) {
        this.viewer = viewer;
    }

    public void start(Board board) {
        Side turn = Side.CHO;

        while (true) {
            System.out.println(board);
            viewer.printBoard(board);
            viewer.printTurnInfo(turn);

            // Board의 해당 기물이 존재하는 지 확인, 움직일 수 있
            Position position = choosePiece(board, turn);
            movePiece(board, position);

            turn = turn.reverse();
        }
    }

    private Position choosePiece(Board board, Side turn) {
        PositionDto positionDto = viewer.readPieceSelection();
        Position position = Position.of(positionDto.row(), positionDto.column());
        // TODO 만약 선택된 기물이 움직일 수 없으면 예외를 반환한다.
        board.checkMoveablePiece(turn, position);

        return position;
    }

    private void movePiece(Board board, Position currentPosition) {
        PositionDto positionDto = viewer.readMove(board.getPiece(currentPosition));
        Position targetPosition = Position.of(positionDto.row(), positionDto.column());
        board.move(currentPosition, targetPosition);
    }
}
