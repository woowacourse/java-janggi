package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.setup.Arrangements;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Pieces {
    private final Map<Position, Piece> pieces;

    public Pieces(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public static Pieces of(Arrangements arrangements) {
        return new Pieces(PositionLayout.build(arrangements));
    }

    public Pieces move(Position source, Position target) {
        Piece sourcePiece = pieceAtOrThrow(source);

        Map<Position, Piece> updatedPieces = new HashMap<>(pieces);

        updatedPieces.remove(source);
        updatedPieces.put(target, sourcePiece);

        return new Pieces(updatedPieces);
    }

    public Piece pieceAtOrThrow(Position position) {
        return pieceAt(position)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 없습니다: " + position));
    }

    public Optional<Piece> pieceAt(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    public boolean isEmpty(Position position) {
        return pieceAt(position).isEmpty();
    }

    public boolean canOccupy(Position position, Piece piece) {
        return pieceAt(position)
                .filter(piece::isSameTeamAs)
                .isEmpty();
    }

    public boolean hasGeneral(Team team) {
        return count(team, PieceType.GENERAL) > 0;
    }

    public int count(Team team, PieceType pieceType) {
        return (int) pieces.values().stream()
                .filter(piece -> piece.isOwnedBy(team))
                .filter(piece -> piece.getPieceType() == pieceType)
                .count();
    }

    public Pieces snapshot() {
        return new Pieces(pieces);
    }

}
