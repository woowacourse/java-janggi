package janggi.game;

import janggi.piece.pieces.Piece;
import janggi.position.Position;
import janggi.piece.DefaultPosition;
import janggi.piece.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pieces {
    private final Map<Position, Piece> pieces;

    public Pieces() {
        pieces = new HashMap<>();
        settingPieces(Team.HAN);
        settingPieces(Team.CHO);
    }

    private void settingPieces(Team team) {
        for (DefaultPosition value : DefaultPosition.values()) {
            pieces.putAll(DefaultPosition.createDefaultPieces(value, team));
        }
    }

    public Piece findPieceByPosition(Position startPoint) {
        Piece piece = pieces.getOrDefault(startPoint, null);
        if (piece == null) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
        }
        return piece;
    }

    public boolean isExistPiece(Position pick) {
        Piece piece = pieces.getOrDefault(pick, null);
        return piece != null;
    }

    public boolean isBombPiece(Position position) {
        Piece piece = pieces.getOrDefault(position, null);
        if (piece == null) {
            return false;
        }
        return piece.isCannon();
    }

    public void moveAndCaptureIfEnemyExists(Position startPoint, Position endPoint) {
        Piece piece = findPieceByPosition(startPoint);

        if (isExistPiece(endPoint)) {
            pieces.remove(endPoint);
        }
        pieces.remove(startPoint);
        pieces.put(endPoint, piece);
    }

    public Team findTeamByPosition(Position endPosition) {
        Piece piece = findPieceByPosition(endPosition);
        return piece.getTeam();
    }

    public boolean isNoneSameTeamUnit(Team turn) {
        return pieces.values().stream().noneMatch(unit -> unit.getTeam() != turn);
    }

    public boolean isEmptyPoint(Position pick) {
        return pieces.keySet().stream().noneMatch(position -> position.isSamePoint(pick));
    }

    public boolean isExistBombInRoute(List<Position> route) {
        return route.stream().anyMatch(this::isBombPiece);
    }

    public HashMap<Position, Piece> getPieces() {
        return new HashMap<>(pieces);
    }
}
