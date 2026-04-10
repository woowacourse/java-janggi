package domain.game;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.formation.FormationType;
import domain.game.condition.BikjangCondition;
import domain.game.condition.ConsecutivePassCondition;
import domain.game.condition.GameEndCondition;
import domain.game.condition.GeneralCapturedCondition;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class JanggiGame {
    private Long id;
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

    public static JanggiGame restore(long id, Turn turn, Board board,
                                      GameRecord record, GameStatus status) {
        JanggiGame game = new JanggiGame(turn, board, defaultConditions(), record, new ScoreCalculator());
        game.id = id;
        game.status = status;
        return game;
    }

    private static List<GameEndCondition> defaultConditions() {
        return List.of(
                new GeneralCapturedCondition(),
                new ConsecutivePassCondition(),
                new BikjangCondition()
        );
    }

    public static JanggiGame of(FormationType choFormation, FormationType hanFormation) {
        return new JanggiGame(
                Turn.first(),
                BoardFactory.create(choFormation, hanFormation),
                defaultConditions(),
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

    public void assignId(long id) {
        if (this.id != null) {
            throw new IllegalStateException("이미 ID가 할당된 게임입니다.");
        }
        this.id = id;
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

    public Team findWinner() {
        if (status.isRunning()) {
            throw new IllegalStateException("아직 게임이 진행 중입니다.");
        }
        return findCaptureWinner()
                .orElseGet(this::findScoreWinner);
    }

    private Optional<Team> findCaptureWinner() {
        return Stream.of(Team.CHO, Team.HAN)
                .filter(team -> !board.hasGeneral(team))
                .findFirst()
                .map(Team::opposite);
    }

    private Team findScoreWinner() {
        double choScore = scoreOf(Team.CHO);
        double hanScore = scoreOf(Team.HAN);
        return Team.compareScore(choScore, hanScore);
    }

    public double scoreOf(Team team) {
        return scoreCalculator.calculate(board.findPiecesByTeam(team), team);
    }

    public Team currentTurn() {
        return turn.current();
    }

    public Board getBoard() {
        return board;
    }

    public Long getId() {
        return id;
    }

    public GameStatus getStatus() {
        return status;
    }

    public GameRecord getRecord() {
        return record;
    }
}
