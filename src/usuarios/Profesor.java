package usuarios;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import actividades.Actividad;
import actividades.Encuesta;
import actividades.Estado;
import actividades.Examen;
import actividades.Quiz;
import actividades.QuizVerdad;
import actividades.Recurso;
import actividades.Tarea;
import actividades.TipoActividades;
import exceptions.UsuarioExistenteException;
import learningPaths.LearningPath;
import modelo.Consola;
import persistencia.PersistenciaActividades;
import persistencia.PersistenciaLearningPath;
import persistencia.PersistenciaUsuarios;

public class Profesor extends Usuario {

	private HashMap<String, Actividad> dataActividades;
	private HashMap<String, LearningPath> dataLearningPaths;
	private ArrayList<String> idActividadesCreadas;
	private ArrayList<String> idLearningPathsCreados;
	
	public Profesor(HashMap<String,Actividad> actividades, HashMap<String,LearningPath> learningPaths, String Login, String Password) {
		super(Login, Password);
		this.dataActividades = actividades;
		this.dataLearningPaths = learningPaths;
		this.idActividadesCreadas = new ArrayList<>();
		this.idLearningPathsCreados = new ArrayList<>();
		}
	
	
	
	
	public ArrayList<String> getIdActividadesCreadas() {
		return idActividadesCreadas;
	}




	public void setIdActividadesCreadas(ArrayList<String> idActividadesCreadas) {
		this.idActividadesCreadas = idActividadesCreadas;
	}




	public ArrayList<String> getIdLearningPathsCreados() {
		return idLearningPathsCreados;
	}




	public void setIdLearningPathsCreados(ArrayList<String> idLearningPathsCreadas) {
		this.idLearningPathsCreados = idLearningPathsCreadas;
	}

	public void agregarLearningPath(LearningPath learnPath) throws UsuarioExistenteException {
		
		dataLearningPaths.put(learnPath.getId(),learnPath);
		//learningPathExistentes.cargarLearningPath(learnPath);
		this.idLearningPathsCreados.add(learnPath.getId());
		//usuarios.cargarProfesor(login, password, idLearningPaths, idActividades);
		
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
	
	
	public void clonarActividad(Actividad actividad) throws UsuarioExistenteException {
		
		Actividad acti = actividad.clonarActividad();
		idActividadesCreadas.add(acti.getId());
		dataActividades.put(acti.getId(), acti);
		//guardarActividad(acti);	
		
	}

	public Recurso crearActividadRecurso(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, String material) throws Exception {
		
			Recurso recurso = new Recurso(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, material);
			System.out.println("Su actividad de tipo recurso ha sido creada.");
			idActividadesCreadas.add(recurso.getId());
			dataActividades.put(recurso.getId(), recurso);
			return recurso;
	}
	
	public void agregarActividadALearningPath(LearningPath learningPath, Actividad actividad) {
		learningPath.agregarActividad(actividad);
		dataLearningPaths.put(learningPath.getId(), learningPath);
	}
	
	public Tarea crearActividadTarea(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio,
			String idActividadTarea) throws Exception {
		
			Tarea tarea = new Tarea(titulo, objetivo,descripcion, nivel, duracionMinutos, obligatorio, idActividadTarea);
			System.out.println("Su actividad de tipo tarea ha sido creada.");
			return tarea;
		}
	
	public Examen crearActividadExamen (String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio) throws Exception {
		
		
			Examen examen= new Examen(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
			System.out.println("Su actividad de tipo examen ha sido creada.");
			idActividadesCreadas.add(examen.getId());
			dataActividades.put(examen.getId(), examen);
			return examen;
	
		}
	
	public Encuesta crearActividadEncuesta (String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio) throws Exception {
			Encuesta encuesta= new Encuesta(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
			System.out.println("Su actividad de tipo examen ha sido creada.");
			idActividadesCreadas.add(encuesta.getId());
			dataActividades.put(encuesta.getId(), encuesta);
			return encuesta;
	
		}
	public QuizVerdad crearQuizVerdaderoFalso(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, float calificacionMinima) {
		
		QuizVerdad quiz= new QuizVerdad(titulo, objetivo, descripcion,nivel ,duracionMinutos, obligatorio, calificacionMinima);
		System.out.println("Su actividad de tipo quiz ha sido creada.");
		return quiz;
		
		
	}
	
	public Quiz crearActividadQuiz (String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, float calificacionMinima) {	
		
		Quiz quiz= new Quiz(titulo, objetivo, descripcion,nivel ,duracionMinutos, obligatorio, calificacionMinima);
		System.out.println("Su actividad de tipo quiz ha sido creada.");
		return quiz;
		
	}
	
	
	public String mostrarRespuestasExamen(Examen examen) {
		
		 StringBuilder sb = new StringBuilder();
		    for (Map.Entry<String, ArrayList<String>> entry : examen.getRespuestas().entrySet()) {
		        String login = entry.getKey();
		        ArrayList<String> respuestas = entry.getValue();
		        
		        sb.append("Estudiante: ").append(login).append("\n");
		        sb.append("Respuestas al examen: ");
		        for (String valor : respuestas) {
		            sb.append(valor).append(", ");
		        }
		        if (!respuestas.isEmpty()) {
		            sb.setLength(sb.length() - 2);
		        }
		        sb.append("\n");
		    }
		    return sb.toString();
		
	}
	
//	private void calificarExamen(String idEstudiante, Examen examen, Estado estado) {
//		
//		
//		
//	}

	
	public String mostrarRespuestasEncuesta(Encuesta encuesta) {
		
		 StringBuilder sb = new StringBuilder();
		    for (Map.Entry<String, ArrayList<String>> entry : encuesta.getRespuestas().entrySet()) {
		        String login = entry.getKey();
		        ArrayList<String> respuestas = entry.getValue();
		        
		        sb.append("Estudiante: ").append(login).append("\n");
		        sb.append("Respuestas al examen: ");
		        for (String valor : respuestas) {
		            sb.append(valor).append(", ");
		        }
		        if (!respuestas.isEmpty()) {
		            sb.setLength(sb.length() - 2);
		        }
		        sb.append("\n");
		    }
		    return sb.toString();
		
	}
	public String mostrarRespuestasTarea(Tarea tarea) {
		
		 StringBuilder sb = new StringBuilder();
		    for (Map.Entry<String, String> entry : tarea.getRespuestas().entrySet()) {
		        String login = entry.getKey();
		        String medio = entry.getValue();
		        
		        sb.append("Estudiante: ").append(login).append("\n");
		        sb.append("Medio de entrega de la tarea: ").append(medio);
		        sb.append("\n");
		    }
		    return sb.toString();
		
	}


//	public void guardarActividad(Actividad actividad) throws UsuarioExistenteException {
//		actividades.put(actividad.getId(),actividad); // Mapa id, Actividad TOTALES
//		actividadesExistentes.cargarActividad(actividad); // Algo de persistencia no se sabe
//		idActividades.add(actividad.getId()); // Lista con id de actividades
//		// NO SE CARGA, SE ACTUALIZA
//		usuarios.cargarProfesor(login, password, idActividades, idLearningPaths);
//	}
	
	public LearningPath crearLearningPath(String titulo, String objetivo, String nivel) {
		LearningPath learningPath = new LearningPath(titulo, objetivo, nivel);
		dataLearningPaths.put(learningPath.getId(), learningPath);
		return learningPath;
	}
//	
//	public void guardarLearningPath(LearningPath learningPath) throws UsuarioExistenteException{
//		learningPaths.put(learningPath.getId(),learningPath);
//		learningPathExistentes.cargarLearningPath(learningPath);
//		idLearningPaths.add(learningPath.getId());
//		usuarios.cargarProfesor(login, password, idActividades, idLearningPaths);
//	}
////	
//	private void agregarActividad(Actividad actividad, LearningPath learnPath) throws UsuarioExistenteException {
//		List<Actividad> actividadesLearn = learnPath.getListaActividades();
//		actividadesLearn.add(actividad);
//		learningPathExistentes.cargarLearningPath(learnPath);
//		usuarios.cargarProfesor(login, password, idLearningPaths, idActividades);
//	}
//	
	private void mostrarResenas(LearningPath learnPath) {
		List<Actividad> actividades  = learnPath.getListaActividades();
		for(Actividad actividad :actividades) {
			List<String> resenas = actividad.getResenas();
			System.out.println("Titulo: "+ actividad.getTitulo());
			int counter = 1;
			for(String resena: resenas){
		
				System.out.println("Resena "+ counter+": " + resena);
			
			System.out.println("---------------------------------------");
			}
		}
	}
}
