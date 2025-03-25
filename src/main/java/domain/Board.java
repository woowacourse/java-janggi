package domain;

import domain.piece.Piece;
import domain.piece.PieceMoveValidator;
import domain.piece.PieceRemover;
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

        PieceMoveValidator moveValidator = new PieceMoveValidator();
        moveValidator.validateTeamPieceInTargetPosition(pieces, targetPosition);
        moveValidator.validateMovePath(piece, targetPosition, getAllPieces());

        List<Position> path = piece.getPath(targetPosition);

        if (piece.isCannon()) {
            moveValidator.validateCannonCapture(getOppositePieces(player), targetPosition);
            moveValidator.validateCannonPath(path, getAllPieces());
        }

        pieces.updatePosition(piece, targetPosition);
        new PieceRemover().removePieceIfExists(getOppositePieces(player), targetPosition);
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

    private List<Pieces> getAllPieces() {
        return board.values().stream()
                .toList();
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
