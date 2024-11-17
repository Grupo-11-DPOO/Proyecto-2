package actividadesPruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividades.Estado;
import actividades.Opcion;
import actividades.Quiz;

class QuizPruebas {
    private Quiz quiz;

    @BeforeEach
    void setUp() {
        quiz = new Quiz(
                "Historia",
                "Aprender sobre historia mundial",
                "Contiene preguntas de múltiples opciones",
                "Avanzado",
                30,
                true,
                60.0f
        );
    }

    @AfterEach
    void tearDown() {
    	// Nos aseguramos que no vaya a quedarse nada guardado de lo que hicimos en alguna prueba, que pueda afectar las siguientes
        quiz = null;
    }

    @Test
    void testAgregarPregunta() {
        quiz.agregarPregunta("¿Quién descubrió América?");
        assertTrue(quiz.getPreguntas().containsKey("¿Quién descubrió América?"), "La pregunta no fue agregada correctamente.");
    }

    @Test
    void testAgregarOpcion() {
        quiz.agregarOpcion("¿Quién descubrió América?", "Cristóbal Colón", Opcion.A, "Fue el primer europeo en llegar a América.");
        assertTrue(quiz.getPreguntas().containsKey("¿Quién descubrió América?"), "La pregunta no fue agregada correctamente.");
        
        HashMap<Opcion, HashMap<String, String>> opciones = quiz.getPreguntas().get("¿Quién descubrió América?");
        assertNotNull(opciones, "Las opciones no fueron agregadas correctamente.");
        assertTrue(opciones.containsKey(Opcion.A), "La opción A no fue agregada correctamente.");
    }

    @Test
    void testAgregarOpcionCorrecta() {
        quiz.agregarOpcionCorrecta("¿Cuál es la capital de Francia?", "París", Opcion.B, "París es la capital de Francia.");
        assertTrue(quiz.getRespuestasCorrectas().containsKey("¿Cuál es la capital de Francia?"), "La pregunta correcta no fue registrada.");
        assertEquals(Opcion.B, quiz.getRespuestasCorrectas().get("¿Cuál es la capital de Francia?"), "La opción correcta no fue registrada correctamente.");
    }

    @Test
    void testVerPreguntas() {
        quiz.agregarPregunta("¿Quién fue el primer presidente de los Estados Unidos?");
        quiz.agregarOpcion("¿Quién fue el primer presidente de los Estados Unidos?", "George Washington", Opcion.A, "Fue el primer presidente de los Estados Unidos.");
        String preguntas = quiz.verPreguntas();
        assertTrue(preguntas.contains("¿Quién fue el primer presidente de los Estados Unidos?"), "La pregunta no aparece en la lista.");
        assertTrue(preguntas.contains("A)"), "La opción A no aparece en la lista.");
    }

    @Test
    void testVerPreguntasConExplicaciones() {
        quiz.agregarOpcionCorrecta("¿Cuál es el río más largo del mundo?", "Nilo", Opcion.C, "El río Nilo es considerado el más largo del mundo.");
        String preguntasConExplicaciones = quiz.verPreguntasConExplicaciones();
        assertTrue(preguntasConExplicaciones.contains("Explicación"), "La explicación no aparece en la lista.");
    }

    @Test
    void testCalificarRespuestasExitosas() throws Exception {
        
        quiz.agregarOpcionCorrecta("¿Qué año comenzó la Segunda Guerra Mundial?", "1939", Opcion.A, "La guerra comenzó en 1939.");
        quiz.agregarOpcionCorrecta("¿Quién escribió 'Cien años de soledad'?", "Gabriel García Márquez", Opcion.B, "Es un famoso autor colombiano.");

        // Respuestas del estudiante
        ArrayList<Opcion> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add(Opcion.A); // Respuesta correcta
        respuestasEstudiante.add(Opcion.B); // Respuesta correcta

        Estado estado = quiz.calificar("12345", respuestasEstudiante);
        assertEquals(Estado.EXITOSA, estado, "La calificación debería ser EXITOSA.");
    }

    @Test
    void testCalificarRespuestasNoExitosas() throws Exception {
        
        quiz.agregarOpcionCorrecta("¿Cuál es la fórmula del agua?", "H2O", Opcion.C, "Es la fórmula química del agua.");
        quiz.agregarOpcionCorrecta("¿Qué planeta es conocido como el planeta rojo?", "Marte", Opcion.D, "Marte es conocido por su color rojo.");

        
        ArrayList<Opcion> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add(Opcion.B); // Respuesta incorrecta
        respuestasEstudiante.add(Opcion.A); // Respuesta incorrecta

        Estado estado = quiz.calificar("67890", respuestasEstudiante);
        assertEquals(Estado.NO_EXITOSA, estado, "La calificación debería ser NO_EXITOSA.");
    }

    @Test
    void testCalificarCantidadIncorrectaRespuestas() {
        
        quiz.agregarOpcionCorrecta("¿Cuál es el océano más grande?", "Pacífico", Opcion.A, "El océano Pacífico es el más grande.");

        ArrayList<Opcion> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add(Opcion.A); // Una respuesta para dos preguntas

        Exception exception = assertThrows(Exception.class, () -> {
            quiz.calificar("56789", respuestasEstudiante);
        });

        assertEquals("La cantidad de respuestas no coincide con el número de preguntas.", exception.getMessage(), "El mensaje de excepción no es el esperado.");
    }
}
