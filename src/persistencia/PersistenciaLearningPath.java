package persistencia;
import java.io.*;

import java.util.List;

import org.json.JSONObject;
import actividades.Actividad;
import learningPaths.LearningPath;
import org.json.JSONArray;
import org.json.JSONException;
import usuarios.*;

public class PersistenciaLearningPath {

	private File archivo;
    private final static String ruta = "data/learningPaths.JSON";
    public JSONArray learningPathArray = new JSONArray();
    List<String> idActividades;
    
    public PersistenciaLearningPath() {
        crearArchivo(); 
        getLearningPath(); 
	    }

    public void crearArchivo() {
        archivo = new File(ruta);
	    File carpeta = archivo.getParentFile();
		if (!carpeta.exists()) {
			 	carpeta.mkdirs();
			}
	    }

    public JSONArray getLearningPath() {
    	if (archivo.exists()) {
    		try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
    			StringBuilder stringBuilder = new StringBuilder();
    			String linea;

    		while ((linea = reader.readLine()) != null) {
    			stringBuilder.append(linea);
    		}

    		String jsonString = stringBuilder.toString();

	               
    	if (jsonString.trim().isEmpty()) {
    		 learningPathArray = new JSONArray();
	         } else {
	                    
	        	 try {
	        		 learningPathArray = new JSONArray(jsonString);
	        	 } catch (JSONException e) {
	        		 System.err.println("El contenido del archivo no es un JSONArray válido.");
	        		 e.printStackTrace();
	        		 learningPathArray = new JSONArray(); 
	                    }
	                }
	            	} catch (FileNotFoundException e) {
	                e.printStackTrace();
	            	} catch (IOException e) {
	            	e.printStackTrace();
	            }
	 
	        } else {
	            learningPathArray = new JSONArray(); 
	        }
			return learningPathArray;
	    }

    public void cargarLearningPath(LearningPath learningPath) {
    	
        crearArchivo();
        getLearningPath();  
        String id = learningPath.getId();
        String titulo = learningPath.getTitulo();
        String descripcion = learningPath.getDescripcion();
        String nivel = learningPath.getNivel();
        int duracion = learningPath.getDuracion();
        double rating = learningPath.getRating();
        if( learningPath.getFechaCreacion()!= null) {
        	String fechaCreacion = learningPath.getFechaCreacion().toString();
        }
        else {
        	String fechaCreacion = "";
        }
        
        if (learningPath.getFechaModificacion()!=null) {
        	 String fechaModificacion = learningPath.getFechaModificacion().toString();
        }
        else {
        	String fechaModificacion = "";
        }
        int version = learningPath.getVersion();
        
        if (learningPath.getListaActividades()!=null) {
        	
        	for(int i =0; i<learningPath.getListaActividades().size();i++){
        		
        		String idActividad = learningPath.getListaActividades().get(i).getId(); 
        		idActividades.add(idActividad);
        	}
        	
        }
        
        Object fechaCreacion = null;
		Object fechaModificacion = null;
		for (int i = 0; i < learningPathArray.length(); i++) {
            JSONObject pathExistente = learningPathArray.getJSONObject(i);
            String idExistente = pathExistente.getString("id");
            
            if (idExistente.equals(learningPath.getId())) {
            	
            	if (titulo!=null && !titulo.isEmpty() && !titulo.equals(pathExistente.getString("titulo"))) {
            		pathExistente.put("titulo", titulo);
            	}
            	if (descripcion!=null && !descripcion.isEmpty() && !descripcion.equals(pathExistente.getString("descripcion"))) {
            		pathExistente.put("descripcion", descripcion);
            	}
            	if (nivel!=null && !nivel.isEmpty() && !nivel.equals(pathExistente.getString("nivel"))) {
            		pathExistente.put("nivel", nivel);
            	}
            		pathExistente.put("Actividades", idActividades);
            		pathExistente.put("duracion", duracion);
            		pathExistente.put("rating", rating);
            		pathExistente.put("version", version);
            	return;
            }
        }

        JSONObject nuevoPat = new JSONObject();
        nuevoPat.put("id", id);
        JSONArray actividades = new JSONArray(idActividades);
        nuevoPat.put("actividades", actividades);
        nuevoPat.put("titulo", titulo);
        nuevoPat.put("descripcion", descripcion);
        nuevoPat.put("nivel", nivel);
        nuevoPat.put("duracion", duracion);
        nuevoPat.put("rating", rating);
        nuevoPat.put("version", version);
        nuevoPat.put("fechaCreacion", fechaCreacion);
        nuevoPat.put("fechaModificacion", fechaModificacion);
        escribirJsonEnArchivo(nuevoPat);
    }


    public void escribirJsonEnArchivo(JSONObject jsonObject) {
        try {
            
        	getLearningPath();
            learningPathArray.put(jsonObject);

            
            try (FileWriter fileWriter = new FileWriter(ruta)) {
                fileWriter.write(learningPathArray.toString(4)); 
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}