package controller;

import domain.board.ElephantSetup;
import domain.board.Position;
import domain.game.JanggiGame;
import domain.piece.Team;
import dto.JanggiGameDto;
import dto.PieceInfoDto;
import dto.PiecePositionDto;
import dto.PiecesDto;
import dto.PositionDto;
import dto.ScoreDto;
import dto.TeamNameDto;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import repository.JanggiGameRepository;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private static final int NEW_GAME_OPTION = 1;
    private static final int PREVIOUS_GAME_OPTION = 2;

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiGameRepository janggiGameRepository;

    public JanggiController(
            final InputView inputView,
            final OutputView outputView,
            final JanggiGameRepository janggiGameRepository
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiGameRepository = janggiGameRepository;
    }

    public void run() {
        retry(this::selectGameOption);
    }

    private void selectGameOption() {
        outputView.printPlayNewGameOrPreviousGame();
        int menuSelection = inputView.readNewGameOrPreviousGame();

        if (menuSelection == NEW_GAME_OPTION) {
            startNewGame();
            return;
        }
        if (menuSelection == PREVIOUS_GAME_OPTION) {
            startPreviousGame();
            return;
        }
        throw new IllegalArgumentException("잘못 입력했습니다. 1 또는 2만 입력 가능합니다.");
    }

    private void startNewGame() {
        JanggiGame janggiGame = createNewGame();
        Long gameId = janggiGameRepository.save(janggiGame);
        play(janggiGame, gameId);
    }

    private void startPreviousGame() {
        Long gameId = getPreviousGameId();
        JanggiGame janggiGame = loadPreviousGame(gameId);
        play(janggiGame, gameId);
    }

    private void play(JanggiGame janggiGame, Long gameId) {
        printJanggiBoard(janggiGame);
        while (!janggiGame.isFinished()) {
            processTurn(janggiGame);
            janggiGameRepository.update(gameId, janggiGame);
        }

        Team winnerTeam = janggiGame.getWinnerTeam();
        outputView.printWinner(TeamNameDto.of(winnerTeam));
    }

    private JanggiGame createNewGame() {
        ElephantSetup choElephantSetup = retry(() -> initElephantSetupFor(Team.CHO));
        ElephantSetup hanElephantSetup = retry(() -> initElephantSetupFor(Team.HAN));
        return JanggiGame.init(choElephantSetup, hanElephantSetup);
    }

    private ElephantSetup initElephantSetupFor(Team team) {
        List<ElephantSetup> elephantSetups = ElephantSetup.all();
        List<String> elephantSetupNames = elephantSetups.stream()
                .map(Enum::toString)
                .toList();
        outputView.printChooseElephantSetupPrompt(elephantSetupNames, TeamNameDto.of(team));
        int index = inputView.readElephantSetupIndex();

        validateIndexRange(index, elephantSetups.size());

        return elephantSetups.get(index);
    }

    private Long getPreviousGameId() {
        List<JanggiGameDto> previousGames = janggiGameRepository.findAll();
        if (previousGames.isEmpty()) {
            throw new IllegalArgumentException("이전에 플레이 한 게임이 존재하지 않습니다. 새 게임을 시작해주세요.");
        }

        outputView.printChoosePreviousGameId(previousGames);
        return inputView.readGameId();
    }

    private JanggiGame loadPreviousGame(Long gameId) {
        return janggiGameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
    }

    private void printJanggiBoard(final JanggiGame janggiGame) {
        PiecesDto pieceInfos = getPieceInfos(janggiGame);
        outputView.printJanggiBoard(pieceInfos);
    }

    private PiecesDto getPieceInfos(final JanggiGame janggiGame) {
        return PiecesDto.from(janggiGame);
    }

    private void processTurn(final JanggiGame janggiGame) {
        retry(() -> process(janggiGame));

        printJanggiBoard(janggiGame);
        printScores(janggiGame);
    }

    private void process(final JanggiGame janggiGame) {
        List<Position> piecePositions = janggiGame.getCurrentPlayerPiecePositions();
        Position from = selectPieceToMove(janggiGame, piecePositions);

        List<Position> movablePositions = janggiGame.getMovablePositions(from);
        checkMovablePositionsIsEmpty(movablePositions);

        Position to = selectPositionToMove(movablePositions);

        janggiGame.move(from, to);
    }

    private void checkMovablePositionsIsEmpty(List<Position> movablePositions) {
        if (movablePositions.isEmpty()) {
            throw new IllegalArgumentException("해당 기물은 이동할 수 있는 위치가 없습니다. 다른 기물을 선택해주세요.");
        }
    }

    private Position selectPieceToMove(final JanggiGame janggiGame, final List<Position> positions) {
        List<PiecePositionDto> piecePositions = positions.stream()
                .map(position -> PiecePositionDto.of(
                        janggiGame.getPieceType(position),
                        janggiGame.getTeam(position),
                        position))
                .toList();

        outputView.printChoosePieceToMovePrompt(piecePositions);
        int pieceIndex = inputView.readPieceIndex();

        validateIndexRange(pieceIndex, piecePositions.size());

        return positions.get(pieceIndex);
    }

    private Position selectPositionToMove(final List<Position> movablePositions) {
        List<PositionDto> movablePositionsDto = movablePositions.stream()
                .map(PositionDto::of)
                .toList();

        outputView.printChoosePositionToMovePrompt(movablePositionsDto);
        int positionIndex = inputView.readPositionIndex();

        validateIndexRange(positionIndex, movablePositions.size());

        return movablePositions.get(positionIndex);
    }

    private void validateIndexRange(final int index, final int count) {
        if (index < 0 || index >= count) {
            String message = String.format("선택 가능한 범위를 벗어났습니다. %d 이상, %d 이하의 정수만 입력 가능합니다.", 1, count);
            throw new IllegalArgumentException(message);
        }
    }

    private void printScores(JanggiGame janggiGame) {
        double choScore = janggiGame.getScoreBy(Team.CHO);
        double hanScore = janggiGame.getScoreBy(Team.HAN);

        outputView.printScores(ScoreDto.of(choScore, hanScore));
    }

    private void retry(final Runnable callback) {
        while (true) {
            try {
                callback.run();
                return;
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    private <T> T retry(final Supplier<T> callback) {
        while (true) {
            try {
                return callback.get();
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }
}
