package domain.piece;

import domain.BoardStatus;
import domain.piece.strategy.MoveStrategy;
import domain.position.Position;
import java.util.List;

public class Po extends Piece {

    public Po(MoveStrategy moveStrategy, Team team) {
        super(moveStrategy, PieceType.PO, team);
    }

    @Override
    public void check(BoardStatus boardStatus, Position start, Position destination) {
        Piece movePiece = boardStatus.status().get(start);
        Piece targetPiece = boardStatus.status().get(destination);

        //0. 목적지에 존재하는 기물이 상대방 포인지 확인해야함.
        if (targetPiece != null && targetPiece.getPieceType() == PieceType.PO) {
            throw new IllegalArgumentException(PieceErrorMessage.PO_CANNOT_CAPTURE_PO.getMessage());
        }

        // 2. 목적지 전 칸까지와 포 사이에 기물이 하나만 존재하는지 확인
        List<Position> movablePath = movePiece.moveStrategy.findMovablePath(start, destination);
        int pieceCount = 0;
        for (Position position : movablePath) {
            Piece pieceOnPath = boardStatus.status().get(position);
            if (pieceOnPath != null && pieceOnPath.getPieceType() == PieceType.PO) {
                throw new IllegalArgumentException(PieceErrorMessage.PO_CANNOT_JUMP_PO.getMessage());
            }
            if (pieceOnPath != null) {
                pieceCount += 1;
            }
        }
        if (pieceCount != 1) {
            throw new IllegalArgumentException(PieceErrorMessage.INVALID_PO_SCREEN_COUNT.getMessage());
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
