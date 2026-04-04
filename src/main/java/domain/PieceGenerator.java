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
import java.util.stream.Collectors;

public class PieceGenerator {

    public Map<Position, Piece> generateInitialPieces(Camp camp,
            ElephantFormation elephantFormation) {
        Map<Position, Piece> board = new HashMap<>();
        board.putAll(generatePieces(camp, PieceType.GENERAL, InitialPositions.GENERAL_POSITIONS));
        board.putAll(generatePieces(camp, PieceType.SOLDIER, InitialPositions.SOLDIER_POSITIONS));
        board.putAll(generatePieces(camp, PieceType.GUARD, InitialPositions.GUARD_POSITIONS));
        board.putAll(generatePieces(camp, PieceType.CANNON, InitialPositions.CANNON_POSITIONS));
        board.putAll(generatePieces(camp, PieceType.CHARIOT, InitialPositions.CHARIOT_POSITIONS));
        board.putAll(generatePieces(camp, elephantFormation));
        return board;
    }

    private Piece generatePieceByPieceType(Camp camp, PieceType pieceType) {
        if (pieceType == PieceType.SOLDIER) {
            return new Soldier(camp);
        }
        if (pieceType == PieceType.GUARD) {
            return new Guard(camp);
        }
        if (pieceType == PieceType.CANNON) {
            return new Cannon(camp);
        }
        if (pieceType == PieceType.CHARIOT) {
            return new Chariot(camp);
        }
        return new General(camp);
    }

    private Map<Position, Piece> generatePieces(Camp camp, PieceType pieceType,
            List<Position> positions) {
        Map<Position, Piece> piecePositions = new HashMap<>();
        List<Position> finalPositions = getFinalPositionByTeam(camp, positions);
        for (Position position : finalPositions) {
            piecePositions.put(position, generatePieceByPieceType(camp, pieceType));
        }
        return piecePositions;
    }

    private Map<Position, Piece> generatePieces(Camp camp, ElephantFormation elephantFormation) {
        return getFinalPositionByFormation(camp, elephantFormation);
    }

    private List<Position> getFinalPositionByTeam(Camp camp, List<Position> positions) {
        if (camp == Camp.CHO) {
            return rotatePositions(positions);
        }
        return positions;
    }

    private Map<Position, Piece> getFinalPositionByFormation(Camp camp,
            ElephantFormation elephantFormation) {
        Map<Position, Piece> piecePositions = new HashMap<>();
        List<Position> positions = getFinalPositionByTeam(camp,
                InitialPositions.HORSE_ELEPHANT_POSITIONS);
        if (camp == Camp.CHO) {
            elephantFormation = rotateFormation(elephantFormation);
        }
        if (elephantFormation == ElephantFormation.OUTER) {
            piecePositions.put(positions.get(0), new Elephant(camp));
            piecePositions.put(positions.get(1), new Horse(camp));
            piecePositions.put(positions.get(2), new Horse(camp));
            piecePositions.put(positions.get(3), new Elephant(camp));
        }
        if (elephantFormation == ElephantFormation.INNER) {
            piecePositions.put(positions.get(0), new Horse(camp));
            piecePositions.put(positions.get(1), new Elephant(camp));
            piecePositions.put(positions.get(2), new Elephant(camp));
            piecePositions.put(positions.get(3), new Horse(camp));
        }
        if (elephantFormation == ElephantFormation.RIGHT) {
            piecePositions.put(positions.get(0), new Elephant(camp));
            piecePositions.put(positions.get(1), new Horse(camp));
            piecePositions.put(positions.get(2), new Elephant(camp));
            piecePositions.put(positions.get(3), new Horse(camp));
        }
        if (elephantFormation == ElephantFormation.LEFT) {
            piecePositions.put(positions.get(0), new Horse(camp));
            piecePositions.put(positions.get(1), new Elephant(camp));
            piecePositions.put(positions.get(2), new Horse(camp));
            piecePositions.put(positions.get(3), new Elephant(camp));
        }
        return piecePositions;
    }

    private ElephantFormation rotateFormation(ElephantFormation elephantFormation) {
        if (elephantFormation == ElephantFormation.LEFT) {
            return ElephantFormation.RIGHT;
        }
        if (elephantFormation == ElephantFormation.RIGHT) {
            return ElephantFormation.LEFT;
        }
        if (elephantFormation == ElephantFormation.OUTER) {
            return ElephantFormation.OUTER;
        }
        return ElephantFormation.INNER;
    }

    private List<Position> rotatePositions(List<Position> positions) {
        return positions.stream().map(Position::reverseRow)
                .collect(Collectors.toList());
    }
}
