package janggi.domain.team;

import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.SetupPolicy;
import janggi.domain.piece.Piece;
import java.util.LinkedHashMap;
import java.util.Map;

public class RedTeam implements Team {

    private static final TeamType TEAM_TYPE = TeamType.RED;
    private final SetupPolicy setupPolicy;


    public RedTeam(final SetupPolicy setupPolicy) {
        this.setupPolicy = setupPolicy;
    }

    @Override
    public Map<Position, Piece> generatePieces() {
        final Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
        final Map<Position, PieceType> positionPieceTypeMap = setupPolicy.offerBoardMap();
        positionPieceTypeMap.forEach((position, pieceType) ->
            positionPieceMap.put(position, pieceType.toPiece(TEAM_TYPE)));

        return positionPieceMap;
    }
}
