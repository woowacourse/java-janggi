package domain;

import domain.boardgenerator.BoardGenerator;
import domain.palace.Palace;
import domain.piece.Piece;
import domain.player.Player;
import domain.player.Players;
import java.util.HashMap;
import java.util.Map;

public class JanggiGame {

    public static final int SEQUENCE_ZERO = 0;
    public static final int SEQUENCE_ONE = 1;


    private final JanggiBoard janggiBoard;
    private final Players players;
    private int sequence = SEQUENCE_ZERO;


    public JanggiGame(BoardGenerator boardGenerator, Players players, Palace palace) {
        this.janggiBoard = new JanggiBoard(boardGenerator, palace);
        this.players = players;
    }


    public Map<Position, Piece> move(Position startPosition, Position targetPosition) {
        validateMovePiece(startPosition, targetPosition);
        Map<Position, Piece> startAndTargetPieces = janggiBoard.move(startPosition, targetPosition);
        if (sequence == SEQUENCE_ZERO) {
            sequence = SEQUENCE_ONE;
            return startAndTargetPieces;
        }
        sequence = SEQUENCE_ZERO;
        return startAndTargetPieces;
    }

    private void validateMovePiece(Position startPosition, Position targetPosition) {
        if (janggiBoard.findPiece(startPosition) == null) {
            throw new IllegalArgumentException("해당 자리에는 말이 없습니다.");
        }
        if (!players.getThisTurnPlayer(sequence).isTeam(janggiBoard.findPiece(startPosition))) {
            throw new IllegalArgumentException("자신의 말만 움직일 수 있습니다.");
        }
        if (startPosition.equals(targetPosition)) {
            throw new IllegalArgumentException("말을 움직여 주세요");
        }
    }

    public Player getThisTurnPlayer() {
        return players.getThisTurnPlayer(sequence);
    }

    public Map<Position, Piece> getBoardState() {
        return janggiBoard.getBoard();
    }

    public boolean checkKingIsDead() {
        return janggiBoard.checkKingIsDead();
    }

    public Map<Player, Integer> calculateScore() {
        Map<Player, Integer> score = new HashMap<>();
        score.put(players.getPlayerByTeam(Team.RED), 0);
        score.put(players.getPlayerByTeam(Team.BLUE), 0);

        Map<Position, Piece> board = janggiBoard.getBoard();
        for (Piece piece : board.values()) {
            Player player = piece.getPlayer();
            score.put(player, score.get(player) + piece.getPoint());
        }

        return score;
    }

    public boolean isTargetPositionIsEmpty(Position position) {
        return janggiBoard.isPositionEmpty(position);
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int currentTurn) {
        this.sequence = currentTurn;
    }
}
