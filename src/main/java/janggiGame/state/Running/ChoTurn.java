package janggiGame.state.Running;

import janggiGame.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.Type;
import janggiGame.state.Finished.ChoWin;
import janggiGame.state.Finished.Draw;
import janggiGame.state.State;
import java.util.List;
import java.util.Map;

public class ChoTurn extends Running{

    public ChoTurn(Map<Dot, Piece> pieces, boolean wasLastTurnPassed) {
        super(pieces, wasLastTurnPassed);
    }

    @Override
    public State skipTurn() {
        if(wasLastTurnPassed) {
            return new Draw();
        }
        return new HanTurn(pieces, true);
    }

    @Override
    public State takeTurn(Dot origin, Dot destination) {
        validateOrigin(origin, Dynasty.CHO);

        Piece originPiece = pieces.get(origin);
        List<Dot> route = originPiece.getRoute(origin, destination);
        Map<Dot, Piece> routeWithPiece = getPiecesOn(route);
        Piece destinationPiece = pieces.getOrDefault(destination, null);

        originPiece.validateMove(routeWithPiece, destinationPiece);
        Map<Dot, Piece> nextTurnPieces = movePiece(origin, destination, originPiece);

        if(destinationPiece != null && destinationPiece.getType() == Type.KING) {
            return new ChoWin();
        }

        return new HanTurn(nextTurnPieces, false);
    }

    @Override
    public Dynasty getCurrentDynasty() {
        return Dynasty.CHO;
    }
}
