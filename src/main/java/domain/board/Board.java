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
        validateExistPiece(from);
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

    public boolean isOpposite(final Position position1, final Position position2) {
        validateExistPiece(position1);
        validateExistPiece(position2);
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

    public boolean isCannon(final Position position) {
        if (isEmpty(position)) {
            return false;
        }
        return pieces.get(position).isCannon();
    }

    public PieceType getPieceType(final Position position) {
        validateExistPiece(position);
        return pieces.get(position).getPieceType();
    }

    public Team getTeam(final Position position) {
        validateExistPiece(position);
        return pieces.get(position).getTeam();
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }

    private void validateExistPiece(final Position position) {
        if (isEmpty(position)) {
            throw new IllegalArgumentException("해당 좌표에 기물이 존재하지 않습니다.");
        }
    }

    public boolean isOnlyOneGeneralRemaining() {
        int generalCount = Math.toIntExact(pieces.values().stream()
                .filter(Piece::isGeneral)
                .count());

        return generalCount == 1;
    }

    public double getScoreBy(final Team team) {
        double score = pieces.values().stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToDouble(Piece::getScore)
                .sum();

        score += team.getBonusScore();
        return score;
    }
}
