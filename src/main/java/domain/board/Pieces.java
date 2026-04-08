package domain.board;

import domain.piece.Piece;
import domain.piece.Team;
import domain.setup.Arrangements;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Pieces {
    private final Map<Position, Piece> pieces;

    public Pieces(Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Pieces of(Arrangements arrangements) {
        return new Pieces(PositionLayout.build(arrangements));
    }

    public Pieces move(Position from, Position to) {
        Piece piece = pieces.get(from);
        if (piece == null) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 없습니다: " + from);
        }
        Map<Position, Piece> newMap = new HashMap<>(pieces);
        newMap.remove(from);
        newMap.put(to, piece);
        return new Pieces(newMap);
    }

    public Optional<Piece> at(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    public Position findGeneral(Team team) {
        return pieces.entrySet().stream()
                .filter(e -> e.getValue().isOwnedBy(team) && e.getValue().isGeneral())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 팀의 장군이 없습니다."));
    }

    public Map<Position, Piece> getAllPiecesOf(Team team) {
        return pieces.entrySet().stream()
                .filter(e -> e.getValue().isOwnedBy(team))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Position, Piece> getAll() {
        return Collections.unmodifiableMap(pieces);
    }
}
