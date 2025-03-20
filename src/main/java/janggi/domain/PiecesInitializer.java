package janggi.domain;

import static janggi.domain.Team.BLUE;
import static janggi.domain.Team.RED;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import janggi.domain.piece.Soldier;
import java.util.ArrayList;
import java.util.List;

public class PiecesInitializer {

    //TODO: 추후에 사용자 입력에 따라 상차림을 달리 할 수 있게 해야함.
    public static List<Piece> initializePieces() {
        final List<Piece> defaultPieces = new ArrayList<>();

        final Piece redSoldier1 = new Soldier(new Position(0, 6), RED);
        final Piece redSoldier2 = new Soldier(new Position(2, 6), RED);
        final Piece redSoldier3 = new Soldier(new Position(4, 6), RED);
        final Piece redSoldier4 = new Soldier(new Position(6, 6), RED);
        final Piece redSoldier5 = new Soldier(new Position(8, 6), RED);

        final Piece redCannon1 = new Cannon(new Position(1, 7), RED);
        final Piece redCannon2 = new Cannon(new Position(7, 7), RED);

        final Piece redGeneral = new General(new Position(4, 8), RED);

        final Piece redChariot1 = new Chariot(new Position(0, 9), RED);
        final Piece redChariot2 = new Chariot(new Position(8, 9), RED);

        final Piece redGuard1 = new Guard(new Position(3, 9), RED);
        final Piece redGuard2 = new Guard(new Position(5, 9), RED);

        final Piece blueSoldier1 = new Soldier(new Position(0, 3), BLUE);
        final Piece blueSoldier2 = new Soldier(new Position(2, 3), BLUE);
        final Piece blueSoldier3 = new Soldier(new Position(4, 3), BLUE);
        final Piece blueSoldier4 = new Soldier(new Position(6, 3), BLUE);
        final Piece blueSoldier5 = new Soldier(new Position(8, 3), BLUE);

        final Piece blueCannon1 = new Cannon(new Position(1, 2), BLUE);
        final Piece blueCannon2 = new Cannon(new Position(7, 2), BLUE);

        final Piece blueGeneral = new General(new Position(4, 1), BLUE);

        final Piece blueChariot1 = new Chariot(new Position(0, 0), BLUE);
        final Piece blueChariot2 = new Chariot(new Position(8, 0), BLUE);

        final Piece blueGuard1 = new Guard(new Position(3, 0), BLUE);
        final Piece blueGuard2 = new Guard(new Position(5, 0), BLUE);

        defaultPieces.add(redSoldier1);
        defaultPieces.add(redSoldier2);
        defaultPieces.add(redSoldier3);
        defaultPieces.add(redSoldier4);
        defaultPieces.add(redSoldier5);
        defaultPieces.add(redCannon1);
        defaultPieces.add(redCannon2);
        defaultPieces.add(redGeneral);
        defaultPieces.add(redChariot1);
        defaultPieces.add(redChariot2);
        defaultPieces.add(redGuard1);
        defaultPieces.add(redGuard2);

        defaultPieces.add(blueSoldier1);
        defaultPieces.add(blueSoldier2);
        defaultPieces.add(blueSoldier3);
        defaultPieces.add(blueSoldier4);
        defaultPieces.add(blueSoldier5);
        defaultPieces.add(blueCannon1);
        defaultPieces.add(blueCannon2);
        defaultPieces.add(blueGeneral);
        defaultPieces.add(blueChariot1);
        defaultPieces.add(blueChariot2);
        defaultPieces.add(blueGuard1);
        defaultPieces.add(blueGuard2);

        defaultPieces.addAll(innerElephantFormationForRed());
        defaultPieces.addAll(innerElephantFormationForBlue());

        return defaultPieces;
    }

    private static List<Piece> innerElephantFormationForRed() {
        final Piece redElephant1 = new Elephant(new Position(2, 9), RED);
        final Piece redElephant2 = new Elephant(new Position(6, 9), RED);
        final Piece redHorse1 = new Horse(new Position(1, 9), RED);
        final Piece redHorse2 = new Horse(new Position(7, 9), RED);

        return new ArrayList<>(List.of(redElephant1, redElephant2, redHorse1, redHorse2));
    }

    private static List<Piece> innerElephantFormationForBlue() {
        final Piece blueElephant1 = new Elephant(new Position(2, 0), BLUE);
        final Piece blueElephant2 = new Elephant(new Position(6, 0), BLUE);
        final Piece blueHorse1 = new Horse(new Position(1, 0), BLUE);
        final Piece blueHorse2 = new Horse(new Position(7, 0), BLUE);

        return new ArrayList<>(List.of(blueElephant1, blueElephant2, blueHorse1, blueHorse2));
    }

    private static List<Piece> outerElephantFormationForRed() {
        final Piece redElephant1 = new Elephant(new Position(1, 9), RED);
        final Piece redElephant2 = new Elephant(new Position(7, 9), RED);
        final Piece redHorse1 = new Horse(new Position(2, 9), RED);
        final Piece redHorse2 = new Horse(new Position(6, 9), RED);

        return new ArrayList<>(List.of(redElephant1, redElephant2, redHorse1, redHorse2));
    }

    private static List<Piece> outerElephantFormationForBlue() {
        final Piece blueElephant1 = new Elephant(new Position(1, 0), BLUE);
        final Piece blueElephant2 = new Elephant(new Position(7, 0), BLUE);
        final Piece blueHorse1 = new Horse(new Position(2, 0), BLUE);
        final Piece blueHorse2 = new Horse(new Position(6, 0), BLUE);

        return new ArrayList<>(List.of(blueElephant1, blueElephant2, blueHorse1, blueHorse2));
    }

    private static List<Piece> leftElephantFormationForRed() {
        final Piece redElephant1 = new Elephant(new Position(1, 9), RED);
        final Piece redElephant2 = new Elephant(new Position(6, 9), RED);
        final Piece redHorse1 = new Horse(new Position(2, 9), RED);
        final Piece redHorse2 = new Horse(new Position(7, 9), RED);

        return new ArrayList<>(List.of(redElephant1, redElephant2, redHorse1, redHorse2));
    }

    private static List<Piece> leftElephantFormationForBlue() {
        final Piece blueElephant1 = new Elephant(new Position(1, 0), BLUE);
        final Piece blueElephant2 = new Elephant(new Position(6, 0), BLUE);
        final Piece blueHorse1 = new Horse(new Position(2, 0), BLUE);
        final Piece blueHorse2 = new Horse(new Position(7, 0), BLUE);

        return new ArrayList<>(List.of(blueElephant1, blueElephant2, blueHorse1, blueHorse2));
    }

    private static List<Piece> rightElephantFormationForRed(final Team team) {
        final Piece redElephant1 = new Elephant(new Position(2, 9), RED);
        final Piece redElephant2 = new Elephant(new Position(7, 9), RED);
        final Piece redHorse1 = new Horse(new Position(1, 9), RED);
        final Piece redHorse2 = new Horse(new Position(6, 9), RED);

        return new ArrayList<>(List.of(redElephant1, redElephant2, redHorse1, redHorse2));
    }

    private static List<Piece> rightElephantFormationForBlue() {
        final Piece blueElephant1 = new Elephant(new Position(2, 0), BLUE);
        final Piece blueElephant2 = new Elephant(new Position(7, 0), BLUE);
        final Piece blueHorse1 = new Horse(new Position(1, 0), BLUE);
        final Piece blueHorse2 = new Horse(new Position(6, 0), BLUE);

        return new ArrayList<>(List.of(blueElephant1, blueElephant2, blueHorse1, blueHorse2));
    }
}
