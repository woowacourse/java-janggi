package janggi.domain.team;

import janggi.domain.Position;
import janggi.domain.board.setup.ElephantFormation;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.LinkedHashMap;
import java.util.Map;

public class Team {
    private final TeamType teamType;
    private final ElephantFormation elephantFormation;

    public Team(final TeamType teamType, final ElephantFormation elephantFormation) {
        this.teamType = teamType;
        this.elephantFormation = elephantFormation;
    }

    public Map<Position, Piece> generatePieces() {
        final Map<Position, PieceType> positionPieceTypeMap = elephantFormation.offerBoardMap();
        return createPositionPieceMap(positionPieceTypeMap);
    }

    public String getName() {
        return teamType.getName();
    }

    private Map<Position, Piece> createPositionPieceMap(Map<Position, PieceType> positionPieceTypeMap) {
        final Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
        positionPieceTypeMap.forEach((position, pieceType) ->
                positionPieceMap.put(teamType.adjustPosition(position), pieceType.toPiece(teamType)));
        return positionPieceMap;
    }
}
