/*
 * Copyright (c) 2021 Fuel.ag.
 * All rights reserved.
 * https://fuel.ag
 */
package ag.fuel.controllers;

import ag.fuel.dao.MotoDAO;
import ag.fuel.pojo.Moto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author Mauro Sousa
 */
@RestController
@RequestMapping("/api")
public class MotoController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MotoController.class);
    private final MotoDAO motoDAO;

    public MotoController(MotoDAO motoDAO) {
        this.motoDAO = motoDAO;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }

    @GetMapping("motos")
    public List<Moto> getMotos() {
        return motoDAO.findAll();
    }

    @PostMapping("motos/{brand}/{model}/{cylinders}")
    @ResponseStatus(HttpStatus.CREATED)
    public Moto postMoto(@PathVariable String brand, @PathVariable String model, @PathVariable Integer cylinders) {
        return motoDAO.insert(brand, model, cylinders);
    }
}
