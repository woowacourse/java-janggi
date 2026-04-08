package controller;

import domain.board.ElephantSetup;
import domain.board.Position;
import domain.game.JanggiGame;
import domain.piece.Team;
import domain.player.Player;
import domain.state.ChoPlayingState;
import domain.state.GameState;
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
import view.InputView;
import view.OutputView;

public class JanggiController {

    private static final GameState FIRST_GAME_STATE = new ChoPlayingState();

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Player choPlayer = retry(() -> initPlayerFor(Team.CHO));
        Player hanPlayer = retry(() -> initPlayerFor(Team.HAN));

        ElephantSetup choElephantSetup = retry(() -> initElephantSetupFor(Team.CHO));
        ElephantSetup hanElephantSetup = retry(() -> initElephantSetupFor(Team.HAN));

        JanggiGame janggiGame = JanggiGame.init(choElephantSetup, hanElephantSetup, FIRST_GAME_STATE);

        printJanggiBoard(janggiGame);

        while (!janggiGame.isFinished()) {
            processTurn(janggiGame);
        }

        Team winnerTeam = janggiGame.getWinnerTeam();
        outputView.printWinner(TeamNameDto.of(winnerTeam));
    }

    private Player initPlayerFor(Team team) {
        outputView.printEnterPlayerNamePrompt(TeamNameDto.of(team));
        String playerName = inputView.readPlayerName();
        return Player.of(playerName, team);
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

    private void printJanggiBoard(final JanggiGame janggiGame) {
        PiecesDto pieceInfos = getPieceInfos(janggiGame);
        outputView.printJanggiBoard(pieceInfos);
    }

    private PiecesDto getPieceInfos(final JanggiGame janggiGame) {
        Map<PositionDto, PieceInfoDto> pieces = janggiGame.getPieces().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> PositionDto.of(entry.getKey()),
                        entry -> PieceInfoDto.of(entry.getValue().getPieceType(), entry.getValue().getTeam())
                ));
        return PiecesDto.of(pieces);
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
