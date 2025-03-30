package team;

import coordinate.Coordinate;
import java.util.Collections;
import java.util.Map;
import piece.Piece;

public class Team {

    private final Country country;
    private final Map<Coordinate, Piece> pieces;
    private Score score;
    private boolean isGoongDead;

    public Team(Country country, Map<Coordinate, Piece> pieces) {
        this.country = country;
        this.pieces = pieces;
        this.score = new Score(0);
        this.isGoongDead = false;
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
        if (getPiece(coordinate).isGoong()) {
            isGoongDead = true;
        }
        pieces.remove(coordinate);
    }

    public void putPiece(Coordinate coordinate, Piece piece) {
        pieces.put(coordinate, piece);
    }

    public void addScore(int score) {
        this.score = this.score.add(score);
    }

    public boolean isGoongDead() {
        return isGoongDead;
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
