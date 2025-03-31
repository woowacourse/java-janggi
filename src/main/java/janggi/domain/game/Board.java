package janggi.domain.game;

import janggi.domain.piece.Piece;
import janggi.domain.piece.Type;
import janggi.domain.position.Position;
import java.util.EnumMap;
import java.util.Map;

public final class Board {

    private static final double DUM = 1.5;

    private final Map<Position, Piece> pieces;

    public Board(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public void move(final Position source, final Position destination, final Piece piece) {
        pieces.remove(source);
        pieces.put(destination, piece);
    }

    public EnumMap<Team, Double> getTeamPoints() {
        EnumMap<Team, Double> teamPoints = new EnumMap<>(Team.class);
        for (Piece piece : pieces.values()) {
            Team team = piece.team();
            int point = piece.point();
            teamPoints.put(team, teamPoints.getOrDefault(team, 0.0) + point);
        }
        teamPoints.put(Team.HAN, teamPoints.get(Team.HAN) + DUM);
        return teamPoints;
    }

    public Piece get(final Position position) {
        if (!pieces.containsKey(position)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d%d 위치에 기물이 없습니다.", position.getColumnValue(), position.getRowValue()));
        }
        return pieces.get(position);
    }

    public boolean hasGeneralOf(final Team team) {
        return pieces.values().stream()
                .filter(piece -> piece.team() == team)
                .anyMatch(piece -> piece.type() == Type.GENERAL);
    }

    public boolean hasPieceAt(final Position position) {
        return pieces.containsKey(position);
    }

    public boolean hasPieceAt(final Position destination, final Team team) {
        return hasPieceAt(destination) && get(destination).team() == team;
    }

    public boolean hasPieceAt(final Position destination, final Type type) {
        return hasPieceAt(destination) && get(destination).type() == type;
    }
}
