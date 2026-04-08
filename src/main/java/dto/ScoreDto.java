package dto;

public record ScoreDto(double choScore, double hanScore) {

    public static ScoreDto of(double choScore, double hanScore) {
        return new ScoreDto(choScore, hanScore);
    }
}
