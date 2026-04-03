package domain.state;

import domain.piece.Team;

public enum GameResult {
    WIN_HAN("한(HAN) 진영 승리"),
    WIN_CHO("초(CHO) 진영 승리"),
    DRAW("무승부");

    private final String message;

    GameResult(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

    public static GameResult winOf(Team winner) {
        if (winner == Team.HAN) {
            return WIN_HAN;
        }
        return WIN_CHO;
    }

    public static GameResult fromScore(double hanScore, double choScore) {
        if (hanScore > choScore) return WIN_HAN;
        if (choScore > hanScore) return WIN_CHO;
        return DRAW;
    }
}
