package domain;

import domain.pieces.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PieceGenerator {

    private final Map<Integer, ElephantFormation> INPUT_FORMATION_MAPPER = new HashMap<>(
            Map.of(1, ElephantFormation.RIGHT, 2, ElephantFormation.INNER, 3,
                    ElephantFormation.LEFT, 4, ElephantFormation.OUTER));

    public Map<Position, Piece> generateInitialPieces(Camp camp, int elephantFormation) {
        Map<Position, Piece> board = new HashMap<>();
        board.putAll(generatePieces(camp, PieceType.GENERAL));
        board.putAll(generatePieces(camp, PieceType.SOLDIER));
        board.putAll(generatePieces(camp, PieceType.GUARD));
        board.putAll(generatePieces(camp, PieceType.CANNON));
        board.putAll(generatePieces(camp, PieceType.CHARIOT));
        board.putAll(generatePieces(camp, INPUT_FORMATION_MAPPER.get(elephantFormation)));
        return board;
    }

    private Map<Position, Piece> generatePieces(Camp camp, PieceType pieceType) {
        Map<Position, Piece> piecePositions = new HashMap<>();
        List<Position> finalPositions = getFinalPositionByCamp(camp, pieceType.getInitialPositions());
        for (Position position : finalPositions) {
            piecePositions.put(position, pieceType.create(camp));
        }
        return piecePositions;
    }

    private Map<Position, Piece> generatePieces(Camp camp, ElephantFormation elephantFormation) {
        return getFinalPositionByFormation(camp, elephantFormation);
    }

    private List<Position> getFinalPositionByCamp(Camp camp, List<Position> positions) {
        if (camp == Camp.CHO) {
            return rotatePositions(positions);
        }
        return positions;
    }

    private Map<Position, Piece> getFinalPositionByFormation(Camp camp,
            ElephantFormation elephantFormation) {
        Map<Position, Piece> piecePositions = new HashMap<>();
        elephantFormation = adjustElephantFormationByCamp(camp, elephantFormation);
        List<Position> positions = getFinalPositions(camp);

        List<PieceType> formations = elephantFormation.getElephantFormation();

        for (int i = 0; i < formations.size(); i++) {
            piecePositions.put(positions.get(i), formations.get(i).create(camp));
        }
        return piecePositions;
    }

    private List<Position> getFinalPositions(Camp camp) {
        List<Position> positions = getFinalPositionByCamp(camp,
                List.of(new Position(1, 0), new Position(2, 0), new Position(6, 0),
                        new Position(7, 0)));
        return positions;
    }

    private ElephantFormation adjustElephantFormationByCamp(Camp camp,
            ElephantFormation elephantFormation) {
        if (camp == Camp.CHO) {
            elephantFormation = rotateFormation(elephantFormation);
        }
        return elephantFormation;
    }


    private ElephantFormation rotateFormation(ElephantFormation elephantFormation) {
        if (elephantFormation == ElephantFormation.LEFT) {
            return ElephantFormation.RIGHT;
        }
        if (elephantFormation == ElephantFormation.RIGHT) {
            return ElephantFormation.LEFT;
        }
        return elephantFormation;
    }

    private List<Position> rotatePositions(List<Position> positions) {
        return positions.stream().map(Position::reverseRow).collect(Collectors.toList());
    }
}
