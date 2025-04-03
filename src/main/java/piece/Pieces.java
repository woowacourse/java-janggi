package piece;

import java.util.ArrayList;
import java.util.Collections;
import location.Position;
import java.util.List;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void add(Piece piece) {
        pieces.add(piece);
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }

    public Piece getByPosition(Position targetPosition) {
        return pieces.stream()
                .filter(piece -> piece.isPlacedAt(targetPosition))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));
    }

    public boolean isContainedPieceAtPosition(Position targetPosition) {
        return pieces.stream()
                .anyMatch(piece -> piece.isPlacedAt(targetPosition));
    }

    public void checkNotExistedPieceInPosition(Position position) {
        if (isContainedPieceAtPosition(position)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }

    public int calculateTotalScore() {
        return pieces.stream()
                .mapToInt(Piece::getScore)
                .sum();
    }
}
