package domain.game;

import domain.JanggiBoard;
import domain.JanggiPosition;
import domain.piece.Piece;
import domain.piece.state.PieceState;
import java.util.Map;

public class Run extends Start {
    private final JanggiBoard janggiBoard;
    private final Player player;

    public Run(JanggiBoard janggiBoard, Player player) {
        this.janggiBoard = janggiBoard;
        this.player = player;
    }

    @Override
    public GameState move(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        validatePlayerTurn(beforePosition);
        PieceState state = janggiBoard.getPieceFrom(afterPosition).getState();

        janggiBoard.move(beforePosition, afterPosition);
        player.change();

        if (janggiBoard.isGeneralDead(state)) {
            return new End();
        }

        return new Run(janggiBoard, player);
    }

    private void validatePlayerTurn(JanggiPosition position) {
        Piece piece = janggiBoard.getPieceFrom(position);
        if (!player.isMyPiece(piece)) {
            throw new IllegalArgumentException("자신의 기물만 움직일 수 있습니다.");
        }
    }

    @Override
    public Map<JanggiPosition, Piece> getBoard() {
        return janggiBoard.getJanggiBoard();
    }

    @Override
    public Player getCurrentPlayer() {
        return player;
    }
}
