package janggi.factory.horse_elephant;

import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceBehavior;
import janggi.domain.piece.behavior.rotatemove.Elephant;
import janggi.domain.piece.behavior.rotatemove.Horse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class HorseElephantPlacement {

    public Map<Position, Piece> generate(Team team) {
        Map<Position, Piece> pieceMap = new HashMap<>();

        pieceMap.putAll(placePieces(getHorsePositionByCho(), team, Horse::new));
        pieceMap.putAll(placePieces(getElephantPositionByCho(), team, Elephant::new));

        return pieceMap;
    }

    private Map<Position, Piece> placePieces(Set<Position> positions, Team team, Supplier<PieceBehavior> supplier) {
        Map<Position, Piece> pieceMap = new HashMap<>();

        for (Position position : parsePositionsBySide(positions, team)) {
            Piece piece = new Piece(team, supplier.get());
            pieceMap.put(position, piece);
        }

        return pieceMap;
    }

    private Set<Position> parsePositionsBySide(Set<Position> positions, Team team) {
        if (team.isSameSide(Team.CHO)) {
            return positions;
        }

        return positions.stream()
                .map(Position::changeToReverseSide)
                .collect(Collectors.toUnmodifiableSet());
    }

    protected abstract Set<Position> getHorsePositionByCho();

    protected abstract Set<Position> getElephantPositionByCho();
}
