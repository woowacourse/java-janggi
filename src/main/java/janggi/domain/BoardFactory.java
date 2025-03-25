package janggi.domain;

import janggi.domain.piece.*;

import java.util.ArrayList;
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
        List<Piece> initializedPieces = initializePiece(General::createWithInitialPositions);
        initializedPieces.addAll(initializePiece(Guard::createWithInitialPositions));
        initializedPieces.addAll(initializePiece(Chariot::createWithInitialPositions));
        initializedPieces.addAll(initializePiece(Cannon::createWithInitialPositions));
        initializedPieces.addAll(initializePiece(Soldier::createWithInitialPositions));
        initializedPieces.addAll(initializeHorse(blueLeftHorsePosition, blueRightHorsePosition, redLeftHorsePosition,
                redRightHorsePosition));
        initializedPieces.addAll(initializeElephant(blueLeftHorsePosition, blueRightHorsePosition, redLeftHorsePosition,
                redRightHorsePosition));

        Map<Position, Piece> board = initializeWithNones();
        initializedPieces.forEach(
                piece -> board.put(piece.getPosition(), piece)
        );
        return new Board(board);
    }

    private static Map<Position, Piece> initializeWithNones() {
        Map<Position, Piece> initializedWithNones = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                initializedWithNones.put(new Position(i, j), new None());
            }
        }
        return initializedWithNones;
    }


    private static List<Piece> initializePiece(Function<Team, List<Piece>> initialize) {

        List<Piece> withInitialPositions = new ArrayList<>(initialize.apply(Team.BLUE));
        withInitialPositions.addAll(initialize.apply(Team.RED));
        return withInitialPositions;
    }

    private static List<Piece> initializeHorse(
            HorseSide blueLeftHorsePosition,
            HorseSide blueRightHorsePosition,
            HorseSide redLeftHorsePosition,
            HorseSide redRightHorsePosition
    ) {
        List<Piece> initializeHorses = new ArrayList<>(
                Horse.createWithInitialPositions(Team.BLUE, blueLeftHorsePosition, blueRightHorsePosition)
        );
        initializeHorses.addAll(Horse.createWithInitialPositions(Team.RED, redLeftHorsePosition, redRightHorsePosition));
        return initializeHorses;
    }

    private static List<Piece> initializeElephant(
            HorseSide blueLeftHorsePosition,
            HorseSide blueRightHorsePosition,
            HorseSide redLeftHorsePosition,
            HorseSide redRightHorsePosition) {

        List<Piece> initializeElephant = new ArrayList<>(
                Elephant.createWithInitialPositions(Team.BLUE, blueLeftHorsePosition, blueRightHorsePosition)
        );
        initializeElephant.addAll(Elephant.createWithInitialPositions(Team.RED, redLeftHorsePosition, redRightHorsePosition));
        return initializeElephant;
    }
}
