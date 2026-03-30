package domain;

import java.util.List;

public record JanggiBoardDTO(List<PieceDTO> janggiBoardDto) {
    public static  JanggiBoardDTO from(JanggiBoard janggiBoard) {
        List<PieceDTO> pieceDtos = janggiBoard.getJanggiBoard().entrySet().stream()
                .map(entry -> PieceDTO.from(entry.getKey(), entry.getValue()))
                .toList();

        return new JanggiBoardDTO(pieceDtos);
    }
}
