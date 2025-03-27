package domain.game;

import domain.JanggiBoard;
import domain.JanggiPosition;
import domain.Score;
import domain.piece.Piece;
import java.util.Map;

public class Run extends Start {
    private final JanggiBoard janggiBoard;
    private final Player player;

    public Run(JanggiBoard janggiBoard, Player player) {
        this.janggiBoard = janggiBoard;
        this.player = player;
    }

    @Override
    public GameState playSingleTurn(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        validatePlayerTurn(beforePosition);
        Piece targetPiece = janggiBoard.getPieceFrom(afterPosition);

        janggiBoard.move(beforePosition, afterPosition);
        player.change();

        if (janggiBoard.isGeneralDead(targetPiece)) {
            return new End(getBoard(), getScoreWhenFinish());
        }

        return new Run(janggiBoard, player);
    }

    private void validatePlayerTurn(JanggiPosition position) {
        Piece piece = janggiBoard.getPieceFrom(position);
        if (!player.isMyPiece(piece)) {
            throw new IllegalArgumentException("자신의 기물만 움직일 수 있습니다.");
        }
    }

    private Score getScoreWhenFinish() {
        return janggiBoard.getScore();
    }

    @Override
    public Map<JanggiPosition, Piece> getBoard() {
        return janggiBoard.getJanggiBoard();
    }

    @Override
    public Player getCurrentPlayer() {
        return new Player(player.getSide());
    }
}
