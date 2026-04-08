package infrastructure.repository;

import domain.board.Position;
import domain.piece.Piece;
import domain.piece.Team;
import domain.setup.Arrangement;
import java.util.Map;
import java.util.Optional;

public record GameDto(
        long gameId,
        String stateName,
        Team currentTeam,
        Optional<Arrangement> hanArrangement,
        Optional<Arrangement> choArrangement,
        Map<Position, Piece> pieces
) {}
