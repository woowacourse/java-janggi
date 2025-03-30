package janggi.data.dao;

import janggi.piece.Camp;

public interface CampDao {

    void save(Camp camp);

    void saveAll(Camp... camps);

    int findIdByName(String name);
}
