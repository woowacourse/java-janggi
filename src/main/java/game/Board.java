package game;

import dto.PieceDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import piece.Country;
import piece.Piece;
import piece.PieceType;
import position.Position;

public class Board {

    private final Map<Position, Piece> board;
    private final double secondMovePoint = 1.5;

    public Board(final Map<Position, Piece> board) {
        this.board = board;
    }

    public Board(StartSet choStartSet, StartSet hanStartSet) {
        BoardSetting boardSetting = new BoardSetting();
        Map<Position, Piece> board = new HashMap<>();
        board.putAll(boardSetting.setting(Country.CHO, choStartSet));
        board.putAll(boardSetting.setting(Country.HAN, hanStartSet));
        this.board = board;
    }

    public static Board toBoard(List<PieceDto> pieces) {
        Map<Position, Piece> board = new HashMap<>();
        for (PieceDto dto : pieces) {
            Position pos = Position.of(dto.column(), dto.row());
            Piece piece = Piece.of(dto.pieceType(), dto.country());
            board.put(pos, piece);
        }
        return new Board(board);
    }


    public void movePiece(Position fromPosition, Position toPosition, Country country) {
        if (!board.containsKey(fromPosition)) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
        }
        Piece piece = board.get(fromPosition);
        if (piece.getCountry() != country) {
            throw new IllegalArgumentException("같은 팀의 기물이 아닙니다.");
        }
        piece.validateMove(fromPosition, toPosition, this);
        board.remove(fromPosition);
        board.put(toPosition, piece);

    }

    public boolean isGeneralDead() {
        return (isChoGeneralDead() || isHanGeneralDead());
    }

    private boolean isChoGeneralDead() {
        return board.values().stream()
                .noneMatch(piece -> piece.getPieceType() == PieceType.GENERAL && piece.getCountry() == Country.CHO);
    }

    private boolean isHanGeneralDead() {
        return board.values().stream()
                .noneMatch(piece -> piece.getPieceType() == PieceType.GENERAL && piece.getCountry() == Country.HAN);
    }

    public PieceType findPieceTypeByPosition(final Position position) {
        if (!board.containsKey(position)) {
            throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
        }
        return board.get(position).getPieceType();
    }

    public Optional<Country> findCountryByPosition(final Position position) {
        return Optional.ofNullable(board.get(position))
                .map(Piece::getCountry);
    }

    public boolean hasPieceAt(final Position position) {
        return board.containsKey(position);
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    public double getCountryScore(Country country) {

        double totalPoint = board.values().stream()
                .filter(piece -> piece.getCountry() == country)
                .mapToInt(Piece::getPieceScore)
                .sum();
        if (country == Country.HAN) {
            totalPoint += 1.5;
        }
        return totalPoint;

    }
}
