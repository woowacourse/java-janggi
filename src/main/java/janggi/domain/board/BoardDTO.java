package janggi.domain.board;

import janggi.dto.PieceDTO;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public record BoardDTO(Map<Position, PieceDTO> piecePosition) {
    public BoardDTO {
        piecePosition = Collections.unmodifiableMap(piecePosition);
    }

    public static BoardDTO from(Board board) {
        Map<Position, PieceDTO> converted = board.getPiecePosition()
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> PieceDTO.from(e.getValue())
                ));

        return new BoardDTO(converted);
    }
}
