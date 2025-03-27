package domain.board;

import domain.piece.King;
import domain.piece.Piece;
import domain.piece.Team;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Pawn;
import domain.piece.Scholar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board {

    private final Map<BoardLocation, Piece> pieces;

    public Board(Map<BoardLocation, Piece> pieces) {
        this.pieces = pieces;
    }

    public Piece getByLocationOrThrow(BoardLocation current) {
        if (pieces.containsKey(current)) {
            return pieces.get(current);
        }
        throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 없습니다.");
    }

    public Optional<Piece> findByLocation(BoardLocation current) {
        return Optional.ofNullable(pieces.get(current));
    }

    public void occupy(BoardLocation current, BoardLocation destination) {
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

    public static Board createWithPieces(Map<BoardLocation, Piece> placements) {
        Map<BoardLocation, Piece> pieces = new HashMap<>(placements);

        pieces.put(new BoardLocation(1, 1), new Chariot(Team.HAN));
        pieces.put(new BoardLocation(4, 1), new Scholar(Team.HAN));
        pieces.put(new BoardLocation(6, 1), new Scholar(Team.HAN));
        pieces.put(new BoardLocation(9, 1), new Chariot(Team.HAN));
        pieces.put(new BoardLocation(5, 2), new King(Team.HAN));
        pieces.put(new BoardLocation(2, 3), new Cannon(Team.HAN));
        pieces.put(new BoardLocation(8, 3), new Cannon(Team.HAN));
        pieces.put(new BoardLocation(1, 4), new Pawn(Team.HAN));
        pieces.put(new BoardLocation(3, 4), new Pawn(Team.HAN));
        pieces.put(new BoardLocation(5, 4), new Pawn(Team.HAN));
        pieces.put(new BoardLocation(7, 4), new Pawn(Team.HAN));
        pieces.put(new BoardLocation(9, 4), new Pawn(Team.HAN));

        pieces.put(new BoardLocation(1, 10), new Chariot(Team.CHO));
        pieces.put(new BoardLocation(4, 10), new Scholar(Team.CHO));
        pieces.put(new BoardLocation(6, 10), new Scholar(Team.CHO));
        pieces.put(new BoardLocation(9, 10), new Chariot(Team.CHO));
        pieces.put(new BoardLocation(5, 9), new King(Team.CHO));
        pieces.put(new BoardLocation(2, 8), new Cannon(Team.CHO));
        pieces.put(new BoardLocation(8, 8), new Cannon(Team.CHO));
        pieces.put(new BoardLocation(1, 7), new Pawn(Team.CHO));
        pieces.put(new BoardLocation(3, 7), new Pawn(Team.CHO));
        pieces.put(new BoardLocation(5, 7), new Pawn(Team.CHO));
        pieces.put(new BoardLocation(7, 7), new Pawn(Team.CHO));
        pieces.put(new BoardLocation(9, 7), new Pawn(Team.CHO));
        return new Board(pieces);
    }
}
