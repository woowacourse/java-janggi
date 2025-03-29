package janggi.temp;

import java.util.Set;

public final class Chariot extends Piece {

    public Chariot(final Position position, final Team team) {
        super(position, team);
    }

    @Override
    public Piece move(final Position destination, final Set<Piece> pieces) {
        // 움직임 규칙에 안 맞는 경우
        if (!destination.isOrthogonallyAligned(position()) && !destination.isDiagonallyAlignedInPalace(position())) {
            throw new IllegalArgumentException("[ERROR] 규칙에 어긋나는 움직입입니다.");
        }
        Movement targetMovement = position().getMovement(destination); // 이동 방향
        Position current = position().move(targetMovement);
        while (!current.equals(destination)) { // 도착지로 갈 때까지
            final Position finalCurrent = current;
            boolean hasBlockingPiece = pieces.stream()
                    .map(Piece::position)
                    .anyMatch(position -> position.equals(finalCurrent));
            if (hasBlockingPiece) {
                throw new IllegalArgumentException("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
            }
            current = current.move(targetMovement); // 1 칸 이동
        }
        return new Chariot(destination, team());
    }
}
