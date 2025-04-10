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

    public void play() {
        // todo 1. 게임 방 정보를 불러온다.
        List<String> rooms = gameRoomDao.findAllGameRoom();
        output.printGameRooms(rooms);

        // todo 2. 입장하고자 하는 게임 방의 번호를 입력받는다.
            // todo 2-1. 해당 게임 방이 존재하지 하면 해당 정보를 이용하여 게임 생성.
            // todo 2-1. 해당 게임 방이 존재하지 않으면 새로 만든다.
        String roomName = input.readGameRoomName();

        GameRoom gameRoom;

        if(gameRoomDao.existsGameRoom(roomName)) {
            gameRoom = gameRoomDao.findByRoomName(roomName);
        } else {
            gameRoom = new GameRoom(roomName, new Turn(Team.CHO));
            gameRoomDao.saveGameRoom(gameRoom);
        }

        // todo 3. 게임 방에 입장하여 게임 데이터를 불러온다.
            // todo 3-1. 게임 정보가 존재하지 않으면 초기화하여 게임 시작
            // todo 3-2. 게임 정보가 존재하면 이어서 게임 시작
            // todo 3-3. 게임이 종료되면 해당 방의 게임 정보 삭제

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
        while (!board.isGameOver()) {
            dropPiece(output, input, board, gameRoom);
        }
        endGame(output, board, gameRoom);
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
