package game.domain.piece;

import game.domain.board.BoardLocation;
import java.util.List;

public class King extends Piece {

    public King(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(BoardLocation current, BoardLocation destination) {
        //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        return List.of(); //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    public void validateArrival(List<Piece> pathPiece) {
        //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    public void validateKillable(Piece destinationPiece) {
        //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || (obj != null && getClass() == obj.getClass());
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
}
