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

    public void putPieces(List<Piece> pieces) {
        pieces.forEach(this::putPiece);
    }

    public void putPiece(Piece piece) {
        pieces.add(piece);
    }

    public boolean isExists(Position position) {
        return pieces.stream()
                .anyMatch(piece -> piece.isSamePosition(position));
    }

    public boolean isSameTeam(Piece piece, final Position newPosition) {
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

    public boolean isCannon(Position position) {
        return pieces.stream()
                .filter(piece -> position.equals(piece.getPosition()))
                .anyMatch(Cannon.class::isInstance);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public Piece findPiece(Position position) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 기물의 위치를 입력해주세요."));
    }

    public Piece findPiece(Position position, Team team) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .filter(piece -> piece.getTeam() == team)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("올바른 기물의 위치를 입력해주세요(현재 턴: %s).", team.name())));
    }

    // findPiece에서 팀 받기

    /*
    TODO
    - 사용자에게 입력받은 position예외처리하고, 있으면 기물 반환?
    - Board 뷰에 출력하기
    - Board <-> Piece 의존성 끊어줄 수 있는지 고민해보기
     */
}
