package domain.piece;

import domain.Position;
import domain.Team;

public class Blank extends Piece {

    public Blank() {
        super(Team.NONE);
    }

    @Override
    public boolean canMove(Position currentPosition, Position targetPosition, PieceProvider pieceProvider) {
        throw new IllegalArgumentException("[ERROR] 빈 공간이므로 이동할 수 없습니다.");
    }
}
