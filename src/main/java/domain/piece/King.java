package domain.piece;

import domain.board.BoardLocation;
import java.util.Collections;
import java.util.List;

public class King extends Piece {

    public King(Team team) {
        super(team);
    }

    @Override
    protected void validateArrival(BoardLocation current, BoardLocation destination) {
        //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    protected List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination) {
        return Collections.emptyList(); //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    protected void validateMovePath(List<Piece> pathPiece) {
        //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    protected void validateKillable(Piece destinationPiece) {
        //TODO 2단계 궁성 단계에서 처리하도록 하기
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}
