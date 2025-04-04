package domain.board;

import domain.piece.Country;
import domain.piece.Piece;
import domain.position.Position;

import java.util.*;

public class Board {

    private final Map<Position, Piece> pieces;
    private final Map<Country, Integer> scoreByCountry;

    public Board(final Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
        this.scoreByCountry = new HashMap<>();
        Arrays.stream(Country.values())
                .forEach(country -> scoreByCountry.put(country, 0));
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

    public void updatePosition(Position src, Position dest, Country country) {
        validate(src, country);
        Piece piece = pieces.get(src);
        piece.validateMove(src, dest,this);
        if (pieces.containsKey(dest)) {
            scoreByCountry.put(country, scoreByCountry.get(country) + pieces.get(dest).getScore());
        }
        pieces.remove(src);
        pieces.put(dest, piece);
        piece.setPosition(dest);
    }

    private void validate(Position src, Country country) {
        if (!existPieceByPosition(src)) {
            throw new IllegalArgumentException("시작 위치의 기물이 존재하지 않습니다.");
        }
        if (!equalsTeamTypeByPosition(src, country)) {
            throw new IllegalArgumentException("시작 위치의 기물은 현재 턴의 나라여야 합니다.");
        }
    }

    public boolean equalsTeamTypeByPosition(Position position, Country country) {
        Piece piece = pieces.get(position);
        return piece.equalsCountry(country);
    }

    public boolean existPieceByPosition(Position position) {
        return pieces.containsKey(position);
    }

    public List<Piece> getPieceList() {
        return new ArrayList<>(pieces.values());
    }

    public Map<Position, Piece> getPieceMap() {
        return Collections.unmodifiableMap(pieces);
    }

    public Map<Country, Integer> getScoreByCountry() {
        return scoreByCountry;
    }
}
