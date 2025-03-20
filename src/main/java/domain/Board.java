package domain;

import java.util.ArrayList;
import java.util.List;

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
        int target = 0;
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).isSamePosition(position)) {
                target = i;
                break;
            }
        }
        pieces.remove(target);
    }

    public boolean isCannon(Position position) {
        return pieces.stream()
                .filter(piece -> position.equals(piece.getPosition()))
                .anyMatch(Cannon.class::isInstance);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    /*
    TODO
    - 사용자에게 입력받은 position예외처리하고, 있으면 기물 반환?
    - Board 뷰에 출력하기
    - Board <-> Piece 의존성 끊어줄 수 있는지 고민해보기
     */
}
