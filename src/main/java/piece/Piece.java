package piece;

import game.Team;
import location.Position;

public abstract class Piece {
    private final Integer pieceId;
    private final Team team;
    private final PieceType pieceType;
    private boolean isCatch;

    protected Piece(Integer pieceId, Team team, PieceType pieceType) {
        this.pieceId = pieceId;
        this.team = team;
        this.pieceType = pieceType;
        this.isCatch = false;
    }

    public int getId() {
        return pieceId;
    }

    public Team getTeam() {
        return team;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public int getScore() {
        return pieceType.getScore();
    }

    public void move(Pieces pieces, Position destination) {
        validateDestination(destination);
        validatePaths(pieces, destination);
        updateCurrentPosition(destination);
    }

    public boolean isCatch() {
        return isCatch;
    }

    public void catchByOpponent() {
        isCatch = true;
    }

    public abstract Position getCurrentPosition();

    public abstract boolean isPlacedAt(Position targetPosition);

    protected abstract void validateDestination(Position destination);

    protected abstract void validatePaths(Pieces pieces, Position destination);

    protected abstract void updateCurrentPosition(Position destination);
}
