package actividadesPruebas;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import actividades.Encuesta;
import actividades.Estado;

class EncuestaPruebas {
    Encuesta encuesta;

    @BeforeEach
    void setUp() {
        encuesta = new Encuesta(
                "Encuesta de satisfacción", 
                "Evaluar la satisfacción de los estudiantes", 
                "Encuesta para conocer la satisfacción de los estudiantes sobre el curso", 
                "Intermedio", 
                10, 
                true, 
                20
        );
    }

    @AfterEach
    void tearDown() {
        encuesta = null;
    }

    @Test
    void agregarPreguntaTest() {
        encuesta.agregarPregunta("¿Cómo calificarías el curso?");
        assertEquals(1, encuesta.getPreguntas().size(), "La pregunta no fue agregada correctamente.");
        assertTrue(encuesta.getPreguntas().contains("¿Cómo calificarías el curso?"));
    }

    @Test
    void eliminarPreguntaTest() {
        encuesta.agregarPregunta("¿Te gustó el contenido del curso?");
        encuesta.eliminarPregunta("¿Te gustó el contenido del curso?");
        assertFalse(encuesta.getPreguntas().contains("¿Te gustó el contenido del curso?"));
        assertEquals(0, encuesta.getPreguntas().size(), "La pregunta no fue eliminada correctamente.");
    }

    @Test
    void verPreguntasTest() {
        encuesta.agregarPregunta("¿Qué mejorarías en el curso?");
        encuesta.agregarPregunta("¿Recomendarías este curso a otros?");
        List<String> preguntas = encuesta.getPreguntas();
        assertEquals(2, preguntas.size(), "No se agregaron las preguntas correctamente.");
        assertEquals("¿Qué mejorarías en el curso?", preguntas.get(0));
        assertEquals("¿Recomendarías este curso a otros?", preguntas.get(1));
    }

    @Test
    void contestarEncuestaTest() {
        ArrayList<String> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add("Muy satisfecho");
        respuestasEstudiante.add("Sí, definitivamente");
        
        Estado estado = encuesta.contestarEncuesta("12345", respuestasEstudiante);
        HashMap<String, ArrayList<String>> respuestas = encuesta.getRespuestas();

        assertEquals(Estado.EXITOSA, estado, "El estado de la encuesta no es correcto.");
        assertTrue(respuestas.containsKey("12345"), "No se guardaron las respuestas correctamente.");
        assertEquals(2, respuestas.get("12345").size(), "La cantidad de respuestas no es la esperada.");
    }

    @Test
    void setPreguntasTest() {
        List<String> nuevasPreguntas = new ArrayList<>();
        nuevasPreguntas.add("¿Qué te pareció el instructor?");
        encuesta.setPreguntas(nuevasPreguntas);
        assertEquals(1, encuesta.getPreguntas().size());
        assertEquals("¿Qué te pareció el instructor?", encuesta.getPreguntas().get(0));
    }

    @Test
    void setRespuestasTest() {
        HashMap<String, ArrayList<String>> nuevasRespuestas = new HashMap<>();
        ArrayList<String> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add("Muy bueno");
        nuevasRespuestas.put("98765", respuestasEstudiante);

        encuesta.setRespuestas(nuevasRespuestas);
        assertEquals(1, encuesta.getRespuestas().size());
        assertTrue(encuesta.getRespuestas().containsKey("98765"));
    }
}