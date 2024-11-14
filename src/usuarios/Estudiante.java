package usuarios;

import java.util.HashMap;

import java.util.List;

import actividades.*;
import exceptions.UsuarioExistenteException;
import learningPaths.LearningPath;
import persistencia.PersistenciaUsuarios;


public class Estudiante extends Usuario {
	
	private PersistenciaUsuarios usuarios;
	private Actividad actividadEnCurso;
	private LearningPath learningPathEnCurso;
	private HashMap<String, Estado> registroActividades; //llave idActividades, valor Estado
	private HashMap<String, Double> registroLearningPaths; //llave idLearningPaths, valor double del progeso 0 a 1
	private List<LearningPath> learningPaths;
	private List<String> intereses;
	
	public Estudiante(HashMap<String, Estado> registroActividades, HashMap<String, Double> registroLearningPaths, PersistenciaUsuarios usuarios, String nLogin, String nPassword, List<String> intereses, Actividad actividadEnCurso, LearningPath learningPathEnCurso) {
		super(nLogin, nPassword);
		this.usuarios = usuarios;
		this.usuarios=usuarios;
		this.actividadEnCurso = actividadEnCurso;
		this.learningPathEnCurso= learningPathEnCurso;
		this.registroActividades= registroActividades;
		this.registroLearningPaths= registroLearningPaths;
	}
	
	
	public LearningPath getLearningPath(String id) {
		for (LearningPath learningPath: learningPaths) {
			if (learningPath.getId().equals(id)) {
				System.out.println("Se encontró el siguiente learningPath: ");
				mostrarLearningPath(learningPath);
				return learningPath;
			}
		}
		System.out.println("Lo siento no se encontró un learningPath con ese titulo");
		return null;
	}
	
	public void mostrarLearningPath(LearningPath learning) {
		System.out.println("Titulo: "+learning.getTitulo()+"\n"+"Descripción: "+learning.getDescripcion()+"\n"+"Nivel"+learning.getNivel()+"\n"+"Duracion"+learning.getDuracion()+"\n"+"Rating"+learning.getRating());
		for(Actividad actividad: learning.getListaActividades()) {
			System.out.println("Titulo"+actividad.getTitulo()+"\n"+"descripcion"+actividad.getDescripcion()+"\n"+"objetivo"+actividad.getObjetivo()+"\n"+"Es obligatoria?"+actividad.isObligatorio()+"\n"+"rating"+actividad.getRating()+"\n"+"resenas:"+actividad.getResenas()+"\n"+"Tipo Actividad: "+actividad.getTipoActividad().name()+"\n"+"Duracion"+actividad.getDuracionMinutos());
			if(actividad.getPrerequisitos()!=null) {
				for(Actividad preActividad:actividad.getPrerequisitos() ) {
					System.out.println("Titulo"+preActividad.getTitulo()+"\n"+"descripcion"+preActividad.getDescripcion()+"\n"+"objetivo"+preActividad.getObjetivo()+"\n"+"Es obligatoria?"+preActividad.isObligatorio()+"\n"+"rating"+preActividad.getRating()+"\n"+"resenas:"+preActividad.getResenas()+"\n"+"Tipo Actividad: "+preActividad.getTipoActividad().name()+"\n"+"Duracion"+preActividad.getDuracionMinutos());
				}
			}
		}
	}
		
	public void agregarLearningPath(LearningPath learning) {
		this.learningPathEnCurso= learning;
	}
	
	public void mostrarActividadesLearningPathEnCurso() {
		mostrarLearningPath(this.learningPathEnCurso);
	}
	public void elegirActividad(String titulo) {
		for(Actividad actividad: this.learningPathEnCurso.getListaActividades()) {
			if(actividad.getTitulo().equals(titulo)) {
				this.actividadEnCurso= actividad;
			} else {
				System.out.println("No se encontro actividad con ese titulo en el learningPath");
			}
		}		
	}

	public PersistenciaUsuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(PersistenciaUsuarios usuarios) {
		this.usuarios = usuarios;
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

	public HashMap<String, Estado> getRegistroActividades() {
		return registroActividades;
	}

	public void registroActividades(HashMap<String, Estado> registroActividades) {
		this.registroActividades = registroActividades;
	}

	public List<LearningPath> getLearningPaths() {
		return learningPaths;
	}

	public void setLearningPaths(List<LearningPath> learningPaths) {
		this.learningPaths = learningPaths;
	}

	public List<String> getIntereses() {
		return intereses;
	}

	public void setIntereses(List<String> intereses) throws UsuarioExistenteException {
		this.intereses = intereses;
		//usuarios.cargarEstudiante(login, password, intereses, registro);
	}
	
	public void iniciarActividadEnCurso() throws Exception {
		TipoActividades tipoActividad = actividadEnCurso.getTipoActividad();
		if(tipoActividad==TipoActividades.Encuesta) {
			Encuesta encuesta = (Encuesta) actividadEnCurso;
			encuesta.realizarActividad();
			this.actividadEnCurso = encuesta;
		}
		else if (tipoActividad==TipoActividades.Examen) {
			Examen examen = (Examen) actividadEnCurso;
			examen.realizarActividad();
			this.actividadEnCurso = examen;
		}
		else if (tipoActividad==TipoActividades.Quiz) {
			Quiz quiz = (Quiz) actividadEnCurso;
			quiz.realizarActividad();
			this.actividadEnCurso = quiz;
		}
		else if (tipoActividad==TipoActividades.Tarea) {
			Tarea tarea = (Tarea) actividadEnCurso;
			tarea.realizarActividad();
			this.actividadEnCurso = tarea;
			registroActividades.put(actividadEnCurso.getTitulo(), tarea.getEstadoTarea());
			usuarios.cargarEstudiante(login, password, intereses, registroActividades);
			this.actividadEnCurso = tarea;
		}
		else if (tipoActividad==TipoActividades.Recurso) {
			Recurso recurso = (Recurso) actividadEnCurso;
			recurso.realizarActividad();
			registroActividades.put(actividadEnCurso.getTitulo(), recurso.getEstado());
			usuarios.cargarEstudiante(login, password, intereses, registroActividades);
			this.actividadEnCurso = recurso;
		}
	}
	
	protected String getLogin() {
		return login;
	}

	protected String getPassword() {
		return password;
	}
		
	public void responderExamen(String respuesta) {
		
		Examen examen = (Examen) actividadEnCurso; 
		examen.responder(respuesta);
		registro.put(examen.getTitulo(), examen.getEstado());
		//usuarios.cargarEstudiante(login, password, intereses, registro);
	}
	
	public void responderQuiz(List<String> respuesta) throws Exception {
		
		Quiz quiz = (Quiz) actividadEnCurso; 
		quiz.agregarRespuestas(respuesta);
		quiz.calificar(respuesta);
		quiz.mostrarExplicaciones();
		registro.put(quiz.getTitulo(), quiz.getEstado());
		usuarios.cargarEstudiante(login, password, intereses, registro);
	}
	
	public void responderEncuesta(String respuesta) {
		
		Encuesta encuesta = (Encuesta) actividadEnCurso; 
		encuesta.responder(respuesta);
		registro.put(encuesta.getTitulo(), encuesta.getEstado());
		//usuarios.cargarEstudiante(login, password, intereses, registro);
		// deberia ser actualozar estudiante NO CARGAR
	}
}
