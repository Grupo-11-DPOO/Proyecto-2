package actividades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Encuesta extends Actividad{
	
	private List<String> preguntas;
	private List<String> respuestas;
	private Estado estado;

	
	public Encuesta(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, int tiempoLimite, List<Actividad> prerequisitos, List<String> preguntas,
			List<String> respuestas) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, tiempoLimite, prerequisitos);
		this.preguntas = preguntas;
		this.respuestas = respuestas;
		this.estado = Estado.PENDIENTE;
		
		
	}

	public void agregarPregunta(String pregunta) {
		if(this.preguntas==null) {
			 this.preguntas= new ArrayList<String>();
		}
		preguntas.add(pregunta);
	}
	
	public void verPreguntas() {
		for (String pregunta:preguntas) {
			System.out.println(pregunta);
		}
	}	
	
	public List<String> getPreguntas() {
		if(this.preguntas==null) {
		 this.preguntas= new ArrayList<String>();
		}
		return preguntas;
	}

	public void setPreguntas(List<String> preguntas) {
		this.preguntas = preguntas;
	}

	public List<String> getRespuestas() {
		if(this.respuestas==null) {
			 this.respuestas= new ArrayList<String>();
		}
		return respuestas;
	}

	public void setRespuestas(List<String> respuestas) {
		this.respuestas = respuestas;
	}
	
	public Estado getEstado() {
		return estado;
	}


	@Override
	public void realizarActividad( ) throws Exception {
		verPreguntas();
	}
	
	public void responder(String respuesta) {
		respuestas.add(respuesta);
		this.estado= Estado.EXITOSA;
	}
}