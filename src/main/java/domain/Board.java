package domain;

import domain.piece.Pieces;
import domain.piece.category.PieceCategory;
import domain.spatial.Position;
import java.util.List;
import java.util.Map;

public record Board(
        Map<Player, Pieces> playerPiecesMap
) {

    public void moveAndCapture(final Player player, final Position startPosition, final Position targetPosition) {
        Pieces playerPieces = playerPiecesMap.get(player);
        Pieces opponentPieces = getOppositePieces(player);

        validatePlayerPieceCapture(targetPosition, playerPieces);

        List<Position> paths = playerPieces.getPiecePaths(startPosition, targetPosition);
        MoveInfos moveInfos = createMoveInfos(paths);

        playerPieces.movePiece(startPosition, targetPosition, moveInfos);
        opponentPieces.removePieceIfExists(targetPosition);
    }

    public boolean isFinish() {
        long kingCount = playerPiecesMap.values().stream()
                .filter(Pieces::existKing)
                .count();

        return kingCount != 2;
    }

    public Player getWinner() {
        return playerPiecesMap.keySet().stream()
                .filter(player -> playerPiecesMap.get(player).existKing())
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    private Pieces getOppositePieces(final Player player) {
        Player oppositePlayer = playerPiecesMap.keySet()
                .stream()
                .filter(opposite -> !opposite.equals(player))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        return playerPiecesMap.get(oppositePlayer);
    }

    private static void validatePlayerPieceCapture(final Position targetPosition, final Pieces playerPieces) {
        if (playerPieces.existByPosition(targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 도착 위치에 아군의 기물이 존재해 이동할 수 없습니다.");
        }
    }

    private MoveInfos createMoveInfos(final List<Position> paths) {
        List<MoveInfo> moveInfoElements = paths.stream()
                .map(path -> new MoveInfo(getPieceCategoryAtPosition(path)))
                .toList();
        return new MoveInfos(moveInfoElements);
    }

    private PieceCategory getPieceCategoryAtPosition(final Position pos) {
        return playerPiecesMap.values().stream()
                .filter(pieces -> pieces.existByPosition(pos))
                .map(pieces -> pieces.getCategoryAtPosition(pos))
                .findFirst()
                .orElse(PieceCategory.NONE);
    }
}
