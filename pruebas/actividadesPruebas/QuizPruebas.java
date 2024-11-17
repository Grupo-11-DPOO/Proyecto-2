package actividadesPruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividades.Estado;
import actividades.Quiz;


class QuizPruebas {
	private Quiz quiz;

    @BeforeEach
    void setUp() {
        quiz = new Quiz("Historia", "Aprender sobre historia mundial", "Contiene preguntas de múltiples opciones", "Avanzado", 30, true, 45);
    }
    
    @AfterEach
    void tearDown() throws Exception {
    }
    
    @Test
    void verPreguntasConPreguntas() {
        quiz.agregarPregunta("¿Cuál es la capital de Francia?", "París");
        quiz.agregarOpcion("¿Cuál es la capital de Francia?", "París", "La capital de Francia es París.");
        quiz.agregarOpcion("¿Cuál es la capital de Francia?", "Londres", "Londres es la capital de Inglaterra.");
        
        String esperado = "Pregunta: ¿Cuál es la capital de Francia?\nOpciones:\nParís: La capital de Francia es París.\nLondres: Londres es la capital de Inglaterra.\n";
        assertEquals(esperado, quiz.verPreguntas());
    }

    @Test
    void verPreguntasSinPreguntas() {
        assertEquals("No hay preguntas que mostrar", quiz.verPreguntas());
    }

    @Test
    void calificarConRespuestasCorrectas() throws Exception {
        quiz.agregarPregunta("¿Cuál es la capital de Francia?", "París");
        List<String> respuestas = new ArrayList<>();
        respuestas.add("París");

        String resultado = quiz.calificar(respuestas);
        assertNotNull(resultado, "El resultado de explicaciones quedo vacio.");
        assertEquals(Estado.EXITOSA, quiz.getEstado(), "El resultado con respuestas correctas no cambia el estado a exitoso." );
    }

    @Test
    void calificarConNumeroIncorrectoDeRespuestas() {
        quiz.agregarPregunta("¿Cuál es la capital de Francia?", "París");
        List<String> respuestas = new ArrayList<>(); // Lista vacía, debería causar error

        Exception exception = assertThrows(Exception.class, () -> quiz.calificar(respuestas));
        assertEquals("La cantidad de respuestas no coincide con el número de preguntas.", exception.getMessage());
    }
}