package domain.board;

import domain.piece.Piece;
import domain.piece.Team;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Board {

    private final Map<Position, Piece> pieces;

    private Board(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board init(final Map<Position, Piece> pieces) {
        return new Board(pieces);
    }

    public void move(Position from, Position to) {
        Piece piece = pieces.remove(from);
        pieces.put(to, piece);
    }

    public List<Position> getPiecePositionsFor(Team team) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().isSameTeam(team))
                .map(Entry::getKey)
                .toList();
    }

    public boolean isEmptyOrOpposite(Position from, Position to) {
        if (!pieces.containsKey(from)) {
            throw new IllegalArgumentException("해당 좌표에 기물이 존재하지 않음");
        }
        return !pieces.containsKey(to) || pieces.get(from).isOpposite(pieces.get(to));
    }

    public Position getAnotherGeneralPosition(Position generalPosition) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().isGeneral())
                .filter(entry -> !entry.getKey().equals(generalPosition))
                .map(Entry::getKey)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("다른 왕이 존재하지 않습니다."));
    }

    public Piece getPieceAt(final Position position) {
        return pieces.get(position);
    }

    public Team getTeam(Position position) {
        if (!pieces.containsKey(position)) {
            throw new IllegalArgumentException("해당 좌표에 기물이 존재하지 않음");
        }
        return pieces.get(position).getTeam();
    }

    public boolean isEmpty(Position position) {
        return !pieces.containsKey(position);
    }

    public Map<Position, Piece> getPieces() {
        return pieces;
    }
}
