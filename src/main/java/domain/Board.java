package domain;

import domain.piece.PieceMover;
import domain.piece.Pieces;
import domain.spatial.Position;
import java.util.Map;

public record Board(
        Map<Player, Pieces> board
) {

    public void moveAndCaptureByTargetPosition(final Player player,
                                               final Position startPosition,
                                               final Position targetPosition
    ) {
        Pieces playerPieces = board.get(player);
        Pieces opponentPieces = getOppositePieces(player);

        new PieceMover(playerPieces, opponentPieces).movePiece(startPosition, targetPosition);
        opponentPieces.removePieceIfExists(targetPosition);
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
