package domain.board;

import domain.game.Team;
import domain.piece.EmptyPiece;
import domain.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
        fillEmptyPositions();
    }

    private void fillEmptyPositions() {
        Position.allPositions()
                .forEach(pos -> pieces.putIfAbsent(pos, EmptyPiece.getInstance()));
    }

    public void move(Position source, Position destination) {
        Piece piece = pieceAt(source);
        piece.validateMove(source, destination, this::pieceAt);
        applyMove(source, destination, piece);
    }

    private void applyMove(Position source, Position destination, Piece piece) {
        pieces.put(destination, piece);
        pieces.put(source, EmptyPiece.getInstance());
    }

    public Piece pieceAt(Position position) {
        return pieces.get(position);
    }

    public List<Piece> findPiecesByTeam(Team team) {
        return pieces.values().stream()
                .filter(piece -> piece.belongsTo(team))
                .toList();
    }

    public boolean hasGeneral(Team team) {
        return pieces.values().stream()
                .anyMatch(piece -> piece.belongsTo(team) && piece.isGeneral());
    }

    public Position findGeneralPosition(Team team) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().belongsTo(team) && entry.getValue().isGeneral())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("궁이 존재하지 않습니다."));
    }

    public boolean hasNoPieceBetween(Position source, Position target) {
        List<Position> route = source.makeRowStraightRoute(target);
        return route.stream().noneMatch(pos -> pieceAt(pos).isNotEmpty());
    }
}
