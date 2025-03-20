package domain;

import static domain.Direction.DOWN;
import static domain.Direction.LEFT;
import static domain.Direction.RIGHT;
import static domain.Direction.UP;

import java.util.List;
import java.util.Map;

public class Pawn extends LimitedChessPiece {

    private static final Map<ChessTeam, List<Directions>> DIRECTIONS = Map.of(
            ChessTeam.RED, List.of(new Directions(List.of(LEFT)), new Directions(List.of(RIGHT)), new Directions(List.of(DOWN))),
            ChessTeam.BLUE, List.of(new Directions(List.of(LEFT)), new Directions(List.of(RIGHT)), new Directions(List.of(UP))));

    public Pawn(ChessPosition position, final ChessTeam chessTeam) {
        super(position, chessTeam, DIRECTIONS.get(chessTeam));
    }

    public static List<Pawn> initPieces() {
        return List.of(
                new Pawn(new ChessPosition(3, 0), ChessTeam.RED),
                new Pawn(new ChessPosition(3, 2), ChessTeam.RED),
                new Pawn(new ChessPosition(6, 0), ChessTeam.BLUE),
                new Pawn(new ChessPosition(6, 2), ChessTeam.BLUE)
        );
    }

    @Override
    protected List<ChessPosition> getCoordinateDestinations(final List<Path> coordinates,
                                                            final ChessPiecePositions positions) {
        return coordinates.stream()
                .filter(path -> !existChessPiece(positions, path))
                .map(Path::getDestination)
                .filter(destination -> isAbleToCatch(destination, positions))
                .toList();
    }

    private boolean existChessPiece(final ChessPiecePositions positions, final Path path) {
        List<ChessPosition> pathPositions = path.getPath();
        for (int i = 0; i < pathPositions.size() - 1; i++) {
            ChessPosition currentPosition = pathPositions.get(i);
            if (positions.existChessPieceByPosition(currentPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAbleToCatch(ChessPosition targetPosition, ChessPiecePositions positions) {
        if (!positions.existChessPieceByPosition(targetPosition)) {
            return true;
        }
        ChessPiece targetPiece = positions.getChessPieceByPosition(targetPosition);
        return targetPiece.getTeam() != getTeam();
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.PAWN;
    }
}
