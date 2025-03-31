package janggi.domain.piece;

import janggi.domain.game.Board;
import janggi.domain.game.Team;
import janggi.domain.movement.ElephantMovement;
import janggi.domain.position.Position;
import java.util.Arrays;

public final class Elephant extends Piece {

    public Elephant(final Team team) {
        super(team);
    }

    @Override
    public void validateMove(final Position source, final Position destination, final Board board) {
        ElephantMovement targetMovement = Arrays.stream(ElephantMovement.values())
                .filter(source::canMove)
                .filter(movement -> source.move(movement).equals(destination))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 규칙에 어긋나는 움직입입니다."));
        if (board.hasPieceAt(source.move(targetMovement.getFirst()))) {
            throw new IllegalArgumentException("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
        }
        if (board.hasPieceAt(source.move(targetMovement.getFirst()).move(targetMovement.getSecond()))) {
            throw new IllegalArgumentException("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
        }
    }

    @Override
    public int point() {
        return 3;
    }

    @Override
    public Type type() {
        return Type.ELEPHANT;
    }
}
