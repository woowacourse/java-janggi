package service;

import dao.JanggiGameDao;
import dao.PieceDao;
import domain.Board;
import domain.BoardFactory;
import domain.JanggiGame;
import domain.Position;
import domain.constant.Country;
import domain.constant.MaSang;
import domain.constant.PieceType;
import dto.BoardDto;
import dto.GameRecordDto;
import dto.GameResultDto;
import dto.PieceDto;
import dto.PositionDto;
import dto.SavedGameDto;
import dto.SavedPieceDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JanggiService {
    private final JanggiGameDao janggiGameDao;
    private final PieceDao pieceDao;

    public JanggiService(JanggiGameDao janggiGameDao, PieceDao pieceDao) {
        this.janggiGameDao = janggiGameDao;
        this.pieceDao = pieceDao;
    }

    public int createNewGame(String initialTurn) {
        return janggiGameDao.createNewGame(initialTurn);
    }

    public void saveInitBoard(int gameId, Board board) {
        BoardDto boardDto = createBoardDto(board);
        for (Map.Entry<PositionDto, PieceDto> entry : boardDto.pieces().entrySet()) {
            PositionDto positionDto = entry.getKey();
            PieceDto pieceDto = entry.getValue();

            pieceDao.savePiecePosition(gameId, pieceDto, positionDto);
        }
    }

    public Board createBoard(List<PieceType> choMaSangChoose, List<PieceType> hanMaSangChoose) {
        return new Board(choMaSangChoose, hanMaSangChoose);
    }

    public JanggiGame createJanggiGame(Board board) {
        return new JanggiGame(board);
    }

    public BoardDto createBoardDto(Board board) {
        Map<PositionDto, PieceDto> pieceDtos = board.getPieces().entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(
                        entry -> new PositionDto(entry.getKey().getRow(), entry.getKey().getCol()),
                        entry -> new PieceDto(entry.getValue().getCountry().name(),
                                entry.getValue().getPieceType().getName())
                ));
        return new BoardDto(pieceDtos);
    }

    public List<PositionDto> getPiecePositions(JanggiGame janggiGame, PieceType pieceType) {
        List<PositionDto> positionDtos = new ArrayList<>();
        for (Position position : janggiGame.getPiecesNowPosition(pieceType)) {
            positionDtos.add(new PositionDto(position.getRow(), position.getCol()));
        }
        return positionDtos;
    }

    public void applyMove(int gameId, Position start, Position end, JanggiGame game) {
        boolean isRemoved = !game.isEmptyPosition(end);
        game.play(start, end);
        updatePieceData(isRemoved, gameId, start, end);
        updateTurnData(game, gameId);
    }

    private void updatePieceData(boolean isRemoved, int gameId, Position start, Position end) {
        if (isRemoved) {
            pieceDao.deletePiece(gameId, end.getRow(), end.getCol());
        }
        pieceDao.movePiece(gameId, start.getRow(), start.getCol(), end.getRow(), end.getCol());
    }

    private void updateTurnData(JanggiGame game, int gameId) {
        if (!game.isFinished()) {
            janggiGameDao.updateTurn(gameId, game.getCountry().name());
        }
    }

    public List<PieceType> createMaSang(int command) {
        return MaSang.getMaSangPosition(command);
    }

    public List<SavedGameDto> getSavedGames() {
        return janggiGameDao.getSavedGames();
    }

    public Board getSavedBoard(int gameId) {
        List<SavedPieceDto> savedPieceDtos = pieceDao.getSavedBoard(gameId);
        return new Board(BoardFactory.createLoadBoard(savedPieceDtos));
    }

    public JanggiGame loadGame(int gameId, Board board) {
        Country country = Country.getCountry(janggiGameDao.getSavedTurn(gameId));
        return new JanggiGame(board, country);
    }

    public void finishGame(int gameId, JanggiGame janggiGame) {
        janggiGameDao.finishGame(gameId, janggiGame.calculateChoScore(), janggiGame.calculateHanScore());
    }

    public List<GameRecordDto> getGameRecords() {
        return janggiGameDao.getGameRecords();
    }

    public GameResultDto getGameResult(JanggiGame janggiGame) {
        return new GameResultDto(janggiGame.getWinnerCountry(), janggiGame.calculateChoScore(),
                janggiGame.calculateHanScore());
    }
}
