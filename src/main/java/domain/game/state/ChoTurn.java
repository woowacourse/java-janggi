package domain.game.state;

import common.exception.JanggiException;
import domain.game.Game;
import domain.player.Team;
import domain.position.Position;
import java.util.Set;

public class ChoTurn extends Running {
    public ChoTurn(Game game) {
        super(game);
    }

    @Override
    public void move(Position source, Position destination) {
        if (game.isHan(source)) {
            throw new JanggiException("초 차례입니다. 한 기물이 선택되었습니다.");
        }
        game.movePiece(source, destination);
        if (game.isJangCaught()) {
            game.changeState(new Finished(game, Team.CHO));
            return;
        }
        game.changeState(new HanTurn(game));
    }

    @Override
    public Team getCurrentTeam() {
        return Team.CHO;
    }

    @Override
    public Set<Position> selectPiece(Position source) {
        if (game.isHan(source)) {
            throw new JanggiException("초 차례입니다. 한 기물이 선택되었습니다.");
        }
        return game.findMovablePositions(source);
    }
}
