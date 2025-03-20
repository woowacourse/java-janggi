package domain;

import java.util.ArrayList;
import java.util.List;

import static domain.Direction.*;
import static domain.Direction.RIGHT;

public class Cannon extends UnlimitedMoveChessPiece {
    private static final List<Direction> directions = List.of(UP, DOWN, LEFT, RIGHT);

    public Cannon(ChessPosition position, final ChessTeam team) {
        super(position, team, directions);
    }

    public static List<Cannon> initPieces() {
        return List.of(
                new Cannon(new ChessPosition(2, 1), ChessTeam.RED),
                new Cannon(new ChessPosition(2, 7), ChessTeam.RED),
                new Cannon(new ChessPosition(7, 1), ChessTeam.BLUE),
                new Cannon(new ChessPosition(7, 7), ChessTeam.BLUE)
        );
    }

    @Override
    protected List<ChessPosition> getCoordinateDestinations(final List<Path> coordinates,
                                                            final ChessPiecePositions positions) {
        final List<ChessPosition> chessPositions = new ArrayList<>();
        for (Path path : coordinates) {
            chessPositions.addAll(getAvailablePositions(positions, path));
        }
        return  chessPositions;
    }

    private List<ChessPosition> getAvailablePositions(final ChessPiecePositions positions, final Path path) {
        boolean isOverHurdles = false;
        final List<ChessPosition> availablePositions = new ArrayList<>();
        for (ChessPosition chessPosition : path.getPath()) {
            if (isOverHurdles) {
                if (!positions.existChessPieceByPosition(chessPosition)){
                    availablePositions.add(chessPosition);
                }
                if (positions.existChessPieceByPosition(chessPosition)){
                    availablePositions.add(chessPosition);
                    break;
                }
            }
            if (positions.existChessPieceByPosition(chessPosition)) {
                if (positions.getChessPieceTypeByPosition(chessPosition) == ChessPieceType.CANNON) {
                    break;
                }
                isOverHurdles = true;
            }
        }
        return availablePositions;
    }


    @Override
    public ChessPieceType getChessPieceType() {
        return ChessPieceType.CANNON;
    }

}
