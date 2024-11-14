package persistencia;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

import actividades.Actividad;
import actividades.*;
import actividades.Estado;
import actividades.TipoActividades;
import learningPaths.LearningPath;

import org.json.JSONArray;
import org.json.JSONException;

import usuarios.*;
public class PersistenciaActividades {

	private File archivo;
    private final static String ruta = "data/actividades.JSON";
    public JSONArray actividadesArray = new JSONArray();
    
    List<String> idActividadesPrerrequisitos;
    private String id;
	public String titulo;
	public String descripcion;
	public String objetivo;
	public String nivel;
	public int duracionMinutos;
	private String resultado;
	public boolean obligatorio;
	public Date fechaLimite;
	public float rating;
	public List<Actividad> prerequisitos;
	public List<String> resenas;
	public TipoActividades tipoActividad;
	
    public PersistenciaActividades() {
        crearArchivo(); 
        getActividades(); 
	    }

    public void crearArchivo() {
        archivo = new File(ruta);
	    File carpeta = archivo.getParentFile();
		if (!carpeta.exists()) {
			 	carpeta.mkdirs();
			}
	    }

    public JSONArray getActividades() {
    	if (archivo.exists()) {
    		try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
    			StringBuilder stringBuilder = new StringBuilder();
    			String linea;

    		while ((linea = reader.readLine()) != null) {
    			stringBuilder.append(linea);
    		}

    		String jsonString = stringBuilder.toString();

	               
    	if (jsonString.trim().isEmpty()) {
    		 actividadesArray = new JSONArray();
	         } else {
	                    
	        	 try {
	        		 actividadesArray = new JSONArray(jsonString);
	        	 } catch (JSONException e) {
	        		 System.err.println("El contenido del archivo no es un JSONArray válido.");
	        		 e.printStackTrace();
	        		 actividadesArray = new JSONArray(); 
	                    }
	                }
	            	} catch (FileNotFoundException e) {
	                e.printStackTrace();
	            	} catch (IOException e) {
	            	e.printStackTrace();
	            }
	 
	        } else {
	            actividadesArray = new JSONArray(); 
	        }
			return actividadesArray;
	    }

	    public void cargarActividad(Actividad actividad) {
	    	
	    	TipoActividades tipoActividad = actividad.getTipoActividad();
	    	
	        crearArchivo();
	            JSONArray actividadesExistentes = getActividades();
	            String id = actividad.getId();
		        String titulo = actividad.getTitulo();
		        String descripcion = actividad.getDescripcion();
		        String objetivo = actividad.getObjetivo();
		        String nivel = actividad.getNivel();
		        int duracionMinutos = actividad.getDuracionMinutos();
		        String resultado = actividad.getResultado();
		        boolean obligatorio = actividad.isObligatorio();
		        String fechaLimite = actividad.fechaLimite.toString();
		        float rating = actividad.getRating();
		        List<String> resenas = actividad.getResenas();
		        if (actividad.getPrerequisitos()!=null) {
		        	
		        	for(int i =0; i<actividad.getPrerequisitos().size();i++){
		        		
		        		String idActividad = actividad.getPrerequisitos().get(i).getId(); 
		        		idActividadesPrerrequisitos.add(idActividad);
		        	}
		        	
		        }
		        else {
		        	this.idActividadesPrerrequisitos = new ArrayList<String>(); 
		        }
	            String idNuevaActividad = actividad.getId();
	            boolean actividadEncontrada = false;

	            for (int i = 0; i < actividadesExistentes.length(); i++) {
	                JSONObject actividadExistente = actividadesExistentes.getJSONObject(i);
	                if (actividadExistente.getString("id").equals(idNuevaActividad)) {
	                	 if(tipoActividad == TipoActividades.Encuesta) {
	         	        	
	                		 JSONObject nuevaActividad = actividadExistente;
	         		        nuevaActividad.put("id", id);
	         		        JSONArray prerrequisitosActividades = new JSONArray(idActividadesPrerrequisitos);
	         		        nuevaActividad.put("actividades", prerrequisitosActividades);
	         		        nuevaActividad.put("objetivo", objetivo);
	         		        nuevaActividad.put("titulo", titulo);
	         		        nuevaActividad.put("descripcion", descripcion);
	         		        nuevaActividad.put("nivel", nivel);
	         		        nuevaActividad.put("duracion", duracionMinutos);
	         		        nuevaActividad.put("resultado", resultado);
	         		        nuevaActividad.put("obligatorio",obligatorio);
	         		        nuevaActividad.put("rating", rating);
	         		        nuevaActividad.put("fechaLimite", fechaLimite);
	         		        nuevaActividad.put("resenas",resenas);
	         		        nuevaActividad.put("tipoActividad", tipoActividad);
	         		        Encuesta encuesta = (Encuesta) actividad;
	         		        List<String> preguntas = encuesta.getPreguntas();
	         		        List<String> respuestas = encuesta.getRespuestas();
	         		        Estado estado = encuesta.getEstado();
	         		        nuevaActividad.put("preguntas", preguntas);
	         		        nuevaActividad.put("respuestas", respuestas);
	         		        nuevaActividad.put("estado", estado.name());
	         		        escribirJsonEnArchivo(nuevaActividad);
	         	        	
	         	        	
	         	        	
	         	        }
	         	        else if (tipoActividad == TipoActividades.Examen) {
	         	        	JSONObject nuevaActividad = new JSONObject();
	         		        nuevaActividad.put("id", id);
	         		        JSONArray prerrequisitosActividades = new JSONArray(idActividadesPrerrequisitos);
	         		        nuevaActividad.put("actividades", prerrequisitosActividades);
	         		        nuevaActividad.put("objetivo", objetivo);
	         		        nuevaActividad.put("titulo", titulo);
	         		        nuevaActividad.put("descripcion", descripcion);
	         		        nuevaActividad.put("nivel", nivel);
	         		        nuevaActividad.put("duracion", duracionMinutos);
	         		        nuevaActividad.put("resultado", resultado);
	         		        nuevaActividad.put("obligatorio",obligatorio);
	         		        nuevaActividad.put("rating", rating);
	         		        nuevaActividad.put("fechaLimite", fechaLimite);
	         		        nuevaActividad.put("resenas",resenas);
	         		        nuevaActividad.put("tipoActividad", tipoActividad);
	         		        Examen examen = (Examen) actividad;
	         		        List<String> preguntas = examen.getPreguntas();
	         		        List<String> respuestas = examen.getRespuestas();
	         		        Estado estado = examen.getEstado();
	         		        nuevaActividad.put("preguntas", preguntas);
	         		        nuevaActividad.put("respuestas", respuestas);
	         		        nuevaActividad.put("estado", estado.name());
	         		        escribirJsonEnArchivo(nuevaActividad);
	         	        }
	         	        else if(tipoActividad == TipoActividades.Recurso) {
	         	        	JSONObject nuevaActividad = new JSONObject();
	         		        nuevaActividad.put("id", id);
	         		        JSONArray prerrequisitosActividades = new JSONArray(idActividadesPrerrequisitos);
	         		        nuevaActividad.put("actividades", prerrequisitosActividades);
	         		        nuevaActividad.put("objetivo", objetivo);
	         		        nuevaActividad.put("titulo", titulo);
	         		        nuevaActividad.put("descripcion", descripcion);
	         		        nuevaActividad.put("nivel", nivel);
	         		        nuevaActividad.put("duracion", duracionMinutos);
	         		        nuevaActividad.put("resultado", resultado);
	         		        nuevaActividad.put("obligatorio",obligatorio);
	         		        nuevaActividad.put("rating", rating);
	         		        nuevaActividad.put("fechaLimite", fechaLimite);
	         		        nuevaActividad.put("resenas",resenas);
	         		        nuevaActividad.put("tipoActividad", tipoActividad);
	         		        Recurso recurso = (Recurso) actividad;
	         		        String material = recurso.getMaterial();
	         		        Estado estado = recurso.getEstado();
	         		        nuevaActividad.put("material", material);
	         		        nuevaActividad.put("estado",estado.name());
	         		        escribirJsonEnArchivo(nuevaActividad);
	         	        }
	         	        else if(tipoActividad == TipoActividades.Tarea) {
	         	        	JSONObject nuevaActividad = new JSONObject();
	         		        nuevaActividad.put("id", id);
	         		        JSONArray prerrequisitosActividades = new JSONArray(idActividadesPrerrequisitos);
	         		        nuevaActividad.put("actividades", prerrequisitosActividades);
	         		        nuevaActividad.put("objetivo", objetivo);
	         		        nuevaActividad.put("titulo", titulo);
	         		        nuevaActividad.put("descripcion", descripcion);
	         		        nuevaActividad.put("nivel", nivel);
	         		        nuevaActividad.put("duracion", duracionMinutos);
	         		        nuevaActividad.put("resultado", resultado);
	         		        nuevaActividad.put("obligatorio",obligatorio);
	         		        nuevaActividad.put("rating", rating);
	         		        nuevaActividad.put("fechaLimite", fechaLimite);
	         		        nuevaActividad.put("resenas",resenas);
	         		        nuevaActividad.put("tipoActividad", tipoActividad);
	         		        Tarea tarea = (Tarea) actividad;
	         		        Estado estado = tarea.getEstadoTarea();
	         		        String medioEntrega  = tarea.getMedioEntrega();
	         		        nuevaActividad.put("medioEntrega", medioEntrega);
	         		        nuevaActividad.put("estado",estado.name());
	         		        escribirJsonEnArchivo(nuevaActividad);
	         		        
	         	        }
	         	        else if (tipoActividad == TipoActividades.Quiz) {
	         	        	JSONObject nuevaActividad = new JSONObject();
	         		        nuevaActividad.put("id", id);
	         		        JSONArray prerrequisitosActividades = new JSONArray(idActividadesPrerrequisitos);
	         		        nuevaActividad.put("actividades", prerrequisitosActividades);
	         		        nuevaActividad.put("objetivo", objetivo);
	         		        nuevaActividad.put("titulo", titulo);
	         		        nuevaActividad.put("descripcion", descripcion);
	         		        nuevaActividad.put("nivel", nivel);
	         		        nuevaActividad.put("duracion", duracionMinutos);
	         		        nuevaActividad.put("resultado", resultado);
	         		        nuevaActividad.put("obligatorio",obligatorio);
	         		        nuevaActividad.put("rating", rating);
	         		        nuevaActividad.put("fechaLimite", fechaLimite);
	         		        nuevaActividad.put("resenas",resenas);
	         		        nuevaActividad.put("tipoActividad", tipoActividad);
	         		        Quiz quiz = (Quiz) actividad;
	         		        Estado estado = quiz.getEstado();
	         		        
	         		        JSONObject jsonPreguntas = new JSONObject();
	         		        for (Map.Entry<String, List<String>> entry : quiz.getPreguntas().entrySet()) {
	         		            JSONArray opcionesArray = new JSONArray(entry.getValue());
	         		            jsonPreguntas.put(entry.getKey(), opcionesArray);
	         		        }
	         		        nuevaActividad.put("preguntas", jsonPreguntas);
	         		        
	         		        JSONObject jsonRespuestasCorrectas = new JSONObject(quiz.getRespuestasCorrectas());
	         		        nuevaActividad.put("respuestasCorrectas", jsonRespuestasCorrectas);


	         		        JSONObject jsonExplicacionOpciones = new JSONObject(quiz.getExplicacionOpciones());
	         		        nuevaActividad.put("explicacionOpciones", jsonExplicacionOpciones);
	         		        
	         		        nuevaActividad.put("estado",estado.name());
	         		        escribirJsonEnArchivo(nuevaActividad);
	                    actividadEncontrada = true;
	                    break;
	                }
	            }

	        
	        if(!actividadEncontrada){
	        	
	        
	        if(tipoActividad == TipoActividades.Encuesta) {
	        	JSONObject nuevaActividad = new JSONObject();
		        nuevaActividad.put("id", id);
		        JSONArray prerrequisitosActividades = new JSONArray(idActividadesPrerrequisitos);
		        nuevaActividad.put("actividades", prerrequisitosActividades);
		        nuevaActividad.put("objetivo", objetivo);
		        nuevaActividad.put("titulo", titulo);
		        nuevaActividad.put("descripcion", descripcion);
		        nuevaActividad.put("nivel", nivel);
		        nuevaActividad.put("duracion", duracionMinutos);
		        nuevaActividad.put("resultado", resultado);
		        nuevaActividad.put("obligatorio",obligatorio);
		        nuevaActividad.put("rating", rating);
		        nuevaActividad.put("fechaLimite", fechaLimite);
		        nuevaActividad.put("resenas",resenas);
		        nuevaActividad.put("tipoActividad", tipoActividad);
		        Encuesta encuesta = (Encuesta) actividad;
		        List<String> preguntas = encuesta.getPreguntas();
		        List<String> respuestas = encuesta.getRespuestas();
		        Estado estado = encuesta.getEstado();
		        nuevaActividad.put("preguntas", preguntas);
		        nuevaActividad.put("respuestas", respuestas);
		        nuevaActividad.put("estado", estado.name());
		        escribirJsonEnArchivo(nuevaActividad);
	        	
	        	
	        	
	        }
	        else if (tipoActividad == TipoActividades.Examen) {
	        	JSONObject nuevaActividad = new JSONObject();
		        nuevaActividad.put("id", id);
		        JSONArray prerrequisitosActividades = new JSONArray(idActividadesPrerrequisitos);
		        nuevaActividad.put("actividades", prerrequisitosActividades);
		        nuevaActividad.put("objetivo", objetivo);
		        nuevaActividad.put("titulo", titulo);
		        nuevaActividad.put("descripcion", descripcion);
		        nuevaActividad.put("nivel", nivel);
		        nuevaActividad.put("duracion", duracionMinutos);
		        nuevaActividad.put("resultado", resultado);
		        nuevaActividad.put("obligatorio",obligatorio);
		        nuevaActividad.put("rating", rating);
		        nuevaActividad.put("fechaLimite", fechaLimite);
		        nuevaActividad.put("resenas",resenas);
		        nuevaActividad.put("tipoActividad", tipoActividad);
		        Examen examen = (Examen) actividad;
		        List<String> preguntas = examen.getPreguntas();
		        List<String> respuestas = examen.getRespuestas();
		        Estado estado = examen.getEstado();
		        nuevaActividad.put("preguntas", preguntas);
		        nuevaActividad.put("respuestas", respuestas);
		        nuevaActividad.put("estado", estado.name());
		        escribirJsonEnArchivo(nuevaActividad);
	        }
	        else if(tipoActividad == TipoActividades.Recurso) {
	        	JSONObject nuevaActividad = new JSONObject();
		        nuevaActividad.put("id", id);
		        JSONArray prerrequisitosActividades = new JSONArray(idActividadesPrerrequisitos);
		        nuevaActividad.put("actividades", prerrequisitosActividades);
		        nuevaActividad.put("objetivo", objetivo);
		        nuevaActividad.put("titulo", titulo);
		        nuevaActividad.put("descripcion", descripcion);
		        nuevaActividad.put("nivel", nivel);
		        nuevaActividad.put("duracion", duracionMinutos);
		        nuevaActividad.put("resultado", resultado);
		        nuevaActividad.put("obligatorio",obligatorio);
		        nuevaActividad.put("rating", rating);
		        nuevaActividad.put("fechaLimite", fechaLimite);
		        nuevaActividad.put("resenas",resenas);
		        nuevaActividad.put("tipoActividad", tipoActividad);
		        Recurso recurso = (Recurso) actividad;
		        String material = recurso.getMaterial();
		        Estado estado = recurso.getEstado();
		        nuevaActividad.put("material", material);
		        nuevaActividad.put("estado",estado.name());
		        escribirJsonEnArchivo(nuevaActividad);
	        }
	        else if(tipoActividad == TipoActividades.Tarea) {
	        	JSONObject nuevaActividad = new JSONObject();
		        nuevaActividad.put("id", id);
		        JSONArray prerrequisitosActividades = new JSONArray(idActividadesPrerrequisitos);
		        nuevaActividad.put("actividades", prerrequisitosActividades);
		        nuevaActividad.put("objetivo", objetivo);
		        nuevaActividad.put("titulo", titulo);
		        nuevaActividad.put("descripcion", descripcion);
		        nuevaActividad.put("nivel", nivel);
		        nuevaActividad.put("duracion", duracionMinutos);
		        nuevaActividad.put("resultado", resultado);
		        nuevaActividad.put("obligatorio",obligatorio);
		        nuevaActividad.put("rating", rating);
		        nuevaActividad.put("fechaLimite", fechaLimite);
		        nuevaActividad.put("resenas",resenas);
		        nuevaActividad.put("tipoActividad", tipoActividad);
		        Tarea tarea = (Tarea) actividad;
		        Estado estado = tarea.getEstadoTarea();
		        String medioEntrega  = tarea.getMedioEntrega();
		        nuevaActividad.put("medioEntrega", medioEntrega);
		        nuevaActividad.put("estado",estado.name());
		        escribirJsonEnArchivo(nuevaActividad);
		        
	        }
	        else if (tipoActividad == TipoActividades.Quiz) {
	        	JSONObject nuevaActividad = new JSONObject();
		        nuevaActividad.put("id", id);
		        JSONArray prerrequisitosActividades = new JSONArray(idActividadesPrerrequisitos);
		        nuevaActividad.put("actividades", prerrequisitosActividades);
		        nuevaActividad.put("objetivo", objetivo);
		        nuevaActividad.put("titulo", titulo);
		        nuevaActividad.put("descripcion", descripcion);
		        nuevaActividad.put("nivel", nivel);
		        nuevaActividad.put("duracion", duracionMinutos);
		        nuevaActividad.put("resultado", resultado);
		        nuevaActividad.put("obligatorio",obligatorio);
		        nuevaActividad.put("rating", rating);
		        nuevaActividad.put("fechaLimite", fechaLimite);
		        nuevaActividad.put("resenas",resenas);
		        nuevaActividad.put("tipoActividad", tipoActividad);
		        Quiz quiz = (Quiz) actividad;
		        Estado estado = quiz.getEstado();
		        
		        JSONObject jsonPreguntas = new JSONObject();
		        for (Map.Entry<String, List<String>> entry : quiz.getPreguntas().entrySet()) {
		            JSONArray opcionesArray = new JSONArray(entry.getValue());
		            jsonPreguntas.put(entry.getKey(), opcionesArray);
		        }
		        nuevaActividad.put("preguntas", jsonPreguntas);
		        
		        JSONObject jsonRespuestasCorrectas = new JSONObject(quiz.getRespuestasCorrectas());
		        nuevaActividad.put("respuestasCorrectas", jsonRespuestasCorrectas);


		        JSONObject jsonExplicacionOpciones = new JSONObject(quiz.getExplicacionOpciones());
		        nuevaActividad.put("explicacionOpciones", jsonExplicacionOpciones);
		        
		        nuevaActividad.put("estado",estado.name());
		        escribirJsonEnArchivo(nuevaActividad);
	        }
	        }
	            }
	    }


	    public void escribirJsonEnArchivo(JSONObject jsonObject) {
	        try {
	            
	        	getActividades();
	        	actividadesArray.put(jsonObject);

	            
	            try (FileWriter fileWriter = new FileWriter(ruta)) {
	                fileWriter.write(actividadesArray.toString(4)); 
	                fileWriter.flush();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}



