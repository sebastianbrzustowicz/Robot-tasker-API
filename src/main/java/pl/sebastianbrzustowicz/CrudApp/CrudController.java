package pl.sebastianbrzustowicz.CrudApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/quadcopter")
public class CrudController {

    @Autowired
    QuadcopterRepository quadcopterRepository;

    @GetMapping("")
    public List<Quadcopter> getAll() {
        return quadcopterRepository.getAll();
    }

    @GetMapping("/{id}")
    public Quadcopter getById(@PathVariable("id") int id) {
        return quadcopterRepository.getById(id);
    }

    @PostMapping("")
    public int add(@RequestBody List<Quadcopter> quadcopterList) {
        return quadcopterRepository.save(quadcopterList);
    }

    @PutMapping("/{id}")
    public int update(@PathVariable("id") int id, @RequestBody Quadcopter updatedquadcopter) {
        Quadcopter quadcopter = quadcopterRepository.getById(id);

        if (quadcopter != null) {
            quadcopter.setMode(updatedquadcopter.getMode());
            quadcopter.setAltitude(updatedquadcopter.getAltitude());

            quadcopterRepository.update(quadcopter);

            return 1;
        } else {
            return -1;
        }
    }

    @PatchMapping("/{id}")
    public int partiallyUpdate(@PathVariable("id") int id, @RequestBody Quadcopter updatedquadcopter) {
        Quadcopter quadcopter = quadcopterRepository.getById(id);

        if (quadcopter != null) {
            //quadcopter.setMode(updatedquadcopter.getMode());
            if (updatedquadcopter.getMode() != null) quadcopter.setMode(updatedquadcopter.getMode());
            if (updatedquadcopter.getAltitude() > 0) quadcopter.setAltitude(updatedquadcopter.getAltitude());

            quadcopterRepository.update(quadcopter);

            return 1;
        } else {
            return -1;
        }
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id) {
        return quadcopterRepository.delete(id);
    }

}
