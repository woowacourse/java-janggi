package controller;

import domain.board.Board;
import domain.board.ElephantSetup;
import domain.board.Position;
import domain.piece.Team;
import domain.player.Player;
import dto.PieceInfoDto;
import dto.PieceInfosDto;
import dto.PositionDto;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private static final int USER_INPUT_START_INDEX = 1;

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Player choPlayer = retry(this::setupChoPlayer);
        Player hanPlayer = retry(this::setupHanPlayer);

        Board board = initBoard();

        outputView.printBoardWithPieces(PieceInfosDto.from(board));

        // TODO: 게임 종료 구현 (사이클 2)
        while (true) {
            processTurn(board, Team.CHO);
            processTurn(board, Team.HAN);
        }
    }

    private Player setupHanPlayer() {
        outputView.printEnterHanPlayerNamePrompt();
        String hanPlayerName = inputView.readPlayerName();
        return Player.han(hanPlayerName);
    }

    private Player setupChoPlayer() {
        outputView.printEnterChoPlayerNamePrompt();
        String choPlayerName = inputView.readPlayerName();
        return Player.cho(choPlayerName);
    }

    private Board initBoard() {
        ElephantSetup choElephantSetup = retry(this::askChoElephantSetup);
        ElephantSetup hanElephantSetup = retry(this::askHanElephantSetup);

        return Board.init(choElephantSetup, hanElephantSetup);
    }

    private ElephantSetup askChoElephantSetup() {
        outputView.printChooseChoElephantSetupPrompt();
        return inputView.readElephantSetup();
    }

    private ElephantSetup askHanElephantSetup() {
        outputView.printChooseHanElephantSetupPrompt();
        return inputView.readElephantSetup();
    }

    private void processTurn(Board board, Team team) {
        retry(() -> process(board, team));
    }

    private void process(Board board, Team team) {
        Map<Position, List<Position>> moveOptions = board.getMoveOptionsFor(team);

        if (moveOptions.isEmpty()) {
            outputView.printNoMovablePiecePrompt();
            return;
        }

        Position from = choosePieceToMove(board, moveOptions);
        Position to = choosePositionToMove(moveOptions, from);

        board.move(from, to, team);

        outputView.printBoardWithPieces(PieceInfosDto.from(board));
    }

    private Position choosePieceToMove(Board board, Map<Position, List<Position>> moveOptions) {
        List<Position> positions = moveOptions.keySet()
                .stream()
                .toList();

        List<PieceInfoDto> pieceInfos = positions.stream()
                .map(position -> PieceInfoDto.of(board.getPieceAt(position), position))
                .toList();

        outputView.printChoosePieceToMovePrompt(pieceInfos);
        int pieceIndex = toZeroBasedIndex(inputView.readPieceNumber());

        if (pieceIndex < 0 || pieceIndex >= positions.size()) {
            throw new IllegalArgumentException("범위 벗어난 입력");
        }

        return positions.get(pieceIndex);
    }

    private Position choosePositionToMove(Map<Position, List<Position>> moveOptions, Position from) {
        List<Position> movablePositions = moveOptions.get(from);
        if (movablePositions.isEmpty()) {
            throw new IllegalArgumentException("이동 가능한 위치 없음");
        }

        List<PositionDto> movablePositionsDto = movablePositions.stream()
                .map(PositionDto::from)
                .toList();
        outputView.printChoosePositionToMovePrompt(movablePositionsDto);

        int positionIndex = toZeroBasedIndex(inputView.readPositionNumber());

        if (positionIndex < 0 || positionIndex >= movablePositions.size()) {
            throw new IllegalArgumentException("범위 벗어난 입력");
        }

        return movablePositions.get(positionIndex);
    }

    private int toZeroBasedIndex(int userInputNumber) {
        return userInputNumber - USER_INPUT_START_INDEX;
    }

    private void retry(Runnable callback) {
        while (true) {
            try {
                callback.run();
                return;
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }

    private <T> T retry(Supplier<T> callback) {
        while (true) {
            try {
                return callback.get();
            } catch (IllegalArgumentException exception) {
                outputView.printExceptionMessage(exception.getMessage());
            }
        }
    }
}
