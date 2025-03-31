package janggi.domain.piece;

import janggi.domain.game.Board;
import janggi.domain.game.Team;
import janggi.domain.movement.Movement;
import janggi.domain.position.Position;

public final class Chariot extends Piece {

    public Chariot(final Team team) {
        super(team);
    }

    @Override
    public void validateMove(final Position source, final Position destination, final Board board) {
        // 움직임 규칙에 안 맞는 경우
        if (!destination.isOrthogonallyAligned(source) && !destination.isDiagonallyAlignedInPalace(source)) {
            throw new IllegalArgumentException("[ERROR] 규칙에 어긋나는 움직입입니다.");
        }
        Movement targetMovement = Movement.from(source, destination); // 이동 방향
        Position current = source.move(targetMovement);
        while (!current.equals(destination)) { // 도착지로 갈 때까지
            if (board.hasPieceAt(current)) {
                throw new IllegalArgumentException("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
            }
            current = current.move(targetMovement); // 1 칸 이동
        }
    }

    @Override
    public int point() {
        return 13;
    }

    @Override
    public Type type() {
        return Type.CHARIOT;
    }
}
