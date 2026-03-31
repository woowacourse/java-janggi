package domain.game.state;

import common.exception.JanggiException;
import domain.game.Game;
import domain.piece.Piece;
import domain.position.Position;

public class HanTurn extends GameState {
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
            game.changeState(new Finished(game));
            return;
        }
        game.changeState(new ChoTurn(game));
    }
}
