package service;

import dao.JanggiDao;
import domain.Board;
import domain.BoardFactory;
import domain.JanggiGame;
import domain.Position;
import domain.constant.Country;
import domain.constant.MaSang;
import domain.constant.PieceType;
import dto.BoardDto;
import dto.PieceDto;
import dto.PositionDto;
import dto.SavedPieceDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JanggiService {
    private final JanggiDao janggiDao;

    public JanggiService(JanggiDao janggiDao) {
        this.janggiDao = janggiDao;
    }

    public int createNewGame(String initialTurn) {
        return janggiDao.createNewGame(initialTurn);
    }

    public void saveInitBoard(int gameId, Board board) {
        BoardDto boardDto = createBoardDto(board);
        for (Map.Entry<PositionDto, PieceDto> entry : boardDto.pieces().entrySet()) {
            PositionDto positionDto = entry.getKey();
            PieceDto pieceDto = entry.getValue();

            janggiDao.savePiecePosition(gameId, pieceDto, positionDto);
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
        boolean checkEndPosition = game.isEmptyPosition(end);

        game.play(start, end);

        if (!checkEndPosition) {
            janggiDao.deletePiece(gameId, end.getRow(), end.getCol());
        }
        janggiDao.movePiece(gameId, start.getRow(), start.getCol(), end.getRow(), end.getCol());
        janggiDao.updateTurn(gameId, game.getCountry().name());
    }

    public List<PieceType> createMaSang(int command) {
        return MaSang.getMaSangPosition(command);
    }

    public List<Integer> getSavedGames() {
        return janggiDao.getSavedGames();
    }

    public Board getSavedBoard(int gameId) {
        List<SavedPieceDto> savedPieces = janggiDao.getSavedBoard(gameId);
        return new Board(BoardFactory.createLoadBoard(savedPieces));
    }

    public JanggiGame loadGame(int gameId, Board board) {
        Country country = Country.getCountry(janggiDao.getSavedTurn(gameId));
        return new JanggiGame(board, country);
    }

    public void deleteGame(int gameId) {
        janggiDao.deleteGame(gameId);
    }
}
