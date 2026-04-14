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
import domain.game.progress.GameProgress;
import domain.game.progress.MoveLog;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;

public class JanggiGame {
    private static final List<GameEndCondition> END_CONDITIONS = List.of(
            new GeneralCapturedCondition(),
            new ConsecutivePassCondition(),
            new BikjangCondition()
    );

    private final Board board;
    private final GameProgress progress;
    private final GameJudge judge;

    private JanggiGame(Board board, GameProgress progress, GameJudge judge) {
        this.board = board;
        this.progress = progress;
        this.judge = judge;
    }

    public static JanggiGame of(FormationType choFormation, FormationType hanFormation) {
        return new JanggiGame(
                BoardFactory.create(choFormation, hanFormation),
                GameProgress.initial(),
                GameJudge.defaultJudge()
        );
    }

    public static JanggiGame restore(Board board, GameProgress progress) {
        return new JanggiGame(board, progress, GameJudge.defaultJudge());
    }

    public BoardMove move(Position source, Position destination) {
        progress.assertRunning();
        validateTurn(source);
        Team mover = progress.currentTurn();
        BoardMove boardMove = board.move(source, destination);
        progress.recordMove(MoveLog.move(mover, boardMove));
        checkEndConditions();
        return boardMove;
    }

    public void pass() {
        progress.assertRunning();
        Team passer = progress.currentTurn();
        progress.recordPass(MoveLog.pass(passer));
        checkEndConditions();
    }

    private void validateTurn(Position source) {
        Piece sourcePiece = board.pieceAt(source);

        if (!sourcePiece.isNotEmpty()) {
            throw new IllegalArgumentException("빈 칸을 선택하셨습니다.");
        }

        if (!sourcePiece.belongsTo(progress.currentTurn())) {
            throw new IllegalArgumentException("현재 턴의 기물이 아닙니다.");
        }
    }

    private void checkEndConditions() {
        boolean ended = END_CONDITIONS.stream()
                .anyMatch(condition -> condition.isSatisfied(board, progress));
        if (ended) {
            progress.finish();
        }
    }

    public boolean isRunning() {
        return progress.isRunning();
    }

    public GameResult result() {
        if (progress.isRunning()) {
            throw new IllegalStateException("아직 게임이 진행 중입니다.");
        }
        return judge.decide(board);
    }

    public double scoreOf(Team team) {
        return judge.scoreOf(board, team);
    }

    public Team currentTurn() {
        return progress.currentTurn();
    }

    public BoardSnapshot boardSnapshot() {
        return board.snapshot();
    }

    public GameStatus getStatus() {
        return progress.status();
    }

    public GameProgress getProgress() {
        return progress;
    }
}
