package janggi.temp.piece;

import janggi.temp.Board;
import janggi.temp.Team;
import janggi.temp.position.Position;

public final class Cannon extends Piece {

    public Cannon(final Team team) {
        super(team);
    }

    @Override
    public void validateMove(final Position source, final Position destination, final Board board) {
        // 움직임 규칙에 안 맞는 경우
//        if (!destination.isOrthogonallyAligned(position()) && !destination.isDiagonallyAlignedInPalace(position())) {
//            throw new IllegalArgumentException("[ERROR] 규칙에 어긋나는 움직입입니다.");
//        }
//        Movement targetMovement = position().getMovement(destination); // 이동 방향
//        Position current = position().move(targetMovement);
//        int blockingCount = 0;
//        while (!current.equals(destination)) { // 도착지로 갈 때까지
//            final Position finalCurrent = current;
//            boolean hasBlockingPiece = pieces.stream()
//                    .map(Piece::position)
//                    .anyMatch(position -> position.equals(finalCurrent));
//            if (hasBlockingPiece) {
//                blockingCount++;
//            }
//            current = current.move(targetMovement); // 1 칸 이동
//        }
//        Optional<Piece> first = pieces.stream()
//                .filter(piece -> piece.position().equals(destination))
//                .findFirst();
//        if (first.isPresent() && first.get().type() == Type.CANNON) {
//            throw new IllegalArgumentException("[ERROR] 포는 포를 잡을 수 없습니다.");
//        }
    }

    @Override
    public Type type() {
        return Type.CANNON;
    }
}
