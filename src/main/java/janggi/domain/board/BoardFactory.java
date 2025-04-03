package janggi.domain.board;

import janggi.domain.piece.HorseSide;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceCache;
import janggi.domain.piece.Position;
import janggi.domain.piece.Team;
import janggi.domain.piece.impl.Cannon;
import janggi.domain.piece.impl.Chariot;
import janggi.domain.piece.impl.Elephant;
import janggi.domain.piece.impl.General;
import janggi.domain.piece.impl.Guard;
import janggi.domain.piece.impl.Horse;
import janggi.domain.piece.impl.Soldier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BoardFactory {
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
        return new Board(pieces, getPalacePositions());
    }

    public static Board getBoardWithPieces(
            final Map<Position, Piece> pieces
    ) {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                if (!pieces.containsKey(new Position(i, j))) {
                    pieces.put(new Position(i, j), PieceCache.getPiece("ㅁ", Team.NONE));
                }
            }
        }
        return new Board(pieces, getPalacePositions());
    }

    private static void initializeWithNones(final Map<Position, Piece> pieces) {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                pieces.put(new Position(i, j), PieceCache.getPiece("ㅁ", Team.NONE));
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
        initializePieces(pieces, General.INITIAL_POSITIONS_BLUE, General.INITIAL_POSITIONS_RED, "궁");
        initializePieces(pieces, Guard.INITIAL_POSITIONS_BLUE, Guard.INITIAL_POSITIONS_RED, "사");
        initializePieces(pieces, Chariot.INITIAL_POSITIONS_BLUE, Chariot.INITIAL_POSITIONS_RED, "차");
        initializePieces(pieces, Cannon.INITIAL_POSITIONS_BLUE, Cannon.INITIAL_POSITIONS_RED, "포");
        initializePieces(pieces, Soldier.INITIAL_POSITIONS_BLUE, Soldier.INITIAL_POSITIONS_RED, "졸");

        initializePieces(
                pieces,
                Horse.getInitialPositions(Team.BLUE, blueLeftHorsePosition, blueRightHorsePosition),
                Horse.getInitialPositions(Team.RED, redLeftHorsePosition, redRightHorsePosition),
                "마"
        );
        initializePieces(
                pieces,
                Elephant.getInitialPositions(Team.BLUE, blueLeftHorsePosition, blueRightHorsePosition),
                Elephant.getInitialPositions(Team.RED, redLeftHorsePosition, redRightHorsePosition),
                "상"
        );
    }

    private static void initializePieces(final Map<Position, Piece> pieces,
                                         final List<Position> bluePositions,
                                         final List<Position> redPositions,
                                         final String pieceName) {
        for (Position position : bluePositions) {
            pieces.put(position, PieceCache.getPiece(pieceName, Team.BLUE));
        }
        for (Position position : redPositions) {
            pieces.put(position, PieceCache.getPiece(pieceName, Team.RED));
        }
    }

    private static Set<Position> getPalacePositions() {
        Set<Position> palacePositions = new HashSet<>();
        for (int x : new int[]{1, 2, 3, 8, 9, 10}) {
            for (int y : new int[]{4, 5, 6}) {
                palacePositions.add(new Position(x, y));
            }
        }
        return palacePositions;
    }
}