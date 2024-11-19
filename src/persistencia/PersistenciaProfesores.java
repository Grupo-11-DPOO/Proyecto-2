package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actividades.Actividad;
import actividades.Estado;
import learningPaths.LearningPath;
import usuarios.Estudiante;
import usuarios.Profesor;

public class PersistenciaProfesores {
	
	
	public PersistenciaProfesores() {
		
	}
	
	
	private File archivo;
    private final static String ruta = "data/profesores.JSON";
    public JSONArray profesoresArray = new JSONArray();
    
    public void crearArchivo() {
        archivo = new File(ruta);
	    File carpeta = archivo.getParentFile();
		if (!carpeta.exists()) {
			 	carpeta.mkdirs();
			}
		 try {
		        // Si el archivo no existe, lo crea
		        if (!archivo.exists()) {
		            archivo.createNewFile();
		            System.out.println("Archivo creado exitosamente: " + archivo.getAbsolutePath());
		        } else {
		            System.out.println("El archivo ya existe y será utilizado: " + archivo.getAbsolutePath());
		        }
		    } catch (IOException e) {
		        System.err.println("Error al crear o utilizar el archivo: " + e.getMessage());
		    }
	    }
    
    public JSONArray leerProfesores() {
    	crearArchivo();
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                StringBuilder stringBuilder = new StringBuilder();
                String linea;
                
                while ((linea = reader.readLine()) != null) {
                    stringBuilder.append(linea);
                }

                String jsonString = stringBuilder.toString().trim();

                if (jsonString.isEmpty()) {
                	profesoresArray = new JSONArray();
                } else {

                	try {
                		profesoresArray = new JSONArray(jsonString);
                    } catch (JSONException e) {
                        System.err.println("El contenido del archivo no es un JSONArray válido.");
                        e.printStackTrace();
                        profesoresArray = new JSONArray(); 
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("El archivo no se encontró.");
                e.printStackTrace();
                profesoresArray = new JSONArray(); 
            } catch (IOException e) {
                System.err.println("Error al leer el archivo.");
                e.printStackTrace();
                profesoresArray = new JSONArray(); 
            }
        } else {
        	profesoresArray = new JSONArray();
        }
        return profesoresArray;
    }
    
    public HashMap<String,Profesor> cargarProfesores(HashMap<String,Actividad> actividades, HashMap<String, LearningPath> learningPaths){
    	
    	
    	HashMap<String,Profesor> profesores = new HashMap<>();
    	
    	JSONArray profesoresJsonArray = leerProfesores();
    	
    	for(int i =0; i<profesoresJsonArray.length();i++) {
    		
    		JSONObject profesorJson = profesoresJsonArray.getJSONObject(i);
    		
    		Profesor profesor = convertirJsonToProfesor(profesorJson, actividades,learningPaths);
    		
    		profesores.put(profesor.getLogin(), profesor);
    	}
    	return profesores;
    }
    
    
    public JSONObject convertirProfesorToJson(Profesor profesor) {
    	
    	
    	JSONObject profesorJson = new JSONObject();
    	
    	String login = profesor.getLogin();
    	
    	String passWord = profesor.getPassword();
    	
    	ArrayList<String> idActividadesCreadas = profesor.getIdActividadesCreadas();
    	ArrayList<String> idLearningPathsCreados = profesor.getIdLearningPathsCreados();
    	
    	profesorJson.put("login", login);
    	
    	profesorJson.put("passWord", passWord);
    	
    	profesorJson.put("idActividadesCreadas", idActividadesCreadas != null ? new JSONArray(idActividadesCreadas) : new JSONArray());
    	
    	profesorJson.put("idLearningPathsCreados", idLearningPathsCreados != null ? new JSONArray(idLearningPathsCreados) : new JSONArray() );
    	
    			
    	
    	return profesorJson;
    	
    	
    	
    }
    public Profesor convertirJsonToProfesor(JSONObject profesorJson, HashMap<String,Actividad> actividades, HashMap<String,LearningPath> learningPaths) {
    	
    	
    	Profesor profesor = null;
    	
    	String login = profesorJson.getString("login");
    	
    	String passWord = profesorJson.getString("PassWord");
    	
    	JSONArray idActividadesCreadasArray = profesorJson.getJSONArray("idActividadesCreadas");
    	JSONArray idLearningPathsCreadosArray = profesorJson.getJSONArray("idLearningPathsCreados");
    	ArrayList<String> idLearningPathsCreados = new ArrayList<>();
        ArrayList<String> idActividadesCreadas = new ArrayList<>();
        for (int i = 0; i < idActividadesCreadasArray.length(); i++) {
        	idActividadesCreadas.add(idActividadesCreadasArray.getString(i));
        }
        for (int i = 0; i < idLearningPathsCreadosArray.length(); i++) {
        	idLearningPathsCreados.add(idLearningPathsCreadosArray.getString(i));
        }
        
        profesor = new Profesor(actividades,learningPaths, login, passWord);
        
        profesor.setIdActividadesCreadas(idActividadesCreadas);
        profesor.setIdLearningPathsCreados(idLearningPathsCreados);
        
        
        
    	
    	
    	
    	
    	return profesor;
    }
    
    
    public Estudiante convertirJsonToEstudiante(JSONObject estudianteJson, HashMap<String,Actividad> actividades, HashMap<String,LearningPath> learningPaths) {
    	
    	
    	
    	Estudiante estudiante = null;
    	
    	String login = estudianteJson.getString("login");
        String passWord = estudianteJson.getString("passWord");
        
        JSONArray interesesArray = estudianteJson.getJSONArray("intereses");
        List<String> intereses = new ArrayList<>();
        for (int i = 0; i < interesesArray.length(); i++) {
            intereses.add(interesesArray.getString(i));
        }
        
        String idActividadEnCurso = estudianteJson.optString("idActividadEnCurso", null);
        
        String idLearningPathEnCurso = estudianteJson.optString("idLearningPathEnCurso", null);
        
        JSONObject registroJson = estudianteJson.getJSONObject("registroActividades");
        HashMap<String, Estado> registroActividades = new HashMap<>();
        for (String clave : registroJson.keySet()) {
            String estadoString = registroJson.getString(clave);
            Estado estado = Estado.valueOf(estadoString); 
            registroActividades.put(clave, estado);
        }
        
        JSONObject registroLearningPathsJson = estudianteJson.getJSONObject("registroLearningPaths");
        HashMap<String, Double> registroLearningPaths = new HashMap<>();
        for (String clave : registroLearningPathsJson.keySet()) {
            Double valor = registroLearningPathsJson.getDouble(clave);
            registroLearningPaths.put(clave, valor);
        }   
        Actividad actividadEnCurso = null;
        if (idActividadEnCurso != null && !idActividadEnCurso.isEmpty()) {
            actividadEnCurso = actividades.get(idActividadEnCurso); // Implementar esta lógica según tu sistema
        }
        LearningPath learningPathEnCurso = null;
        
        if (idLearningPathEnCurso != null && !idLearningPathEnCurso.isEmpty()) {
            learningPathEnCurso = learningPaths.get(idLearningPathEnCurso) ;// Implementar esta lógica
        }
        
        estudiante = new Estudiante(actividades, learningPaths,login,passWord,intereses);
        estudiante.setActividadEnCurso(actividadEnCurso);
        estudiante.setRegistroActividades(registroActividades);
        estudiante.setRegistroLearningPaths(registroLearningPaths);
        estudiante.setLearningPathEnCurso(learningPathEnCurso);
        estudiante.setActividadEnCurso(actividadEnCurso);
        return estudiante;
    	
    }
    
    public void guardarProfesor(Profesor profesor) {
        try {
            JSONArray profesoresArray = leerProfesores();
            
            JSONObject nuevoProfesor = convertirProfesorToJson(profesor);
            
            boolean encontrada = false;
            for (int i = 0; i < profesoresArray.length(); i++) {
                JSONObject profesorExistente = profesoresArray.getJSONObject(i);
                if (profesorExistente.getString("id").equals(profesor.getLogin())) {
                	profesoresArray.put(i, nuevoProfesor);
                    encontrada = true;
                    break;
                }
            }
            
            if (!encontrada) {
            	profesoresArray.put(nuevoProfesor);
            }
            
            try (FileWriter fileWriter = new FileWriter(ruta)) {
                fileWriter.write(profesoresArray.toString(4)); // Formato bonito con indentación de 4 espacios
            }
            
            System.out.println("Profesor guardado exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al guardar el profesor: " + e.getMessage());
        }
    }

}
