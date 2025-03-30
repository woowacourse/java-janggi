package janggi.domain.piece;

import janggi.domain.game.Board;
import janggi.domain.position.Position;
import janggi.domain.game.Team;
import janggi.domain.movement.Movement;

public final class Cannon implements Piece {

    private final Team team;

    public Cannon(final Team team) {
        this.team = team;
    }

    @Override
    public void validateMove(final Position source, final Position destination, final Board board) {
        // 움직임 규칙에 안 맞는 경우
        if (!destination.isOrthogonallyAligned(source) && !destination.isDiagonallyAlignedInPalace(source)) {
            throw new IllegalArgumentException("[ERROR] 규칙에 어긋나는 움직입입니다.");
        }

        Movement targetMovement = source.getMovement(destination); // 이동 방향
        Position current = source.move(targetMovement);
        int blockingCount = 0;
        while (!current.equals(destination)) { // 도착지로 갈 때까지
            if (board.hasPieceAt(current)) {
                blockingCount++;
            }
            if (blockingCount == 2) {
                throw new IllegalArgumentException("[ERROR] 포는 하나의 기물만 뛰어넘을 수 있습니다.");
            }
            if (board.hasPieceAt(current, Type.CANNON)) {
                throw new IllegalArgumentException("[ERROR] 포는 포를 넘을 수 없습니다.");
            }
            current = current.move(targetMovement); // 1 칸 이동
        }
        if (blockingCount == 0) {
            throw new IllegalArgumentException("[ERROR] 포는 다른 기물을 뛰어 넘어서 이동해야 합니다.");
        }
        if (board.hasPieceAt(destination, Type.CANNON)) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 잡을 수 없습니다.");
        }
    }

    @Override
    public int point() {
        return 7;
    }

    @Override
    public Type type() {
        return Type.CANNON;
    }

    @Override
    public Team team() {
        return team;
    }
}
