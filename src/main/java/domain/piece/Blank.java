package domain.piece;

import domain.Position;
import domain.Team;

import java.util.Collections;

public class Blank extends Piece {

    public Blank() {
        super(Team.NONE, (currentPosition, board) -> Collections.emptyList());
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BLANK;
    }

    @Override
    public boolean canMove(Position currentPosition, Position targetPosition, PieceProvider pieceProvider) {
        throw new IllegalArgumentException("[ERROR] 빈 공간이므로 이동할 수 없습니다.");
    }

    @Override
    public boolean isBridge() {
        return false;
    }

    @Override
    public boolean isCatchByCannon() {
        return false;
    }

    @Override
    public boolean isBlank() {
        return true;
    }
}
