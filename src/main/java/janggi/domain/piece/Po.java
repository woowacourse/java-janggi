package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public class Po extends Piece {

    public Po(final Position position) {
        super(position);
    }

    @Override
    public List<Path> getMoveablePaths(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Path> availablePaths = new ArrayList<>();

        final List<Position> allCrossPositions = position.getAllCrossPositions();

        for (Position endPosition : allCrossPositions) {
            final Path path = Path.start(endPosition).nextPath(endPosition);
            if (isBlockedByOnePieceAndIsNotPo(path, allyPieces, enemyPieces)
                    && !path.isEndedWith(getPositionsOf(List.of(allyPieces, poPieces(enemyPieces))))
            ) {
                availablePaths.add(path);
            }
        }

        return availablePaths;
    }

    private boolean isBlockedByOnePieceAndIsNotPo(final Path path, final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Piece> pieces = new ArrayList<>();
        pieces.addAll(allyPieces);
        pieces.addAll(enemyPieces);

        final List<Piece> blockingPieces = pieces.stream()
                .filter(piece -> path.isBlockedWith(List.of(piece.position)))
                .toList();

        return blockingPieces.size() == 1 && blockingPieces.getFirst() instanceof Po;
    }

    private List<Piece> poPieces(final List<Piece> pieces) {
        return pieces.stream()
                .filter(piece -> piece instanceof Po)
                .toList();
    }
}
