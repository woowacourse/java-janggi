package service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import config.ConnectionManager;
import config.TransactionManager;
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
import service.dto.ResultDto;
import service.dto.ScoreDto;

public class JanggiService {

    private final TransactionManager txManager = new TransactionManager();

    public void applyMove(Position start, Position end, int gameId) {
        txManager.executeWrite(conn -> {
            JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
            JanggiGame janggiGame = janggiGameRepository.findByGameId(gameId);
            janggiGame.play(start, end);
            janggiGameRepository.saveMoveReuslt(janggiGame,gameId);
            return null;
        });
    }

    public int initializeData(JanggiGame janggiGame) {
        return txManager.executeWrite(conn -> {
            JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);

            ConnectionManager.makeBoardAndPieceData(conn);

            int gameId= janggiGameRepository.saveGame(janggiGame);
            janggiGameRepository.saveBoard(janggiGame.getBoard(), gameId);
            return gameId;
        });
    }

    public boolean isGameOver(int gameId)  {
        JanggiGame janggiGame = findGameById(gameId);
        return janggiGame.isGameOver();
    }

    public void validateExistGame(int num) {
        txManager.executeRead(conn -> {
            JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
            janggiGameRepository.validateGame(num);
            return null;
        });
    }

    public Board createBoard(MaSang choMasangChoose, MaSang hanMasangChoose) {
        List<PieceType> masang = new ArrayList<>(choMasangChoose.getPieces());
        masang.addAll(hanMasangChoose.getPieces());
        return new Board(masang);
    }

    public JanggiGame createJanggiGame(Board board) {
        return new JanggiGame(board);
    }

    public ResultDto buildResultDto(int gameId) {
        Board board = findBoardByGameId(gameId);
        return new ResultDto(buildBoardDto(board), buildColorDto(board));
    }

    private BoardDto buildBoardDto(Board board) {
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

    private ColorDto buildColorDto(Board board)  {
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

    public ScoreDto buildScoreDto(int gameId) {
        JanggiGame janggiGame = findGameById(gameId);
        double choScore = janggiGame.calculateScore(Country.CHO);
        double hanScore = janggiGame.calculateScore(Country.HAN);
        return new ScoreDto(choScore, hanScore);
    }

    public List<PositionDto> buildAvailabelPositions(int gameId,Position start) {
        Board board = findBoardByGameId(gameId);
        List<PositionDto> positionDtos = new ArrayList<>();
        for (Position position : board.findAvailablePositions(start)){
            positionDtos.add(new PositionDto(position.getX(), position.getY()));
        }
        return positionDtos;
    }

    public List<PositionDto> getPiecePositions(int gameId, PieceType pieceType) {
        JanggiGame janggiGame = findGameById(gameId);
        List<PositionDto> positionDtos = new ArrayList<>();
        for (Position position : janggiGame.getPiecesNowPosition(pieceType)) {
            positionDtos.add(new PositionDto(position.getX(), position.getY()));
        }
        return positionDtos;
    }

    public Country getFinalWinner(int gameId) {
        JanggiGame janggiGame = findGameById(gameId);
        return janggiGame.calculateWinner();
    }

    public Country getNowTurnCountry(int gameId)  {
        State state = findStateByGameId(gameId);
        return state.getCountry();
    }

    private JanggiGame findGameById(int gameId) {
        return txManager.executeRead(conn -> {
            JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
            return janggiGameRepository.findByGameId(gameId);
        });
    }

    private Board findBoardByGameId(int gameId) {
        return txManager.executeRead(conn -> {
            JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
            return janggiGameRepository.findBoardByGameId(gameId);
        });
    }

    private State findStateByGameId(int gameId) {
        return txManager.executeRead(conn -> {
            JanggiGameRepository janggiGameRepository = new JanggiGameRepository(conn);
            return janggiGameRepository.findStateByGameId(gameId);
        });
    }
}
