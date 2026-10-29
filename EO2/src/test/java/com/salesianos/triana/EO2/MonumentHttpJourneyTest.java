package com.salesianos.triana.EO2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties={"spring.datasource.url=jdbc:h2:mem:monument_journey;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE", "spring.jpa.hibernate.ddl-auto=create-drop"})
@AutoConfigureMockMvc
class MonumentHttpJourneyTest {
    @Autowired MockMvc http;
    @Autowired MonumentoRepository repository;
    @BeforeEach void clearFictionalFixture() { repository.deleteAll(); }
    private String body(String name) {
        return "{\"pais\":\"Demo country\",\"ciudad\":\"Demo city\",\"localizacion\":[0.0,0.0],\"nombre\":\""+name+"\",\"descripcion\":\"Fictional fixture\",\"foto\":\"https://example.invalid/monument.png\"}";
    }
    @Test void createAndReadPreserveTheJsonAndGeneratedIdentifier() throws Exception {
        http.perform(post("/monumento").contentType(MediaType.APPLICATION_JSON).content(body("Demo monument")))
            .andExpect(status().isCreated()).andExpect(jsonPath("$.nombre").value("Demo monument"));
        Long id=repository.findAll().get(0).getId();
        http.perform(get("/monumento")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id.intValue()));
        http.perform(get("/monumento/"+id)).andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id.intValue())).andExpect(jsonPath("$.localizacion[0]").value(0.0));
    }
    @Test void missingReadIs404() throws Exception {
        http.perform(get("/monumento/1000")).andExpect(status().isNotFound());
    }
    @Test void missingUpdateIs404AndDoesNotCreateARow() throws Exception {
        http.perform(put("/monumento/1000").contentType(MediaType.APPLICATION_JSON).content(body("Changed")))
            .andExpect(status().isNotFound());
        assertEquals(0,repository.count());
    }
    @Test void deletionReturns204Then404WithoutAnotherWrite() throws Exception {
        Monumento row=repository.save(new Monumento("Demo country","Demo city",new double[]{0,0},"Demo monument","Fictional fixture","https://example.invalid/monument.png"));
        http.perform(delete("/monumento/"+row.getId())).andExpect(status().isNoContent());
        http.perform(delete("/monumento/"+row.getId())).andExpect(status().isNotFound());
        assertEquals(0,repository.count());
    }
    @Test void aPostWithAnExistingIdentifierCannotOverwriteTheRow() throws Exception {
        Monumento row=repository.save(new Monumento("Demo country","Demo city",new double[]{0,0},"Original","Fictional fixture","https://example.invalid/monument.png"));
        String request=body("Overwrite").replaceFirst("\\{", "{\"id\":"+row.getId()+",");
        http.perform(post("/monumento").contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(status().isBadRequest());
        assertEquals("Original",repository.findById(row.getId()).orElseThrow().getNombre());
    }
    @Test void aValidUpdateKeepsTheIdentifierAndChangesTheStoredFields() throws Exception {
        Monumento row=repository.save(new Monumento("Demo country","Demo city",new double[]{0,0},"Original","Fictional fixture","https://example.invalid/monument.png"));
        http.perform(put("/monumento/"+row.getId()).contentType(MediaType.APPLICATION_JSON).content(body("Changed")))
            .andExpect(status().isOk()).andExpect(jsonPath("$.nombre").value("Changed"));
        assertEquals(1,repository.count()); assertEquals("Changed",repository.findById(row.getId()).orElseThrow().getNombre());
    }
}
