package domain.game;

import domain.JanggiBoard;
import domain.JanggiPosition;
import domain.piece.Piece;
import java.util.Map;
import service.JanggiService;

public class Run extends Start {
    private JanggiBoard janggiBoard;
    private final Player player;
    private final Long gameId;

    public Run(Long gameId, JanggiService janggiService, JanggiBoard janggiBoard, Player player) {
        super(janggiService);
        this.gameId = gameId;
        this.janggiBoard = janggiBoard;
        this.player = player;
    }

    @Override
    public GameState playSingleTurn(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        validatePieceExist(beforePosition);
        validatePlayerTurn(beforePosition);
        Piece targetPiece = janggiBoard.getPieceFrom(afterPosition);

        janggiBoard = janggiService.move(gameId, beforePosition, afterPosition);
        player.change();

        if (janggiBoard.isGeneralDead(targetPiece)) {
            return new End(getBoard(), getChoScore(), getHanScore());
        }

        return new Run(gameId, this.janggiService, janggiBoard, player);
    }

    private void validatePieceExist(JanggiPosition beforePosition) {
        Piece pieceToMove = janggiBoard.getPieceFrom(beforePosition);

        if (pieceToMove.isEmpty()) {
            throw new IllegalArgumentException("이동하려는 위치에 기물이 없습니다.");
        }
    }

    private void validatePlayerTurn(JanggiPosition position) {
        Piece piece = janggiBoard.getPieceFrom(position);
        if (!player.isMyPiece(piece)) {
            throw new IllegalArgumentException("자신의 기물만 움직일 수 있습니다.");
        }
    }

    @Override
    public int getChoScore() {
        return janggiBoard.getChoScore();
    }

    @Override
    public int getHanScore() {
        return janggiBoard.getHanScore();
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
