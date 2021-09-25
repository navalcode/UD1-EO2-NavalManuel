package com.salesianos.triana.EO2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class InitData {

    private final MonumentoRepository repository;

    @PostConstruct
    public void init() {
        repository.saveAll(
                Arrays.asList(
                        new Monumento("ES", "Sevilla", new double[]{37.3862, -5.9926}, "La Giralda", "Giralda es el nombre que recibe la torre campanario de la catedral de Santa María de la Sede de la ciudad de Sevilla, en Andalucía (España).", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/La_Giralda_August_2012_Seville_Spain.jpg/240px-La_Giralda_August_2012_Seville_Spain.jpg"),
                        new Monumento("TR", "Estambúl", new double[]{41.00850865, 28.980009932}, "Santa Sofía", "Santa Sofía o Hagia Sophia (del griego: Άγια Σοφία, «Santa Sabiduría»; en latín: Sancta Sophia o Sancta Sapientia; en turco: Ayasofya) es una antigua basílica ortodoxa, posteriormente convertida en mezquita, luego en museo y, desde 2020, nuevamente en una mezquita de la ciudad de Estambul, Turquía.", "https://static1.abc.es/media/sociedad/2020/07/10/santa-sofia-kQsF--1200x630@abc.jpg")

                )
        );
    }
}
