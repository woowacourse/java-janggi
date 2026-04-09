package domain.game;

import domain.board.Board;
import domain.board.BoardFactory;
리import domain.board.formation.FormationType;
import domain.game.condition.BikjangCondition;
import domain.game.condition.ConsecutivePassCondition;
import domain.game.condition.GameEndCondition;
import domain.game.condition.GeneralCapturedCondition;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;

public class JanggiGame {
    private Turn turn;
    private final Board board;
    private final List<GameEndCondition> endConditions;
    private final GameRecord record;
    private final ScoreCalculator scoreCalculator;
    private GameStatus status;

    public JanggiGame(Turn turn, Board board, List<GameEndCondition> endConditions,
                      GameRecord record, ScoreCalculator scoreCalculator) {
        this.turn = turn;
        this.board = board;
        this.endConditions = endConditions;
        this.record = record;
        this.scoreCalculator = scoreCalculator;
        this.status = GameStatus.RUNNING;
    }

    public static JanggiGame of(FormationType choFormation, FormationType hanFormation) {
        List<GameEndCondition> conditions = List.of(
                new GeneralCapturedCondition(),
                new ConsecutivePassCondition(),
                new BikjangCondition()
        );
        return new JanggiGame(
                Turn.first(),
                BoardFactory.create(choFormation, hanFormation),
                conditions,
                new GameRecord(),
                new ScoreCalculator()
        );
    }

    public void move(Position source, Position destination) {
        validateRunning();
        validateTurn(source);
        board.move(source, destination);
        record.recordMove();
        turn = turn.next();
        checkEndConditions();
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
        boolean ended = endConditions.stream()
                .anyMatch(condition -> condition.isSatisfied(board, record));
        if (ended) {
            status = GameStatus.FINISHED;
        }
    }

    public boolean isRunning() {
        return status.isRunning();
    }

    public double scoreOf(Team team) {
        return scoreCalculator.calculate(board.findPiecesByTeam(team));
    }

    public Team currentTurn() {
        return turn.current();
    }

    public Board getBoard() {
        return board;
    }

    public GameStatus getStatus() {
        return status;
    }
}
