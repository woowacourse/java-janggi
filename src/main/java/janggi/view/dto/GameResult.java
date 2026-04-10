package janggi.view.dto;

public class GameResult {

    private final int choScore;
    private final int hanScore;
    private final ViewResult viewResult;

    public GameResult(int choScore, int hanScore) {
        this.choScore = choScore;
        this.hanScore = hanScore;
        this.viewResult = isWin(choScore, hanScore);
    }

    private ViewResult isWin(int choScore, int hanScore) {
        if (choScore > hanScore) {
            return ViewResult.CHO_WIN;
        }
        if (choScore < hanScore) {
            return ViewResult.HAN_WIN;
        }
        return ViewResult.DRAW;
    }

    public int getChoScore() {
        return choScore;
    }

    public int getHanScore() {
        return hanScore;
    }

    public String getDescription() {
        return viewResult.getDescription();
    }

    private enum ViewResult {
        CHO_WIN("초 승리"),
        DRAW("무승부"),
        HAN_WIN("한 승리");

        private final String description;

        ViewResult(String description) {
            this.description = description;
        }

        private String getDescription() {
            return description;
        }
    }
}
