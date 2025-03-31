package domain.piece;

import domain.Position;
import domain.Team;
import domain.player.Player;
import java.util.List;

public abstract class Piece {

    protected final Player player;
    protected final int point;

    public Piece(Player player, int point) {
        this.player = player;
        this.point = point;
    }

    public abstract List<Position> calculatePath(Position startPosition, Position targetPosition);

    public abstract PieceType getPieceType();

    public Team getTeam() {
        return player.getTeam();
    }

    public Player getPlayer() {
        return player;
    }

    public int getPoint() {
        return point;
    }

    public boolean comparePlayer(Piece otherPiece) {
        return this.player == otherPiece.player;
    }
}
