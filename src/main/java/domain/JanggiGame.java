package domain;

import domain.board.Board;
import domain.board.BoardStatus;
import domain.piece.Team;
import domain.position.Position;

import domain.GameId;

public class JanggiGame {
    private final GameId id;
    private final Board board;
    private final GameContext context;
    private final ScoreCalculator scoreCalculator;

    public JanggiGame(GameId id, Board board, GameContext context, ScoreCalculator scoreCalculator) {
        this.id = id;
        this.board = board;
        this.context = context;
        this.scoreCalculator = scoreCalculator;
    }

    private JanggiGame(Board board, GameContext context, ScoreCalculator scoreCalculator) {
        this(null, board, context, scoreCalculator);
    }

    public GameId getId() {
        return id;
    }

    public GameContext getContext() {
        return context;
    }

    public Board getBoard() {
        return board;
    }

    public static JanggiGame init(SettingType choSetting, SettingType hanSetting) {
        GameContext gameContext = new GameContext(new Turn(Team.CHO), GameState.PLAYING);
        return new JanggiGame(
                Board.of(choSetting, hanSetting),
                gameContext,
                new ScoreCalculator()
        );
    }

    public void executeMove(Position start, Position destination) {
        validateGameIsNotFinished();
        board.move(context.getTurn(), start, destination);
        checkGameTermination();
        if (context.getGameState() == GameState.PLAYING) {
            passTurn();
        }
    }

    private void validateGameIsNotFinished() {
        if (context.getGameState() == GameState.END) {
            throw new IllegalStateException(JanggiGameErrorMessage.ALREADY_END.getMessage());
        }
    }

    private void checkGameTermination() {
        if (board.isKingCaptured(Team.CHO) || board.isKingCaptured(Team.HAN)) {
            context.finishGame();
        }
    }

    public void passTurn() {
        context.passTurn();
    }

    public boolean isFinished() {
        return context.getGameState() == GameState.END;
    }

    public JanggiScore getGameScore(BoardStatus status) {
        return scoreCalculator.calculate(status);
    }

    public Team getWinner() {
        if (context.getGameState() != GameState.END) {
            throw new IllegalStateException(JanggiGameErrorMessage.NOW_ON_PLAYING.getMessage());
        }
        if (board.isKingCaptured(Team.CHO)) {
            return Team.HAN;
        }
        return Team.CHO;
    }

    public BoardStatus getJanggiGameStatus() {
        return board.getBoardStatus();
    }

    public Team getTurnOwnTeam() {
        return context.getTurn().turnOwnTeam();
    }
}
