package janggi;

import janggi.board.Board;
import janggi.board.TableOption;
import janggi.board.Turn;
import janggi.dao.BoardDao;
import janggi.dao.DatabaseConnector;
import janggi.dao.TurnDao;
import janggi.piece.Piece;
import janggi.piece.PieceGenerator;
import janggi.position.Position;
import janggi.team.Team;
import janggi.view.Input;
import janggi.view.Output;

import java.util.Map;

public class JanggiGame {

    public static void main(String[] args) {
        Input input = new Input();
        Output output = new Output();
        DatabaseConnector connector = new DatabaseConnector();

        BoardDao boardDao = new BoardDao(connector);
        TurnDao turnDao = new TurnDao(connector);

        Map<Position, Piece> initialPieces;
        Turn turn;
        if (boardDao.existsBoardPiece()) {
            System.out.println("진행 중인 게임 데이터를 불러옵니다");
            initialPieces = boardDao.findAllBoardPiece();
            turn = new Turn(turnDao.findCurrentTurn());
        } else {
            initialPieces = generateInitialPieces(input);
            boardDao.saveAllBoardPiece(initialPieces);
            turn = new Turn();
            turnDao.saveTurn(turn.getTurn());
        }

        Board board = new Board(initialPieces);
        output.printBoard(board.getLocatedPieces());
        while (!board.isGameOver()) {
            turn = dropPiece(output, input, board, turn, boardDao, turnDao);
        }
        endGame(output, board, boardDao, turnDao);
    }

    private static Map<Position, Piece> generateInitialPieces(Input input) {
        TableOption choTableOption = input.readTableOption(Team.CHO);
        TableOption hanTableOption = input.readTableOption(Team.HAN);
        return new PieceGenerator().generateInitialPieces(hanTableOption, choTableOption);
    }

    private static Turn dropPiece(Output output, Input input, Board board, Turn turn, BoardDao boardDao, TurnDao turnDao) {
        try {
            output.printTurn(turn);
            output.printScore(board.calculateScore(Team.CHO), board.calculateScore(Team.HAN));
            Map.Entry<Position, Position> moveableInfo = input.readMoveablePiece();

            Position startPosition = moveableInfo.getKey();
            Position arrivedPosition = moveableInfo.getValue();

            board.movePiece(turn, startPosition, arrivedPosition);

            boardDao.deletePieceByPosition(arrivedPosition);
            boardDao.updateBoardPiece(startPosition, arrivedPosition);

            output.printBoard(boardDao.findAllBoardPiece());

            Turn nextTurn = turn.turnOver();
            turnDao.updateTurn(turn.getTurn(), nextTurn.getTurn());
            return nextTurn;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return turn;
        }
    }

    private static void endGame(Output output, Board board, BoardDao boardDao, TurnDao turnDao) {
        output.printGameResult(board.extractWinnerKing());
        boardDao.deleteAll();
        turnDao.deleteAll();
    }
}
