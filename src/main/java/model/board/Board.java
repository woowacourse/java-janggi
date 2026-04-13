package model.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import model.Team;
import model.coordinate.Position;
import model.piece.Piece;
import model.piece.PieceType;

public class Board {

    public static final int BOARD_ROW = 10;
    public static final int BOARD_COL = 9;

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public void move(Position current, Position next) {
        Piece piece = pickPiece(current);
        findByPosition(next).ifPresent(piece::validateTarget);

        board.remove(current);
        board.put(next, piece);
    }

    public Piece pickPiece(Position position) {
        return findByPosition(position)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 존재하는 장기말이 없습니다."));
    }

    public boolean isAliveGeneral(Team team) {
        return board.values()
                .stream()
                .anyMatch(piece -> isTargetGeneral(piece, team));
    }

    public void arrangePieces(Map<Position, Piece> pieces) {
        board.putAll(pieces);
    }

    public List<Piece> extractPiecesByPath(List<Position> path) {
        return path.stream()
                .filter(this::hasPieceAt)
                .map(this::pickPiece)
                .toList();
    }

    public Position findGeneralPositionByTeam(Team team) {
        return board.entrySet()
                .stream()
                .filter(data -> isTargetGeneral(data.getValue(), team))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(team.getName() + "의 왕이 없습니다."));
    }

    public double calculateBaseScore(Team team) {
        return board.values()
                .stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToDouble(Piece::score)
                .sum();
    }

    private boolean isTargetGeneral(Piece piece, Team team) {
        return piece.isSameType(PieceType.GENERAL) && piece.isSameTeam(team);
    }

    private Optional<Piece> findByPosition(Position position) {
        return Optional.ofNullable(board.get(position));
    }

    private boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }

    public Map<Position, Piece> board() {
        return Map.copyOf(board);
    }
}
