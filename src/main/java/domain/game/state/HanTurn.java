package domain.game.state;

import common.exception.JanggiException;
import domain.game.Game;
import domain.player.Team;
import domain.position.Position;
import java.util.Set;

public class HanTurn extends Running {
    public HanTurn(Game game) {
        super(game);
    }

    @Override
    public void move(Position source, Position destination) {
        if (game.isCho(source)) {
            throw new JanggiException("한 차례입니다. 초 기물이 선택되었습니다.");
        }
        game.movePiece(source, destination);
        if (game.isJangCaught()) {
            game.changeState(new Finished(game, Team.HAN));
            return;
        }
        game.changeState(new ChoTurn(game));
    }

    @Override
    public Team getCurrentTeam() {
        return Team.HAN;
    }

    @Override
    public Set<Position> selectPiece(Position source) {
        if (game.isCho(source)) {
            throw new JanggiException("한 차례입니다. 초 기물이 선택되었습니다.");
        }
        return game.findMovablePositions(source);
    }
}
