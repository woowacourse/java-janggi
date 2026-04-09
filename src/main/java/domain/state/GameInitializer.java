package domain.state;

import domain.Board;
import domain.piece.Team;
import domain.settingType.SettingType;

public class GameInitializer {
    private static final Team FIRST_TURN = Team.CHO;

    public static JanggiGame init(SettingType choSettingType, SettingType hanSettingType) {
        return new Playing(Board.of(choSettingType, hanSettingType), FIRST_TURN);
    }

    public static JanggiGame load(Board board, Team turn) {
        if (board.isAnyJangDead()) {
            return new Finished(board, turn);
        }
        if (board.isBikjang()) {
            return new Bikjang(board, turn);
        }
        return new Playing(board, turn);
    }

}
