package janggi.temp;

import java.util.Arrays;
import java.util.Set;

public final class Elephant extends Piece {

    public Elephant(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Piece move(final Position destination, final Set<Piece> pieces) {
        // 도착지 판단
        ElephantMovement targetMovement = Arrays.stream(ElephantMovement.values())
                .filter(position()::canMove)
                .filter(movement -> position().move(movement).equals(destination))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 규칙에 어긋나는 움직입입니다."));
        // 첫 번째 위치 판단
        boolean hasBlockingPiece = pieces.stream()
                .map(Piece::position)
                .anyMatch(blockingPiece -> position().move(targetMovement.getFirst()).equals(blockingPiece));
        if (hasBlockingPiece) {
            throw new IllegalArgumentException("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
        }
        // 두 번째 위치 판단
        boolean hasBlockingPiece2 = pieces.stream()
                .map(Piece::position)
                .anyMatch(blockingPiece -> position().move(targetMovement.getFirst()).move(targetMovement.getSecond())
                        .equals(blockingPiece));
        if (hasBlockingPiece2) {
            throw new IllegalArgumentException("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
        }
        return new Elephant(destination, team());
    }
}
