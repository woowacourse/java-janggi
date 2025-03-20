package domain;

import java.util.ArrayList;
import java.util.List;

import domain.piece.Cannon;
import domain.piece.Piece;

public class Board {

    private final List<Piece> pieces;

    public Board() {
        this.pieces = new ArrayList<>();
    }

    public Board(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void putPieces(final List<Piece> pieces) {
        pieces.forEach(this::putPiece);
    }

    public void putPiece(final Piece piece) {
        pieces.add(piece);
    }

    public boolean isExists(final Position position) {
        return pieces.stream()
                .anyMatch(piece -> piece.isSamePosition(position));
    }

    public boolean isSameTeam(final Piece piece, final Position newPosition) {
        return pieces.stream()
                .anyMatch(p -> p.isSamePosition(newPosition) && p.isSameTeam(piece));
    }

    public void remove(final Position position) {
        int target = -1;
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).isSamePosition(position)) {
                target = i;
                break;
            }
        }
        if (target != -1) {
            pieces.remove(target);
        }
    }

    public boolean isCannon(final Position position) {
        return pieces.stream()
                .filter(piece -> position.equals(piece.getPosition()))
                .anyMatch(Cannon.class::isInstance);
    }

    public Piece findPiece(final Position position) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 기물의 위치를 입력해주세요."));
    }

    public Piece findPiece(final Position position, final Team team) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .filter(piece -> piece.getTeam() == team)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("올바른 기물의 위치를 입력해주세요(현재 턴: %s).", team.name())));
    }

    public List<Piece> getPieces() {
        return pieces;
    }

}
