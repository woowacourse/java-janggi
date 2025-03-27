package domain;

import domain.piece.Pieces;
import domain.piece.category.PieceCategory;
import domain.spatial.Position;
import java.util.List;
import java.util.Map;

public record Board(
        Map<Player, Pieces> playerPiecesMap
) {

    public void moveAndCapture(final Player current, final Position start, final Position target) {
        Pieces player = playerPiecesMap.get(current);
        Pieces opponent = getOppositePieces(current);

        List<Position> paths = player.getPiecePaths(start, target);

        validatePlayerPieceCapture(target, player);
        MoveInfos moveInfos = createMoveInfos(paths);

        player.movePiece(start, target, moveInfos);
        opponent.removePieceIfExists(target);
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

    private Pieces getOppositePieces(final Player current) {
        Player opposite = playerPiecesMap.keySet()
                .stream()
                .filter(player -> !player.equals(current))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("서버에 문제가 발생했습니다. - 상대 플레이어가 없습니다."));

        return playerPiecesMap.get(opposite);
    }

    private static void validatePlayerPieceCapture(final Position target, final Pieces current) {
        if (current.existByPosition(target)) {
            throw new IllegalArgumentException("도착 위치에 아군의 기물이 존재해 이동할 수 없습니다.");
        }
    }

    private MoveInfos createMoveInfos(final List<Position> paths) {
        List<MoveInfo> moveInfos = paths.stream()
                .map(path -> new MoveInfo(getPieceCategoryAtPosition(path)))
                .toList();
        return new MoveInfos(moveInfos);
    }

    private PieceCategory getPieceCategoryAtPosition(final Position position) {
        return playerPiecesMap.values().stream()
                .filter(pieces -> pieces.existByPosition(position))
                .map(pieces -> pieces.getCategoryAtPosition(position))
                .findFirst()
                .orElse(PieceCategory.NONE);
    }
}
