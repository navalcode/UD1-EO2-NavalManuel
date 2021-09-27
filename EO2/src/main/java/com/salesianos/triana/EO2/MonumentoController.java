package com.salesianos.triana.EO2;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Anotamos la clase como controlador REST y mapeamos para que las peticiones comiencen por /monumento.
@RestController
@RequestMapping("/monumento")
@RequiredArgsConstructor
public class MonumentoController {
    private final MonumentoRepository repository;

    //Este método usa el protocolo get para encontrar todos los monumentos, lanza el mensaje 200.
    @GetMapping("")
    public List<Monumento> findAll() {
        return repository.findAll();
        
        //Este método también serviría, El método ResponseEntity nos surte de varios métodos para responder con los códigos que 
        //necesitemos. 
        // return ResponseEntity.ok()
                              //.body(repository.findAll());
    }

    //Este método devuelve una sola entidad de monumento dado un id que pasamos como variable, en caso de no encotrarlo
    //devuelve null.
    @GetMapping("/{id}")
    public Monumento findOne(@PathVariable("id") Long id) {
        //Con PathVariable marcamos la variable que solicitamos o que nos proporciona el cliente, podríamos aportarle 
        //otro nombre que no fuera "id", dentro de esos mismos paréntesis, independientemente del nombre que proporcionemos
        //a la varible con la que trabajemos en el cuerpo del método.
        return repository.findById(id).orElse(null);
        
        //Siguiendo con los métodos de ResponseEntity podríamos usar este de aquí. 
        //of devuelve un optional genérico, de está forma devuelve un código 200 y el monumento con el id solicitado.
        //return ResponseEntity.of(repository.findById(id));
    }

    //Este método devuelve un mensaje con código 201, creado correctamente
    // si guarda el monumento que aporta el cliente en el repositorio.
    @PostMapping("")
    //Si devolvemos el tipo de datos que hemos creado el código de la respuesta sería 200, pero necesitamos que sea
    //201, por eso no podemos usar directamente la clase modelo y debemos usar la clase ResponseEntity. (ver documentación).
    public ResponseEntity<Monumento> create(@RequestBody Monumento monumento) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.save(monumento));
    }

    //Este método nos permite editar un monumento dado un id concreto.
    @PutMapping("/{id}")
    public Monumento edit(@RequestBody Monumento monumento, @PathVariable Long id) {
        //Primero buscamos por id el monumento que queremos editar y lo guardamos en una variable temporal.

        Monumento antiguo = repository.findById(id).orElse(null);

        //A continuación debemos setear las propiedades del monumento antiguo con los nuevos datos.

        antiguo.setCiudad(monumento.getCiudad());
        antiguo.setDescripcion(monumento.getDescripcion());
        antiguo.setFoto(monumento.getFoto());
        antiguo.setLocalizacion(monumento.getLocalizacion());
        antiguo.setNombre(monumento.getNombre());
        antiguo.setPais(monumento.getPais());

        //Ahora a la vez que retornamos el monumento con los datos modificados salvamos los cambios.
        return repository.save(antiguo);
    }

    //Este método nos permite eliminar un monumento dado su id.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable("id") Long id) {

        //Borramos del respositorio el monumento dado su id
        repository.deleteById(id);
        //Posteriormente lanzamos un mensaje al cliente, en este caso debemos mandar un código 204.
        return ResponseEntity.noContent().build();

    }
}
