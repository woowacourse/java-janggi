package domain.state;

import domain.Board;
import domain.piece.Team;
import domain.settingType.SettingType;

public class GameInitializer {
    private static final Team FIRST_TURN = Team.CHO;

    public static JanggiGame init(long id, SettingType choSettingType, SettingType hanSettingType) {
        return new Playing(id, Board.of(choSettingType, hanSettingType), FIRST_TURN);
    }

    public static JanggiGame load(long id, Board board, Team turn) {
        if (board.isBikjang()) {
            return new Bikjang(id, board, turn);
        }
        return new Playing(id, board, turn);
    }

}
