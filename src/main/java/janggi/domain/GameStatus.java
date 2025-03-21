package janggi.domain;

public enum GameStatus {
    CONTINUE(""),
    RED_WIN("한 팀이 승리하였습니다!"),
    GREEN_WIN("초 팀이 승리하였습니다!"),
    DRAW("무승부 입니다!"),
    ;

    private final String text;

    GameStatus(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
