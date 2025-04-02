package janggi.data.dao.test;

import janggi.data.dao.CampDao;
import janggi.piece.Camp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class FakeCampDao implements CampDao {

    private final Map<Integer, String> camps = new HashMap<>();
    private final AtomicInteger id = new AtomicInteger(1);

    @Override
    public void saveAll(Camp... camps) {
        for (Camp camp : camps) {
            this.camps.put(id.getAndIncrement(), camp.name());
        }
    }

    @Override
    public int findIdByName(String name) {
        for (Map.Entry<Integer, String> entry : camps.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("진영 정보를 조회하는 도중 오류가 발생했습니다.");
    }
}
