package dto;

public record ScoreDto(double choScore, double hanScore) {

    public static ScoreDto of(final double choScore, final double hanScore) {
        return new ScoreDto(choScore, hanScore);
    }
}
