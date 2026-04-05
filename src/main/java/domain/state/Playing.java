package domain.state;

import domain.Board;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;

public class Playing extends Running {
    protected Playing(long id, Board board, Team turn) {
        super(id, board, turn);
    }

    public static JanggiGame init(long id, SettingType choSettingType, SettingType hanSettingType) {
        return new Playing(id, Board.of(choSettingType, hanSettingType), Team.CHO);
    }

    public static JanggiGame load(long id, Board board, Team turn) {
        if (board.isBikjang()) {
            return new Bikjang(id, board, turn);
        }
        return new Playing(id, board, turn);
    }

    @Override
    public JanggiGame move(Position start, Position destination) {
        board.move(turn, start, destination);
        if (board.isAnyJangDead()) {
            return new Finished(id, board, turn.changeTeam());
        }
        if (board.isBikjang()) {
            return new Bikjang(id, board, turn.changeTeam());
        }
        return new Playing(id, board, turn.changeTeam());
    }

    @Override
    public JanggiGame pass() {
        return new Playing(id, board, turn.changeTeam());
    }
}
