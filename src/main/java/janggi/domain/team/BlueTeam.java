package janggi.domain.team;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.setup.ElephantFormation;
import java.util.LinkedHashMap;
import java.util.Map;

public class BlueTeam implements Team {

    private static final TeamType TEAM_TYPE = TeamType.BLUE;
    private final ElephantFormation elephantFormation;

    public BlueTeam(final ElephantFormation elephantFormation) {
        this.elephantFormation = elephantFormation;
    }

    @Override
    public Map<Position, Piece> generatePieces() {
        final Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
        final Map<Position, PieceType> positionPieceTypeMap = elephantFormation.offerBoardMap();
        positionPieceTypeMap.forEach((position, pieceType) ->
                positionPieceMap.put(position.flipAroundMiddleRow(), pieceType.toPiece(TEAM_TYPE)));

        return positionPieceMap;
    }

    @Override
    public String getName() {
        return TEAM_TYPE.getName();
    }
}
