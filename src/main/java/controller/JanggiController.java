package controller;

import domain.board.Board;
import domain.board.ElephantSetup;
import domain.piece.Position;
import domain.piece.Team;
import domain.player.Player;
import dto.PieceInfoDto;
import dto.PieceInfosDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Player choPlayer = setupChoPlayer();
        Player hanPlayer = setupHanPlayer();

        Board board = initBoard();

        outputView.printBoardWithPieces(board.getAllPieceInfos());

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
        retry(() -> {
            movePiece(board, team);
            outputView.printBoardWithPieces(board.getAllPieceInfos());
        });
    }

    private void movePiece(Board board, Team team) {
        Position from = selectPieceToMove(board, team).getKey();
        Position to = selectPositionToMove(board, from);

        board.move(from, to, team);
    }

    private Entry<Position, PieceInfoDto> selectPieceToMove(Board board, Team team) {
        PieceInfosDto teamPieces = board.getPieceInfosBy(team);
        List<Entry<Position, PieceInfoDto>> entryList = new ArrayList<>(teamPieces.pieceInfos().entrySet());

        outputView.printChoosePieceToMovePrompt(entryList);
        int pieceIndex = inputView.readPieceNumber() - 1;

        if (pieceIndex < 0 || pieceIndex >= entryList.size()) {
            throw new IllegalArgumentException();
        }

        return entryList.get(pieceIndex);
    }

    private Position selectPositionToMove(Board board, Position from) {
        List<Position> movablePositions = board.getMovablePositions(from);

        if (movablePositions.isEmpty()) {
            throw new IllegalArgumentException();
        }

        outputView.printChoosePositionToMovePrompt(movablePositions);
        int positionIndex = inputView.readPositionNumber() - 1;

        if (positionIndex < 0 || positionIndex >= movablePositions.size()) {
            throw new IllegalArgumentException();
        }

        return movablePositions.get(positionIndex);
    }

    private void retry(Runnable callback) {
        while (true) {
            try {
                callback.run();
                return;
            } catch (IllegalArgumentException exception) {
                outputView.printInvalidNumberInput();
            }
        }
    }

    private <T> T retry(Supplier<T> callback) {
        while (true) {
            try {
                return callback.get();
            } catch (IllegalArgumentException exception) {
                outputView.printInvalidNumberInput();
            }
        }
    }
}
