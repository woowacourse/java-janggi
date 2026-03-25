package janggi.domain.team;

import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.SetupPolicy;
import janggi.domain.piece.Piece;
import java.util.LinkedHashMap;
import java.util.Map;

public class BlueTeam implements Team {

    private static final TeamType TEAM_TYPE = TeamType.BLUE;
    private final SetupPolicy setupPolicy;

    public BlueTeam(final SetupPolicy setupPolicy) {
        this.setupPolicy = setupPolicy;
    }

    @Override
    public Map<Position, Piece> generatePieces() {
        final Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
        final Map<Position, PieceType> positionPieceTypeMap = setupPolicy.offerBoardMap();
        positionPieceTypeMap.forEach((position, pieceType) ->
                positionPieceMap.put(position.flipAroundMiddleRow(), pieceType.toPiece(TEAM_TYPE)));

        return positionPieceMap;
    }

    @Override
    public String getName() {
        return TEAM_TYPE.getName();
    }

    @Override
    public boolean hasPiece(final Piece piece) {
        return piece.belongsToTeam(TEAM_TYPE);
    }
}
