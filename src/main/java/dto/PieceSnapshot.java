package dto;

import domain.board.Board;

import java.util.List;

public record PieceSnapshot(int column, int row, String pieceType, String team) {

    public static List<PieceSnapshot> from(Board board) {
        return board.getBoard().entrySet().stream()
                .map(entry -> new PieceSnapshot(
                        entry.getKey().coordination().get(0),
                        entry.getKey().coordination().get(1),
                        entry.getValue().pieceType().name(),
                        entry.getValue().team().name()
                ))
                .toList();
    }
}
