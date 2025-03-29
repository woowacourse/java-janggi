package piece;

import game.Team;
import location.Direction;
import location.PathUtility;
import location.Position;

public class Guard implements Piece{
    private final Integer pieceId;
    private final Team team;
    private boolean isCatch;
    private final PieceType pieceType;
    private Position currentPosition;

    public Guard(int pieceId, Team team, Position currentPosition) {
        this.pieceId = pieceId;
        this.team = team;
        this.isCatch = false;
        this.pieceType = PieceType.GUARD;
        this.currentPosition = currentPosition;
    }

    @Override
    public void validateDestination(Position destination) {
        PathUtility.checkOneMovement(currentPosition, destination);

        if(PathUtility.isPalacePosition(currentPosition)
                && Direction.isDiagonal(currentPosition, destination)) {
            PathUtility.checkValidOneDiagonalMovementInPalace(currentPosition, destination);
            return;
        }
        PathUtility.checkStraightMovement(currentPosition, destination);
    }

    @Override
    public void validatePaths(Pieces pieces, Position destination) {

    }

    @Override
    public void move(Position destination) {
        currentPosition = destination;
    }

    @Override
    public void catchByOpponent() {
        isCatch = true;
    }

    @Override
    public boolean isPlacedAt(Position targetPosition) {
        return currentPosition.equals(targetPosition);
    }

    @Override
    public int getId() {
        return pieceId;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public int getScore() {
        return pieceType.getScore();
    }

    @Override
    public boolean isCatch() {
        return isCatch;
    }
}
