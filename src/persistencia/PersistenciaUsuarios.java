package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;

import exceptions.UsuarioExistenteException;
import usuarios.*;

public class PersistenciaUsuarios {

    private File archivo;
    private final static String ruta = "data/usuarios.JSON";
    public JSONArray usuariosArray = new JSONArray();

    public PersistenciaUsuarios() {
        crearArchivo(); 
        getUsuarios(); 
    }

    public void crearArchivo() {
        archivo = new File(ruta);
        File carpeta = archivo.getParentFile();
		if (!carpeta.exists()) {
		    carpeta.mkdirs();
		}
    }

    public JSONArray getUsuarios() {
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                StringBuilder stringBuilder = new StringBuilder();
                String linea;

                while ((linea = reader.readLine()) != null) {
                    stringBuilder.append(linea);
                }

                String jsonString = stringBuilder.toString();

               
                if (jsonString.trim().isEmpty()) {
                    usuariosArray = new JSONArray();
                } else {
                    
                    try {
                        usuariosArray = new JSONArray(jsonString);
                    } catch (JSONException e) {
                        System.err.println("El contenido del archivo no es un JSONArray válido.");
                        e.printStackTrace();
                        usuariosArray = new JSONArray(); 
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
 
        } else {
            usuariosArray = new JSONArray(); 
        }
		return usuariosArray;
    }

    public void cargarProfesor(String login,String password, List<String> idLearningPaths,List<String> idActividades ) throws UsuarioExistenteException {
        crearArchivo();
        getUsuarios();  
        
        for (int i = 0; i < usuariosArray.length(); i++) {
            JSONObject usuarioExistente = usuariosArray.getJSONObject(i);
            String loginExistente = usuarioExistente.getString("login");
            
            if (loginExistente.equals(login)) {
            	JSONObject nuevoProfesor =usuarioExistente;
                nuevoProfesor.put("tipoUsuario", TipoUsuario.Profesor.name());
                nuevoProfesor.put("login", login);
                nuevoProfesor.put("password", password);
                JSONArray LearningPaths = new JSONArray(idLearningPaths);
                JSONArray actividades = new JSONArray(idActividades);
                nuevoProfesor.put("learningPaths", LearningPaths);
                nuevoProfesor.put("actividades", actividades);
                escribirJsonEnArchivo(nuevoProfesor);
                return; 
            } else {
            	throw new UsuarioExistenteException();
            }
        }
        
        JSONObject nuevoProfesor = new JSONObject();
        nuevoProfesor.put("tipoUsuario", TipoUsuario.Profesor.name());
        nuevoProfesor.put("login", login);
        nuevoProfesor.put("password", password);
        JSONArray LearningPaths = new JSONArray(idLearningPaths);
        JSONArray actividades = new JSONArray(idActividades);
        nuevoProfesor.put("learningPaths", LearningPaths);
        nuevoProfesor.put("actividades", actividades);
        escribirJsonEnArchivo(nuevoProfesor);
    }
    public void cargarEstudiante(String login,String password,List<String> intereses, HashMap<String,String> registro ) {
        crearArchivo();
        getUsuarios();  
        
        for (int i = 0; i < usuariosArray.length(); i++) {
            JSONObject usuarioExistente = usuariosArray.getJSONObject(i);
            String loginExistente = usuarioExistente.getString("login");
            
            if (loginExistente.equals(login)) {
                System.out.println("El usuario con el login '" + login + "' ya está registrado.");
                return; 
            }
        }
        
        
        JSONObject nuevoEstudiante = new JSONObject();
        nuevoEstudiante.put("login", login);
        nuevoEstudiante.put("password", password);
        
        JSONObject jsonActividades = new JSONObject();
        for (Map.Entry<String, String> entry : registro.entrySet()) {
            jsonActividades.put(entry.getKey(),entry.getValue());
        }
        JSONObject jsonIntereses = new JSONObject();
        nuevoEstudiante.put("Intereses", jsonIntereses);
        nuevoEstudiante.put("Actividades", jsonActividades);
        escribirJsonEnArchivo(nuevoEstudiante);
    }
    
    
    

    public void escribirJsonEnArchivo(JSONObject jsonObject) {
        try {
            
            getUsuarios();
            usuariosArray.put(jsonObject);

            
            try (FileWriter fileWriter = new FileWriter(ruta)) {
                fileWriter.write(usuariosArray.toString(4)); 
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
