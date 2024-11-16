package actividades;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Quiz extends Actividad{
	
	public float calificacionMinima;
	public HashMap<String, HashMap<String, String>> preguntas;//Mapa de las preguntas con sus opciones, la llave es la pregunta, la clave es un mapa donde en el mapa la llave es la opcion y la clave es la explicación de la opción
	private Map<String,String> respuestasCorrectas; // Mapa donde la llave es la pregunta y la clave es la respuesta correcta
	
	
	
	
	public Quiz(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, float calificacionMinima) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
		this.calificacionMinima = calificacionMinima;
		this.preguntas = new HashMap<String,HashMap<String,String>>();
		this.respuestasCorrectas = new HashMap<String,String>();
		this.tipoActividad = TipoActividades.Quiz;
	}

	public float getCalificacionMinima() {
		return calificacionMinima;
	}

	public void setCalificacionMinima(float d) {
		this.calificacionMinima = d;
	}

	public HashMap<String, HashMap<String, String>> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(HashMap<String, HashMap<String, String>> preguntas) {
		this.preguntas = preguntas;
	}

	public Map<String, String> getRespuestasCorrectas() {
		return respuestasCorrectas;
	}

	public void setRespuestasCorrectas(Map<String, String> respuestasCorrectas) {
		this.respuestasCorrectas = respuestasCorrectas;
	}
	
	public void agregarPregunta(String enunciado, String opcionCorrecta) {
		
		preguntas.put(enunciado, new HashMap<String,String>());
		respuestasCorrectas.put(enunciado, opcionCorrecta);
	}
	
	public void agregarOpcion(String pregunta, String opcion, String explicacion) {
		
		boolean x = preguntas.containsKey(pregunta);
		
		if (x) {
			
			HashMap<String, String> entry = preguntas.get(pregunta); 
			entry.put(opcion, explicacion);
			
		}
		
	}
	public String verPreguntas() {
	    StringBuilder resultado = new StringBuilder();
	    
	    if (!this.preguntas.isEmpty()) {
	        for (Map.Entry<String, HashMap<String, String>> entradaPregunta : preguntas.entrySet()) {
	            String enunciadoPregunta = entradaPregunta.getKey();
	            HashMap<String, String> opciones = entradaPregunta.getValue();

	            resultado.append("Pregunta: ").append(enunciadoPregunta).append("\nOpciones:\n");
	            if (!opciones.isEmpty()) {
	                for (Map.Entry<String, String> entradaOpciones : opciones.entrySet()) {
	                    String claveOpcion = entradaOpciones.getKey();
	                    String textoOpcion = entradaOpciones.getValue();
	                    resultado.append(claveOpcion).append(": ").append(textoOpcion).append("\n");
	                }
	            }
	        }
	    } else {
	        return "No hay preguntas que mostrar";
	    }

	    return resultado.toString();
	}

	
	public String calificar(List<String> respuestas) throws Exception{
		if (preguntas.size()==respuestas.size()) {
			int cantidadPreguntas = preguntas.size();
			int contadorCorrectas = 0;
			Collection<String> respuestasCorrectasMetodo = respuestasCorrectas.values();
			for (int i=0;i<respuestasCorrectasMetodo.size();i++) {
				 String respuesta = respuestas.get(i);
				if (respuestasCorrectasMetodo.contains(respuesta)) {
					contadorCorrectas += 1;
				}
			}
			float calificacion = (contadorCorrectas/cantidadPreguntas)*100;
			if (calificacion >= calificacionMinima) {
				this.setEstado(Estado.EXITOSA);
			} else {
				this.setEstado(Estado.NO_EXITOSA);
			}
		} else {
			throw new Exception("La cantidad de respuestas no coincide con el número de preguntas.");
		}
		 this.setEstado(Estado.NO_EXITOSA);
		 String explicaciones =generarMostrarExplicaciones();
		 return explicaciones;
		 
	}
	
	public Estado getEstado(){
		 return this.estado;
	 }
	
	public String generarMostrarExplicaciones() {
		
		StringBuffer sb = new StringBuffer( );
		for (Map.Entry<String, String> entradaPregunta : preguntas.values()) {
		        String enunciadoPregunta = entradaPregunta.getKey();
		        sb.append("Pregunta: " + enunciadoPregunta+" \n Opciones:");
		        sb.append("Explicacion:" + entradaPregunta.getValue()+"\n");      
		  } 
		  return sb.toString();
	}
}
