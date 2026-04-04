package domain.dto;

import java.util.List;

public record BoardDto(List<List<PieceDto>> board) {
}
