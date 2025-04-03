package domain.game;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Pieces;
import domain.player.Player;
import domain.position.Position;
import dto.MoveResultDto;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record Board(
        Map<Player, Pieces> board
) {

    public MoveResultDto move(final Player player, final Position startPosition, final Position targetPosition) {
        Pieces pieces = board.get(player);
        Piece piece = pieces.findByPosition(startPosition);

        validateTeamPieceInTargetPosition(targetPosition, pieces);
        validatePieceMovingPath(player, targetPosition, piece);

        Piece updatedPiece = pieces.updatePosition(piece, targetPosition);
        Optional<Piece> catchResult = catchOppositePieceIfExistsTargetPosition(player, targetPosition);

        return new MoveResultDto(updatedPiece, catchResult);
    }

    public boolean isFinish() {
        long kingCount = board.values().stream()
                .filter(Pieces::existGeneral)
                .count();

        return kingCount != 2;
    }

    public Player getWinner() {
        return board.keySet().stream()
                .filter(player -> board.get(player).existGeneral())
                .findAny()
                .orElseThrow(RuntimeException::new);
    }

    private void validateTeamPieceInTargetPosition(final Position targetPosition, final Pieces pieces) {
        if (pieces.existByPosition(targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 도착 위치에 아군의 기물이 존재해 이동할 수 없습니다.");
        }
    }

    private void validatePieceMovingPath(final Player player, final Position targetPosition, final Piece piece) {
        piece.validateMovablePosition(targetPosition);

        List<Position> path = piece.getPath(targetPosition);
        if (piece.isEqualType(PieceType.CANNON)) {
            validateCannonMoving(player, targetPosition, path);
            return;
        }

        if (existsOtherPieceInPath(path)) {
            throw new IllegalArgumentException("[ERROR] 다른 기물을 지나칠 수 없습니다.");
        }
    }

    private void validateCannonMoving(final Player player, final Position targetPosition, final List<Position> path) {
        int count = getContainedPiecesCount(path);
        if (count != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 중간에 기물이 1개여야 합니다.");
        }
        if (board.get(getOppositePlayer(player)).isCannonByPosition(targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 포는 상대 포를 잡을 수 없습니다.");
        }
        if (path.stream().anyMatch(this::existsCannon)) {
            throw new IllegalArgumentException("[ERROR] 포는 다른 포를 지나칠 수 없습니다.");
        }
    }

    private boolean existsOtherPieceInPath(final List<Position> path) {
        int containedPiecesCount = getContainedPiecesCount(path);
        return containedPiecesCount != 0;
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

    private Optional<Piece> catchOppositePieceIfExistsTargetPosition(final Player player,
                                                                     final Position targetPosition) {
        Player oppositePlayer = getOppositePlayer(player);
        Pieces oppositePieces = board.get(oppositePlayer);
        if (oppositePieces.existByPosition(targetPosition)) {
            return Optional.of(oppositePieces.deleteByPosition(targetPosition));
        }
        return Optional.empty();
    }

    private Player getOppositePlayer(final Player player) {
        return board.keySet()
                .stream()
                .filter(opposite -> !opposite.equals(player))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public void calculateScores() {
        for (Player player : board.keySet()) {
            player.addScore(board.get(player).calculateTotalScore());
        }
    }
}
