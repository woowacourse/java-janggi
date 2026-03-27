package domain.piece;

import domain.Path;
import domain.board.Position;

import java.util.List;

public interface MoveStrategy {
    // piece가 from에서 해당 도착지 to로 도착할 수 있는지 없다면 예외를 던진다
    // from에서 to까지의 경로 Position을 반환
    List<Position> getPathPositions(Position from, Position to);

    // 기물 각각의 이동 로직(각각의 이동 로직은 전략으로 주입)
    void canMove(List<Path> paths, Position to);
}
