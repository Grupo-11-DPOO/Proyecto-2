package actividades;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz extends Actividad{
	
	public float calificacionMinima = (float) 3;
	public HashMap<String,List<String>> preguntas;
	private HashMap<String, String> respuestasCorrectas;
	private HashMap<String, String> explicacionOpciones;
	public Estado estado ;
	private List<String> respuestas;
	
	
	public Quiz(String id, String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, Map<String, List<String>> preguntasQuiz,Map<String, String> respuestasCorrectasQuiz,Map<String, String> explicacionOpcionesQuiz,Estado estado,TipoActividades tipoActividad) {
		super(titulo, objetivo, descripcion, nivel,duracionMinutos, obligatorio, null, null,tipoActividad);
		this.preguntas = new HashMap<String, List<String>>();
		this.respuestasCorrectas = new HashMap<String,String>();
		this.estado = Estado.PENDIENTE;
		this.explicacionOpciones = new HashMap<String,String>();
	}
	
	public void crearYEditarPreguntaOpcionMultiple(String enunciado, List<String> opciones, String opcionCorrecta, String explicacionOpciones) {
		this.preguntas.put(enunciado, opciones);
		this.respuestasCorrectas.put(enunciado, opcionCorrecta);
		this.explicacionOpciones.put(enunciado, explicacionOpciones);
	}
	
	public HashMap<String, List<String>> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(HashMap<String, List<String>> preguntas) {
		this.preguntas = preguntas;
	}

	public HashMap<String, String> getRespuestasCorrectas() {
		return respuestasCorrectas;
	}

	public void setRespuestasCorrectas(HashMap<String, String> respuestasCorrectas) {
		this.respuestasCorrectas = respuestasCorrectas;
	}

	public HashMap<String, String> getExplicacionOpciones() {
		return explicacionOpciones;
	}

	public void setExplicacionOpciones(HashMap<String, String> explicacionOpciones) {
		this.explicacionOpciones = explicacionOpciones;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<String> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<String> respuestas) {
		this.respuestas = respuestas;
	}

	protected void eliminarPregunta(String enunciado) {
		preguntas.remove(enunciado);
	}
	
	public float getCalificacionMinima() {
		return calificacionMinima;
	}
	
	public void setCalificacionMinima(float calificacionMinima) {
		this.calificacionMinima = calificacionMinima;
	}	
	
	public void agregarRespuestas(List<String> respuestas) {
		this.respuestas= respuestas;
	}
	
	public void verPreguntas() {
	    for (Map.Entry<String, List<String>> entradaPregunta : preguntas.entrySet()) {
	        String enunciadoPregunta = entradaPregunta.getKey();
	        System.out.println("Pregunta: " + enunciadoPregunta+" \n Opciones:");
	        for(int i =0; i<entradaPregunta.getValue().size();i++) {
	        	
	        	System.out.println( entradaPregunta.getValue().get(i));
	        }
	    }
	}
	
	public void calificar(List<String> respuestas) throws Exception{
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
				this.estado = Estado.EXITOSA;
			} else {
				this.estado = Estado.NO_EXITOSA;
			}
		} else {
			throw new Exception("La cantidad de respuestas no coincide con el número de preguntas.");
		}
		 this.estado=Estado.NO_EXITOSA;
		 mostrarExplicaciones();
	}
	
	public void mostrarExplicaciones() {
		  for (Map.Entry<String, String> entradaPregunta : explicacionOpciones.entrySet()) {
		        String enunciadoPregunta = entradaPregunta.getKey();
		        System.out.println("Pregunta: " + enunciadoPregunta+" \n Opciones:");
		        System.out.println("Explicacion:" + entradaPregunta.getValue());      
		  } 
	}
	
	@Override
	public void realizarActividad( ) throws Exception {
		verPreguntas();
	}
}
