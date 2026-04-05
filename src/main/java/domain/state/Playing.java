package domain.state;

import domain.Board;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;

public class Playing extends Running {
    protected Playing(Board board, Team turn) {
        super(board, turn);
    }

    public static JanggiGame init(SettingType choSettingType, SettingType hanSettingType) {
        return new Playing(Board.of(choSettingType, hanSettingType), Team.CHO);
    }

    @Override
    public JanggiGame move(Position start, Position destination) {
        board.move(turn, start, destination);
        if (board.isAnyJangDead()) {
            return new Finished(board, turn.changeTeam());
        }
        if (board.isBikjang()) {
            return new Bikjang(board, turn.changeTeam());
        }
        return new Playing(board, turn.changeTeam());
    }

    @Override
    public JanggiGame pass() {
        return new Playing(board, turn.changeTeam());
    }
}
