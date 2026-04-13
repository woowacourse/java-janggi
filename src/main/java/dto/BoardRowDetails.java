package dto;

import domain.board.Board;

import java.util.List;

public record BoardRowDetails(List<BoardRowDetail> boardRowDetails) {

    public static BoardRowDetails from(Board board) {
        List<BoardRowDetail> boardRowDetails = board.getBoard().entrySet().stream()
                .map(entry -> new BoardRowDetail(
                        entry.getKey().coordination().get(0),
                        entry.getKey().coordination().get(1),
                        entry.getValue().pieceType().name(),
                        entry.getValue().team().name()
                ))
                .toList();
        return new BoardRowDetails(boardRowDetails);
    }
}
