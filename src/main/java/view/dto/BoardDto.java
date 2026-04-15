package view.dto;

import domain.coordination.Coordination;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import view.PieceName;

public record BoardDto(Map<List<Integer>, String> board) {

    public static BoardDto from(Map<Coordination, Piece> board) {
        Map<List<Integer>, String> result = board.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().coordination(),
                        entry -> PieceName.from(entry.getValue())
                ));
        return new BoardDto(result);
    }
}
