package domain.piece;

import domain.game.Team;
import domain.position.Position;
import java.util.List;

public abstract class ActivePiece implements Piece {
    private final Team team;
    private final PieceType type;

    protected ActivePiece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    @Override
    public boolean isAlly(Piece other) {
        return other instanceof ActivePiece activePiece && this.team == activePiece.team;
    }

    @Override
    public void validateRoute(List<Piece> piecesOnRoute, Piece destinationPiece) {
        for (Piece piece : piecesOnRoute) {
            if (piece.isNotEmpty()) {
                throw new IllegalArgumentException("이동 경로에 기물이 있습니다.");
            }
        }
        validateDestination(destinationPiece);
    }

    private void validateDestination(Piece destinationPiece) {
        if (destinationPiece.isAlly(this)) {
            throw new IllegalArgumentException("아군 기물이 있는 위치로 이동할 수 없습니다.");
        }
    }

    protected int forwardDirection() {
        return team.forwardRowDirection();
    }

    @Override
    public String display(PieceAppearance colorizer) {
        return colorizer.colorize(team, type);
    }

    @Override
    public String toString() {
        return type.name();
    }

    @Override
    public boolean isNotEmpty() {
        return true;
    }
}
