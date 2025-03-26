package domain;

import domain.piece.Pieces;
import domain.piece.category.PieceCategory;
import domain.spatial.Position;
import java.util.List;
import java.util.Map;

public record Board(
        Map<Player, Pieces> board
) {

    public void moveAndCaptureByTargetPosition(final Player player, final Position startPosition,
                                               final Position targetPosition) {
        Pieces playerPieces = board.get(player);
        Pieces opponentPieces = getOppositePieces(player);

        List<Pieces> allPieces = getAllPieces();
        List<Position> paths = playerPieces.getPiecePaths(startPosition, targetPosition);
        List<MoveInfo> moveInfoElements = paths.stream()
                .map(path -> new MoveInfo(getPieceCategoryAtPosition(allPieces, path)))
                .toList();
        MoveInfos moveInfos = new MoveInfos(moveInfoElements);

        playerPieces.movePiece(startPosition, targetPosition, moveInfos);
        opponentPieces.removePieceIfExists(targetPosition);
    }

    private PieceCategory getPieceCategoryAtPosition(final List<Pieces> allPieces, final Position path) {
        return allPieces.stream()
                .filter(pieces -> pieces.existByPosition(path))
                .map(pieces -> pieces.getCategoryAtPosition(path))
                .findFirst()
                .orElse(PieceCategory.NONE);
    }

    private List<Pieces> getAllPieces() {
        return board.values().stream()
                .toList();
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
                .findFirst()
                .orElseThrow(RuntimeException::new);
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
