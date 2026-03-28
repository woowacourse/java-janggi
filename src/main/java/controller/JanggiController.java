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
        outputView.printEnterChoPlayerNamePrompt();
        String choPlayerName = inputView.readPlayerName();
        Player choPlayer = Player.cho(choPlayerName);

        outputView.printEnterHanPlayerNamePrompt();
        String hanPlayerName = inputView.readPlayerName();
        Player hanPlayer = Player.han(hanPlayerName);

        outputView.printChooseChoElephantSetupPrompt();
        ElephantSetup choElephantSetup = inputView.readElephantSetup();

        outputView.printChooseHanElephantSetupPrompt();
        ElephantSetup hanElephantSetup = inputView.readElephantSetup();

        Board board = Board.init(choElephantSetup, hanElephantSetup);

        PieceInfosDto pieceInfos = board.getAllPieceInfos();
        outputView.printBoardWithPieces(pieceInfos);

        processTurn(board, Team.CHO);
        processTurn(board, Team.HAN);
    }

    private void processTurn(Board board, Team team) {
        while (true) {
            PieceInfosDto teamPieces = board.getPieceInfosBy(team);
            List<Entry<Position, PieceInfoDto>> entryList = new ArrayList<>(teamPieces.pieceInfos().entrySet());

            outputView.printChoosePieceToMovePrompt(entryList);
            int pieceIndex = inputView.readPieceNumber() - 1;

            if (pieceIndex < 0 || pieceIndex >= entryList.size()) {
                outputView.printInvalidNumberInput();
                continue;
            }

            Position selectedPiecePos = entryList.get(pieceIndex).getKey();
            List<Position> movablePositions = board.getMovablePositions(selectedPiecePos);

            if (movablePositions.isEmpty()) {
                outputView.printNoMovablePositionMessage();
                continue;
            }

            outputView.printChoosePositionToMovePrompt(movablePositions);
            int positionIndex = inputView.readPositionNumber() - 1;

            if (positionIndex < 0 || positionIndex >= movablePositions.size()) {
                outputView.printInvalidNumberInput();
                continue;
            }

            Position targetPos = movablePositions.get(positionIndex);

            board.move(selectedPiecePos, targetPos, team);
            outputView.printBoardWithPieces(board.getAllPieceInfos());
            break;
        }
    }
}
