package domain.game.state;

import common.JanggiException;
import domain.game.Game;
import domain.player.Team;
import domain.position.Position;

public class HanTurn extends Running {
    public HanTurn(Game game) {
        super(game);
    }

    @Override
    public Team getCurrentTeam() {
        return Team.HAN;
    }

    @Override
    protected void validateTurn(Position source) {
        if (game.isCho(source)) {
            throw new JanggiException("한 차례입니다. 초 기물이 선택되었습니다.");
        }
    }

    @Override
    protected void changeTurn() {
        game.changeState(new ChoTurn(game));
    }

    @Override
    protected void finishGame() {
        game.changeState(new Finished(game, Team.HAN));
    }
}
