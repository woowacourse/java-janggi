package janggi.temp;

import java.util.Arrays;
import java.util.Set;

public final class Horse extends Piece {

    public Horse(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Piece move(final Position destination, final Set<Piece> pieces) {
        // 도착지 판단
        HorseMovement targetMovement = Arrays.stream(HorseMovement.values())
                .filter(destination::canMove)
                .filter(movement -> position().move(movement).equals(destination))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 규칙에 어긋나는 움직입입니다."));
        // 첫 번째 위치 판단
        boolean hasPieceInFirstMove = pieces.stream()
                .map(Piece::position)
                .anyMatch(position -> targetMovement.calculateFirstPosition(position()).equals(position));
        if (hasPieceInFirstMove) {
            throw new IllegalArgumentException("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
        }
        return new Horse(destination, team());
    }
}
