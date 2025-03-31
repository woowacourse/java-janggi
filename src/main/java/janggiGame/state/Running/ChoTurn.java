package janggiGame.state.Running;

import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.position.Position;
import janggiGame.state.Finished.ChoWin;
import janggiGame.state.State;
import java.util.Map;

public class ChoTurn extends Running {

    public ChoTurn(Map<Position, Piece> pieces, boolean wasLastTurnPassed) {
        super(pieces, wasLastTurnPassed);
    }

    @Override
    public Dynasty getCurrentDynasty() {
        return Dynasty.CHO;
    }

    @Override
    protected Dynasty getTurnOwner() {
        return Dynasty.CHO;
    }

    @Override
    protected State createNextTurnState(Map<Position, Piece> nextTurnPieces, boolean wasLastTurnPassed) {
        return new HanTurn(nextTurnPieces, wasLastTurnPassed);
    }

    @Override
    protected State createWinState() {
        return new ChoWin();
    }
}
