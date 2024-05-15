package Controllers;

import java.sql.SQLException;

public interface InitializableWithId {
    void init(Integer activityId) throws SQLException;
}
