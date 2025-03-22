package domain.chessPiece;

import domain.direction.Direction;
import domain.path.Path;
import domain.position.ChessPiecePositions;
import domain.position.ChessPosition;
import domain.type.ChessPieceType;
import domain.type.ChessTeam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static domain.direction.Direction.*;
import static domain.direction.Direction.RIGHT;

public class Chariot extends UnlimitedMoveChessPiece {

    private static final List<Direction> directions = List.of(UP, DOWN, LEFT, RIGHT);

    public Chariot(final ChessTeam team) {
        super(team, directions);
    }

    public static Map<ChessPosition, ChessPiece> initPieces() {
        return Map.of(
                new ChessPosition(0, 0), new Chariot(ChessTeam.RED),
                new ChessPosition(0, 8), new Chariot(ChessTeam.RED),
                new ChessPosition(9, 0), new Chariot(ChessTeam.BLUE),
                new ChessPosition(9, 8), new Chariot(ChessTeam.BLUE)
        );
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

    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.CHARIOT;
    }

}
