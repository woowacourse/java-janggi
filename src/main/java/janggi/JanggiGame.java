package janggi;

import janggi.board.Board;
import janggi.board.TableOption;
import janggi.dao.BoardDao;
import janggi.piece.Piece;
import janggi.piece.PieceGenerator;
import janggi.position.Position;
import janggi.team.Team;
import janggi.view.Input;
import janggi.view.Output;

import java.util.List;
import java.util.Map;

public class JanggiGame {
    public static void main(String[] args) {
        Input input = new Input();
        Output output = new Output();
        BoardDao boardDao = new BoardDao();

        // todo 장기말 데이터가 존재하지 않으면 초기화부터 진행한다.
        // todo 장기말 데이터가 존재하면 장기말 데이터를 모두 가져와서 게임을 이어서 진행한다.
            // todo 해당 게임의 턴에 따라서 게임을 진행한다.

        List<Piece> initialPieces;

        if (boardDao.existsBoardPiece()) {
            initialPieces = generateInitialPieces(input);
        } else {
            boardDao.findAllBoardPiece();
            boardDao.addAllBoardPiece(initialPieces);
        }


        // todo 게임 상태가 진행 중 상태가 아니면 초기값에 맞게 초기화
        // todo 기물이 이동하면 기물 정보를 업데이트 해준다.
        // todo 기물 이동 완료되면 턴을 업데이트한다.
        // todo 게임이 종료되는 조건을 만나면 보유하고 있는 모든 장기말 데이터 삭제

        Team turn = Team.CHO;
        Board board = new Board(initialPieces);
        output.printBoard(board.extractLocatedLivePicecs());
        while (board.isGameOver()) {
            try {
                output.printScore(board.calculateScore(Team.CHO), board.calculateScore(Team.HAN));
                Map.Entry<Position, Position> moveableInfo = input.readMoveablePiece();
                board.move(turn, moveableInfo.getKey(), moveableInfo.getValue(), boardDao);
                output.printBoard(board.extractLocatedLivePicecs());
                turn = changeTurn(turn);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        output.printGameResult(board.extractWinnerKing());
    }

    public static Team changeTurn(Team turn) {
        if (turn == Team.CHO) {
            return Team.HAN;
        }
        return Team.CHO;
    }

    public static List<Piece> generateInitialPieces(Input input) {
        TableOption choTableOption = input.readTableOption(Team.CHO);
        TableOption hanTableOption = input.readTableOption(Team.HAN);
        return new PieceGenerator().generateInitialPieces(hanTableOption, choTableOption);
    }

}
