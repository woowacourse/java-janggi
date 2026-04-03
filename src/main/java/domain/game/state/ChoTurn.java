package domain.game.state;

import common.JanggiException;
import domain.game.Game;
import domain.player.Team;
import domain.position.Position;

public class ChoTurn extends Running {
    public ChoTurn(Game game) {
        super(game);
    }

    @Override
    public Team getCurrentTeam() {
        return Team.CHO;
    }

    @Override
    protected void validateTurn(Position source) {
        if (game.isHan(source)) {
            throw new JanggiException("초 차례입니다. 한 기물이 선택되었습니다.");
        }
    }

    @Override
    protected void changeTurn() {
        game.changeState(new HanTurn(game));
    }

    @Override
    protected void finishGame() {
        game.changeState(new Finished(game, Team.CHO));
    }
}
