package service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import config.ConnectionManager;
import domain.Board;
import domain.enums.Country;
import domain.JanggiGame;
import domain.enums.MaSang;
import domain.enums.PieceType;
import domain.Position;
import domain.state.State;
import repository.JanggiGameRepository;
import service.dto.BoardDto;
import service.dto.ColorDto;
import service.dto.PositionDto;
import service.dto.ScoreDto;

public class JanggiService {

    public Board createBoard(MaSang choMasangChoose, MaSang hanMasangChoose) {
        List<PieceType> masang = new ArrayList<>(choMasangChoose.getPieces());
        masang.addAll(hanMasangChoose.getPieces());
        return new Board(masang);
    }

    public JanggiGame createJanggiGame(Board board) {
        return new JanggiGame(board);
    }

    public BoardDto buildBoardDto(int gameId) {
        Connection conn = ConnectionManager.getConnection();
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
        JanggiGame janggiGame = janggiGameRepository.findByGameId(gameId);
        ConnectionManager.closeConnection(conn);

        Board board = janggiGame.getBoard();
        List<BoardDto.Row> boardAll = new ArrayList<>();
        for (int x = 1; x <= Position.MAX_ROW; x++) {
            List<String> values = new ArrayList<>();
            for (int y = 1; y <= Position.MAX_COL; y++) {
                PieceType pieceType = board.getPiece(Position.create(x, y));
                values.add(pieceType.getName());
            }
            boardAll.add(new BoardDto.Row(values));
        }
        return new BoardDto(boardAll);
    }

    public ColorDto buildColorDto(int gameId)  {
        Connection conn = ConnectionManager.getConnection();
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
        JanggiGame janggiGame = janggiGameRepository.findByGameId(gameId);
        ConnectionManager.closeConnection(conn);

        Board board = janggiGame.getBoard();
        List<ColorDto.Row> boardAll = new ArrayList<>();
        for (int x = 1; x <= Position.MAX_ROW; x++) {
            List<String> values = new ArrayList<>();
            for (int y = 1; y <= Position.MAX_COL; y++) {
                Country country = board.getPieceCountry(Position.create(x, y));
                values.add(country.getColor());
            }
            boardAll.add(new ColorDto.Row(values));
        }
        return new ColorDto(boardAll);
    }

    public List<PositionDto> getPiecePositions(int gameId, PieceType pieceType) {
        Connection conn = ConnectionManager.getConnection();
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
        JanggiGame janggiGame = janggiGameRepository.findByGameId(gameId);
        ConnectionManager.closeConnection(conn);

        List<PositionDto> positionDtos = new ArrayList<>();
        for (Position position : janggiGame.getPiecesNowPosition(pieceType)) {
            positionDtos.add(new PositionDto(position.getX(), position.getY()));
        }
        return positionDtos;
    }

    public void applyMove(Position start, Position end, int gameId) {
        Connection conn = ConnectionManager.getConnection();
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
        JanggiGame janggiGame = janggiGameRepository.findByGameId(gameId);

        janggiGame.play(start, end);

        janggiGameRepository.saveMoveReuslt(janggiGame,gameId);
        ConnectionManager.closeConnection(conn);
    }

    public ScoreDto buildScoreDto(int gameId) {
        Connection conn = ConnectionManager.getConnection();
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
        JanggiGame janggiGame = janggiGameRepository.findByGameId(gameId);
        ConnectionManager.closeConnection(conn);

        double choScore = janggiGame.calculateScore(Country.CHO);
        double hanScore = janggiGame.calculateScore(Country.HAN);
        return new ScoreDto(choScore, hanScore);
    }

    public Country getFinalWinner(int gameId) {
        Connection conn = ConnectionManager.getConnection();
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
        JanggiGame janggiGame = janggiGameRepository.findByGameId(gameId);
        ConnectionManager.closeConnection(conn);
        return janggiGame.calculateWinner();
    }

    public List<PositionDto> buildAvailabelPositions(int gameId,Position start) {
        Connection conn = ConnectionManager.getConnection();
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
        Board board = janggiGameRepository.findBoardByGameId(gameId);
        ConnectionManager.closeConnection(conn);

        List<PositionDto> positionDtos = new ArrayList<>();
        for (Position position : board.findAvailablePositions(start)){
            positionDtos.add(new PositionDto(position.getX(), position.getY()));
        }
        return positionDtos;
    }

    public int initializeData(JanggiGame janggiGame) {
        Connection conn = ConnectionManager.getConnection();
        ConnectionManager.makeBoardAndPieceDate(conn);

        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
        int gameId= janggiGameRepository.saveGame(janggiGame);
        janggiGameRepository.saveBoard(janggiGame.getBoard(), gameId);

        ConnectionManager.closeConnection(conn);
        return gameId;
    }

    public void validateExistGame(int num) {
        Connection conn = ConnectionManager.getConnection();
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
        janggiGameRepository.validateGame(num);
        ConnectionManager.closeConnection(conn);
    }

    public Country getNowTurnCountry(int gameId)  {
        Connection conn = ConnectionManager.getConnection();
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
        State state = janggiGameRepository.findStateByGameId(gameId);
        ConnectionManager.closeConnection(conn);
        return state.getCountry();
    }

    public boolean isGameOver(int gameId)  {
        Connection conn = ConnectionManager.getConnection();
        JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
        JanggiGame janggiGame = janggiGameRepository.findByGameId(gameId);
        ConnectionManager.closeConnection(conn);
        return janggiGame.isGameOver();
    }
}
