package pl.sebastianbrzustowicz.CrudApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/quadcopter")
public class CrudController {

    @Autowired
    QuadcopterRepository quadcopterRepository;

    //@GetMapping("/test")
    //public int test() {
    //    return 1;
    //}

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
}
