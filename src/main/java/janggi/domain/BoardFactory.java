package janggi.domain;

import janggi.domain.piece.None;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {

    public static Board getInitializedBoard(
        HorseSide blueLeftHorsePosition,
        HorseSide blueRightHorsePosition,
        HorseSide redLeftHorsePosition,
        HorseSide redRightHorsePosition

    ) {
        List<Piece> initializedPieces = new ArrayList<>();
        initializedPieces.addAll(JanggiPieceSetup.INITIAL_CANNON);
        initializedPieces.addAll(JanggiPieceSetup.INITIAL_CHARIOT);
        initializedPieces.addAll(JanggiPieceSetup.INITIAL_GENERAL);
        initializedPieces.addAll(JanggiPieceSetup.INITIAL_GUARD);
        initializedPieces.addAll(JanggiPieceSetup.INITIAL_SOLDIER);
        initializedPieces.addAll(
            JanggiPieceSetup.createElephantWithInitialPositions(Team.BLUE, blueLeftHorsePosition,
                blueRightHorsePosition)
        );
        initializedPieces.addAll(
            JanggiPieceSetup.createElephantWithInitialPositions(Team.RED, redLeftHorsePosition,
                redRightHorsePosition)
        );
        initializedPieces.addAll(
            JanggiPieceSetup.createHorseWithInitialPositions(Team.BLUE, blueLeftHorsePosition,
                blueRightHorsePosition)
        );
        initializedPieces.addAll(
            JanggiPieceSetup.createHorseWithInitialPositions(Team.RED, redLeftHorsePosition,
                redRightHorsePosition)
        );

        Map<Position, Piece> board = initializeWithNones();
        initializedPieces.forEach(
            piece -> board.put(piece.getPosition(), piece)
        );
        return new Board(board, Turn.First());
    }

    private static Map<Position, Piece> initializeWithNones() {
        Map<Position, Piece> initializedWithNones = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                initializedWithNones.put(new Position(i, j), new None(new Position(i, j)));
            }
        }
        return initializedWithNones;
    }
}
