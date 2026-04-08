package dto;

public record GameRecordDto(
        int gameId,
        String country,
        double choScore,
        double hanScore
) {
}
