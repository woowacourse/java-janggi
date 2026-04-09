package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.team.Team;
import java.util.LinkedHashMap;
import java.util.Map;

public final class BoardGenerator {

    private BoardGenerator() {

    }

    public static Board generate(final Team blueTeam, final Team redTeam) {
        final Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
        positionPieceMap.putAll(redTeam.generatePieces());
        positionPieceMap.putAll(blueTeam.generatePieces());

        return new Board(positionPieceMap);
    }
}
