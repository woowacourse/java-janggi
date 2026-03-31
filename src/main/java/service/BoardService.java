package service;

import repository.BoardRepository;
import repository.GameRoomRepository;
import repository.GameStateRepository;

public class BoardService {

    private final BoardRepository boardRepository;
    private final GameRoomRepository gameRoomRepository;
    private final GameStateRepository gameStateRepository;

    public BoardService(BoardRepository boardRepository, GameRoomRepository gameRoomRepository,
                        GameStateRepository gameStateRepository) {
        this.boardRepository = boardRepository;
        this.gameRoomRepository = gameRoomRepository;
        this.gameStateRepository = gameStateRepository;
    }
}
