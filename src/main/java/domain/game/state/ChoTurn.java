package domain.game.state;

import common.exception.JanggiException;
import domain.game.Game;
import domain.piece.Piece;
import domain.player.Team;
import domain.position.Position;

public class ChoTurn extends Running {
    public ChoTurn(Game game) {
        super(game);
    }

    @Override
    public void move(Position source, Position destination) {
        if (game.isHan(source)) {
            throw new JanggiException("초 차례입니다. 한 기물이 선택되었습니다.");
        }
        Piece caughtPiece = game.movePiece(source, destination);
        if (caughtPiece.isJang()) {
            game.changeState(new Finished(game, Team.CHO));
            return;
        }
        game.changeState(new HanTurn(game));
    }

    @Override
    public Team getCurrentTeam() {
        return Team.CHO;
    }
}
