package janggi.domain;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import janggi.domain.piece.PositionSide;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.TeamType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public void putPiece(final Position position, final Piece piece) {
        pieces.put(position, piece);
    }

    public Piece getPiece(final Position position) {
        return pieces.getOrDefault(position, Piece.createEmpty());
    }

    public Map<Position, Piece> getPieces() {
        Map<Position, Piece> copy = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                copy.put(new Position(i, j), getPiece(new Position(i, j)));
            }
        }
        return copy;
    }

    public static Board getInitializedBoard(
            PositionSide blueLeftHorsePosition,
            PositionSide blueRightHorsePosition,
            PositionSide redLeftHorsePosition,
            PositionSide redRightHorsePosition

    ) {
        Map<Position, Piece> pieces = new HashMap<>();
        initializeGeneral(pieces);
        initializeGuard(pieces);
        initializeChariot(pieces);
        initializeCannon(pieces);
        initializeSoldier(pieces);
        initializeHorse(pieces, blueLeftHorsePosition, blueRightHorsePosition);
        initializeElephant(pieces, redLeftHorsePosition, redRightHorsePosition);
        return new Board(pieces);
    }

    private static void initializeGeneral(final Map<Position, Piece> pieces) {
        putInBoard(pieces, General.createWithInitialPositions(TeamType.BLUE));
        putInBoard(pieces, General.createWithInitialPositions(TeamType.RED));
    }

    private static void initializeGuard(final Map<Position, Piece> pieces) {
        putInBoard(pieces, Guard.createWithInitialPositions(TeamType.BLUE));
        putInBoard(pieces, Guard.createWithInitialPositions(TeamType.RED));
    }

    private static void initializeChariot(final Map<Position, Piece> pieces) {
        putInBoard(pieces, Chariot.createWithInitialPositions(TeamType.BLUE));
        putInBoard(pieces, Chariot.createWithInitialPositions(TeamType.RED));
    }

    private static void initializeCannon(final Map<Position, Piece> pieces) {
        putInBoard(pieces, Cannon.createWithInitialPositions(TeamType.BLUE));
        putInBoard(pieces, Cannon.createWithInitialPositions(TeamType.RED));
    }

    private static void initializeSoldier(final Map<Position, Piece> pieces) {
        putInBoard(pieces, Soldier.createWithInitialPositions(TeamType.BLUE));
        putInBoard(pieces, Soldier.createWithInitialPositions(TeamType.RED));
    }

    private static void initializeHorse(final Map<Position, Piece> pieces, PositionSide leftHorsePosition, PositionSide rightHorsePosition) {
        putInBoard(pieces, Horse.createWithInitialPositions(TeamType.BLUE, leftHorsePosition, rightHorsePosition));
        putInBoard(pieces, Horse.createWithInitialPositions(TeamType.RED, leftHorsePosition, rightHorsePosition));
    }

    private static void initializeElephant(final Map<Position, Piece> pieces, PositionSide leftElephantPosition, PositionSide rightElephantPosition) {
        putInBoard(pieces, Elephant.createWithInitialPositions(TeamType.BLUE, leftElephantPosition, rightElephantPosition));
        putInBoard(pieces, Elephant.createWithInitialPositions(TeamType.RED, leftElephantPosition, rightElephantPosition));
    }

    private static void putInBoard(final Map<Position, Piece> board, List<Piece> pieces) {
        for (Piece piece : pieces) {
            board.put(piece.getPosition(), piece);
        }
    }
}
