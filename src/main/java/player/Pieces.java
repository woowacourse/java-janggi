package player;

import java.util.ArrayList;
import piece.Piece;
import java.util.List;
import pieceProperty.Position;

public class Pieces {
    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public void removePiece(Position destination) {
        pieces.stream()
                .filter(piece -> piece.isSamePosition(destination))
                .findFirst()
                .ifPresent(pieces::remove);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public boolean isKingDie() {
        return pieces.stream()
                .noneMatch(Piece::isKing);
    }

    public void validateOurTeam(Position presentPosition) {
        pieces.stream()
                .filter(piece -> piece.isSamePosition(presentPosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 움직일 수 있는 기물이 위치하지 않습니다."));
    }
}
