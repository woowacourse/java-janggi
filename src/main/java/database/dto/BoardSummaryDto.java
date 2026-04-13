package database.dto;

public record BoardSummaryDto(
        Long boardId,
        String currentTurn,
        boolean isFinished
) {
}
