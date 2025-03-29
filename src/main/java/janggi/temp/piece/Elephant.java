package janggi.temp.piece;

import janggi.temp.Board;
import janggi.temp.Team;
import janggi.temp.movement.ElephantMovement;
import janggi.temp.position.Position;
import java.util.Arrays;

public final class Elephant extends Piece {

    public Elephant(final Team team) {
        super(team);
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
//        boolean hasBlockingPiece = pieces.stream()
//                .map(Piece::position)
//                .anyMatch(blockingPiece -> position().move(targetMovement.getFirst()).equals(blockingPiece));
        if (board.hasPieceAt(source.move(targetMovement.getFirst()))) {
            throw new IllegalArgumentException("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
        }
        // 두 번째 위치 판단
//        boolean hasBlockingPiece2 = pieces.stream()
//                .map(Piece::position)
//                .anyMatch(blockingPiece -> position().move(targetMovement.getFirst()).move(targetMovement.getSecond())
//                        .equals(blockingPiece));
        if (board.hasPieceAt(source.move(targetMovement.getFirst()).move(targetMovement.getSecond()))) {
            throw new IllegalArgumentException("[ERROR] 경로가 기물에 막혀 이동할 수 없습니다.");
        }
    }

    @Override
    public Type type() {
        return Type.ELEPHANT;
    }
}
