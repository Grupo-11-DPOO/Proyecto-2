package learningPathPruebas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividades.Actividad;
import actividades.Quiz;
import learningPaths.LearningPath;

class LearningPathTest {
	LearningPath learnPath;
	Quiz quiz1;
	Quiz quiz2;

	@BeforeEach
	void setUp() throws Exception {
		learnPath = new LearningPath("PythonBasics","Este curso esta diseñado para aprender Python desde principiante","Principiante",180);
		quiz1 = new Quiz("Condicionales","Demostrar conocimiento en condicionales","Preguntas opcion multiple con una sola respuesta","Intermedio",30, true, 70);
		quiz2 = new Quiz("Asignacion","Demostrar conocimiento sobre las asignaciones en pyhton","Preguntas opcion multiple sobre asignacion","Principiante",20, true, 80);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
    void getTituloTest() {
        assertEquals("PythonBasics", learnPath.getTitulo(), "El título no es el esperado.");
    }

    @Test
    void getDescripcionTest() {
        assertEquals("Curso básico de Python", learnPath.getDescripcion(), "La descripción no es la esperada.");
    }

    @Test
    void getNivelTest() {
        assertEquals("Principiante", learnPath.getNivel(), "El nivel no es el esperado.");
    }

    @Test
    void getDuracionTest() {
        assertEquals(30, learnPath.getDuracion(), "La duración no es la esperada.");
    }

    @Test
    void getFechaCreacionTest() {
        assertNotNull(learnPath.getFechaCreacion(), "La fecha de creación no debería ser nula.");
    }

    @Test
    void getFechaModificacionTest() {
        assertNotNull(learnPath.getFechaModificacion(), "La fecha de modificación no debería ser nula.");
    }

    @Test
    void getVersionTest() {
        assertEquals(0, learnPath.getVersion(), "La versión inicial debería ser 0.");
    }

    @Test
    void setTituloTest() {
        learnPath.setTitulo("JavaBasics");
        assertEquals("JavaBasics", learnPath.getTitulo(), "El título no fue actualizado correctamente.");
    }

    @Test
    void setDescripcionTest() {
        learnPath.setDescripcion("Curso avanzado de Java");
        assertEquals("Curso avanzado de Java", learnPath.getDescripcion(), "La descripción no fue actualizada correctamente.");
    }

    @Test
    void setNivelTest() {
        learnPath.setNivel("Avanzado");
        assertEquals("Avanzado", learnPath.getNivel(), "El nivel no fue actualizado correctamente.");
    }

    @Test
    void setDuracionTest() {
        learnPath.setDuracion(45);
        assertEquals(45, learnPath.getDuracion(), "La duración no fue actualizada correctamente.");
    }

    @Test
    void calcularRatingTest() {
        float ratingEsperado = (float) (5.0 + 4.5) / 2;
        assertEquals(ratingEsperado, learnPath.calcularRating(learnPath.getListaActividades()), 0.01, "El cálculo del rating no es correcto.");
    }

    @Test
    void getActividadPorIdTest() {
        String idActividad = quiz1.getId();
        Actividad resultado = learnPath.getActividad(learnPath.actividades, idActividad);
        assertEquals(quiz1, resultado, "No se encontró la actividad por ID.");
    }

    @Test
    void getActividadPorTituloTest() {
    	Actividad resultado = learnPath.getActividad("Asignacion", learnPath.actividades);
        assertEquals(quiz2, resultado, "No se encontró la actividad por título.");
    }

    @Test
    void getIdTest() {
        assertNotNull(learnPath.getId(), "El ID no debería ser nulo.");
    }
}
