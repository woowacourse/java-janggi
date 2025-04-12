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
        JanggiGame janggiGame = new JanggiGame(boardDao, gameRoomDao);
        output.printGameRooms(janggiGame.findAllGameRoom());

        GameRoom gameRoom = janggiGame.selectGameRoom(input.readGameRoomName());

        Board board = initialBoard(gameRoom, janggiGame);

        playGame(board, gameRoom);

        endGame(output, board, gameRoom);
    }

    private Board initialBoard(GameRoom gameRoom, JanggiGame janggiGame) {
        Board board;
        if (janggiGame.existsBoardPieceByRoomName(gameRoom)) {
            System.out.println("진행 중인 게임 데이터를 불러옵니다");
            board = janggiGame.makeSavedBoard(gameRoom);
        } else {
            board = janggiGame.makeInitialBoard(gameRoom, input.readTableOption(Team.CHO), input.readTableOption(Team.HAN));
        }
        output.printBoard(board.getLocatedPieces());
        return board;
    }

    private void playGame(Board board, GameRoom gameRoom) {
        while (!board.isGameOver()) {
            dropPiece(output, input, board, gameRoom);
        }
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
