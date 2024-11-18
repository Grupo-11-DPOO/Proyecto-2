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
import persistencia.PersistenciaActividades;
import persistencia.PersistenciaLearningPath;
import persistencia.PersistenciaUsuarios;
import usuarios.Profesor;

class ProfesorPruebas {
    private Profesor profesor;
    private PersistenciaUsuarios persistenciaUsuarios;
    private PersistenciaLearningPath persistenciaLearningPath;
    private PersistenciaActividades persistenciaActividades;
    private List<String> idActividades;
    private List<String> idLearningPaths;

    @BeforeEach
    void setUp() {
        persistenciaUsuarios = new PersistenciaUsuarios();
        persistenciaLearningPath = new PersistenciaLearningPath();
        persistenciaActividades = new PersistenciaActividades();
        idActividades = new ArrayList<>();
        idLearningPaths = new ArrayList<>();

        profesor = new Profesor(
                persistenciaUsuarios,
                persistenciaLearningPath,
                persistenciaActividades,
                "profesor1",
                "password123",
                idActividades,
                idLearningPaths
        );
    }

    @AfterEach
    void tearDown() {
        profesor = null;
    }

    @Test
    void testCrearActividadRecurso() throws Exception {
        Recurso recurso = profesor.crearActividadRecurso("Documentación Java", "Aprender Java", "PDF sobre Java", "Intermedio", 60, true, "Material en PDF");
        assertNotNull(recurso, "La actividad de tipo Recurso no fue creada correctamente.");
        assertEquals("Documentación Java", recurso.getTitulo());
    }

    @Test
    void testCrearActividadTarea() throws Exception {
        Tarea tarea = profesor.crearActividadTarea("Tarea 1", "Resolver problemas", "Problemas matemáticos", "Avanzado", 120, true, "Entrega en PDF");
        assertNotNull(tarea, "La actividad de tipo Tarea no fue creada correctamente.");
        assertEquals("Tarea 1", tarea.getTitulo());
    }

    @Test
    void testCrearActividadExamen() throws Exception {
        Examen examen = profesor.crearActividadExamen("Examen Final", "Evaluar conocimientos", "Examen escrito", "Avanzado", 90, true);
        assertNotNull(examen, "La actividad de tipo Examen no fue creada correctamente.");
        assertEquals("Examen Final", examen.getTitulo());
    }

    @Test
    void testCrearActividadEncuesta() throws Exception {
        Encuesta encuesta = profesor.crearActividadEncuesta("Encuesta de satisfacción", "Conocer opiniones", "Preguntas abiertas", "Básico", 30, true);
        assertNotNull(encuesta, "La actividad de tipo Encuesta no fue creada correctamente.");
        assertEquals("Encuesta de satisfacción", encuesta.getTitulo());
    }

    @Test
    void testCrearActividadQuiz() {
        Quiz quiz = profesor.crearActividadQuiz("Quiz Historia", "Evaluar conocimientos en historia", "Preguntas de opción múltiple", "Intermedio", 45, true, 70.0f);
        assertNotNull(quiz, "La actividad de tipo Quiz no fue creada correctamente.");
        assertEquals("Quiz Historia", quiz.getTitulo());
        assertEquals(70.0f, quiz.getCalificacionMinima());
    }

    @Test
    void testCrearLearningPath() {
        LearningPath learningPath = profesor.crearLearningPath("Ruta de Aprendizaje Java", "Dominar Java", "Intermedio");
        assertNotNull(learningPath, "El Learning Path no fue creado correctamente.");
        assertEquals("Ruta de Aprendizaje Java", learningPath.getTitulo());
    }

    @Test
    void testAgregarLearningPath() throws UsuarioExistenteException {
        LearningPath learningPath = profesor.crearLearningPath("Ruta Python", "Aprender Python", "Principiante");
        profesor.agregarLearningPath(learningPath);
        List<String> learningPathsPropios = profesor.getLearningPathsPropios();
        assertTrue(learningPathsPropios.contains(learningPath.getId()), "El Learning Path no fue agregado correctamente.");
    }

    @Test
    void testClonarActividad() throws UsuarioExistenteException {
        Tarea tarea = profesor.crearActividadTarea("Tarea Clonada", "Clonar una tarea", "Descripción de la tarea", "Básico", 30, true, "PDF");
        profesor.clonarActividad(tarea);
        assertTrue(profesor.getActividades().containsKey(tarea.getId()), "La actividad clonada no fue guardada correctamente.");
    }

    @Test
    void testAgregarActividadALearningPath() throws Exception {
        LearningPath learningPath = profesor.crearLearningPath("Ruta de Matemáticas", "Dominar álgebra", "Avanzado");
        Tarea tarea = profesor.crearActividadTarea("Tarea Álgebra", "Resolver ejercicios", "Problemas de álgebra", "Intermedio", 45, true, "PDF");
        profesor.agregarActividadALearningPath(learningPath, tarea);
        assertTrue(learningPath.getListaActividades().contains(tarea), "La actividad no fue agregada al Learning Path correctamente.");
    }

    @Test
    void testMarcarTareaComoExitosa() throws Exception {
        Tarea tarea = profesor.crearActividadTarea("Tarea Éxito", "Completar ejercicios", "Tarea de éxito", "Intermedio", 60, true, "PDF");
        profesor.guardarActividad(tarea);
        profesor.marcarTareaComoExitosa(tarea, true);
        assertEquals(Estado.EXITOSA, tarea.getEstadoTarea(), "La tarea no fue marcada como exitosa correctamente.");
    }
}