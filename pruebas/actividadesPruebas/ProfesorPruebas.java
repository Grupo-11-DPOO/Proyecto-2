package actividadesPruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividades.*;
import exceptions.UsuarioExistenteException;
import learningPaths.LearningPath;
import usuarios.Profesor;

class ProfesorPruebas {
    private Profesor profesor;
    private HashMap<String, Actividad> actividades;
    private HashMap<String, LearningPath> learningPaths;

    @BeforeEach
    void setUp() {
        actividades = new HashMap<>();
        learningPaths = new HashMap<>();
        profesor = new Profesor(actividades, learningPaths, "profesor1", "password123");
    }

    @AfterEach
    void tearDown() {
        profesor = null;
    }

    @Test
    void testCrearActividadRecurso() throws Exception {
        Recurso recurso = profesor.crearActividadRecurso("Lectura Java", "Aprender conceptos básicos", "PDF sobre Java", "Intermedio", 20, true, "Material.pdf");
        assertNotNull(recurso, "El recurso no fue creado correctamente.");
        assertEquals("Lectura Java", recurso.getTitulo(), "El título del recurso no es el esperado.");
        assertTrue(profesor.getIdActividadesCreadas().contains(recurso.getId()), "El ID del recurso no fue registrado.");
    }

    @Test
    void testCrearActividadTarea() throws Exception {
        Tarea tarea = profesor.crearActividadTarea("Tarea 1", "Resolver ejercicios", "Matemáticas básicas", "Intermedio", 30, true, "Entrega en PDF");
        assertNotNull(tarea, "La tarea no fue creada correctamente.");
        assertEquals("Tarea 1", tarea.getTitulo(), "El título de la tarea no es el esperado.");
    }

    @Test
    void testCrearActividadExamen() throws Exception {
        Examen examen = profesor.crearActividadExamen("Examen Final", "Evaluar conocimientos", "Examen escrito", "Avanzado", 60, true);
        assertNotNull(examen, "El examen no fue creado correctamente.");
        assertEquals("Examen Final", examen.getTitulo(), "El título del examen no es el esperado.");
        assertTrue(profesor.getIdActividadesCreadas().contains(examen.getId()), "El ID del examen no fue registrado.");
    }

    @Test
    void testCrearActividadEncuesta() throws Exception {
        Encuesta encuesta = profesor.crearActividadEncuesta("Encuesta de Satisfacción", "Evaluar satisfacción", "Preguntas abiertas", "Básico", 15, true);
        assertNotNull(encuesta, "La encuesta no fue creada correctamente.");
        assertEquals("Encuesta de Satisfacción", encuesta.getTitulo(), "El título de la encuesta no es el esperado.");
    }

    @Test
    void testBuscarLearningPathPorNombre() throws UsuarioExistenteException {
        LearningPath lp1 = new LearningPath("Java Avanzado", "Aprender Java a fondo", "Intermedio");
        profesor.agregarLearningPath(lp1);

        LearningPath encontrado = profesor.buscarLearningPathPorNombre("Java Avanzado");
        assertNotNull(encontrado, "El Learning Path no fue encontrado.");
        assertEquals("Java Avanzado", encontrado.getTitulo(), "El Learning Path encontrado no tiene el título esperado.");
    }

    @Test
    void testBuscarLearningPathPorNombreNoEncontrado() {
        LearningPath encontrado = profesor.buscarLearningPathPorNombre("Python Básico");
        assertNull(encontrado, "No debería haber encontrado un Learning Path.");
    }

    @Test
    void testGetLearningPathById() throws UsuarioExistenteException {
        LearningPath lp = new LearningPath("Historia Mundial", "Explorar eventos históricos", "Avanzado");
        profesor.agregarLearningPath(lp);

        LearningPath encontrado = profesor.getLearningPathById(lp.getId());
        assertNotNull(encontrado, "El Learning Path no fue encontrado por ID.");
        assertEquals(lp.getTitulo(), encontrado.getTitulo(), "El Learning Path encontrado no tiene el título esperado.");
    }

    @Test
    void testClonarActividad() throws Exception {
    	Tarea tarea = profesor.crearActividadTarea("Tarea 1", "Resolver ejercicios", 
    			"Matemáticas básicas", "Intermedio", 30, true, "Entrega en PDF");
        assertNotNull(tarea, "La tarea no fue creada correctamente.");
        profesor.clonarActividad(tarea);

        Actividad actividadClonada = profesor.getActividadById(tarea.getId());
        assertNotNull(actividadClonada, "La actividad clonada no fue encontrada.");
        assertNotEquals(tarea.getId(), actividadClonada.getId(), 
        		"El ID de la actividad clonada no debería ser igual al original.");
    }
    
    

    @Test
    void testMostrarRespuestasEncuesta() throws Exception {
        Encuesta encuesta = profesor.crearActividadEncuesta("Encuesta de Satisfacción", "Evaluar satisfacción", "Preguntas abiertas sin resultado", "Fácil", 10, true);
        ArrayList<String> respuestas = new ArrayList<>();
        respuestas.add("Muy satisfecho");
        respuestas.add("Buena experiencia");

        encuesta.contestarEncuesta("estudiante1", respuestas);

        String resultado = profesor.mostrarRespuestasEncuesta(encuesta);
        assertTrue(resultado.contains("Muy satisfecho"), "Las respuestas no fueron mostradas correctamente.");
        assertTrue(resultado.contains("estudiante1"), "El nombre del estudiante no fue mostrado correctamente.");
    }
    
    
}
