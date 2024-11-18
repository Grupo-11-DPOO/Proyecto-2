package actividadesPruebas;

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
		learnPath = new LearningPath("PythonBasics","Este curso esta diseñado para aprender Python desde principiante","Principiante");
		quiz1 = new Quiz("Condicionales", "Demostrar conocimiento en condicionales",
                "Preguntas opción múltiple con una sola respuesta", "Intermedio", 30, true, 45);
        quiz2 = new Quiz("Asignación", "Demostrar conocimiento sobre asignaciones en Python",
                "Preguntas opción múltiple sobre asignación", "Principiante", 20, true, 40);
	}

	@AfterEach
	void tearDown() throws Exception {
		learnPath = null;
		quiz1 = null;
		quiz2 = null;
	}
	@Test
	void agregarActividadTest() {
		learnPath.agregarActividad(quiz1);
		learnPath.agregarActividad(quiz2);
		assertTrue(learnPath.actividades.size() == 2,"El  numero de actividades no incremento correctamente en la lista de actividades.");
	}
	@Test
    void getTituloTest() {
        assertEquals("PythonBasics", learnPath.getTitulo(), "El título no es el esperado.");
    }

    @Test
    void getDescripcionTest() {
        assertEquals("Este curso esta diseñado para aprender Python desde principiante", learnPath.getDescripcion(), "La descripción no es la esperada.");
    }

    @Test
    void getNivelTest() {
        assertEquals("Principiante", learnPath.getNivel(), "El nivel no es el esperado.");
    }

    @Test
    void getDuracionTest() {
    	learnPath.agregarActividad(quiz1);
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
        assertEquals(1, learnPath.getVersion(), "La versión inicial debería ser 0.");
    }

    @Test
    void setTituloTest() {
        learnPath.setTitulo("JavaBasic");
        assertEquals("JavaBasic", learnPath.getTitulo(), "El título no fue actualizado correctamente.");
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
        learnPath.agregarActividad(quiz1);
        learnPath.agregarActividad(quiz2);
        assertEquals(50, learnPath.getDuracion(), "La duración no fue actualizada correctamente.");
    }

    @Test
    void calcularRatingTest() {
    	quiz1.setRating(5);
    	quiz2.setRating(4.5f);
    	learnPath.agregarActividad(quiz1);
    	learnPath.agregarActividad(quiz2);
        float ratingEsperado = (float) (5.0f + 4.5f) / 2;
        assertEquals(ratingEsperado, learnPath.calcularRating(learnPath.getListaActividades()), 0.01, "El cálculo del rating no es correcto.");
    }

    @Test
    void getActividadPorIdTest() {
    	learnPath.agregarActividad(quiz1);
    	learnPath.agregarActividad(quiz2);
        String idActividad = quiz1.getId();
        Actividad resultado = learnPath.getActividad(learnPath.actividades, idActividad);
        assertEquals(quiz1, resultado, "No se encontró la actividad por ID.");
    }

    @Test
    void getActividadPorTituloTest() {
    	learnPath.agregarActividad(quiz1);
    	learnPath.agregarActividad(quiz2);
    	Actividad resultado = learnPath.getActividad("Asignación", learnPath.actividades);
        assertEquals(quiz2, resultado, "No se encontró la actividad por título.");
    }

    @Test
    void getIdTest() {
        assertNotNull(learnPath.getId(), "El ID no debería ser nulo.");
    }
    @Test
    void getRatingInicial() {
    	assertEquals(0,learnPath.getRating(),"El rating encontrado no fue 0 como se esperaba.");
    	
    }
    @Test 
    void getRatingConCambios() {
    	quiz1.setRating(5);
    	quiz2.setRating(4.5f);
    	learnPath.agregarActividad(quiz1);
    	learnPath.agregarActividad(quiz2);
        float ratingEsperado = (float) (5.0f + 4.5f) / 2;
        assertEquals(ratingEsperado, learnPath.getRating(), 0.01,"La respuesta no fue la esperada");
    }
}
