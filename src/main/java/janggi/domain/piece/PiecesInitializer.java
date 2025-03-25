package janggi.domain.piece;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import janggi.domain.BoardSetup;
import janggi.domain.Team;
import janggi.domain.piece.direction.Position;
import java.util.ArrayList;
import java.util.List;

public class PiecesInitializer {

    public static List<Piece> initializePieces(final BoardSetup redBoardSetup, final BoardSetup blueBoardSetup) {
        final List<Piece> defaultPieces = new ArrayList<>();

        initializeRedPieces(defaultPieces);
        initializeBluePieces(defaultPieces);

        defaultPieces.addAll(elephantFormation(redBoardSetup, RED));
        defaultPieces.addAll(elephantFormation(blueBoardSetup, BLUE));

        return defaultPieces;
    }

    private static void initializeRedPieces(final List<Piece> pieces) {
        pieces.add(new Soldier(new Position(0, 6), RED));
        pieces.add(new Soldier(new Position(2, 6), RED));
        pieces.add(new Soldier(new Position(4, 6), RED));
        pieces.add(new Soldier(new Position(6, 6), RED));
        pieces.add(new Soldier(new Position(8, 6), RED));

        pieces.add(new Cannon(new Position(1, 7), RED));
        pieces.add(new Cannon(new Position(7, 7), RED));

        pieces.add(new General(new Position(4, 8), RED));

        pieces.add(new Chariot(new Position(0, 9), RED));
        pieces.add(new Chariot(new Position(8, 9), RED));

        pieces.add(new Guard(new Position(3, 9), RED));
        pieces.add(new Guard(new Position(5, 9), RED));
    }

    private static void initializeBluePieces(final List<Piece> pieces) {
        pieces.add(new Soldier(new Position(0, 3), BLUE));
        pieces.add(new Soldier(new Position(2, 3), BLUE));
        pieces.add(new Soldier(new Position(4, 3), BLUE));
        pieces.add(new Soldier(new Position(6, 3), BLUE));
        pieces.add(new Soldier(new Position(8, 3), BLUE));

        pieces.add(new Cannon(new Position(1, 2), BLUE));
        pieces.add(new Cannon(new Position(7, 2), BLUE));

        pieces.add(new General(new Position(4, 1), BLUE));

        pieces.add(new Chariot(new Position(0, 0), BLUE));
        pieces.add(new Chariot(new Position(8, 0), BLUE));

        pieces.add(new Guard(new Position(3, 0), BLUE));
        pieces.add(new Guard(new Position(5, 0), BLUE));
    }

    private static List<Piece> elephantFormation(final BoardSetup boardSetup, final Team team) {
        if (team == RED) {
            return switch (boardSetup) {
                case INNER_ELEPHANT_SETUP -> innerElephantFormationForRed();
                case OUTER_ELEPHANT_SETUP -> outerElephantFormationForRed();
                case LEFT_ELEPHANT_SETUP -> leftElephantFormationForRed();
                case RIGHT_ELEPHANT_SETUP -> rightElephantFormationForRed();
                default -> throw new IllegalArgumentException("존재하지 않는 상차림입니다.");
            };
        } else {
            return switch (boardSetup) {
                case INNER_ELEPHANT_SETUP -> innerElephantFormationForBlue();
                case OUTER_ELEPHANT_SETUP -> outerElephantFormationForBlue();
                case LEFT_ELEPHANT_SETUP -> leftElephantFormationForBlue();
                case RIGHT_ELEPHANT_SETUP -> rightElephantFormationForBlue();
                default -> throw new IllegalArgumentException("존재하지 않는 상차림입니다.");
            };
        }
    }

    private static List<Piece> innerElephantFormationForRed() {
        return List.of(
                new Elephant(new Position(2, 9), RED),
                new Elephant(new Position(6, 9), RED),
                new Horse(new Position(1, 9), RED),
                new Horse(new Position(7, 9), RED)
        );
    }

    private static List<Piece> innerElephantFormationForBlue() {
        return List.of(
                new Elephant(new Position(2, 0), BLUE),
                new Elephant(new Position(6, 0), BLUE),
                new Horse(new Position(1, 0), BLUE),
                new Horse(new Position(7, 0), BLUE)
        );
    }

    private static List<Piece> outerElephantFormationForRed() {
        return List.of(
                new Elephant(new Position(1, 9), RED),
                new Elephant(new Position(7, 9), RED),
                new Horse(new Position(2, 9), RED),
                new Horse(new Position(6, 9), RED)
        );
    }

    private static List<Piece> outerElephantFormationForBlue() {
        return List.of(
                new Elephant(new Position(1, 0), BLUE),
                new Elephant(new Position(7, 0), BLUE),
                new Horse(new Position(2, 0), BLUE),
                new Horse(new Position(6, 0), BLUE)
        );
    }

    private static List<Piece> leftElephantFormationForRed() {
        return List.of(
                new Elephant(new Position(1, 9), RED),
                new Elephant(new Position(6, 9), RED),
                new Horse(new Position(2, 9), RED),
                new Horse(new Position(7, 9), RED)
        );
    }

    private static List<Piece> leftElephantFormationForBlue() {
        return List.of(
                new Elephant(new Position(1, 0), BLUE),
                new Elephant(new Position(6, 0), BLUE),
                new Horse(new Position(2, 0), BLUE),
                new Horse(new Position(7, 0), BLUE)
        );
    }

    private static List<Piece> rightElephantFormationForRed() {
        return List.of(
                new Elephant(new Position(2, 9), RED),
                new Elephant(new Position(7, 9), RED),
                new Horse(new Position(1, 9), RED),
                new Horse(new Position(6, 9), RED)
        );
    }

    private static List<Piece> rightElephantFormationForBlue() {
        return List.of(
                new Elephant(new Position(2, 0), BLUE),
                new Elephant(new Position(7, 0), BLUE),
                new Horse(new Position(1, 0), BLUE),
                new Horse(new Position(6, 0), BLUE)
        );
    }
}
