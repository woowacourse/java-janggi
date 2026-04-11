package janggi;

import janggi.domain.GameRepository;
import janggi.domain.GameState;
import janggi.domain.ScoreResult;
import janggi.domain.Team;
import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    private static final String END_COMMAND = "end";
    private static final int POSITION_INPUT_SIZE = 2;

    private final Board board;
    private final GameRepository gameRepository;
    private final long gameId;

    private JanggiGame(Board board, GameRepository gameRepository, long gameId) {
        this.board = board;
        this.gameRepository = gameRepository;
        this.gameId = gameId;
    }

    public static JanggiGame from(GameRepository gameRepository) {
        String mode = InputView.readGameMode();
        if (mode.equals("1")) {
            return initialize(gameRepository);
        }
        return load(gameRepository);
    }

    private static JanggiGame initialize(GameRepository gameRepository) {
        String hanSetup = InputView.readHanSetup();
        String choSetup = InputView.readChoSetup();
        Board board = BoardFactory.create(hanSetup, choSetup);
        long gameId = gameRepository.save(board, Team.CHO);
        OutputView.printGameId(gameId);
        return new JanggiGame(board, gameRepository, gameId);
    }

    private static JanggiGame load(GameRepository gameRepository) {
        long gameId = InputView.readGameId();
        Board board = gameRepository.findBoardById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 게임을 찾을 수 없습니다: " + gameId));
        return new JanggiGame(board, gameRepository, gameId);
    }

    public void start() {
        OutputView.printBoard(board.showBoard());
        play();
    }

    private void play() {
        Team currentTeam = gameRepository.findTurnById(gameId);
        GameState state = GameState.PLAYING;
        while (state.isPlaying()) {
            String input = InputView.readPosition(currentTeam.getDisplayName());
            state = progressTurn(input, currentTeam);
            if (state.isTurnSuccess()) {
                currentTeam = currentTeam.convert();
                gameRepository.update(gameId, board, currentTeam);
            }
        }
    }

    private GameState progressTurn(String input, Team currentTeam) {
        if (input.equals(END_COMMAND)) {
            printScoreResult();
            OutputView.printGameEnd();
            return GameState.FINISHED;
        }
        return processTurn(input, currentTeam);
    }

    private GameState processTurn(String input, Team currentTeam) {
        try {
            Map<Position, Piece> updatedBoard = movePiece(input, currentTeam);
            if (board.isGeneralCaptured()) {
                OutputView.printBoard(updatedBoard);
                printScoreResult();
                OutputView.printWinner(currentTeam);
                return GameState.FINISHED;
            }
            OutputView.printBoard(updatedBoard);
            return GameState.TURN_SUCCESS;
        } catch (IllegalArgumentException exception) {
            OutputView.printError(exception.getMessage());
            return GameState.TURN_FAILED;
        }
    }

    private void printScoreResult() {
        ScoreResult result = board.calculateScoreResult();
        OutputView.printScore(result.hanScore(), result.choScore());
        if (result.isHanWin()) {
            OutputView.printScoreWinner(Team.HAN);
            return;
        }
        if (result.isChoWin()) {
            OutputView.printScoreWinner(Team.CHO);
            return;
        }
        OutputView.printDraw();
    }

    private Map<Position, Piece> movePiece(String input, Team currentTeam) {
        List<String> positions = parsePositions(input);
        return board.move(
                Position.from(positions.getFirst()),
                Position.from(positions.getLast()),
                currentTeam);
    }

    private List<String> parsePositions(String input) {
        List<String> positions = List.of(input.split(" "));
        validateInputSize(positions);
        return positions;
    }

    private void validateInputSize(List<String> positions) {
        if (positions.size() != POSITION_INPUT_SIZE) {
            throw new IllegalArgumentException("[ERROR] 이동 좌표는 출발과 도착 2개를 입력해야 합니다.");
        }
    }
}
