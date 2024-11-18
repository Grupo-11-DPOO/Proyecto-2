package actividadesPruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividades.*;
import learningPaths.LearningPath;
import usuarios.Estudiante;

class EstudiantePruebas {
    private Estudiante estudiante;
    private HashMap<String, Actividad> actividades;
    private HashMap<String, LearningPath> learningPaths;
    private List<String> intereses;

    @BeforeEach
    void setUp() {
        
        actividades = new HashMap<String, Actividad>();
        learningPaths = new HashMap<>();
        intereses = new ArrayList<>();
        intereses.add("Java");
        intereses.add("Historia");
        intereses.add("programar");

        estudiante = new Estudiante(actividades, learningPaths, "estudiante1", "password123", intereses);
    }

    @AfterEach
    void tearDown() {
        estudiante = null;
    }

    @Test
    void testBuscarLearningPathPorNombre() {
    	LearningPath lp1 = new LearningPath("Aprender Python", "Vuelvete un duro en Python", "Principiante");
        LearningPath lp2 = new LearningPath("Aprender Java", "Java desde cero", "Principiante");
        learningPaths.put(lp1.getId(), lp1);
        learningPaths.put(lp2.getId(), lp2);
        
        LearningPath encontrado = estudiante.buscarLearningPathPorNombre("Aprender Java");
        assertNotNull(encontrado, "El Learning Path no fue encontrado.");
        assertEquals("Aprender Java", encontrado.getTitulo(), "El learningPath encontrado es distinto al esperado.");
    }
    @Test
    void testBuscarLearningPathPorNombreNoEncontro() {
    	LearningPath lp1 = new LearningPath("Aprender Python", "Vuelvete un duro en Python", "Principiante");
        LearningPath lp2 = new LearningPath("Aprender Java", "Java desde cero", "Principiante");
        learningPaths.put(lp1.getId(), lp1);
        learningPaths.put(lp2.getId(), lp2);
        
        LearningPath encontrado = estudiante.buscarLearningPathPorNombre("Aprender C++");
        assertNull(encontrado, "El resultado no fue null al recorrer todos los learning Paths y no encontrar uno");
    }
    @Test
    void testBuscarLearningPathPorNombreNull() {
    	assertNull(estudiante.buscarLearningPathPorNombre("Aprender Java"), "El programa devuelve incorrectamente en caso de que no haya learningPaths");
    }

    @Test
    void testBuscarActividadPorNombre() {
        Tarea tarea = new Tarea("Tarea 1", "Resolver ejercicios", "Problemas matemáticos", "Básico", 60, true, "PDF");
        actividades.put(tarea.getId(), tarea);
        
        Actividad actividad = estudiante.buscarActividadPorNombre("Tarea 1");
        assertNotNull(actividad, "La actividad no fue encontrada.");
        assertEquals("Tarea 1", actividad.getTitulo());
    }
    @Test
    void testBuscarActividadPorNombreCasosIfyWhile() {
    	assertNull(estudiante.buscarActividadPorNombre("Lectura sobre Raquel Bernal"), "Encontro una lectura cuando no existen actividades.");
    	Tarea tarea = new Tarea("Tarea 1", "Resolver ejercicios", "Problemas matemáticos", "Básico", 60, true, "PDF");
    	Quiz quiz = new Quiz("Quiz Historia Colombia", "Demostrar conocimiento sobre Colombia", "Opcion multiple", "Principiante", 30, false, 60);
    	Recurso recurso = new Recurso("Lectura sobre Napoleón", "Aprender sobre el inicio de la conquista", "En esta lectura aprenderas sobre Napoleon y el inicio de su conquista.", "Fácil", 40, true, "RecursoNapoleon.PDF" );
    	actividades.put(quiz.getId(), quiz);
    	actividades.put(tarea.getId(), tarea);
    	actividades.put(recurso.getId(), recurso);
    	
    	
    	assertNull(estudiante.buscarActividadPorNombre("Lectura sobre Raquel Bernal"), "Encontro una lectura cuando no existe nada de Raquel.");
    	
    }
    
    @Test
    void testRealizarQuiz() throws Exception {
    	
    	Quiz quiz = new Quiz("Quiz Historia Colombia", "Demostrar conocimiento sobre Colombia", "Opcion multiple", "Principiante", 30, false, 60);
    	quiz.agregarPregunta("¿Qué año comenzó la guerra civil?", Opcion.A);
        quiz.agregarOpcion("¿Qué año comenzó la guerra civil?", "Todas Las Anteriores", Opcion.A, "Han habido varias guerras civiles en Colombia.");
        
        quiz.agregarPregunta("¿Quién escribió 'Cien años de soledad'?", Opcion.B);
        quiz.agregarOpcion("¿Quién escribió 'Cien años de soledad'?", "Gabriel García Márquez", Opcion.B, "Es un famoso autor Colombiano, ganador de un premio Nobel.");
        
        ArrayList<Opcion> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add(Opcion.A);
        respuestasEstudiante.add(Opcion.B);
        assertEquals(Estado.EXITOSA,estudiante.realizarQuiz(quiz,respuestasEstudiante));
        assertTrue(estudiante.getRegistroActividades().containsKey(quiz.getId()));
    }

    @Test
    void testEmpezarLearningPath() {
        LearningPath lp = new LearningPath("Historia Mundial", "Explorar la historia", "Avanzado");
        LearningPath lp2 = new LearningPath("Messi", "Aprende de la vida de Messi","Aficionado");
        boolean resultado = estudiante.empezarLearningPath(lp);
        assertTrue(resultado, "El Learning Path no fue comenzado.");
        assertEquals(lp, estudiante.getLearningPathEnCurso());
        assertFalse(estudiante.empezarLearningPath(lp2));
    }

    @Test
    void testFinalizarLearningPath() {
        LearningPath lp = new LearningPath("Matemáticas", "Dominar álgebra", "Intermedio");
        estudiante.empezarLearningPath(lp);
        estudiante.finalizarLearningPath();
        assertNull(estudiante.getLearningPathEnCurso(), "El Learning Path no fue finalizado.");
    }

	@Test
    void testRecomendarLearningPaths() {
        LearningPath lp1 = new LearningPath("Curso de programar en Java", "Aprender conceptos avanzados de Java", "Intermedio");
        LearningPath lp2 = new LearningPath("Historia Moderna", "Explorar los eventos clave del siglo XX", "Básico");
        LearningPath lp3 = new LearningPath("Fútbol para Dummies", "Vuelvete experto en fútbol", "Básico");


        learningPaths.put(lp1.getId(), lp1);
        learningPaths.put(lp2.getId(), lp2);
        learningPaths.put(lp3.getId(), lp3);

        List<LearningPath> recomendaciones = estudiante.recomendarLearningPaths(learningPaths);
        assertEquals(3, recomendaciones.size(), "La cantidad de recomendaciones no es la esperada.");
        assertEquals(lp1.getTitulo(),recomendaciones.get(0).getTitulo(), "No se encontro la recomendacion esperada");
        assertEquals(lp3.getTitulo(), recomendaciones.get(2).getTitulo(),"No se encontro la recomendacion esperada.");
    }

    @Test
    void testRealizarEncuesta() {
        Encuesta encuesta = new Encuesta("Encuesta 1", "Evaluar satisfacción", "Preguntas abiertas", "Básico", 15, true);
        ArrayList<String> respuestas = new ArrayList<>();
        respuestas.add("Muy satisfecho");
        
        estudiante.realizarEncuesta(encuesta, respuestas);
        HashMap<String, Estado> registro = estudiante.getRegistroActividades();
        assertEquals(Estado.EXITOSA, registro.get(encuesta.getId()), "La encuesta no fue registrada correctamente.");
    }

    @Test
    void testRealizarExamen() {
        Examen examen = new Examen("Examen Final", "Evaluar conocimientos", "Examen escrito", "Avanzado", 60, true);
        examen.agregarPregunta("¿Cuantos paises juegan las eliminatorias de conmebol?");
        ArrayList<String> respuestas = new ArrayList<>();
        respuestas.add("10");
        
        estudiante.realizarExamen(examen, respuestas);
        HashMap<String, Estado> registro = estudiante.getRegistroActividades();
        assertEquals(Estado.PENDIENTE, registro.get(examen.getId()), "El examen no fue registrado correctamente.");
    }
    
    @Test
    void testRealizarRecurso() {
        Recurso recurso = new Recurso("Documentación", "Leer sobre Java", "PDF", "Intermedio", 10, true, "Material PDF");
        estudiante.realizarRecurso(recurso);
        HashMap<String, Estado> registro = estudiante.getRegistroActividades();
        assertEquals(Estado.EXITOSA, registro.get(recurso.getId()), "El recurso no fue registrado correctamente.");
    }

    @Test
    void testRealizarTarea() {
        Tarea tarea = new Tarea("Tarea 1", "Resolver problemas", "Matemáticas básicas", "Intermedio", 30, true, "Entrega en PDF");
        estudiante.realizarTarea(tarea, "Entrega en PDF");
        HashMap<String, Estado> registro = estudiante.getRegistroActividades();
        assertEquals(Estado.ENVIADA, registro.get(tarea.getId()), "La tarea no fue registrada correctamente.");
    }
    @Test
    void testRealizarQuizVerdad() throws Exception {
    	QuizVerdad quiz = new QuizVerdad("Quiz Java", "Demostrar conocimiento en Java a traves de preguntas verdad o falso", "Preguntas Verdadero o Falso", "Fácil", 15, true, 80);
    	quiz.agregarPregunta("Java se usó para crear Minecraft.", VerdaderoFalso.Verdadero);
        quiz.agregarPregunta("Los comentarios en Java se escriben con '//'.", VerdaderoFalso.Verdadero);

       
        ArrayList<VerdaderoFalso> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add(VerdaderoFalso.Verdadero);
        respuestasEstudiante.add(VerdaderoFalso.Verdadero); 
        
        assertEquals(Estado.EXITOSA, estudiante.realizarQuizVerdad(quiz, respuestasEstudiante),"El estado devuelto no fue el esperado");

    }
    @Test
    
    void getLearningPathById() {
    	LearningPath lp1 = new LearningPath("Curso de programar en Java", "Aprender conceptos avanzados de Java", "Intermedio");
        LearningPath lp2 = new LearningPath("Historia Moderna", "Explorar los eventos clave del siglo XX", "Básico");
        LearningPath lp3 = new LearningPath("Fútbol para Dummies", "Vuelvete experto en fútbol", "Básico");


        learningPaths.put(lp1.getId(), lp1);
        learningPaths.put(lp2.getId(), lp2);
        learningPaths.put(lp3.getId(), lp3);
        
        assertEquals(lp2.getTitulo(), estudiante.getLearningPathById(lp2.getId()).getTitulo(), "No se devolvio el learning path que se esperaba");
        assertNull(estudiante.getLearningPathById("LioMessi"),"El texto devolvio algo incorrectamente");
    }
    
    @Test
    void getActividadById() {
    	
    	assertNull(estudiante.getActividadById("SirPaul"),"No fue correcto y se devolvio algo, cuando debia ser null");
    	Tarea tarea = new Tarea("Tarea 1", "Resolver problemas", "Matemáticas básicas", "Intermedio", 30, true, "Entrega en PDF");
    	QuizVerdad quiz = new QuizVerdad("Quiz Java", "Demostrar conocimiento en Java a traves de preguntas verdad o falso", "Preguntas Verdadero o Falso", "Fácil", 15, true, 80);
    	actividades.put(tarea.getId(),tarea);
    	actividades.put(quiz.getId(), quiz);
    	
    	assertEquals(quiz.getTitulo(),estudiante.getActividadById(quiz.getId()).getTitulo(),"No se devolvio la actividad esperada.");
    	
    	
    }
    
    @Test
    
    void testEmpezarActividad() {
    	Tarea tarea = new Tarea("Tarea 1", "Resolver problemas", "Matemáticas básicas", "Intermedio", 30, true, "Entrega en PDF");
    	QuizVerdad quiz = new QuizVerdad("Quiz Java", "Demostrar conocimiento en Java a traves de preguntas verdad o falso", "Preguntas Verdadero o Falso", "Fácil", 15, true, 80);
    	boolean empezar = estudiante.empezarActividad(quiz);
    	assertTrue(empezar,"No se empezo correctamente la actividad cuando antes era null");
    	empezar = estudiante.empezarActividad(tarea);
    	assertFalse(empezar,"Se empezó la actividad cuando ya habia una empezada");
    	
    	
    }
    
    @Test
    
    void testRecomendarActividad() {
    	
        Tarea tarea1 = new Tarea("Tarea 1", "Resolver ejercicios básicos", "Matemáticas básicas", "Fácil", 30, true, "PDF");
        Tarea tarea2 = new Tarea("Tarea 2", "Resolver ejercicios intermedios", "Matemáticas intermedias", "Intermedio", 45, true, "PDF");
        Tarea tarea3 = new Tarea("Tarea 3", "Resolver ejercicios avanzados", "Matemáticas avanzadas", "Avanzado", 60, true, "PDF");
        
        
        actividades.put(tarea1.getId(), tarea1);
        actividades.put(tarea2.getId(), tarea2);
        actividades.put(tarea3.getId(), tarea3);

        
        LearningPath learnPath = new LearningPath("Matemáticas", "Dominar conceptos de matemáticas", "Intermedio");
        learnPath.agregarActividad(tarea1);
        learnPath.agregarActividad(tarea2);
        learnPath.agregarActividad(tarea3);
        
        estudiante.getRegistroActividades().put(tarea1.getId(), Estado.EXITOSA);
        estudiante.getRegistroActividades().put(tarea2.getId(), Estado.NO_EXITOSA);
        estudiante.getRegistroActividades().put(tarea3.getId(), Estado.ENVIADA);

        // Caso 1: Actividad exitosa - recomienda la siguiente
        Actividad recomendada = estudiante.recomendarActividad(tarea1, learnPath);
        assertNotNull(recomendada, "No se recomendó una actividad.");
        assertEquals(tarea2.getId(), recomendada.getId(), "La actividad recomendada no es la siguiente en la lista.");

        // Caso 2: Actividad no exitosa - recomienda la anterior
        recomendada = estudiante.recomendarActividad(tarea2, learnPath);
        assertNotNull(recomendada, "No se recomendó una actividad.");
        assertEquals(tarea1.getId(), recomendada.getId(), "La actividad recomendada no es la anterior en la lista.");


        // Caso 3: Última actividad exitosa, no hay actividad siguiente
        recomendada = estudiante.recomendarActividad(tarea3, learnPath);
        assertNull(recomendada, "No debería haber una actividad recomendada después de la última.");

        // Caso 4: Primera actividad no exitosa, no hay actividad anterior
        estudiante.getRegistroActividades().put(tarea1.getId(), Estado.NO_EXITOSA);
        recomendada = estudiante.recomendarActividad(tarea1, learnPath);
        assertNull(recomendada, "No debería haber una actividad recomendada antes de la primera.");
    }
    
    @Test
    void testVerProgresoLearningPath() {
        
        Tarea tarea1 = new Tarea("Tarea 1", "Resolver ejercicios básicos", "Matemáticas básicas", "Fácil", 30, true, "PDF");
        Tarea tarea2 = new Tarea("Tarea 2", "Resolver ejercicios intermedios", "Matemáticas intermedias", "Intermedio", 45, true, "PDF");
        Tarea tarea3 = new Tarea("Tarea 3", "Resolver ejercicios avanzados", "Matemáticas avanzadas", "Avanzado", 60, true, "PDF");

        
        LearningPath learningPath = new LearningPath("Matemáticas", "Dominar conceptos de matemáticas", "Intermedio");
        learningPath.agregarActividad(tarea1);
        learningPath.agregarActividad(tarea2);
        learningPath.agregarActividad(tarea3);

        estudiante.empezarLearningPath(learningPath);

        estudiante.getRegistroActividades().put(tarea1.getId(), Estado.EXITOSA);
        estudiante.getRegistroActividades().put(tarea2.getId(), Estado.ENVIADA);
        estudiante.getRegistroActividades().put(tarea3.getId(), Estado.NO_EXITOSA);

        
        List<Double> progreso = estudiante.verProgresoLearningPath();
        
        
        assertEquals(66.67, progreso.get(0), 0.01, "El porcentaje de actividades completadas no es el esperado.");
        assertEquals(33.33, progreso.get(1), 0.01, "El porcentaje de actividades exitosas no es el esperado.");
    }

    @Test
    void testVerProgresoLearningPathSinActividadesCompletadas() {
       
        Tarea tarea1 = new Tarea("Tarea 1", "Resolver ejercicios básicos", "Matemáticas básicas", "Fácil", 30, true, "PDF");
        Tarea tarea2 = new Tarea("Tarea 2", "Resolver ejercicios intermedios", "Matemáticas intermedias", "Intermedio", 45, true, "PDF");

        
        LearningPath learningPath = new LearningPath("Ciencia", "Introducción a la ciencia", "Básico");
        learningPath.agregarActividad(tarea1);
        learningPath.agregarActividad(tarea2);

        estudiante.empezarLearningPath(learningPath);

        List<Double> progreso = estudiante.verProgresoLearningPath();


        
        assertEquals(0.0, progreso.get(0), 0.01, "El porcentaje de actividades completadas no es el esperado.");
        assertEquals(0.0, progreso.get(1), 0.01, "El porcentaje de actividades exitosas no es el esperado.");
    }

}
