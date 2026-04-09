package janggi.domain.team;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.setup.SetupPolicy;
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
    public boolean hasPiece(final Piece piece) {
        return TEAM_TYPE == piece.getTeamType();
    }

    @Override
    public TeamType getTeamType() {
        return TEAM_TYPE;
    }

    @Override
    public String getName() {
        return TEAM_TYPE.getName();
    }
}
