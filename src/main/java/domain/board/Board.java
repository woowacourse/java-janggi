package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import java.util.Collections;
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

    public void move(final Position from, final Position to) {
        Piece piece = pieces.remove(from);
        pieces.put(to, piece);
    }

    public List<Position> getPositionsByTeam(final Team team) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().isSameTeam(team))
                .map(Entry::getKey)
                .toList();
    }

    public boolean isEmpty(final Position position) {
        return !pieces.containsKey(position);
    }

    public boolean isOpposite(Position position1, Position position2) {
        if (!pieces.containsKey(position1) || !pieces.containsKey(position2)) {
            throw new IllegalArgumentException("해당 좌표에 기물이 존재하지 않습니다.");
        }
        return pieces.get(position1).isOpposite(pieces.get(position2));
    }

    public boolean isEmptyOrOpposite(final Position from, final Position to) {
        return isEmpty(to) || isOpposite(from, to);
    }

    public Position getAnotherGeneralPosition(final Position generalPosition) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().isGeneral())
                .filter(entry -> !entry.getKey().equals(generalPosition))
                .map(Entry::getKey)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("다른 왕이 존재하지 않습니다."));
    }

    public boolean isCannon(Position position) {
        if (!pieces.containsKey(position)) {
            return false;
        }
        return pieces.get(position).isCannon();
    }

    public PieceType getPieceType(Position position) {
        if (!pieces.containsKey(position)) {
            throw new IllegalArgumentException("해당 좌표에 기물이 존재하지 않습니다.");
        }
        return pieces.get(position).getPieceType();
    }

    public Team getTeam(final Position position) {
        if (!pieces.containsKey(position)) {
            throw new IllegalArgumentException("해당 좌표에 기물이 존재하지 않습니다.");
        }
        return pieces.get(position).getTeam();
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }
}
