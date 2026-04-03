package infrastructure.repository;

import domain.board.Position;
import domain.piece.Piece;
import domain.piece.Team;
import domain.setup.Arrangement;
import java.util.Map;

public record GameDto(
        long gameId,
        String stateName,
        Team currentTeam,
        Arrangement hanArrangement,
        Arrangement choArrangement,
        Map<Position, Piece> pieces
) {}
