package blossom.project.designmode.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SimpleJdbcTemplate {

    // 假设有一个方法来获取数据库连接
    protected abstract Connection getConnection() throws SQLException;

    // 执行查询的模板方法
    public <T> T query(String sql, RowMapper<T> rowMapper, Object... args) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            for (Object arg : args) {
                ps.setObject(paramIndex++, arg);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rowMapper.mapRow(rs);
                }
                return null;
            }
        }
    }

    // 定义RowMapper接口
    public interface RowMapper<T> {
        T mapRow(ResultSet rs) throws SQLException;
    }
}
