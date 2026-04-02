package controller;

import domain.board.ElephantSetup;
import domain.board.Position;
import domain.game.JanggiGame;
import domain.piece.Team;
import domain.player.Player;
import dto.PieceInfoDto;
import dto.PieceInfosDto;
import dto.PositionDto;
import java.util.List;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Player choPlayer = retry(this::initChoPlayer);
        Player hanPlayer = retry(this::initHanPlayer);

        ElephantSetup choElephantSetup = retry(this::initChoElephantSetup);
        ElephantSetup hanElephantSetup = retry(this::initHanElephantSetup);

        JanggiGame janggiGame = JanggiGame.init(choElephantSetup, hanElephantSetup);

        outputView.printBoardWithPieces(PieceInfosDto.from(janggiGame));

        while (true) {
            processTurn(janggiGame, Team.CHO);
            processTurn(janggiGame, Team.HAN);
        }
    }

    private Player initChoPlayer() {
        outputView.printEnterHanPlayerNamePrompt();
        String hanPlayerName = inputView.readPlayerName();
        return Player.han(hanPlayerName);
    }

    private Player initHanPlayer() {
        outputView.printEnterChoPlayerNamePrompt();
        String choPlayerName = inputView.readPlayerName();
        return Player.cho(choPlayerName);
    }

    private ElephantSetup initChoElephantSetup() {
        outputView.printChooseChoElephantSetupPrompt();
        return inputView.readElephantSetup();
    }

    private ElephantSetup initHanElephantSetup() {
        outputView.printChooseHanElephantSetupPrompt();
        return inputView.readElephantSetup();
    }

    private void processTurn(JanggiGame janggiGame, Team team) {
        retry(() -> process(janggiGame, team));
    }

    private void process(JanggiGame janggiGame, Team team) {
        List<Position> piecePositions = janggiGame.getPiecePositionsFor(team);

        Position from = selectPieceToMove(janggiGame, piecePositions);
        List<Position> movablePositions = janggiGame.getMovablePositions(from);

        Position to = selectPositionToMove(movablePositions);

        janggiGame.move(from, to);

        outputView.printBoardWithPieces(PieceInfosDto.from(janggiGame));
    }

    private Position selectPieceToMove(JanggiGame janggiGame, List<Position> piecePositions) {
        List<PieceInfoDto> pieceInfos = piecePositions.stream()
                .map(position -> PieceInfoDto.of(janggiGame.getPieceAt(position), position))
                .toList();

        outputView.printChoosePieceToMovePrompt(pieceInfos);
        int pieceIndex = inputView.readPieceIndex();

        if (pieceIndex < 0 || pieceIndex >= pieceInfos.size()) {
            throw new IllegalArgumentException("범위 벗어난 입력");
        }

        return piecePositions.get(pieceIndex);
    }

    private Position selectPositionToMove(List<Position> movablePositions) {
        List<PositionDto> movablePositionsDto = movablePositions.stream()
                .map(PositionDto::of)
                .toList();

        outputView.printChoosePositionToMovePrompt(movablePositionsDto);

        int positionIndex = inputView.readPositionIndex();

        if (positionIndex < 0 || positionIndex >= movablePositions.size()) {
            throw new IllegalArgumentException("범위 벗어난 입력");
        }

        return movablePositions.get(positionIndex);
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
