package janggi.domain;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.HorseSide;
import janggi.domain.piece.None;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BoardFactory {

    public static Board getInitializedBoard(
            HorseSide blueLeftHorsePosition,
            HorseSide blueRightHorsePosition,
            HorseSide redLeftHorsePosition,
            HorseSide redRightHorsePosition

    ) {
        Map<Position, Piece> pieces = new HashMap<>();
        initializeWithNones(pieces);
        initializeBoard(
                pieces,
                blueLeftHorsePosition,
                blueRightHorsePosition,
                redLeftHorsePosition,
                redRightHorsePosition
        );
        return new Board(pieces);
    }

    private static void initializeWithNones(final Map<Position, Piece> pieces) {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                pieces.put(new Position(i, j), new None());
            }
        }
    }

    private static void initializeBoard(
            final Map<Position, Piece> pieces,
            HorseSide blueLeftHorsePosition,
            HorseSide blueRightHorsePosition,
            HorseSide redLeftHorsePosition,
            HorseSide redRightHorsePosition
    ) {
        initializePieces(pieces, General.INITIAL_POSITIONS_BLUE, General.INITIAL_POSITIONS_RED, General::new);
        initializePieces(pieces, Guard.INITIAL_POSITIONS_BLUE, Guard.INITIAL_POSITIONS_RED, Guard::new);
        initializePieces(pieces, Chariot.INITIAL_POSITIONS_BLUE, Chariot.INITIAL_POSITIONS_RED, Chariot::new);
        initializePieces(pieces, Cannon.INITIAL_POSITIONS_BLUE, Cannon.INITIAL_POSITIONS_RED, Cannon::new);
        initializePieces(pieces, Soldier.INITIAL_POSITIONS_BLUE, Soldier.INITIAL_POSITIONS_RED, Soldier::new);
        initializePieces(
                pieces,
                Horse.getInitialPositions(Team.BLUE, blueLeftHorsePosition, blueRightHorsePosition),
                Horse.getInitialPositions(Team.RED, redLeftHorsePosition, redRightHorsePosition),
                Horse::new
        );
        initializePieces(
                pieces,
                Elephant.getInitialPositions(Team.BLUE, blueLeftHorsePosition, blueRightHorsePosition),
                Elephant.getInitialPositions(Team.RED, redLeftHorsePosition, redRightHorsePosition),
                Elephant::new
        );
    }

    private static void initializePieces(final Map<Position, Piece> pieces,
                                         List<Position> bluePositions,
                                         List<Position> redPositions,
                                         Function<Team, Piece> pieceCreator) {
        for (Position position : bluePositions) {
            pieces.put(position, pieceCreator.apply(Team.BLUE));
        }
        for (Position position : redPositions) {
            pieces.put(position, pieceCreator.apply(Team.RED));
        }
    }
}
