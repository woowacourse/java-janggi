package janggi;

import janggi.domain.Position;
import janggi.domain.Turn;
import janggi.domain.board.Board;
import janggi.domain.board.ElephantFormation;
import janggi.domain.board.InitialPiecePlacement;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
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
            ElephantSetUpFormat elephantSetUpFormat = InputView.readElephantSettingCommand(camp);
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
            Camp camp = turn.currentTurn();
            RetryHandler.retryOnInvalidInput(() -> playTurn(board, camp));
            OutputView.printBoard(toPiecePositions(board.getBoard()));
            if (board.isRivalGeneralKilled(turn)) {
                break;
            }
            turn.finishTurn();
        }
        OutputView.printWinner(turn.currentTurn());
    }
    
    private void playTurn(Board board, Camp camp) {
        Position source = readSource(board, camp);
        Position destination = readDestination(board, source, camp);
        board.movePiece(source, destination, camp);
    }

    private Position readSource(Board board, Camp camp) {
        return RetryHandler.retryOnInvalidInput(() -> {
            Position source = Position.from(InputView.readSource(camp));
            board.validateSource(source, camp);
            return source;
        });
    }

    private Position readDestination(Board board, Position source, Camp camp) {
        return RetryHandler.retryOnInvalidInput(() -> {
            Position destination = Position.from(InputView.readDestination());
            board.validateDestination(destination, source, camp);
            return destination;
        });
    }
}
