package view.dto;

public record GameResultDto(
        boolean ended,
        String winnerName
) {
}
