package janggi.manager;

import janggi.domain.Board;
import janggi.domain.PieceState;
import janggi.domain.Position;
import janggi.domain.piece.Side;
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
            PieceState pieceState = choosePiece(board, turn);
            movePiece(board, pieceState);

            turn = turn.reverse();
        }
    }

    private PieceState choosePiece(Board board, Side turn) {
        PositionDto positionDto = viewer.readPieceSelection();
        Position position = Position.of(positionDto.row(), positionDto.column());
        // TODO 만약 선택된 기물이 움직일 수 없으면 예외를 반환한다.
        return board.getPiecePositionBySameSide(turn, position);
    }

    private void movePiece(Board board, PieceState pieceState) {
        PositionDto positionDto = viewer.readMove(pieceState);
        Position targetPosition = Position.of(positionDto.row(), positionDto.column());
        board.move(pieceState, targetPosition);
    }
}
