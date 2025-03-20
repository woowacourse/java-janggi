package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static domain.Direction.*;
import static domain.Direction.RIGHT;

public class Chariot extends UnlimitedMoveChessPiece {

    private static final List<Direction> directions = List.of(UP, DOWN, LEFT, RIGHT);

    public Chariot(ChessPosition chessPosition, final ChessTeam team) {
        super(chessPosition, team, directions);
    }

    @Override
    protected List<ChessPosition> getCoordinateDestinations(final List<Path> coordinates,
                                                            final ChessPiecePositions positions) {
        return coordinates.stream()
                .flatMap(path -> getAvailablePosition(positions, path))
                .toList();
    }

    private Stream<ChessPosition> getAvailablePosition(final ChessPiecePositions positions, final Path path) {
        final List<ChessPosition> chessPositions = new ArrayList<>();
        for (ChessPosition chessPosition : path.getPath()) {
            if (positions.existChessPieceByPosition(chessPosition)) {
                if (positions.getChessPieceByPosition(chessPosition).getTeam() != getTeam()) {
                    chessPositions.add(chessPosition);
                }
                break;
            }
            chessPositions.add(chessPosition);
        }
        return chessPositions.stream();
    }

    public static List<Chariot> initPieces() {
        return List.of(
                new Chariot(new ChessPosition(0, 0), ChessTeam.RED),
                new Chariot(new ChessPosition(0, 8), ChessTeam.RED),
                new Chariot(new ChessPosition(9, 0), ChessTeam.BLUE),
                new Chariot(new ChessPosition(9, 8), ChessTeam.BLUE)
        );
    }

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.CHARIOT;
    }

}
