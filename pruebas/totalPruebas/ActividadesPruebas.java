package totalPruebas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividades.Encuesta;
import actividades.Examen;
import actividades.Quiz;
import actividades.QuizVerdad;
import actividades.Recurso;
import actividades.Tarea;
import learningPaths.LearningPath;
import usuarios.Estudiante;

class ActividadesPruebas {
	private Tarea tarea;
    private Quiz quiz;
    private Recurso recurso;
    private Encuesta encuesta;
    private QuizVerdad quizV;
    private Examen examen;
	@BeforeEach
	void setUp() throws Exception {
		
        tarea = new Tarea("Tarea 1", "Resolver ejercicios", "Problemas matemáticos", "Básico", 60, true, "PDF");
    	quiz = new Quiz("Quiz Historia Colombia", "Demostrar conocimiento sobre Colombia", "Opcion multiple", "Principiante", 30, false, 60);
    	encuesta = new Encuesta("Encuesta 1", "Evaluar satisfacción", "Preguntas abiertas", "Básico", 15, true);
    	recurso = new Recurso("Lectura sobre Napoleón", "Aprender sobre el inicio de la conquista", "En esta lectura aprenderas sobre Napoleon y el inicio de su conquista.", "Fácil", 40, true, "RecursoNapoleon.PDF" );
    	quizV = new QuizVerdad("Quiz Java", "Demostrar conocimiento en Java a traves de preguntas verdad o falso", "Preguntas Verdadero o Falso", "basico", 15, true, 80);
    	examen = new Examen("Examen Final", "Evaluar el aprendizaje del curso", "Examen escrito", "Avanzado", 60, true);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
	
	}

}
