package actividades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Quiz extends Actividad{
	
	public float calificacionMinima;
	private HashMap<String, HashMap<Opcion, HashMap<String,String>>> preguntas;//Mapa de las preguntas con sus opciones, la llave es la preg¿unta, la clave es un mapa donde la llave es la opcion y su valor otro mapa donde ya este ultimo mapa contiene como llave el enunciado de la opcion y como valor la explicación del porque es correcta
	private HashMap<String,Opcion> respuestasCorrectas; // Mapa donde la llave es la pregunta y la clave es la respuesta correcta
	private HashMap<String,ArrayList<Opcion>> respuestasEstudiantes;
	
	
	
	public Quiz(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio,float calificacionMinima) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
		this.calificacionMinima = calificacionMinima;
		this.preguntas = new HashMap<>();
		this.respuestasCorrectas = new HashMap<>();
		this.respuestasEstudiantes = new HashMap<>();
		this.tipoActividad = TipoActividades.Quiz;
	}
	
	public HashMap<String, ArrayList<Opcion>> getRespuestasEstudiantes() {
		return respuestasEstudiantes;
	}

	public void setRespuestasEstudiantes(HashMap<String, ArrayList<Opcion>> respuestasEstudiantes) {
		this.respuestasEstudiantes = respuestasEstudiantes;
	}

	public float getCalificacionMinima() {
		return calificacionMinima;
	}

	public void setCalificacionMinima(float d) {
		this.calificacionMinima = d;
	}

	
	public HashMap<String, HashMap<Opcion, HashMap<String, String>>> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(HashMap<String, HashMap<Opcion, HashMap<String, String>>> preguntas) {
		this.preguntas = preguntas;
	}

	public HashMap<String, Opcion> getRespuestasCorrectas() {
		return respuestasCorrectas;
	}

	public void setRespuestasCorrectas(HashMap<String, Opcion> respuestasCorrectas) {
		this.respuestasCorrectas = respuestasCorrectas;
	}
	
	public void agregarPregunta(String enunciado) {
		HashMap<Opcion,HashMap<String,String>> entryOpcion = new HashMap<>();
		preguntas.put(enunciado, entryOpcion);
	}
	
	public void agregarOpcion(String pregunta, String enunciado,Opcion opcion, String explicacion) {
		
		boolean x = preguntas.containsKey(pregunta);
		
		if (x) {
			
			HashMap<Opcion, HashMap<String,String>> entry = preguntas.get(pregunta); 
			HashMap<String,String> enunciadoExplicacion = new HashMap<>();
			enunciadoExplicacion.put(enunciado, explicacion);
			entry.put(opcion, enunciadoExplicacion);
			
		}
		else {
			agregarPregunta(pregunta);
			HashMap<Opcion, HashMap<String,String>> entry = preguntas.get(pregunta); 
			HashMap<String,String> enunciadoExplicacion = new HashMap<>();
			enunciadoExplicacion.put(enunciado, explicacion);
			entry.put(opcion, enunciadoExplicacion);
			
		}
	}
	
	public void agregarOpcionCorrecta(String pregunta, String enunciado,Opcion opcion, String explicacion) {
		
		agregarOpcion(pregunta, enunciado, opcion, explicacion);
		respuestasCorrectas.put(pregunta, opcion);
		
	}
	
	
	
	
	public String verPreguntas() {
	    StringBuilder resultado = new StringBuilder();
	    
	    if (!this.preguntas.isEmpty()) {
	        for (Map.Entry<String, HashMap<Opcion, HashMap<String,String>>> entradaPregunta : preguntas.entrySet()) {
	            String enunciadoPregunta = entradaPregunta.getKey();
	            HashMap<Opcion, HashMap<String,String>> opciones = entradaPregunta.getValue();

	            resultado.append("Pregunta: ").append(enunciadoPregunta).append("\nOpciones:\n");
	            if (!opciones.isEmpty()) {
	                for (Map.Entry<Opcion, HashMap<String,String>> entradaOpciones : opciones.entrySet()) {
	                    String opcion = entradaOpciones.getKey().name();
	                    HashMap<String,String> entradaOpcion = entradaOpciones.getValue();
	                    if(!entradaOpcion.isEmpty()) {
	                    	for(Map.Entry<String, String> entry: entradaOpcion.entrySet()) {
	                    		String enunciado = entry.getKey();
	    	                    resultado.append(opcion).append("): ").append(enunciado).append("\n");
	                    	}
	                    }
	                }
	            }
	        }
	    } else {
	        return "No hay preguntas que mostrar";
	    }
	    return resultado.toString(); 
	}
		public String verPreguntasConExplicaciones() {
		    StringBuilder resultado = new StringBuilder();
		    
		    if (!this.preguntas.isEmpty()) {
		        for (Map.Entry<String, HashMap<Opcion, HashMap<String,String>>> entradaPregunta : preguntas.entrySet()) {
		            String enunciadoPregunta = entradaPregunta.getKey();
		            HashMap<Opcion, HashMap<String,String>> opciones = entradaPregunta.getValue();

		            resultado.append("Pregunta: ").append(enunciadoPregunta).append("\nOpciones:\n");
		            if (!opciones.isEmpty()) {
		                for (Map.Entry<Opcion, HashMap<String,String>> entradaOpciones : opciones.entrySet()) {
		                    String opcion = entradaOpciones.getKey().name();
		                    HashMap<String,String> entradaOpcion = entradaOpciones.getValue();
		                    if(!entradaOpcion.isEmpty()) {
		                    	for(Map.Entry<String, String> entry: entradaOpcion.entrySet()) {
		                    		String enunciado = entry.getKey();
		                    		String explicacion = entry.getValue();
		    	                    resultado.append(opcion).append("): ").append(enunciado).append("\n");
		    	                    resultado.append("\nExplicación: \n").append(explicacion).append("\n");
		                    	}
		                    }
		                }
		            }
		        }
		    } else {
		        return "No hay preguntas que mostrar";
		    }

	    return resultado.toString();
	}

	
	public Estado calificar(String pregunta, ArrayList<Opcion> respuestas) throws Exception{
		if (preguntas.size()==respuestas.size()) {
			int cantidadPreguntas = preguntas.size();
			int contadorCorrectas = 0;
			HashMap<String> respuestasCorrectasMetodo = respuestasCorrectas.values();
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
	

}
