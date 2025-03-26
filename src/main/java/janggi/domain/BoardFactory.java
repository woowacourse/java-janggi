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

    private static final Map<String, Piece> pieceCache = new HashMap<>();

    public static Board getInitializedBoard(
            final HorseSide blueLeftHorsePosition,
            final HorseSide blueRightHorsePosition,
            final HorseSide redLeftHorsePosition,
            final HorseSide redRightHorsePosition
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
            final HorseSide blueLeftHorsePosition,
            final HorseSide blueRightHorsePosition,
            final HorseSide redLeftHorsePosition,
            final HorseSide redRightHorsePosition
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
                                         final List<Position> bluePositions,
                                         final List<Position> redPositions,
                                         final Function<Team, Piece> pieceCreator) {
        for (Position position : bluePositions) {
            pieces.put(position, getOrCreatePiece(pieceCreator, Team.BLUE));
        }
        for (Position position : redPositions) {
            pieces.put(position, getOrCreatePiece(pieceCreator, Team.RED));
        }
    }

    private static Piece getOrCreatePiece(final Function<Team, Piece> pieceCreator, final Team team) {
        String key = team.name() + pieceCreator.getClass().getSimpleName();
        return pieceCache.computeIfAbsent(key, k -> pieceCreator.apply(team));
    }
}