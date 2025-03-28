package domain.piece;

import java.util.Arrays;

public enum PieceType {
    CANNON(7,2),
    CHARIOT(13,2),
    ELEPHANT(3,2),
    GUARD(3,2),
    HORSE(5,2),
    KING(0,1),
    SOLDIER(2,5);

    private final double score;
    private final int count;

    private static final double HAN_BONUS_SCORE = 1.5;

    PieceType(int score, int count) {
        this.score = score;
        this.count = count;
    }

    public static double getTotalScore(TeamType teamType){
        double totalScore = Arrays.stream(PieceType.values())
                .mapToDouble(type -> type.score * type.count)
                .sum();
        if(teamType.equals(TeamType.HAN)){
            return totalScore + HAN_BONUS_SCORE;
        }
        return totalScore;
    }
}
