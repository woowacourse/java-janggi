package domain;

import domain.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Piece> alivePieces;
    private final List<Piece> deadPieces;

    public Board(List<Piece> pieces) {
        this.alivePieces = new ArrayList<>(pieces);
        this.deadPieces = new ArrayList<>();
    }

    public void movePiece(Position startPosition, Position endPosition) {
        Piece findPiece = alivePieces.stream()
                .filter(piece -> piece.hasSamePosition(startPosition))
                .findAny().orElseThrow(() -> new IllegalArgumentException("해당 좌표에는 말이 존재하지 않습니다."));
        boolean canMove = findPiece.canMove(endPosition, alivePieces);
        if(!canMove){
            throw new IllegalArgumentException("해당 좌표로 이동시킬 수 없습니다.");
        }
        Piece endPositionPiece = alivePieces.stream()
                .filter(piece -> piece.hasSamePosition(endPosition))
                .findFirst().orElse(null);
        if(endPositionPiece!=null){
            alivePieces.remove(endPositionPiece);
            deadPieces.add(endPositionPiece);
        }
        findPiece.moveTo(endPosition);
    }

    public List<Piece> getAlivePieces() {
        return alivePieces.stream().map(Piece::newInstance).toList();
    }
}
