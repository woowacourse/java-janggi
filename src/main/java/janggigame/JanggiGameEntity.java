package janggigame;

import domain.piece.Side;

import java.time.Instant;

public class JanggiGameEntity {
    private Long id;
    private Side currenTurn;
    private JanggiGameStatus status;
    private int choJanggunCount;
    private int hanJanggunCount;
    private Instant createdAt;

    private JanggiGameEntity(Long id, Side currenTurn, JanggiGameStatus status, int choJanggunCount, int hanJanggunCount, Instant createdAt) {
        this.id = id;
        this.currenTurn = currenTurn;
        this.status = status;
        this.choJanggunCount = choJanggunCount;
        this.hanJanggunCount = hanJanggunCount;
        this.createdAt = createdAt;
    }

    public static JanggiGameEntity newGame() {
        return new JanggiGameEntity(
                null,
                Side.CHO,
                JanggiGameStatus.WAITING_HAN_PLACEMENT,
                0,
                0,
                Instant.now()
        );
    }

    public Side getCurrenTurn() {
        return currenTurn;
    }

    public JanggiGameStatus getStatus() {
        return status;
    }
}
