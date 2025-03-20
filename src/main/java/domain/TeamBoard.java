package domain;

import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class TeamBoard {

    protected final Map<BoardLocation, Piece> pieces;

    protected TeamBoard(Map<BoardLocation, Piece> pieces) {
        this.pieces = pieces;
    }

    protected Map<BoardLocation, Piece> getPieces() {
        return pieces;
    }

    protected Piece findByLocation(BoardLocation current) {
        if (pieces.containsKey(current)) {
            return pieces.get(current);
        }
        throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 없습니다.");
    }

    protected void validateAllyMove(List<BoardLocation> allPath, BoardLocation destination) {
        Set<BoardLocation> located = pieces.keySet();
        if (located.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 현재 목적지에 아군이 위치해있습니다");
        }

        for (BoardLocation boardLocation : allPath) {
            if (located.contains(boardLocation)) {
                throw new IllegalArgumentException("[ERROR] 현재 이동 경로에 아군이 위치해있습니다");
            }
        }
    }

    protected void validateEnemyMove(List<BoardLocation> allPath) {
        Set<BoardLocation> located = pieces.keySet();
        for (BoardLocation boardLocation : allPath) {
            if (located.contains(boardLocation)) {
                throw new IllegalArgumentException("[ERROR] 현재 이동 경로에 적군이 위치해있습니다");
            }
        }
    }

    protected void removeIfHas(BoardLocation destination) {
        pieces.remove(destination);
    }

    protected void move(BoardLocation current, BoardLocation destination) {
        Piece piece = pieces.remove(current);
        pieces.put(destination, piece);
    }

    protected void validateCannon(List<BoardLocation> allPath, BoardLocation destination) {
        int count = 0;
        for (BoardLocation boardLocation : allPath){
            for (BoardLocation pieceLocation : pieces.keySet()){
                if (boardLocation.equals(pieceLocation) && !pieces.get(pieceLocation).isCannon()){
                    count++;
                }
            }
        }
        if (count != 1 || pieces.get(destination).isCannon()){
            throw new IllegalArgumentException("[ERROR] 움직일 수 없는 좌표입니다");
        }
    }
}
