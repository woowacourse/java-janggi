package janggi;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.board.StandardBoardInitializer;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.dto.PiecePositionDto;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class JanggiGame {

    private final Queue<Camp> turns = new ArrayDeque<>(List.of(Camp.CHO, Camp.HAN));

    public void run() {
        Board board = createBoard();
        OutputView.printBoard(toPiecePositions(board.getBoard()));
        play(board);
    }

    private Board createBoard() {
        String hanCommand = InputView.readElephantSettingCommand(Camp.HAN);
        String choCommand = InputView.readElephantSettingCommand(Camp.CHO);
        BoardInitializer initializer = new StandardBoardInitializer(hanCommand, choCommand);
        return new Board(initializer);
    }

    private void play(Board board) {
        int playCount = 100;
        while (playCount-- > 0) {
            Camp turn = turns.poll();
            OutputView.printBoard(playTurn(board, turn));
            turns.offer(turn);
        }
    }

    private List<PiecePositionDto> playTurn(Board board, Camp camp) {
        Position from = toPosition(InputView.readStartPosition(camp));
        Position to = toPosition(InputView.readGoalPosition());
        Map<Position, Piece> boardState = board.movePiece(from, to);
        return toPiecePositions(boardState);
    }

    private Position toPosition(List<Integer> rawPosition) {
        return new Position(rawPosition.get(0), rawPosition.get(1));
    }

    private List<PiecePositionDto> toPiecePositions(Map<Position, Piece> boardState) {
        return boardState.entrySet().stream()
                .map(entry -> PiecePositionDto.of(entry.getKey(), entry.getValue()))
                .toList();
    }
}
