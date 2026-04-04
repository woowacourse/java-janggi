package mapper;

import dto.ScoreDto;

public class ScoreMapper {

    public static ScoreDto toDto(double chuSideScore, double hanSideScore) {
        return new ScoreDto(chuSideScore,hanSideScore);
    }
}
