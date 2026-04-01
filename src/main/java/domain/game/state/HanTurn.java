package domain.game.state;

import common.exception.JanggiException;
import domain.game.Game;
import domain.piece.Piece;
import domain.player.Team;
import domain.position.Position;
import java.util.List;

public class HanTurn extends Running {
    public HanTurn(Game game) {
        super(game);
    }

    @Override
    public void move(Position source, Position destination) {
        if (game.isCho(source)) {
            throw new JanggiException("한 차례입니다. 초 기물이 선택되었습니다.");
        }
        Piece caughtPiece = game.movePiece(source, destination);
        if (caughtPiece.isJang()) {
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
    public List<Position> selectPiece(Position source) {
        if (game.isCho(source)) {
            throw new JanggiException("한 차례입니다. 초 기물이 선택되었습니다.");
        }
        return game.findMovablePositions(source);
    }
}
