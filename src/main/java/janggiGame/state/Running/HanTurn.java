package janggiGame.state.Running;

import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.position.Position;
import janggiGame.state.Finished.HanWin;
import janggiGame.state.State;
import java.util.Map;

public class HanTurn extends Running {

    public HanTurn(Map<Position, Piece> pieces, boolean wasLastTurnPassed) {
        super(pieces, wasLastTurnPassed);
    }

    @Override
    public Dynasty getCurrentDynasty() {
        return Dynasty.HAN;
    }

    @Override
    protected Dynasty getTurnOwner() {
        return Dynasty.HAN;
    }

    @Override
    protected State createNextTurnState(Map<Position, Piece> nextTurnPieces, boolean wasLastTurnPassed) {
        return new ChoTurn(nextTurnPieces, wasLastTurnPassed);
    }

    @Override
    protected State createWinState() {
        return new HanWin();
    }
}
