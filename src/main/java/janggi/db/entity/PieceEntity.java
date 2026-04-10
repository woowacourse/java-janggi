package janggi.db.entity;

public class PieceEntity {

    private final Long id;
    private final Long gameId;
    private final String pieceType;
    private final String team;
    private final int positionX;
    private final int positionY;

    public PieceEntity(Long id, Long gameId, String pieceType, String team, int positionX, int positionY) {
        this.id = id;
        this.gameId = gameId;
        this.pieceType = pieceType;
        this.team = team;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public String getPieceType() {
        return pieceType;
    }

    public String getTeam() {
        return team;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
