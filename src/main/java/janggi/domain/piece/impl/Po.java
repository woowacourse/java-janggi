package janggi.domain.piece.impl;

import janggi.domain.piece.Gung;
import janggi.domain.piece.GungSpecialMovePiece;
import janggi.domain.piece.Piece;
import janggi.domain.position.Path;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public class Po extends GungSpecialMovePiece {

    public Po(final Position position, final Gung gung) {
        super(position, gung);
    }

    @Override
    protected List<Path> getMoveablePathsWithNoGung(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Path> availablePaths = new ArrayList<>();

        final List<Position> allCrossPositions = position.getAllCrossPositions();

        for (Position endPosition : allCrossPositions) {
            final Path path = Path.start(position).nextPath(endPosition);
            if (isBlockedByOnePieceAndIsNotPo(path, allyPieces, enemyPieces)
                    && isNotEndWithAllyPieces(path, allyPieces)
                    && isNotEndWithPo(path, allyPieces, enemyPieces)
            ) {
                availablePaths.add(path);
            }
        }

        return availablePaths;
    }

    @Override
    protected List<Path> getGungSpecialPaths(final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        return gung.getAllPathsFrom(position).stream()
                .filter(path -> isBlockedByOnePieceAndIsNotPo(path, allyPieces, enemyPieces))
                .filter(path -> isNotEndWithAllyPieces(path, allyPieces))
                .filter(path -> isNotEndWithPo(path, allyPieces, enemyPieces))
                .toList();
    }

    private boolean isNotEndWithAllyPieces(final Path path, final List<Piece> allyPieces) {
        return !path.isEndWith(getPositionsOf(List.of(allyPieces)));
    }

    private boolean isNotEndWithPo(final Path path, final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        return !path.isEndWith(getPositionsOf(List.of(poPieces(allyPieces), poPieces(enemyPieces))));
    }

    private boolean isBlockedByOnePieceAndIsNotPo(final Path path, final List<Piece> allyPieces, final List<Piece> enemyPieces) {
        final List<Piece> pieces = new ArrayList<>();
        pieces.addAll(allyPieces);
        pieces.addAll(enemyPieces);

        final List<Piece> blockingPieces = pieces.stream()
                .filter(piece -> path.isBlockedWith(List.of(piece.getPosition())))
                .toList();

        return blockingPieces.size() == 1 && !(blockingPieces.getFirst() instanceof Po);
    }

    private List<Piece> poPieces(final List<Piece> pieces) {
        return pieces.stream()
                .filter(piece -> piece instanceof Po)
                .toList();
    }
}
