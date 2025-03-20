package domain;

import domain.boardgenerator.BoardGenerator;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    public static final int SEQUENCE_ZERO = 0;
    public static final int SEQUENCE_ONE = 1;

    private final JanggiBoard janggiBoard;
    private final List<Player> players;
    private int sequence = SEQUENCE_ZERO;


    public JanggiGame(BoardGenerator boardGenerator, List<String> playerNames) {
        this.janggiBoard = new JanggiBoard(boardGenerator);
        this.players = List.of(new Player(playerNames.getFirst(), Team.BLUE),
                new Player(playerNames.getLast(), Team.RED));
    }

    public void move(Position startPosition, Position targetPosition) {
        if (!players.get(sequence).isTeam(janggiBoard.findPiece(startPosition))) {
            throw new IllegalArgumentException("자신의 말만 움직일 수 있습니다.");
        }
        janggiBoard.move(startPosition, targetPosition);
        if (sequence == SEQUENCE_ZERO) {
            sequence = SEQUENCE_ONE;
            return;
        }
        sequence = SEQUENCE_ZERO;
    }

    public Player getThisTurnPlayer() {
        return players.get(sequence);
    }

    public Map<Position, Piece> getBoardState() {
        return janggiBoard.getBoard();
    }
}
