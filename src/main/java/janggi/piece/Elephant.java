package janggi.piece;

import janggi.game.Board;
import janggi.position.Position;
import janggi.game.Team;
import janggi.movement.ElephantMovement;
import java.util.Arrays;

public final class Elephant implements Piece {

    private final Team team;

    public Elephant(final Team team) {
        this.team = team;
    }

    @Override
    public void validateMove(final Position source, final Position destination, final Board board) {
        // 도착지 판단
        ElephantMovement targetMovement = Arrays.stream(ElephantMovement.values())
                .filter(source::canMove)
                .filter(movement -> source.move(movement).equals(destination))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 규칙에 어긋나는 움직입입니다."));
        // 첫 번째 위치 판단
        if (board.hasPieceAt(source.move(targetMovement.getFirst()))) {
            throw new IllegalArgumentException("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
        }
        // 두 번째 위치 판단
        if (board.hasPieceAt(source.move(targetMovement.getFirst()).move(targetMovement.getSecond()))) {
            throw new IllegalArgumentException("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
        }
    }

    @Override
    public Type type() {
        return Type.ELEPHANT;
    }

    @Override
    public Team team() {
        return team;
    }
}
