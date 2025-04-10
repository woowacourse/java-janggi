package janggi.game;

import janggi.board.Board;
import janggi.board.TableOption;
import janggi.board.Turn;
import janggi.dao.BoardDao;
import janggi.dao.GameRoomDao;
import janggi.piece.Piece;
import janggi.piece.PieceGenerator;
import janggi.position.Position;
import janggi.team.Team;
import janggi.view.Input;
import janggi.view.Output;

import java.util.List;
import java.util.Map;

public class GameManager {
    private final Input input;
    private final Output output;
    private final BoardDao boardDao;
    private final GameRoomDao gameRoomDao;

    public GameManager(Input input, Output output, BoardDao boardDao, GameRoomDao gameRoomDao) {
        this.input = input;
        this.output = output;
        this.boardDao = boardDao;
        this.gameRoomDao = gameRoomDao;
    }

    public void progress() {
        GameRoom gameRoom = generateGameRoom();
        Board board = initialBoard(gameRoom);
        playGame(board, gameRoom);
        endGame(output, board, gameRoom);
    }

    private void playGame(Board board, GameRoom gameRoom) {
        while (!board.isGameOver()) {
            dropPiece(output, input, board, gameRoom);
        }
    }

    private GameRoom generateGameRoom() {
        List<String> rooms = gameRoomDao.findAllGameRoom();
        output.printGameRooms(rooms);
        String roomName = input.readGameRoomName();
        GameRoom gameRoom;
        if(gameRoomDao.existsGameRoom(roomName)) {
            gameRoom = gameRoomDao.findByRoomName(roomName);
        } else {
            gameRoom = new GameRoom(roomName, new Turn(Team.CHO));
            gameRoomDao.saveGameRoom(gameRoom);
        }
        return gameRoom;
    }

    private Board initialBoard(GameRoom gameRoom) {
        Map<Position, Piece> initialPieces;
        if (boardDao.existsBoardPieceByRoomName(gameRoom.getRoomName())) {
            System.out.println("진행 중인 게임 데이터를 불러옵니다");
            initialPieces = boardDao.findAllBoardPieceByRoomName(gameRoom.getRoomName());
        } else {
            initialPieces = generateInitialPieces(input);
            boardDao.saveAllBoardPiece(initialPieces, gameRoom);
        }

        Board board = new Board(initialPieces);
        output.printBoard(board.getLocatedPieces());
        return board;
    }

    private Map<Position, Piece> generateInitialPieces(Input input) {
        TableOption choTableOption = input.readTableOption(Team.CHO);
        TableOption hanTableOption = input.readTableOption(Team.HAN);
        return new PieceGenerator().generateInitialPieces(hanTableOption, choTableOption);
    }

    private void dropPiece(Output output, Input input, Board board, GameRoom gameRoom) {
        try {
            output.printTurn(gameRoom.getTurnTeam());
            output.printScore(board.calculateScore(Team.CHO), board.calculateScore(Team.HAN));
            Map.Entry<Position, Position> moveableInfo = input.readMoveablePiece();

            Position startPosition = moveableInfo.getKey();
            Position arrivedPosition = moveableInfo.getValue();

            board.movePiece(gameRoom, startPosition, arrivedPosition);

            boardDao.deletePieceByPositionAndRoomName(arrivedPosition,gameRoom);
            boardDao.updateBoardPiece(gameRoom,startPosition, arrivedPosition);

            output.printBoard(boardDao.findAllBoardPieceByRoomName(gameRoom.getRoomName()));

            gameRoomDao.updateTurn(gameRoom.getRoomName(), gameRoom.turnOver());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void endGame(Output output, Board board, GameRoom gameRoom) {
        output.printGameResult(board.extractWinnerKing());
        boardDao.deleteAllByRoomName(gameRoom);
    }
}
