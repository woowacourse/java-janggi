package domain.game;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.BoardMove;
import domain.board.BoardSnapshot;
import domain.board.formation.FormationType;
import domain.game.condition.BikjangCondition;
import domain.game.condition.ConsecutivePassCondition;
import domain.game.condition.GameEndCondition;
import domain.game.condition.GeneralCapturedCondition;
import domain.game.judge.GameJudge;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;

public class JanggiGame {
    private static final List<GameEndCondition> END_CONDITIONS = List.of(
            new GeneralCapturedCondition(),
            new ConsecutivePassCondition(),
            new BikjangCondition()
    );

    private Turn turn;
    private final Board board;
    private final GameRecord record;
    private final GameJudge judge;
    private GameStatus status;

    private JanggiGame(Turn turn, Board board, GameRecord record, GameJudge judge, GameStatus status) {
        this.turn = turn;
        this.board = board;
        this.record = record;
        this.judge = judge;
        this.status = status;
    }

    public static JanggiGame of(FormationType choFormation, FormationType hanFormation) {
        return new JanggiGame(
                Turn.first(),
                BoardFactory.create(choFormation, hanFormation),
                new GameRecord(),
                GameJudge.defaultJudge(),
                GameStatus.RUNNING
        );
    }

    public static JanggiGame restore(Turn turn, Board board, GameRecord record, GameStatus status) {
        return new JanggiGame(turn, board, record, GameJudge.defaultJudge(), status);
    }

    public BoardMove move(Position source, Position destination) {
        validateRunning();
        validateTurn(source);
        BoardMove boardMove = board.move(source, destination);
        record.recordMove();
        turn = turn.next();
        checkEndConditions();
        return boardMove;
    }

    public void pass() {
        validateRunning();
        record.recordPass();
        turn = turn.next();
        checkEndConditions();
    }

    private void validateRunning() {
        if (!status.isRunning()) {
            throw new IllegalStateException("이미 종료된 게임입니다.");
        }
    }

    private void validateTurn(Position source) {
        Piece sourcePiece = board.pieceAt(source);

        if (!sourcePiece.isNotEmpty()) {
            throw new IllegalArgumentException("빈 칸을 선택하셨습니다.");
        }

        if (!sourcePiece.belongsTo(turn.current())) {
            throw new IllegalArgumentException("현재 턴의 기물이 아닙니다.");
        }
    }

    private void checkEndConditions() {
        boolean ended = END_CONDITIONS.stream()
                .anyMatch(condition -> condition.isSatisfied(board, record));
        if (ended) {
            status = GameStatus.FINISHED;
        }
    }

    public boolean isRunning() {
        return status.isRunning();
    }

    public GameResult result() {
        if (status.isRunning()) {
            throw new IllegalStateException("아직 게임이 진행 중입니다.");
        }
        return judge.decide(board);
    }

    public double scoreOf(Team team) {
        return judge.scoreOf(board, team);
    }

    public Team currentTurn() {
        return turn.current();
    }

    public BoardSnapshot boardSnapshot() {
        return board.snapshot();
    }

    public GameStatus getStatus() {
        return status;
    }

    public GameRecord getRecord() {
        return record;
    }
}
