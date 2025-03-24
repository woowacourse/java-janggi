package domain;

import static domain.Team.CHO;
import static domain.Team.HAN;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Scholar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamBoard {

    private final Map<BoardLocation, Piece> pieces;

    public TeamBoard(Map<BoardLocation, Piece> pieces) {
        this.pieces = pieces;
    }

    public Piece findByLocation(BoardLocation current) {
        if (pieces.containsKey(current)) {
            return pieces.get(current);
        }
        throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 없습니다.");
    }

    public boolean contains(BoardLocation destination) {
        return pieces.containsKey(destination);
    }

    public void removeIfHas(BoardLocation destination) {
        pieces.remove(destination);
    }

    public void move(BoardLocation current, BoardLocation destination) {
        Piece piece = pieces.remove(current);
        pieces.put(destination, piece);
    }

    public List<Piece> extractPathPiece(List<BoardLocation> allPath) {
        return allPath.stream()
                .filter(pieces::containsKey)
                .map(pieces::get)
                .toList();
    }

    public Map<BoardLocation, Piece> getPieces() {
        return pieces;
    }

    public static TeamBoard createWithPieces(Map<BoardLocation, Piece> placements) {
        Map<BoardLocation, Piece> pieces = new HashMap<>(placements);

        pieces.put(new BoardLocation(1, 1), new Chariot(HAN));
        pieces.put(new BoardLocation(4, 1), new Scholar(HAN));
        pieces.put(new BoardLocation(6, 1), new Scholar(HAN));
        pieces.put(new BoardLocation(9, 1), new Chariot(HAN));
        pieces.put(new BoardLocation(5, 2), new King(HAN));
        pieces.put(new BoardLocation(2, 3), new Cannon(HAN));
        pieces.put(new BoardLocation(8, 3), new Cannon(HAN));
        pieces.put(new BoardLocation(1, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(3, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(5, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(7, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(9, 4), new Pawn(HAN));

        pieces.put(new BoardLocation(1, 10), new Chariot(CHO));
        pieces.put(new BoardLocation(4, 10), new Scholar(CHO));
        pieces.put(new BoardLocation(6, 10), new Scholar(CHO));
        pieces.put(new BoardLocation(9, 10), new Chariot(CHO));
        pieces.put(new BoardLocation(5, 9), new King(CHO));
        pieces.put(new BoardLocation(2, 8), new Cannon(CHO));
        pieces.put(new BoardLocation(8, 8), new Cannon(CHO));
        pieces.put(new BoardLocation(1, 7), new Pawn(CHO));
        pieces.put(new BoardLocation(3, 7), new Pawn(CHO));
        pieces.put(new BoardLocation(5, 7), new Pawn(CHO));
        pieces.put(new BoardLocation(7, 7), new Pawn(CHO));
        pieces.put(new BoardLocation(9, 7), new Pawn(CHO));
        return new TeamBoard(pieces);
    }
}
