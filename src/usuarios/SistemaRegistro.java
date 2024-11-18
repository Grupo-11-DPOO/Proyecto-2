package usuarios;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actividades.Actividad;
import actividades.Estado;
import actividades.TipoActividades;
import exceptions.UsuarioExistenteException;
import learningPaths.LearningPath;
import persistencia.PersistenciaActividades;
import persistencia.PersistenciaLearningPath;
import persistencia.PersistenciaUsuarios;


public class SistemaRegistro {
	private PersistenciaUsuarios usuarios;
	private PersistenciaActividades persistenciaActividades;
	private PersistenciaLearningPath persistenciaLearningPath;
	private HashMap<String,String> notaActividad;
	private HashMap<String,Profesor>datosProfesores;
	private HashMap<String,Estudiante>datosEstudiantes;
	public HashMap<String,LearningPath> learningPaths;
	public HashMap<String, Actividad> actividades;
	
	public SistemaRegistro() {
		this.usuarios = new PersistenciaUsuarios();
		this.persistenciaActividades= new PersistenciaActividades();
		this.persistenciaLearningPath = new PersistenciaLearningPath();
		try {
			cargarDatos();
		} catch (UsuarioExistenteException e) {
			e.printStackTrace();
		}
	}
	
	public void cargarDatos() throws UsuarioExistenteException{
		this.datosProfesores = new HashMap<String,Profesor>();
		this.datosEstudiantes = new HashMap<String,Estudiante>();
		JSONArray datosUsuarios = usuarios.getUsuarios();
		JSONArray datosLearningPaths = persistenciaLearningPath.getLearningPath();
		JSONArray datosActividades = persistenciaActividades.getActividades();
		
		this.actividades = new HashMap<String,Actividad>();
		if (datosActividades== null) {
	    for (int i = 0; i < datosActividades.length(); i++) {
	    	try {
                JSONObject actividadJson = datosActividades.getJSONObject(i);
                if (!actividadJson.has("id")) {
                    System.out.println("Warning: Activity at index " + i + " is missing 'id' field");
                    continue;
                }
                String id = actividadJson.getString("id");
                String tipoActividadStr = actividadJson.getString("tipoActividad");
    	        TipoActividades tipoActividad = TipoActividades.valueOf(tipoActividadStr);
    	        
    	        String titulo = actividadJson.getString("titulo");
    	        String descripcion = actividadJson.getString("descripcion");
    	        String objetivo = actividadJson.getString("objetivo");
    	        String nivel = actividadJson.getString("nivel");
    	        int duracionMinutos = actividadJson.getInt("duracion");
    	        String resultado = actividadJson.getString("resultado");
    	        boolean obligatorio = actividadJson.getBoolean("obligatorio");
    	        float rating = (float) actividadJson.getDouble("rating");
    	        LocalDate fechaLimite = LocalDate.parse(actividadJson.getString("fechaLimite"));
    	        List<String> resenas = jsonArrayToList(actividadJson.getJSONArray("resenas"));

    	        List<String> prerrequisitos = jsonArrayToList(actividadJson.getJSONArray("actividades"));

    	        Actividad actividad = null;

    	        switch (tipoActividad) {
    	            case Tarea:
    	                String medioEntrega = actividadJson.getString("medioEntrega");
    	                Estado estadoTarea = Estado.valueOf(actividadJson.getString("estado"));       
    	                //actividad = new Tarea(id, titulo, objetivo, descripcion,nivel, duracionMinutos, obligatorio, fechaLimite, prerrequisitos,rating, resenas, medioEntrega, estadoTarea,tipoActividad);
    	                this.actividades.put(id, actividad);
    	                break;
    	            case Examen:
    	                List<String> preguntasExamen = jsonArrayToList(actividadJson.getJSONArray("preguntas"));
    	                List<String> respuestasExamen = jsonArrayToList(actividadJson.getJSONArray("respuestas"));
    	                Estado estadoExamen = Estado.valueOf(actividadJson.getString("estado"));
    	                //actividad = new Examen(id, titulo, descripcion, objetivo, nivel, duracionMinutos, resultado, obligatorio, rating, resenas, preguntasExamen, respuestasExamen, estadoExamen, tipoActividad);
    	                this.actividades.put(id, actividad);
    	                break;
    	            case Encuesta:
    	                List<String> preguntasEncuesta = jsonArrayToList(actividadJson.getJSONArray("preguntas"));
    	                List<String> respuestasEncuesta = jsonArrayToList(actividadJson.getJSONArray("respuestas"));
    	                Estado estadoEncuesta = Estado.valueOf(actividadJson.getString("estado"));
    	                //actividad = new Encuesta(id, titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, prerrequisitos,rating, resenas, preguntasEncuesta, respuestasEncuesta, estadoEncuesta, tipoActividad);
    	                this.actividades.put(id, actividad);
    	                break;
    	            case Recurso:
    	                String material = actividadJson.getString("material");
    	                Estado estadoRecurso = Estado.valueOf(actividadJson.getString("estado"));
    	                //actividad = new Recurso(id, titulo, objetivo, descripcion, nivel, duracionMinutos,  obligatorio,  rating, resenas, material, estadoRecurso, tipoActividad);
    	                this.actividades.put(id, actividad);
    	                break;
    	            case Quiz:
    	       
    	                Map<String, List<String>> preguntasQuiz = jsonToMap(actividadJson.getJSONObject("preguntas"));
    	                Map<String, String> respuestasCorrectasQuiz = jsonToMapString(actividadJson.getJSONObject("respuestasCorrectas"));
    	                Map<String, String> explicacionOpcionesQuiz = jsonToMapString(actividadJson.getJSONObject("explicacionOpciones"));
    	                
    	                Estado estadoQuiz = Estado.valueOf(actividadJson.getString("estado"));
    	                //actividad = new Quiz(id, titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, preguntasQuiz, respuestasCorrectasQuiz, explicacionOpcionesQuiz, estadoQuiz, tipoActividad);
    	                this.actividades.put(id, actividad);
    	                break;
    	        }
    	      
            } catch (JSONException e) {
                System.out.println("Error processing activity at index " + i + ": " + e.getMessage());
            }
        }
		}	
	        
	    if (datosLearningPaths==null) {
	    	
	    
	    for (int i = 0; i < datosLearningPaths.length(); i++) {
	    	try {
                JSONObject jsonObject = datosLearningPaths.getJSONObject(i);
                if (!jsonObject.has("id")) {
                    System.out.println("Warning: Learning path at index " + i + " is missing 'id' field");
                    continue;
                }
                String id = jsonObject.getString("id");
    	        String titulo = jsonObject.getString("titulo");
    	        String descripcion = jsonObject.getString("descripcion");
    	        String nivel = jsonObject.getString("nivel");
    	        int duracion = jsonObject.getInt("duracion");
    	        double rating = jsonObject.getDouble("rating");
    	        LocalDateTime fechaCreacion = LocalDateTime.parse(jsonObject.getString("fechaCreacion"));
    	        LocalDateTime fechaModificacion = LocalDateTime.parse(jsonObject.getString("fechaModificacion"));
    	        int version = jsonObject.getInt("version");
    	        
    	        JSONArray actividadesArray = jsonObject.getJSONArray("actividades");
    	        List<Actividad> actividades1 = new ArrayList<>();
    	        
    	        for (int j = 0; j < actividadesArray.length(); j++) {
    				String idActividad1 = actividadesArray.getString(j);
    	            Actividad actividad = obtenerActividadPorId(idActividad1);
    	            if (actividad != null) {
    	                actividades1.add(actividad);
    	            }
    	        }
    	        
    	        //LearningPath learningPath1 = new LearningPath(id, titulo, descripcion, nivel, duracion, rating, version, fechaCreacion, fechaModificacion, actividades1);
    	        //learningPaths.put(learningPath1.getId(), learningPath1);
    	    }
             catch (JSONException e) {
                System.out.println("Error processing learning path at index " + i + ": " + e.getMessage());
            }

	    }    
	    }
	    
	    if (datosUsuarios!=null) {
	    for (int i = 0; i < datosUsuarios.length(); i++) {
	        JSONObject usuarioJson = datosUsuarios.getJSONObject(i);
	        String tipoUsuario = usuarioJson.getString("tipoUsuario");
	        String login = usuarioJson.getString("login");
	        String password = usuarioJson.getString("password");

	        if (tipoUsuario.equals("Profesor")) {
	            List<String> learningPathsProfesor = jsonArrayToList(usuarioJson.getJSONArray("learningPaths"));
	            List<String> actividadesProfesor = jsonArrayToList(usuarioJson.getJSONArray("actividades"));
	            
	            Profesor profesor = new Profesor(
	                usuarios, 
	                persistenciaLearningPath, 
	                persistenciaActividades, 
	                login, 
	                password, 
	                learningPathsProfesor, 
	                actividadesProfesor
	            );
	            asociarLearningPathsYActividades(profesor, learningPathsProfesor, actividadesProfesor);

	            this.datosProfesores.put(login, profesor);
	            
	        } else if (tipoUsuario.equals("Estudiante")) {

	        	List<String> interesesEstudiante = jsonArrayToList(usuarioJson.getJSONArray("intereses"));
	            String learningPathEnCursoID = usuarioJson.getString("learningPathEnCurso");
	            LearningPath learningPathEnCurso = learningPaths.get(learningPathEnCursoID); 
	            String actividadEnCursoID = usuarioJson.getString("actividadEnCurso");
	            Actividad actividadEnCurso = actividades.get(actividadEnCursoID);

	            Estudiante estudiante = new Estudiante(
	            	// registro no se como manejarlo
	                null,
	                null, 
	                login, 
	                password,
	                interesesEstudiante
	            );

	            this.datosEstudiantes.put(login, estudiante);
	        
	        }
	    }
	    }
	    
		
	    
	}

	private void asociarLearningPathsYActividades(Profesor profesor, List<String> idLearningPaths, List<String> idActividades) throws UsuarioExistenteException {

	    for (String idLearningPath : idLearningPaths) {
	        LearningPath lp = learningPaths.get(idLearningPath);
	        if (lp != null) {
	            profesor.agregarLearningPath(lp);
	        }
	    }

	    for (String idActividad : idActividades) {
	        Actividad actividad = actividades.get(idActividad);
	        if (actividad != null) {
	            profesor.guardarActividad(actividad);;
	        }
	    }
	}
	    

	
	private Actividad obtenerActividadPorId(String idActividad) {
	  
	    return actividades.get(idActividad);  
	}
	    
			
		private List<String> jsonArrayToList(JSONArray jsonArray) {
		    List<String> list = new ArrayList<>();
		    for (int i = 0; i < jsonArray.length(); i++) {
		        list.add(jsonArray.getString(i));
		    }
		    return list;
		}

		private Map<String, List<String>> jsonToMap(JSONObject jsonObject) {
		    Map<String, List<String>> map = new HashMap<>();
		    for (String key : jsonObject.keySet()) {
		        JSONArray jsonArray = jsonObject.getJSONArray(key);
		        List<String> list = jsonArrayToList(jsonArray);
		        map.put(key, list);
		    }
		    return map;
		}

		private Map<String, String> jsonToMapString(JSONObject jsonObject) {
		    Map<String, String> map = new HashMap<>();
		    for (String key : jsonObject.keySet()) {
		        map.put(key, jsonObject.getString(key));
		    }
		    return map;
		}

		
	
	public Profesor registrarProfesor(String login, String passWord) throws UsuarioExistenteException {
		
		Profesor profesor = new Profesor(this.actividades,this.learningPaths,login, passWord);
		
		
		usuarios.cargarProfesor(login, passWord, null, null);
		
		return profesor;
		
	}
	
	public Estudiante registrarEstudiante(String login, String passWord, List<String> intereses) throws UsuarioExistenteException {
		
<<<<<<< HEAD
		Estudiante estudiante = new Estudiante(null, null, login, passWord, intereses);
=======
		Estudiante estudiante = new Estudiante(this.actividades, this.learningPaths,login, passWord, intereses);
>>>>>>> branch 'master' of https://github.com/Grupo-11-DPOO/Proyecto-2.git
		
		usuarios.cargarEstudiante(login, passWord, intereses, estudiante.getRegistroActividades());
		
		return estudiante;
	}

	
	public boolean iniciarSesionProfesor(String login, String password) {
	    for (Map.Entry<String, Profesor> entry : datosProfesores.entrySet()) {
	        Profesor profesor = entry.getValue();
	        if (profesor.getLogin().equals(login) && profesor.getPassword().equals(password)) {
	            return true;
	        }
	    }
	    return false;  
	}
	public boolean iniciarSesionEstudiante(String login, String password) {
	    for (Map.Entry<String, Estudiante> entry : datosEstudiantes.entrySet()) {
	        Estudiante estudiante = entry.getValue();
	        if (estudiante.getLogin().equals(login) && estudiante.getPassword().equals(password)) {
	            return true;
	        }
	    }
	    return false;  
	}

	public HashMap<String, Profesor> getDatosProfesores() {
		return datosProfesores;
	}

	public void setDatosProfesores(HashMap<String, Profesor> datosProfesores) {
		this.datosProfesores = datosProfesores;
	}

	public HashMap<String, Estudiante> getDatosEstudiantes() {
		return datosEstudiantes;
	}

	public void setDatosEstudiantes(HashMap<String, Estudiante> datosEstudiantes) {
		this.datosEstudiantes = datosEstudiantes;
	}

	
}

