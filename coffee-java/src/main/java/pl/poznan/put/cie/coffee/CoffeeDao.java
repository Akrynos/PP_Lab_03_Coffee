package pl.poznan.put.cie.coffee;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.sqlite.core.DB;

import javax.sql.DataSource;

public class CoffeeDao {

	private final NamedParameterJdbcTemplate jdbc;

	public CoffeeDao() {
		this.jdbc = new NamedParameterJdbcTemplate(DbUtilities.getSQLiteDataSource("jdbc:sqlite:jakchcemy.sqlite"));
	}

	/**
	 * Returns a coffee with given name.
	 *
	 * @param name coffee name
	 * @return coffee object or null
	 */
	public Coffee get(final String name) {
		String sql = "SELECT sup_id, price, sales, total FROM coffees "
				  + "WHERE cof_name = :cof_name";
		MapSqlParameterSource params = new MapSqlParameterSource("cof_name", name);
		return jdbc.query(sql, params, new ResultSetExtractor<Coffee>() {

			@Override
			public Coffee extractData(ResultSet rs) throws SQLException, DataAccessException {
				int supplierId = rs.getInt("sup_id");
				BigDecimal price = new BigDecimal(rs.getInt("price"));
				int sales = rs.getInt("sales");
				int total = rs.getInt("total");
				return new Coffee(name,supplierId,price,sales,total);
			}
		});
	}

	/**
	 * Returns a list of all coffees.
	 *
	 * @return list of all coffees
	 */
	public List<Coffee> getAll() {
		String sql = "SELECT cof_name, sup_id, price, sales, total FROM coffees";
		try {
			return jdbc.query(sql, new RowMapper<Coffee>() {
				@Override
				public Coffee mapRow(ResultSet resultSet, int i) throws SQLException {
					return new Coffee(
					resultSet.getString("cof_name"),
					resultSet.getInt("sup_id"),
					new BigDecimal(resultSet.getInt("price")),
					resultSet.getInt("sales"),
					resultSet.getInt("total")
					);
				};
			});
		} catch (EmptyResultDataAccessException ex) {
			return new ArrayList<>();
		}
	}

	public void update(Coffee c) {
		String sql = "UPDATE coffees "
				  + "SET price = :price, sales = :sales, total = :total "
				  + "WHERE cof_name = :cof_name AND sup_id = :sup_id";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("price", c.getPrice());
		parameters.put("sales", c.getSales());
		parameters.put("total", c.getTotal());
		parameters.put("cof_name", c.getName());
		parameters.put("sup_id", c.getSupplierId());
		jdbc.update(sql, parameters);
	}

	public void delete(String name, int supplierId) {
		String Dlt = "DELETE FROM coffees WHERE cof_name = :cof_name AND sup_id = :sup_id";
		MapSqlParameterSource parm = new MapSqlParameterSource("cof_name",name).addValue("sup_id",supplierId);
		jdbc.update(Dlt, parm);
	}

	public void create(Coffee c) {
		String Add ="INSERT INTO coffees(cof_name, sup_id, price, sales, total) VALUES(:cof_name, :sup_id, :price, :sales, :total)";
		MapSqlParameterSource parm = new MapSqlParameterSource("cof_name",c.getName())
				.addValue("sup_id",c.getSupplierId())
				.addValue("price",c.getPrice())
				.addValue("sales",c.getSales())
				.addValue("total",c.getTotal());
		jdbc.update(Add, parm);
	}

}
