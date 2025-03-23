package domain.piece.strategy;

import domain.BoardLocation;
import domain.piece.MoveStrategy;
import domain.piece.Piece;
import java.util.List;

public class KingMoveStrategy implements MoveStrategy {

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
    public boolean canDestination(Piece selectPiece, Piece destinationPiece) {
        return false; //TODO 2단계 궁성 단계에서 처리하도록 하기
    }
}
