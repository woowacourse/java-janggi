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

        List<Position> paths = playerPieces.getPiecePaths(startPosition, targetPosition);

        validatePlayerPieceCapture(targetPosition, playerPieces);
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
                .orElseThrow(() -> new RuntimeException("서버에 문제가 발생했습니다. - 우승자 플레이어가 없습니다."));
    }

    private Pieces getOppositePieces(final Player player) {
        Player oppositePlayer = playerPiecesMap.keySet()
                .stream()
                .filter(opposite -> !opposite.equals(player))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("서버에 문제가 발생했습니다. - 상대 플레이어가 없습니다."));

        return playerPiecesMap.get(oppositePlayer);
    }

    private static void validatePlayerPieceCapture(final Position targetPosition, final Pieces playerPieces) {
        if (playerPieces.existByPosition(targetPosition)) {
            throw new IllegalArgumentException("도착 위치에 아군의 기물이 존재해 이동할 수 없습니다.");
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
