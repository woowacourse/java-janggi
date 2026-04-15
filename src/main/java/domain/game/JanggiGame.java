package domain.game;

import domain.board.Board;
import domain.board.Pieces;
import domain.piece.Team;
import domain.setup.Arrangements;
import domain.setup.Command;
import domain.setup.Coordinate;
import domain.state.FinishState;
import domain.state.GamePhase;
import domain.state.GameState;
import domain.state.ReadyState;

public class JanggiGame {
    private Turn turn;
    private GameState gameState;
    private Board board;
    private Pieces piecesSnapshot;

    public JanggiGame() {
        this.turn = new Turn(Team.HAN);
        this.gameState = new ReadyState(new Arrangements());
    }

    public void processCommand(Command command) {
        gameState = gameState.handle(this, command);
    }

    public void setupBoard(Arrangements arrangements) {
        this.piecesSnapshot = Pieces.of(arrangements);
        this.board = new Board(piecesSnapshot);
        this.turn = new Turn(Team.CHO);
    }

    public Board getBoard() {
        return board;
    }

    public Turn getTurn() {
        return turn;
    }

    public void move(Coordinate coordinate) {
        this.board = board.move(coordinate, turn.getTeam());
    }

    public void nextTurn() {
        this.turn = turn.changeTeam();
    }

    public boolean isFinished() {
        return isGeneralCaptured(Team.CHO) || isGeneralCaptured(Team.HAN);
    }

    private boolean isGeneralCaptured(Team team) {
        return !board.hasGeneral(team);
    }

    public GameResult createGameResult() {
        validateFinishedGame();
        return GameResult.from(piecesSnapshot, board.snapshot(), turn.getTeam());
    }

    private void validateFinishedGame() {
        if (!isFinished()) {
            throw new IllegalStateException("[ERROR] 아직 게임이 종료되지 않았습니다.");
        }
    }

    public boolean isReadyPhase() {
        return gameState.phase() == GamePhase.READY;
    }

    public boolean isPlayingPhase() {
        return gameState.phase() == GamePhase.PLAYING;
    }

    public boolean isFinishPhase() {
        return gameState.phase() == GamePhase.FINISH;
    }

    public GameResult getGameResult() {
        validateFinishPhase();
        return finishState().getGameResult();
    }

    private void validateFinishPhase() {
        if (!isFinishPhase()) {
            throw new IllegalStateException("[ERROR] 게임 결과가 아직 없습니다.");
        }
    }

    private FinishState finishState() {
        return (FinishState) gameState;
    }
}
