package com.salesianos.triana.EO2;

import jdk.javadoc.doclet.Taglet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//Anotaciones para generar la entidad y los constructores, getters y setters con lombok.
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Monumento {

    @javax.persistence.Id
    @GeneratedValue
    private Long id;
    private String pais;
    private String ciudad;
    private double[] localizacion;
    private String nombre;

    //@Column(length = 1000) otra forma de aumentar el tamaño del tipo de dato.
    //@Column(columnDefinition = "TEXT") otra forma más de cambiar el tipo de dato en la base de datos.
    @Lob
    private String descripcion;
    @Lob
    private String foto;

    public Monumento(String pais, String ciudad, double[] localizacion, String nombre, String descripcion, String foto) {
        this.pais = pais;
        this.ciudad = ciudad;
        this.localizacion = localizacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
    }
}
