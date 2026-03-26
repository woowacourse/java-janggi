package domain;

public class JanggiGame {
    private final Board board;

    private JanggiGame(Board board) {
        this.board = board;
    }

    public static JanggiGame init(SettingType choSetting, SettingType hanSetting) {
        return new JanggiGame(Board.of(choSetting, hanSetting));
    }

    public BoardStatus getJanggiGameStatus() {
        return board.getBoardStatus();
    }
}
