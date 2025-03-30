package domain.piece;

import domain.Position;
import domain.Team;
import domain.player.Player;
import java.util.List;

public abstract class Piece {

    protected final Player player;  // Player 객체만 필드로 추가

    // Player 객체를 생성자로 받도록 수정
    public Piece(Player player) {
        this.player = player;
    }

    public abstract List<Position> calculatePath(Position startPosition, Position targetPosition);

    public abstract PieceType getPieceType();

    public Team getTeam() {
        return player.getTeam();
    }

    public Player getPlayer() {
        return player;
    }

    public boolean comparePlayer(Piece otherPiece) {
        return this.player == otherPiece.player;
    }
}
