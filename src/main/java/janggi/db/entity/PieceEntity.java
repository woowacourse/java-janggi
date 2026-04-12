package janggi.db.entity;

import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

public class PieceEntity {
    private final Long gameId;
    private final int x;
    private final int y;
    private final PieceType pieceType;
    private final Team team;

    public PieceEntity(Long gameId, int x, int y, PieceType pieceType, Team team) {
        this.gameId = gameId;
        this.x = x;
        this.y = y;
        this.pieceType = pieceType;
        this.team = team;
    }

    public static PieceEntity from(Long gameId, Position position, Piece piece) {
        return new PieceEntity(gameId, position.getX(), position.getY(), piece.getPieceType(), piece.getTeam());
    }

    public Long getGameId() {
        return gameId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }
}
