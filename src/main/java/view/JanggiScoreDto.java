package view;

import domain.JanggiScore;

public record JanggiScoreDto(
        double choScore,
        double hanScore
) {
    public static JanggiScoreDto toDto(JanggiScore score) {
        return new JanggiScoreDto(
                score.choScore(),
                score.hanScore()
        );
    }
}
