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
			boolean obligatorio, int tiempoLimite, float calificacionMinima) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, tiempoLimite);
		this.calificacionMinima = calificacionMinima;
		this.preguntas = new HashMap<String,HashMap<String,String>>();
		this.respuestasCorrectas = new HashMap<String,String>();
		this.tipoActividad = TipoActividades.Quiz;
	}

	public float getCalificacionMinima() {
		return calificacionMinima;
	}

	public void setCalificacionMinima(float calificacionMinima) {
		this.calificacionMinima = calificacionMinima;
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
	
	public void agregarPregunta(String enunciado) {
		
		preguntas.put(enunciado, new HashMap<String,String>());
	}
	
	public void agregarOpcion(String pregunta, String opcion, String explicacion) {
		
		boolean x = preguntas.containsKey(pregunta);
		
		if (x) {
			
			HashMap<String, String> entry = preguntas.get(pregunta); 
			entry.put(opcion, explicacion);
			
		}
		
	}
	
	public void verPreguntas() {
		
		if(!this.preguntas.isEmpty()) {	
		    for (Map.Entry<String, HashMap<String, String>> entradaPregunta : preguntas.entrySet()) {
		        String enunciadoPregunta = entradaPregunta.getKey();
		        HashMap<String,String> opciones = entradaPregunta.getValue();
		        
		        System.out.println("Pregunta: " + enunciadoPregunta+" \n Opciones:");
		        if(!opciones.isEmpty()) {
			        for(Map.Entry<String,String> entradaOpciones: opciones.entrySet()) {
			        	
			        	System.out.println( entradaPregunta.getValue().get(i));
			        }
		        }
		    }
		}
		else {
			System.out.println("No hay preguntas que mostrar");
		}
	}
	
//	public void calificar(List<String> respuestas) throws Exception{
//		if (preguntas.size()==respuestas.size()) {
//			int cantidadPreguntas = preguntas.size();
//			int contadorCorrectas = 0;
//			Collection<String> respuestasCorrectasMetodo = respuestasCorrectas.values();
//			for (int i=0;i<respuestasCorrectasMetodo.size();i++) {
//				 String respuesta = respuestas.get(i);
//				if (respuestasCorrectasMetodo.contains(respuesta)) {
//					contadorCorrectas += 1;
//				}
//			}
//			float calificacion = (contadorCorrectas/cantidadPreguntas)*100;
//			if (calificacion >= calificacionMinima) {
//				this.estado = Estado.EXITOSA;
//			} else {
//				this.estado = Estado.NO_EXITOSA;
//			}
//		} else {
//			throw new Exception("La cantidad de respuestas no coincide con el número de preguntas.");
//		}
//		 this.estado=Estado.NO_EXITOSA;
//		 mostrarExplicaciones();
//	}
//	
//	public void mostrarExplicaciones() {
//		  for (Map.Entry<String, String> entradaPregunta : explicacionOpciones.entrySet()) {
//		        String enunciadoPregunta = entradaPregunta.getKey();
//		        System.out.println("Pregunta: " + enunciadoPregunta+" \n Opciones:");
//		        System.out.println("Explicacion:" + entradaPregunta.getValue());      
//		  } 
//	}
//	
	@Override
	public void realizarActividad( ) throws Exception {
		verPreguntas();
	}
}
