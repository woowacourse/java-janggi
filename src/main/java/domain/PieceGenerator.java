package domain;

import domain.pieces.Cannon;
import domain.pieces.Chariot;
import domain.pieces.Elephant;
import domain.pieces.General;
import domain.pieces.Guard;
import domain.pieces.Horse;
import domain.pieces.Piece;
import domain.pieces.Soldier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieceGenerator {

    public static Map<Position, Piece> generatePieces(Camp camp, ElephantFormation elephantFormation) {
        Map<Position, Piece> board = new HashMap<>();
        board.putAll(generatePiece(camp));
        board.putAll(generatePiece(camp, elephantFormation));

        return board;
    }

    private static Map<Position, Piece> generatePiece(Camp camp) {
        Map<Position, Piece> board = new HashMap<>();
        board.putAll(generateGeneral(camp));
        board.putAll(generateSoldier(camp));
        board.putAll(generateGuard(camp));
        board.putAll(generateCannon(camp));
        board.putAll(generateChariot(camp));

        return board;
    }

    private static Map<Position, Piece> generatePiece(Camp camp, ElephantFormation elephantFormation) {
        Map<Position, Piece> board = new HashMap<>();
        board.putAll(generateHorse(camp, elephantFormation));
        board.putAll(generateElephant(camp, elephantFormation));

        return board;
    }

    private static Map<Position, Piece> generateGeneral(Camp camp) {
        Map<Position, Piece> generalPosition = new HashMap<>();

        List<Position> positions = InitialPieceLocation.GENERAL.getPositions(camp);
        positions.forEach(position -> generalPosition.put(position, new General(camp)));

        return generalPosition;
    }

    private static Map<Position, Piece> generateSoldier(Camp camp) {
        Map<Position, Piece> soldierPosition = new HashMap<>();
        List<Position> positions = InitialPieceLocation.SOLDIER.getPositions(camp);
        positions.forEach(position -> soldierPosition.put(position, new Soldier(camp)));

        return soldierPosition;
    }

    private static Map<Position, Piece> generateGuard(Camp camp) {
        Map<Position, Piece> guardPosition = new HashMap<>();
        List<Position> positions = InitialPieceLocation.GUARD.getPositions(camp);
        positions.forEach(position -> guardPosition.put(position, new Guard(camp)));

        return guardPosition;
    }

    private static Map<Position, Piece> generateHorse(Camp camp, ElephantFormation elephantFormation) {
        Map<Position, Piece> horsePosition = new HashMap<>();
        List<Position> positions = InitialPieceLocation.HORSE.getPositions(camp, elephantFormation);
        positions.forEach(position -> horsePosition.put(position, new Horse(camp)));

        return horsePosition;
    }

    private static Map<Position, Piece> generateCannon(Camp camp) {
        Map<Position, Piece> cannonPosition = new HashMap<>();
        List<Position> positions = InitialPieceLocation.CANNON.getPositions(camp);
        positions.forEach(position -> cannonPosition.put(position, new Cannon(camp)));

        return cannonPosition;
    }

    private static Map<Position, Piece> generateElephant(Camp camp, ElephantFormation elephantFormation) {
        Map<Position, Piece> elephantPosition = new HashMap<>();
        List<Position> positions = InitialPieceLocation.ELEPHANT.getPositions(camp, elephantFormation);
        positions.forEach(position -> elephantPosition.put(position, new Elephant(camp)));

        return elephantPosition;
    }

    private static Map<Position, Piece> generateChariot(Camp camp) {
        Map<Position, Piece> chariotPosition = new HashMap<>();
        List<Position> positions = InitialPieceLocation.CHARIOT.getPositions(camp);
        positions.forEach(position -> chariotPosition.put(position, new Chariot(camp)));

        return chariotPosition;
    }
}
