package usuarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import actividades.Actividad;
import actividades.Encuesta;
import actividades.Estado;
import actividades.Examen;
import actividades.Opcion;
import actividades.Quiz;
import actividades.QuizVerdad;
import actividades.Recurso;
import actividades.Tarea;
import actividades.VerdaderoFalso;
import exceptions.UsuarioExistenteException;
import learningPaths.LearningPath;


public class Estudiante extends Usuario {
	private Actividad actividadEnCurso;
	private LearningPath learningPathEnCurso; //Lista con los learningPaths en curso, donde se almacenan sus id
	private HashMap <String, Actividad> dataActividades; // mapa con las actividades que existen
	private HashMap <String, LearningPath> dataLearningPaths; // mapa con los learningPath que existen
	private HashMap<String, Estado> registroActividades; //llave idActividades, valor Estado
	private HashMap<String, Double> registroLearningPaths; //llave idLearningPaths, valor double del progeso 0 a 1
	private List<String> intereses;
	
	public Estudiante(HashMap<String, Actividad> actividades, HashMap<String,LearningPath> learningPaths, String nLogin, String nPassword, List<String> intereses) {
		super(nLogin, nPassword);
		this.intereses = intereses;
		this.dataActividades = actividades;
		this.dataLearningPaths = learningPaths;
		this.actividadEnCurso = null;
		this.learningPathEnCurso = null;
	}
	
	public LearningPath buscarLearningPathPorNombre(String nombreLearningPath) {
		
		if(!dataLearningPaths.isEmpty()) {
			Iterator<LearningPath> iteradorClaves = dataLearningPaths.values().iterator();
			 while (iteradorClaves.hasNext()) {
				 	LearningPath learningPath = iteradorClaves.next();
			        if (learningPath.getTitulo().equalsIgnoreCase(nombreLearningPath)) {
			            return learningPath;
			        }
			    }
			}
		return null;
	}
	
	public LearningPath getLearningPathById(String id) {
		
		boolean x= dataLearningPaths.containsKey(id);
		
		if (x) {
			
			return dataLearningPaths.get(id);
		}
		else return null;
		
		}
	public Actividad buscarActividadPorNombre(String nombreActividad) {
		
		if(!dataActividades.isEmpty()) {
			Iterator<Actividad> iteradorClaves = dataActividades.values().iterator();
			 while (iteradorClaves.hasNext()) {
				 	Actividad actividad = iteradorClaves.next();
			        if (actividad.getTitulo().equalsIgnoreCase(nombreActividad)) {
			            return actividad;
			        }
			    }
			}
		return null;
	}
	
	public Actividad getActividadById(String id) {
		
		boolean x= dataActividades.containsKey(id);
		
		if (x) {
			
			return dataActividades.get(id);
		}
		else return null;
		
		}
	
	
	public boolean empezarLearningPath(LearningPath learningPath) {
			
			if(learningPathEnCurso == null) {
			
			learningPathEnCurso = learningPath;
			return true;
		}
			else {
				return false;
			}
		
	}
	
	public void finalizarLearningPath() {
		
		this.learningPathEnCurso = null;
		
	}
	

	public Actividad getActividadEnCurso() {
		return actividadEnCurso;
	}

	public void setActividadEnCurso(Actividad actividadEnCurso) {
		this.actividadEnCurso = actividadEnCurso;
	}

	public LearningPath getLearningPathEnCurso() {
		return learningPathEnCurso;
	}

	public void setLearningPathEnCurso(LearningPath learningPathEnCurso) {
		this.learningPathEnCurso = learningPathEnCurso;
	}

	public List<String> getIntereses() {
		return intereses;
	}

	public void setIntereses(List<String> intereses) throws UsuarioExistenteException {
		this.intereses = intereses;
		//usuarios.cargarEstudiante(login, password, intereses, registro);
	}
	
	public boolean empezarActividad(Actividad actividad) {
		
		if (actividadEnCurso!=null) {
			
			actividadEnCurso = actividad;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void finalizarActividad() {
		
		actividadEnCurso = null; 
	}
	
	
	public void realizarEncuesta(Encuesta encuesta, ArrayList<String> respuestas) {
		// TODO 
		// Llamar a clase encuesta
		Estado estado = encuesta.contestarEncuesta(this.login, respuestas);
		String id= encuesta.getId();
		registroActividades.put(id, estado);

		// Cargar estudiante no deberia ser. debe ser actualizar estudiante
		//usuarios.cargarEstudiante(login, password, intereses, registroActividades);
	}
	
	public HashMap<String, Estado> getRegistroActividades() {
		return registroActividades;
	}

	public void setRegistroActividades(HashMap<String, Estado> registroActividades) {
		this.registroActividades = registroActividades;
	}

	public HashMap<String, Double> getRegistroLearningPaths() {
		return registroLearningPaths;
	}

	public void setRegistroLearningPaths(HashMap<String, Double> registroLearningPaths) {
		this.registroLearningPaths = registroLearningPaths;
	}

	public void realizarExamen(Examen examen, ArrayList<String> respuestas) {
		// TODO 
		// Llamar a clase examen
		String idActividad = examen.getId();
		Estado estado = examen.contestarExamen(login, respuestas);
		registroActividades.put(idActividad, estado);

		// Cargar estudiante no deberia ser. debe ser actualizar estudiante
		//usuarios.cargarEstudiante(login, password, intereses, registroActividades);
	}
	
	public Estado realizarQuizVerdad(QuizVerdad quiz, ArrayList<VerdaderoFalso> respuestas) throws Exception {
		
		String idActividad = quiz.getId();
		Estado estado = quiz.calificar(login, respuestas);
		registroActividades.put(idActividad, estado);
		return estado;
		// Cargar estudiante no deberia ser. debe ser actualizar estudiante
		//usuarios.cargarEstudiante(login, password, intereses, registroActividades);
	}
	
	public Estado realizarQuiz(Quiz quiz, ArrayList<Opcion> respuestas) throws Exception {
		// TODO 
		// Llamar a clase quiz
		String idActividad = quiz.getId();
		Estado estado = quiz.calificar(login, respuestas);
		registroActividades.put(idActividad, estado);
		return estado;
		// Cargar estudiante no deberia ser. debe ser actualizar estudiante
		//usuarios.cargarEstudiante(login, password, intereses, registroActividades);
	}
	
	public void realizarRecurso(Recurso recurso) {
		Estado estado = recurso.realizarRecurso();
		String idActividad = recurso.getId();
		registroActividades.put(idActividad, estado);

		// Cargar estudiante no deberia ser. debe ser actualizar estudiante
		//usuarios.cargarEstudiante(login, password, intereses, registroActividades);
	}
	
	public void realizarTarea(Tarea tarea, String medioEntrega) {
		String idActividad = tarea.getId();
		Estado estado = tarea.realizarTarea(login, medioEntrega);
		registroActividades.put(idActividad, estado);
		
		// Cargar estudiante no deberia ser. debe ser actualizar estudiante
		//usuarios.cargarEstudiante(login, password, intereses, registroActividades);
	}
	
	public int calcularSimilitud(LearningPath learningPath) {
        int puntuacion = 0;
        List<String> intereses = getIntereses().stream().map(String::toLowerCase).collect(Collectors.toList());
        String titulo = learningPath.getTitulo().toLowerCase();
        String descripcion = learningPath.getDescripcion().toLowerCase();

        for (String interes : intereses) {
            if (titulo.contains(interes) || descripcion.contains(interes)) {
                puntuacion++;
            }
        }
        return puntuacion;
    }

    // Método principal que ordena los Learning Paths en orden de preferencia al estudiante.
    public List<LearningPath> recomendarLearningPaths(HashMap<String, LearningPath> totalLearningPaths) {
        // Crear lista a partir del HashMap
        List<LearningPath> listaCompletaLearningPaths = new ArrayList<>(totalLearningPaths.values());

        // Ordenar según la similitud
        listaCompletaLearningPaths.sort((lp1, lp2) -> {
            int similitud1 = calcularSimilitud(lp1);
            int similitud2 = calcularSimilitud(lp2);
            return Integer.compare(similitud2, similitud1); });

        return listaCompletaLearningPaths;
    }
}
