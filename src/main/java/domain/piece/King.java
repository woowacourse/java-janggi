package domain.piece;

import domain.BoardLocation;
import domain.Team;
import java.util.List;

public class King extends Piece {

    public King(Team team) {
        super(PieceType.KING, team);
    }

    @Override
    public boolean isMovable(BoardLocation current, BoardLocation destination) {
        return false; //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        return List.of(); //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    public boolean canArrive(List<Piece> pathPiece) {
        return false; //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    public boolean canDestination(Piece destinationPiece) {
        return false; //TODO 2단계 궁성 단계에서 처리하도록 하기
    }
}
