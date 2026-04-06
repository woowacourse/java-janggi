package janggi.domain.state;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

public class InProgress implements GameState {

    private Side currentSide;

    public InProgress(Side currentSide) {
        this.currentSide = currentSide;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public void update(GameContext context, Piece piece) {
        if (piece.isSame(PieceType.GUNG)) {
            context.changeState(new End(currentSide));
            return;
        }
        currentSide = currentSide.switchTurn();
    }

    @Override
    public Side getCurrentSide() {
        return currentSide;
    }

    @Override
    public Side getWinner() {
        throw new IllegalStateException("게임이 진행중인 상태에는 승자가 존재하지 않습니다.");
    }
}
