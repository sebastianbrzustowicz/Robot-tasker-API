package pl.sebastianbrzustowicz.CrudApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuadcopterRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Quadcopter> getAll() {
        return jdbcTemplate.query("SELECT id, mode, altitude FROM quadcopter",
                BeanPropertyRowMapper.newInstance(Quadcopter.class));
    }

    public Quadcopter getById(int id) {
        return jdbcTemplate.queryForObject("SELECT id, mode, altitude FROM quadcopter WHERE " +
                "id = ?", BeanPropertyRowMapper.newInstance(Quadcopter.class), id);
    }

    public int save(List<Quadcopter> quadcopterList) {
        quadcopterList.forEach(param -> jdbcTemplate
                .update("INSERT INTO quadcopter(mode, altitude) VALUES(?, ?)",
                        param.getMode(), param.getAltitude()
                ));

        return 1;
    }

    public int update(Quadcopter quadcopter) {
        return jdbcTemplate.update("UPDATE quadcopter SET mode=?, altitude=? WHERE id=?",
                quadcopter.getMode(), quadcopter.getAltitude(), quadcopter.getId());
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM quadcopter WHERE id=?", id);
    }
}
