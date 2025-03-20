package domain;

import domain.piece.Piece;
import domain.piece.Pieces;
import domain.piece.Position;
import java.util.List;
import java.util.Map;

public record Board(Map<Player, Pieces> board) {

    public void move(final Player player, final Position startPosition, final Position targetPosition) {
        Piece piece = board.get(player).findByPosition(startPosition);

        List<Position> path = piece.getPath(targetPosition);

        if (board.get(player).existByPosition(targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 도착 위치에 아군의 기물이 존재해 이동할 수 없습니다.");
        }

        if (piece.isCannon()) {
            int count = getContainedPiecesCount(path);
            if (count != 1) {
                throw new IllegalArgumentException("[ERROR] 포는 중간에 기물이 1개여야 합니다.");
            }

            if (getOppositePieces(player).isCannonByPosition(targetPosition)) {
                throw new IllegalArgumentException("[ERROR] 포는 상대 포를 잡을 수 없습니다.");
            }

            if (path.stream().anyMatch(this::has)) {
                throw new IllegalArgumentException("[ERROR] 포는 다른 포를 지나칠 수 없습니다.");
            }
        }

        if (!piece.isCannon() && !canMove(path)) {
            throw new IllegalArgumentException();
        }

        board.get(player).updatePosition(piece, targetPosition);

        Pieces oppositePieces = getOppositePieces(player);
        if (oppositePieces.existByPosition(targetPosition)) {
            oppositePieces.deleteByPosition(targetPosition);
        }
    }

    private boolean canMove(final List<Position> path) {
        int containedPiecesCount = getContainedPiecesCount(path);
        return containedPiecesCount == 0;
    }

    private int getContainedPiecesCount(final List<Position> path) {
        return board.values().stream()
                .mapToInt(pieces -> pieces.countPiecesInPositions(path))
                .sum();
    }

    private Pieces getOppositePieces(final Player player) {
        Player oppositePlayer = board.keySet()
                .stream()
                .filter(opposite -> !opposite.equals(player))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        return board.get(oppositePlayer);
    }

    private boolean has(final Position position) {
        return board.values().stream()
                .anyMatch(pieces -> pieces.isCannonByPosition(position));
    }

    public boolean isFinish() {
        long kingCount = board.values().stream()
                .filter(Pieces::existKing)
                .count();

        return kingCount != 2;
    }

    public Player getWinner() {
        return board.keySet().stream()
                .filter(player -> board.get(player).existKing())
                .findAny()
                .orElseThrow(RuntimeException::new);
    }
}
