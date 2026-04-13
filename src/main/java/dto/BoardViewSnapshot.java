package dto;

import domain.board.Board;
import domain.coordination.Coordination;
import domain.piece.Piece;
import util.PieceName;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record BoardViewSnapshot(Map<List<Integer>, String> board) {

    public static BoardViewSnapshot from(Board board) {
        Map<Coordination, Piece> boardMap = board.getBoard();
        Map<List<Integer>, String> result = boardMap.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().coordination(),
                        entry -> PieceName.display(entry.getValue().pieceType(), entry.getValue().team())
                ));
        return new BoardViewSnapshot(result);
    }
}
