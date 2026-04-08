package dto;

public record GameResultDto(
        String winner,
        double choScore,
        double hanScore
) {
}
