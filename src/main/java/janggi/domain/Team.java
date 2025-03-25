package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.impl.Jang;
import janggi.domain.piece_initiaizer.PieceInitializer;
import janggi.domain.position.Position;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Team(
            final List<Piece> pieces,
            final Country country
    ) {
        this.country = country;
        this.pieces = pieces.stream().collect(Collectors.toMap(
                Piece::getPosition,
                piece -> piece
        ));
    }

    public static Team getFirstTeam(final Team team1, final Team team2) {
        validateIsDifferentTeam(team1, team2);
        if (team1.country == Country.CHO) {
            return team1;
        }
        return team2;
    }

    public static Team getSecondTeam(final Team team1, final Team team2) {
        validateIsDifferentTeam(team1, team2);
        if (team1.country == Country.HAN) {
            return team1;
        }
        return team2;
    }

    private static void validateIsDifferentTeam(final Team team1, final Team team2) {
        if (team1.country == team2.country) {
            throw new IllegalArgumentException("서로 다른 팀이어야 합니다.");
        }
    }

    public List<Piece> getPieces() {
        return pieces.values().stream().toList();
    }

    public Country getCountry() {
        return country;
    }

    public void move(final Position fromPosition, final Position tagetPosition, final Team team) {
        validateIsPieceExistInPosition(fromPosition);

        final Piece targetPiece = pieces.get(fromPosition);
        targetPiece.move(tagetPosition, pieces.values().stream().toList(), team.pieces.values().stream().toList());
        pieces.remove(fromPosition);
        pieces.put(tagetPosition, targetPiece);
    }

    private void validateIsPieceExistInPosition(final Position fromPosition) {
        if (pieces.get(fromPosition) == null) {
            throw new IllegalArgumentException("해당 위치의 기물이 존재하지 않습니다.");
        }
    }

    public boolean isSameCountry(final Team team2) {
        return country == team2.country;
    }

    public boolean isEnd() {
        return pieces.values().stream()
                .noneMatch(piece -> piece instanceof Jang);
    }
}
