package domain;

import domain.piece.Piece;
import domain.piece.Pieces;
import domain.spatial.Position;
import java.util.List;
import java.util.Map;

public record Board(
        Map<Player, Pieces> board
) {

    public void move(final Player player, final Position startPosition, final Position targetPosition) {
        Pieces pieces = board.get(player);
        Piece piece = pieces.findByPosition(startPosition);

        validateTeamPieceInTargetPosition(targetPosition, pieces);
        validatePieceMovingPath(player, targetPosition, piece);

        pieces.updatePosition(piece, targetPosition);
        catchOppositePieceIfExistsTargetPosition(player, targetPosition);
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

    private void validateTeamPieceInTargetPosition(final Position targetPosition, final Pieces pieces) {
        if (pieces.existByPosition(targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 도착 위치에 아군의 기물이 존재해 이동할 수 없습니다.");
        }
    }

    private void validatePieceMovingPath(final Player player, final Position targetPosition, final Piece piece) {
        List<Position> path = piece.getPath(targetPosition);
        int pathPieceCount = getContainedPiecesCount(path);

        piece.validateMoveByPathPieceCount(pathPieceCount);
        if (piece.isCannon()) {
            validateCannonMoving(player, targetPosition, path);
        }
    }

    private void validateCannonMoving(final Player player, final Position targetPosition, final List<Position> path) {
        if (getOppositePieces(player).isCannonByPosition(targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 포는 상대 포를 잡을 수 없습니다.");
        }
        if (path.stream().anyMatch(this::existsCannon)) {
            throw new IllegalArgumentException("[ERROR] 포는 다른 포를 지나칠 수 없습니다.");
        }
    }

    private int getContainedPiecesCount(final List<Position> path) {
        return board.values().stream()
                .mapToInt(pieces -> pieces.countPiecesInPositions(path))
                .sum();
    }

    private boolean existsCannon(final Position position) {
        return board.values().stream()
                .anyMatch(pieces -> pieces.isCannonByPosition(position));
    }

    private void catchOppositePieceIfExistsTargetPosition(final Player player, final Position targetPosition) {
        Pieces oppositePieces = getOppositePieces(player);
        if (oppositePieces.existByPosition(targetPosition)) {
            oppositePieces.deleteByPosition(targetPosition);
        }
    }

    private Pieces getOppositePieces(final Player player) {
        Player oppositePlayer = board.keySet()
                .stream()
                .filter(opposite -> !opposite.equals(player))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        return board.get(oppositePlayer);
    }
}
