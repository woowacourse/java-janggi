package model.board;

import model.coordinate.Position;
import model.game.Team;
import model.piece.Piece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board {

    public static final int BOARD_ROW = 10;
    public static final int BOARD_COL = 9;

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = new HashMap<>(board);
    }

    public Optional<Piece> movePiece(Position current, Position next) {
        Piece currentPiece = pickPiece(current);
        Piece capturedPiece = board.get(next);
        if (capturedPiece != null) {
            validateAlly(currentPiece, capturedPiece);
        }

        board.remove(current);
        board.put(next, currentPiece);
        return Optional.ofNullable(capturedPiece);
    }

    public Piece pickPiece(Position position) {
        if (!hasPieceAt(position)) {
            throw new IllegalArgumentException("해당 위치에 존재하는 장기말이 없습니다.");
        }
        return board.get(position);
    }

    private boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }

    private void validateAlly(Piece piece, Piece otherPiece) {
        if (piece.isSameTeam(otherPiece)) {
            throw new IllegalArgumentException("해당 위치는 아군이 존재하는 위치입니다.");
        }
    }

    public Map<Position, Piece> getBoardMap() {
        return Map.copyOf(board);
    }

    public double calculateScore(Team team) {
        return board.values().stream()
                .filter(piece -> !piece.isEnemy(team))
                .mapToDouble(Piece::getScore)
                .sum();
    }

    public Route createRoute(List<Position> positions) {
        List<Piece> pieces = positions.stream()
                .filter(this::hasPieceAt)
                .map(this::pickPiece)
                .toList();
        return new Route(pieces);
    }
}
