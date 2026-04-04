package dto;

public class ScoreDto {

    private final double chuSideScore;
    private final double hanSideScore;

    public ScoreDto(double chuSideScore, double hanSideScore) {
        this.chuSideScore = chuSideScore;
        this.hanSideScore = hanSideScore;
    }

    public double getChuSideScore() {
        return chuSideScore;
    }

    public double getHanSideScore() {
        return hanSideScore;
    }
}
