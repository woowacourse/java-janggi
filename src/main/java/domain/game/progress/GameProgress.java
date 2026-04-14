package domain.game.progress;

import domain.game.GameStatus;
import domain.game.Team;
import domain.game.Turn;
import java.util.List;

public class GameProgress {
    private Turn turn;
    private GameStatus status;
    private final GameRecord record;
    private final PassStreak passStreak;

    private GameProgress(Turn turn, GameStatus status, GameRecord record, PassStreak passStreak) {
        this.turn = turn;
        this.status = status;
        this.record = record;
        this.passStreak = passStreak;
    }

    public static GameProgress initial() {
        return new GameProgress(Turn.first(), GameStatus.RUNNING, new GameRecord(), new PassStreak());
    }

    public static GameProgress restore(Turn turn, GameStatus status, GameRecord record, PassStreak passStreak) {
        return new GameProgress(turn, status, record, passStreak);
    }

    public void recordMove(MoveLog log) {
        assertRunning();
        record.append(log);
        passStreak.reset();
        turn = turn.next();
    }

    public void recordPass(MoveLog log) {
        assertRunning();
        record.append(log);
        passStreak.increase();
        turn = turn.next();
    }

    public void finish() {
        this.status = GameStatus.FINISHED;
    }

    public void assertRunning() {
        if (!status.isRunning()) {
            throw new IllegalStateException("이미 종료된 게임입니다.");
        }
    }

    public boolean isRunning() {
        return status.isRunning();
    }

    public Team currentTurn() {
        return turn.current();
    }

    public GameStatus status() {
        return status;
    }

    public int consecutivePassCount() {
        return passStreak.value();
    }

    public List<MoveLog> history() {
        return record.logs();
    }

    public GameRecord record() {
        return record;
    }
}
