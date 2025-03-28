package board;

import piece.Country;
import piece.Piece;
import position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> pieces;

    public Board(final Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public List<Position> findExistPositions(List<Position> positions) {
        return positions.stream()
                .filter(pieces::containsKey)
                .toList();
    }

    public boolean isCorrectExistPositionCount(List<Position> positions, int expectedCount) {
        int count = (int) positions.stream()
                .filter(pieces::containsKey)
                .count();
        return count == expectedCount;
    }

    public Piece getPieceBy(Position position) {
        if (!existPieceByPosition(position)) {
            throw new IllegalArgumentException("장기판에 기물이 존재하지 않습니다");
        }
        return pieces.get(position);
    }

    public Map<Position, Piece> getPieces() {
        return new HashMap<>(pieces);
    }

    public void updatePosition(Position src, Position dest, Country country) {
        validate(src, country);
        Piece piece = pieces.get(src);
        piece.validateMove(src, dest,this);
        pieces.remove(src);
        pieces.put(dest, piece);
    }

    private void validate(Position src, Country country) {
        if (!existPieceByPosition(src)) {
            throw new IllegalArgumentException("시작 위치의 기물이 존재하지 않습니다.");
        }
        if (!equalsTeamTypeByPosition(src, country)) {
            throw new IllegalArgumentException("시작 위치의 기물은 현재 턴의 나라여야 합니다.");
        }
    }

    public boolean existPieceByPosition(Position position) {
        return pieces.containsKey(position);
    }

    public boolean equalsTeamTypeByPosition(Position position, Country country) {
        Piece piece = pieces.get(position);
        return piece.equalsCountry(country);
    }
}
