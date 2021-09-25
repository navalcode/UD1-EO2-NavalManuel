package com.salesianos.triana.EO2;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//Anotamos la clase como controlador REST y mapeamos para que las peticiones comiencen por /monumento.
@RestController
@RequestMapping("/monumento")
@RequiredArgsConstructor
public class MonumentoController {
    private final MonumentoRepository repository;

    @GetMapping("")
    public List<Monumento> findAll () {
        return repository.findAll();
    }
}
