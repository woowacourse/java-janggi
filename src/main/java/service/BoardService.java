package service;

import domain.board.Board;
import domain.board.BoardStateFactory;
import domain.piece.Side;
import domain.position.Movement;
import dto.BoardResponseDto;
import repository.BoardRepository;

public class BoardService {
    private final Board board;
    private final BoardRepository boardRepository;

    public BoardService(Board board, BoardRepository boardRepository) {
        this.board = board;
        this.boardRepository = boardRepository;
    }

    public boolean isFinished() {
        return board.isFinished();
    }

    public void move(Movement movement, Side side) {
        board.move(movement.startPosition(), movement.endPosition(), side);
        boardRepository.save(board);
    }

    public BoardResponseDto findState() {
        return board.findState();
    }

    public void initialState(BoardStateFactory boardStateFactory) {
        board.initialState(boardStateFactory.create());
        boardRepository.save(board);
    }
}
