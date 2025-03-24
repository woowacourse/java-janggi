package domain.piece;

import domain.BoardLocation;
import domain.Team;
import java.util.List;

public class Scholar extends Piece {

    public Scholar(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(BoardLocation current, BoardLocation destination) {
        return; //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        return List.of(); //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    public void validateArrival(List<Piece> pathPiece) {
        return; //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    public void validateKillable(Piece destinationPiece) {
        return; //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    public PieceType getType() {
        return PieceType.SCHOLAR;
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
