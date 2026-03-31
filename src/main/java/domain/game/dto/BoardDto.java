package domain.game.dto;

import domain.coordination.Coordination;
import domain.piece.Piece;
import view.PieceName;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record BoardDto(Map<List<Integer>, String> board) {

    public static BoardDto from(Map<Coordination, Piece> board) {
        Map<List<Integer>, String> result = board.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().coordination(),
                        entry -> PieceName.from(entry.getValue().getClass().getSimpleName(), entry.getValue().team())
                ));
        return new BoardDto(result);
    }
}
