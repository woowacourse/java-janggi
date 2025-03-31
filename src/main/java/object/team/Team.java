package object.team;

import java.util.Collections;
import java.util.Map;
import object.coordinate.Coordinate;
import object.piece.Piece;

public class Team {

    private final Country country;
    private final Map<Coordinate, Piece> pieces;
    private Score score;

    public Team(Country country, Map<Coordinate, Piece> pieces, Score score) {
        this.country = country;
        this.pieces = pieces;
        this.score = score;
    }

    public boolean isMyPiece(Coordinate coordinate) {
        return pieces.containsKey(coordinate);
    }

    public Piece getPiece(Coordinate coordinate) {
        Piece piece = pieces.get(coordinate);
        if (piece == null) {
            throw new IllegalStateException("해당 좌표에 기물이 없습니다.");
        }
        return piece;
    }

    public void removePiece(Coordinate coordinate) {
        pieces.remove(coordinate);
    }

    public void putPiece(Coordinate coordinate, Piece piece) {
        pieces.put(coordinate, piece);
    }

    public void addScore(int score) {
        this.score = this.score.add(score);
    }

    public boolean isGoongDead() {
        return pieces.values().stream()
                .noneMatch(Piece::isGoong);
    }

    public boolean isSameCountry(Team team) {
        return this.country.isSameCountry(team.country);
    }

    public boolean isSameCountry(Country country) {
        return this.country.isSameCountry(country);
    }

    public Country getCountry() {
        return country;
    }

    public Map<Coordinate, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }

    public int getScore() {
        return score.getScore();
    }
}
