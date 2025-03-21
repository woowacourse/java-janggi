package domain;

import domain.piece.Piece;
import domain.piece_initiaizer.PieceInitializer;
import domain.position.Position;

import java.util.Collections;
import java.util.Map;

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
        if (team1.country == Country.초나라) {
            return team1;
        }
        return team2;
    }

    public static Team getSecondTeam(final Team team1, final Team team2) {
        validateIsDifferentTeam(team1, team2);
        if (team1.country == Country.한나라) {
            return team1;
        }
        return team2;
    }

    private static void validateIsDifferentTeam(final Team team1, final Team team2) {
        if (team1.country == team2.country) {
            throw new IllegalArgumentException("서로 다른 팀이어야 합니다.");
        }
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }

    public void move(final Position fromPosition, final Position tagetPosition, final Map<Position, Piece> enemyPieces) {
        validateIsPieceExistInPosition(fromPosition);

        final Piece movePiece = pieces.get(fromPosition);
        final Piece movedPiece = movePiece.move(tagetPosition, pieces.values().stream().toList(), enemyPieces.values().stream().toList());
        pieces.remove(fromPosition);
        pieces.put(movedPiece.getPosition(), movedPiece);
    }

    private void validateIsPieceExistInPosition(final Position fromPosition) {
        if (pieces.get(fromPosition) == null) {
            throw new IllegalArgumentException("해당 위치의 기물이 존재하지 않습니다.");
        }
    }

    public boolean isSameCountry(final Team team2) {
        return country == team2.country;
    }
}
