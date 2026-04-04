package janggi.domain.team;

import janggi.domain.Position;
import janggi.domain.board.setup.ElephantFormation;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.LinkedHashMap;
import java.util.Map;

public class RedTeam implements Team {

    private static final TeamType TEAM_TYPE = TeamType.RED;
    private final ElephantFormation elephantFormation;


    public RedTeam(final ElephantFormation elephantFormation) {
        this.elephantFormation = elephantFormation;
    }

    @Override
    public Map<Position, Piece> generatePieces() {
        final Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
        final Map<Position, PieceType> positionPieceTypeMap = elephantFormation.offerBoardMap();
        positionPieceTypeMap.forEach((position, pieceType) ->
                positionPieceMap.put(position, pieceType.toPiece(TEAM_TYPE)));

        return positionPieceMap;
    }

    @Override
    public String getName() {
        return TEAM_TYPE.getName();
    }
}
