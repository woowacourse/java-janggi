package repository.mapper;

import dao.MoveLogRawData;
import db.PieceTypeMapper;
import domain.game.JanggiGame;
import domain.game.Team;
import domain.game.progress.MoveLog;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;

public final class MoveLogMapper {

    private MoveLogMapper() {
    }

    public static MoveLogRawData toLastLogRawData(JanggiGame game) {
        List<MoveLog> history = game.getProgress().history();
        int seq = history.size() - 1;
        return toRawLog(seq, history.get(seq));
    }

    public static MoveLogRawData toRawLog(int seq, MoveLog log) {
        if (log.isPass()) {
            return new MoveLogRawData(seq, log.type().name(), log.turn().name(),
                    null, null, null, null, null);
        }
        return new MoveLogRawData(
                seq,
                log.type().name(),
                log.turn().name(),
                log.source().row(),
                log.source().column(),
                log.destination().row(),
                log.destination().column(),
                PieceTypeMapper.toTypeName(log.piece())
        );
    }

    public static List<MoveLog> toHistory(List<MoveLogRawData> rawLogs) {
        return rawLogs.stream()
                .map(MoveLogMapper::toDomainLog)
                .toList();
    }

    public static MoveLog toDomainLog(MoveLogRawData raw) {
        Team turn = Team.valueOf(raw.turn());
        MoveLog.Type type = MoveLog.Type.valueOf(raw.type());
        if (type == MoveLog.Type.PASS) {
            return new MoveLog(type, turn, null, null, null);
        }
        Position source = new Position(raw.fromRow(), raw.fromCol());
        Position destination = new Position(raw.toRow(), raw.toCol());
        Piece piece = PieceTypeMapper.toPiece(raw.pieceType(), turn);
        return new MoveLog(type, turn, source, destination, piece);
    }
}
