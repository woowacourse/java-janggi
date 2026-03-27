package domain.piece;

import domain.Path;
import domain.board.Position;

import java.util.List;

public record Piece(PieceType pieceType, Team team, MoveStrategy moveStrategy) {

    List<Position> getPathPositions(Position from, Position to) {
        return moveStrategy.getPathPositions(from, to);
    }

    // 기물 각각의 이동 로직(각각의 이동 로직은 전략으로 주입)
    void canMove(List<Path> paths, Position to) {
        moveStrategy.canMove(paths, to);
    }

    boolean isSameTeam(Piece another){
        return another.team == team;
    }
}
