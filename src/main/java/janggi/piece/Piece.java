package janggi.piece;

import janggi.board.Position;
import janggi.palace.PalaceArea;
import janggi.team.TeamName;
import java.util.Objects;

public abstract class Piece {
    protected static final int OFFSET_ZERO = 0;
    protected static final int OFFSET_ONE = 1;
    protected static final int OFFSET_TWO = 2;
    protected static final int OFFSET_THREE = 3;
    protected static final int OFFSET_X_MAX = 8;
    protected static final int OFFSET_Y_MAX = 9;

    protected static final String INVALID_MOVEMENT = "해당 말에 맞는 이동 방식이 아닙니다.";

    protected PieceName pieceName;
    protected TeamName teamName;
    protected Position position;
    protected PieceStatus pieceStatus;

    public Piece() {
        this.pieceStatus = PieceStatus.ALIVE;
    }

    public abstract void validateMovement(Position currentPosition, Position destination, PalaceArea palaceArea);

    public void updateStatusIfCaught(Position opponentPosition) {
        if (this.position.equals(opponentPosition)) {
            this.pieceStatus = PieceStatus.CAUGHT;
        }
    }

    public boolean isOccupiedByMe(Position position) {
        return position.equals(this.position);
    }

    public void move(Position position) {
        this.position = new Position(position.x(), position.y());
    }

    public boolean matchPosition(Position position) {
        return this.position.equals(position);
    }

    public boolean matchName(String name) {
        return pieceName.getName().equalsIgnoreCase(name);
    }

    public boolean matchTeam(TeamName teamName) {
        return this.teamName == teamName;
    }

    public boolean matchStatus(PieceStatus pieceStatus) {
        return this.pieceStatus == pieceStatus;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return pieceName.getName();
    }

    public String getTeamName() {
        return teamName.getName();
    }

    public PieceStatus getStatus() {
        return pieceStatus;
    }

    public double getScore() {
        return pieceName.getScore();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Piece piece = (Piece) obj;
        return pieceName == piece.pieceName && teamName == piece.teamName && Objects.equals(position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceName, teamName, position);
    }
}
