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
        Piece movePiece = boardStatus.getBoardStatus().get(start);
        Piece piecePlacedAtDestination = boardStatus.getBoardStatus().get(destination);

        //0. 목적지에 존재하는 기물이 상대방 포인지 확인해야함.
        if (piecePlacedAtDestination != null) { // 존재한다면 아군은 일단 아님.
            if (piecePlacedAtDestination.getPieceType() == PieceType.PO) {
                throw new IllegalArgumentException("포끼리 그러는 거 아님");
            }
        }
        // 2. 목적지 전 칸까지와 포 사이에 기물이 하나만 존재하는지 확인
        List<Position> movablePath = movePiece.moveStrategy.findMovablePath(start, destination);
        int pieceCount = 0;
        for (Position position : movablePath) {
            Piece pieceToCheck = boardStatus.getBoardStatus().get(position);
            if (pieceToCheck != null && pieceToCheck.getPieceType() == PieceType.PO) {
                throw new IllegalArgumentException("포끼리 그러는 거 아님");
            }
            if (pieceToCheck != null) {
                pieceCount += 1;
            }
        }
        if (pieceCount != 1) {
            throw new IllegalArgumentException("이동할 수 없습니다.");
        }
    }
}
