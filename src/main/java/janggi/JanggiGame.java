package janggi;

import janggi.domain.Position;
import janggi.domain.Turn;
import janggi.domain.board.Board;
import janggi.domain.board.ElephantFormation;
import janggi.domain.board.InitialPiecePlacement;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.dto.CampDto;
import janggi.dto.PiecePositionDto;
import janggi.util.RetryHandler;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.format.ElephantSetUpFormat;
import java.util.List;
import java.util.Map;

public class JanggiGame {

    public void run() {
        Board board = createBoard();
        OutputView.printBoard(toPiecePositions(board.getBoard()));
        play(board);
    }

    private Board createBoard() {
        ElephantFormation hanElephantFormation = readElephantFormation(Camp.HAN);
        ElephantFormation choElephantFormation = readElephantFormation(Camp.CHO);
        return InitialPiecePlacement.initialize(hanElephantFormation, choElephantFormation);
    }

    private ElephantFormation readElephantFormation(Camp camp) {
        return RetryHandler.retryOnInvalidInput(() -> {
            ElephantSetUpFormat elephantSetUpFormat = InputView.readElephantSettingCommand(CampDto.from(camp));
            return elephantSetUpFormat.toElephantFormation(camp);
        });
    }

    private List<PiecePositionDto> toPiecePositions(Map<Position, Piece> boardState) {
        return boardState.entrySet().stream()
                .map(entry -> PiecePositionDto.of(entry.getKey(), entry.getValue()))
                .toList();
    }

    private void play(Board board) {
        Turn turn = new Turn();
        while (true) {
            RetryHandler.retryOnInvalidInput(() -> playTurn(board, turn));
            OutputView.printBoard(toPiecePositions(board.getBoard()));
        }
    }

    private void playTurn(Board board, Turn turn) {
        Camp camp = turn.currentTurn();
        Position source = readSource(board, camp);
        Position destination = RetryHandler.retryOnInvalidInput(() -> Position.from(InputView.readDestination()));
        board.movePiece(source, destination, camp);
        turn.finishTurn();
    }

    private Position readSource(Board board, Camp camp) {
        return RetryHandler.retryOnInvalidInput(() -> {
            List<Integer> rawPosition = InputView.readSource(CampDto.from(camp));
            Position source = Position.from(rawPosition);
            board.validateCampTurn(source, camp);
            return source;
        });
    }
}
