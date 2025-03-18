package janggi.manager;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.piece.PiecePosition;
import janggi.domain.piece.Side;
import janggi.dto.PositionDto;
import janggi.view.Viewer;

public class JanggiGame {

    private final Viewer viewer;

    public JanggiGame(Viewer viewer) {
        this.viewer = viewer;
    }

    public void start(Board board) {
        viewer.printBoard(board);
        Side turn = Side.CHO;

        while (true) {
            // 대충 돌아가는 로직
            viewer.printTurnInfo(turn);

            // Board의 해당 기물이 존재하는 지 확인, 움직일 수 있
            PiecePosition piecePosition = choosePiece(board, turn);

            turn = turn.reverse();
        }
    }

    private PiecePosition choosePiece(Board board, Side turn) {
        PositionDto positionDto = viewer.readPieceSelection();
        Position position = new Position(positionDto.row(), positionDto.column());
        // TODO 만약 선택된 기물이 움직일 수 없으면 예외를 반환한다.
        return board.getPiecePosition(turn, position);
    }
}
