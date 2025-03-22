package game;

import java.util.Collections;
import java.util.Map;
import piece.Piece;
import piece_initiaizer.PieceInitializer;
import position.Position;

public final class Team {

    private final Country country;
    private final Map<Position, Piece> pieces;

    public Team(
            final StartingPosition startingPosition,
            final PieceInitializer initializer,
            final Country country
    ) {
        this.country = country;
        this.pieces = initializer.init(startingPosition, country);
    }

    public static Team getFirstTeam(final Team team1, final Team team2) {
        validateIsDifferentTeam(team1, team2);
        if (team1.getCountry() == Country.CHO) {
            return team1;
        }
        return team2;
    }

    public static Team getSecondTeam(final Team team1, final Team team2) {
        validateIsDifferentTeam(team1, team2);
        if (team1.getCountry() == Country.HAN) {
            return team1;
        }
        return team2;
    }

    private static void validateIsDifferentTeam(final Team team1, final Team team2) {
        if (team1.getCountry() == team2.getCountry()) {
            throw new IllegalArgumentException("서로 다른 팀이어야 합니다.");
        }
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }

    public Country getCountry() {
        return country;
    }

    public void move(final Position fromPosition, final Position tagetPosition,
                     final Map<Position, Piece> enemyPieces) {
        validateIsPieceExistInPosition(fromPosition);

        final Piece movePiece = pieces.get(fromPosition);
        pieces.remove(fromPosition);
        final Piece movedPiece = movePiece.movePiece(tagetPosition, pieces.values().stream().toList(),
                enemyPieces.values().stream().toList());
        if (pieces.containsValue(movedPiece.getPosition())) {
            pieces.remove(movedPiece.getPosition());
        }
        pieces.put(movedPiece.getPosition(), movedPiece);
    }

    private void validateIsPieceExistInPosition(final Position fromPosition) {
        if (pieces.get(fromPosition) == null) {
            throw new IllegalArgumentException("해당 위치의 기물이 존재하지 않습니다.");
        }
    }
}
