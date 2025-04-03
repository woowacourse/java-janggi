package board;

import direction.Point;
import java.util.ArrayList;
import java.util.List;
import piece.Cannon;
import piece.Chariot;
import piece.Elephant;
import piece.General;
import piece.Guard;
import piece.Horse;
import piece.Piece;
import piece.Soldier;
import team.Team;

public class PiecesInitializer {
    public List<Piece> makeAllPieces() {
        List<Piece> allPieces = new ArrayList<>();

        allPieces.addAll(makeChoPieces());
        allPieces.addAll(makeHanPieces());

        return allPieces;
    }

    private List<Piece> makeHanPieces() {
        List<Piece> hanPieces = new ArrayList<>();

        hanPieces.addAll(makeHanChariots());
        hanPieces.addAll(makeHanElephants());
        hanPieces.addAll(makeHanHorses());
        hanPieces.addAll(makeHanGuards());
        hanPieces.add(makeHanGeneral());
        hanPieces.addAll(makeHanCannons());
        hanPieces.addAll(makeHanSoldiers());

        return hanPieces;
    }

    private List<Piece> makeChoPieces() {
        List<Piece> choPieces = new ArrayList<>();

        choPieces.addAll(makeChoChariots());
        choPieces.addAll(makeChoElephants());
        choPieces.addAll(makeChoHorses());
        choPieces.addAll(makeChoGuards());
        choPieces.addAll(makeChoGeneral());
        choPieces.addAll(makeChoCannons());
        choPieces.addAll(makeChoSoldiers());

        return choPieces;
    }

    private List<Piece> makeHanSoldiers() {
        return List.of(
                new Soldier(Team.HAN, new Point(1, 4)),
                new Soldier(Team.HAN, new Point(3, 4)),
                new Soldier(Team.HAN, new Point(5, 4)),
                new Soldier(Team.HAN, new Point(7, 4)),
                new Soldier(Team.HAN, new Point(9, 4))
        );
    }

    private List<Piece> makeHanCannons() {
        return List.of(
                new Cannon(Team.HAN, new Point(2, 3)),
                new Cannon(Team.HAN, new Point(8, 3))
        );
    }

    private Piece makeHanGeneral() {
        return new General(Team.HAN, new Point(5, 2));
    }

    private List<Piece> makeHanGuards() {
        return List.of(
                new Guard(Team.HAN, new Point(4, 1)),
                new Guard(Team.HAN, new Point(6, 1))
        );
    }

    private List<Piece> makeHanHorses() {
        return List.of(
                new Horse(Team.HAN, new Point(2, 1)),
                new Horse(Team.HAN, new Point(8, 1))
        );
    }

    private List<Piece> makeHanElephants() {
        return List.of(
                new Elephant(Team.HAN, new Point(3, 1)),
                new Elephant(Team.HAN, new Point(7, 1))
        );
    }

    private List<Piece> makeHanChariots() {
        return List.of(
                new Chariot(Team.HAN, new Point(1, 1)),
                new Chariot(Team.HAN, new Point(9, 1))
        );
    }

    private List<Piece> makeChoChariots() {
        return List.of(
                new Chariot(Team.CHO, new Point(1, 10)),
                new Chariot(Team.CHO, new Point(9, 10))
        );
    }

    private List<Piece> makeChoElephants() {
        return List.of(
                new Elephant(Team.CHO, new Point(2, 10)),
                new Elephant(Team.CHO, new Point(7, 10))
        );
    }

    private List<Piece> makeChoHorses() {
        return List.of(
                new Horse(Team.CHO, new Point(3, 10)),
                new Horse(Team.CHO, new Point(8, 10))
        );
    }

    private List<Piece> makeChoGuards() {
        return List.of(
                new Guard(Team.CHO, new Point(4, 10)),
                new Guard(Team.CHO, new Point(6, 10))
        );
    }

    private List<Piece> makeChoGeneral() {
        return List.of(
                new General(Team.CHO, new Point(5, 9))
        );
    }

    private List<Piece> makeChoCannons() {
        return List.of(
                new Cannon(Team.CHO, new Point(2, 8)),
                new Cannon(Team.CHO, new Point(8, 8))
        );
    }

    private List<Piece> makeChoSoldiers() {
        return List.of(
                new Soldier(Team.CHO, new Point(1, 7)),
                new Soldier(Team.CHO, new Point(3, 7)),
                new Soldier(Team.CHO, new Point(5, 7)),
                new Soldier(Team.CHO, new Point(7, 7)),
                new Soldier(Team.CHO, new Point(9, 7))
        );
    }
}
