package unicorns.backend.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
@Log4j2
public class DataSourceChecker {

    private final DataSource dataSource;

    public DataSourceChecker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void checkDataSource() {
        try (Connection conn = dataSource.getConnection()) {
            log.info("AutoCommit: {}", conn.getAutoCommit());
            log.info("Driver Name: {}", conn.getMetaData().getDriverName());
            log.info("DB URL: {}", conn.getMetaData().getURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}