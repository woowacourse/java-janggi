package janggi.dto;

public record GameStateDto(
        String turnColor,
        String winner,
        boolean isFinished,
        int redScore,
        int blueScore
) {
}
