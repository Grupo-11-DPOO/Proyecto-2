package usuarios;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import actividades.Actividad;
import actividades.Encuesta;
import actividades.Estado;
import actividades.Examen;
import actividades.Quiz;
import actividades.Recurso;
import actividades.Tarea;
import actividades.TipoActividades;
import exceptions.UsuarioExistenteException;
import learningPaths.LearningPath;
import persistencia.PersistenciaActividades;
import persistencia.PersistenciaLearningPath;
import persistencia.PersistenciaUsuarios;

public class Profesor extends Usuario {
	
	private String login;
	private String password;
	private HashMap<String, Actividad> actividades;
	private HashMap<String, LearningPath> learningPaths;
	private PersistenciaActividades actividadesExistentes;
	private PersistenciaLearningPath learningPathExistentes;
	private PersistenciaUsuarios usuarios;
	private List<String> idActividades;
	private List<String> idLearningPaths;
	
	public Profesor(PersistenciaUsuarios usuarios, PersistenciaLearningPath learninPathExistentes,PersistenciaActividades actividadesExistentes, String nLogin, String nPassword, List<String> idActividades, List<String> idLearningPaths) {
		super(nLogin, nPassword);
		this.usuarios= usuarios;
		this.actividadesExistentes= actividadesExistentes;
		this.learningPathExistentes= learninPathExistentes;
		this.login = nLogin;
		this.password = nPassword;
		this.idActividades = idActividades;
		this.idLearningPaths = idLearningPaths;
		}
	
	public HashMap<String, Actividad> getActividades() {
		return actividades;
	}
	
	public void setActividades(List<Actividad> actividades) throws UsuarioExistenteException {
		this.actividades= (HashMap<String, Actividad>) actividades;
		this.idActividades = new ArrayList<String>();
		for(Actividad actividad: actividades) {
			String idActividad = actividad.getId();
			this.idActividades.add(idActividad);
			
		}
		usuarios.cargarProfesor(login, password, idLearningPaths, idActividades);
	}

	public List<String> getLearningPaths() {
		return idLearningPaths;
	}

	public void setLearningPaths(HashMap<String, LearningPath> learningPaths) throws UsuarioExistenteException {
		this.learningPaths = learningPaths;
		this.idLearningPaths = new ArrayList<String>();
		for(LearningPath learning: learningPaths.values()) {
			String idLearning = learning.getId();
			this.idLearningPaths.add(idLearning);
			
		}
		usuarios.cargarProfesor(login, password, idLearningPaths, idActividades);
	}

	protected String getPassword() {
		return password;
	}

	protected void setPassword(String password) throws UsuarioExistenteException {
		this.password = password;
		usuarios.cargarProfesor(login, password, idLearningPaths, idActividades);
	}

	protected String getLogin() {
		return login;
	}

	protected void setLogin(String login) throws UsuarioExistenteException {
		this.login = login;
		usuarios.cargarProfesor(login, password, idLearningPaths, idActividades);
	}

	public void agregarLearningPath(LearningPath learnPath) throws UsuarioExistenteException {
		if (this.learningPaths== null) {
			this.learningPaths = new HashMap<String,LearningPath>();
		}
		if (this.idLearningPaths==null) {
			this.idLearningPaths= new ArrayList<String>();
		}
		learningPaths.put(learnPath.getId(),learnPath);
		learningPathExistentes.cargarLearningPath(learnPath);
		this.idLearningPaths.add(learnPath.getId());
		usuarios.cargarProfesor(login, password, idLearningPaths, idActividades);
		
	}
	
	private void clonarActividad(Actividad actividad) throws UsuarioExistenteException {
		Actividad acti = actividad.clonarActividad();
		actividadesExistentes.cargarActividad(acti);
		guardarActividad(acti);	
		
	}

	public void crearActividadRecurso(String id, String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio,float rating, List<String> resenas ,String material,Estado estado, TipoActividades tipoActividad) throws Exception {
		
			Recurso recurso = new Recurso(id,titulo, objetivo,descripcion, nivel, duracionMinutos, obligatorio,rating, resenas, material,estado,tipoActividad);
			guardarActividad(recurso);
			System.out.println("Su actividad de tipo recurso ha sido creada y agregada con exito a sus actividades.");
	}
	
	public void agregarActividadALearningPath(LearningPath learningPath, Actividad actividad) {
		learningPath.getListaActividades().add(actividad);

	}
	
	public void crearActividadTarea(String id, String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio,
			LocalDate fechaLimite, List<String> prerrequisitos,double rating,List<String> resenas,String medioEntrega,Estado estado,   TipoActividades tipoActividad) throws Exception {
		
			Tarea tarea = new Tarea(id,titulo, objetivo,descripcion, nivel, duracionMinutos, obligatorio, fechaLimite, prerrequisitos,rating,resenas ,medioEntrega, estado,tipoActividad);
			guardarActividad(tarea);
			System.out.println("Su actividad de tipo tarea ha sido creada y agregada con exito a sus actividades.");
		}
	
	public void crearActividadExamen (String id, String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, String resultado,
			boolean obligatorio,float rating, List<String> resenas,List<String> preguntasExamen,List<String> respuestasExamen, Estado estadoExamen, TipoActividades tipoActividad) throws Exception {
		
		
			Examen examen= new Examen(id,titulo, objetivo, descripcion,nivel ,duracionMinutos, resultado,obligatorio, rating, resenas,preguntasExamen,respuestasExamen,estadoExamen,tipoActividad);
			guardarActividad(examen);
			System.out.println("Su actividad de tipo examen ha sido creada y agregada con exito a sus actividades.");
	
		}
	
	public void crearActividadEncuesta (String id, String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, List<String> prerequisitos,float rating,List<String> resenas,List<String> preguntasEncuesta, List<String> respuestasEncuestas,Estado estado,TipoActividades tipoActividad) throws Exception {
		
			Encuesta encuesta= new Encuesta(id,titulo, objetivo, descripcion,nivel, duracionMinutos, obligatorio, prerequisitos,rating,resenas,preguntasEncuesta,respuestasEncuestas, estado,tipoActividad);
			guardarActividad(encuesta);
			System.out.println("Su actividad de tipo examen ha sido creada y agregada con exito a sus actividades.");
	
		}
	
	public void crearActividadQuiz (String id, String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, Map<String, List<String>> preguntasQuiz, Map<String, String> respuestasCorrectasQuiz, Map<String, String> explicacionOpcionesQuiz,Estado estado,TipoActividades tipoActividad) throws UsuarioExistenteException{		
		
		Quiz quiz= new Quiz(id,titulo, objetivo, descripcion,nivel ,duracionMinutos, obligatorio, preguntasQuiz, respuestasCorrectasQuiz, explicacionOpcionesQuiz, estado, tipoActividad);
		guardarActividad(quiz);
		System.out.println("Su actividad de tipo quiz ha sido creada y agregada con exito a sus actividades.");
		
	}
	
	private void marcarTareaComoExitosa(Actividad actividad, boolean exitoso) throws Exception{
		// Se revisa que la actividad sea de pertenencia del profesor.
		List<Actividad> actividadesProfesor = (List<Actividad>) getActividades();
		if (actividadesProfesor.contains(actividad)) {
			// Se revisa tipo de actividad
			if (actividad.getTipoActividad().equals(TipoActividades.Tarea)) {
				Tarea tarea = (Tarea) actividad;
				// Decide como se debe marcar la actividad (exitosa = true, no exitosa = false)
				tarea.marcarComoExitosa(exitoso);
			} else if (actividad.getTipoActividad().equals(TipoActividades.Examen)) {
				Examen tarea = (Examen) actividad;
				// Decide como se debe marcar la actividad (exitosa = true, no exitosa = false)
				tarea.marcarComoExitosa(exitoso);
			}	
		} else {
			throw new Exception("La actividad no le pertenece.");
		}
	}
		

	public void guardarActividad(Actividad actividad) throws UsuarioExistenteException {
		actividades.put(actividad.getId(),actividad);
		actividadesExistentes.cargarActividad(actividad);
		idActividades.add(actividad.getId());
		usuarios.cargarProfesor(login, password, idActividades, idLearningPaths);
	}
	
	private void agregarActividad(Actividad actividad, LearningPath learnPath) throws UsuarioExistenteException {
		List<Actividad> actividadesLearn = learnPath.getListaActividades();
		actividadesLearn.add(actividad);
		learningPathExistentes.cargarLearningPath(learnPath);
		usuarios.cargarProfesor(login, password, idLearningPaths, idActividades);
	}
	
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
