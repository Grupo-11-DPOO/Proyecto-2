package actividades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Encuesta extends Actividad{
	
	private List<String> preguntas;
	private List<String> respuestas;
	private Estado estado;
	
	public Encuesta(String id, String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, List<String> prerequisitos,float rating,List<String> resenas,List<String> preguntasEncuesta, List<String> respuestasEncuestas,Estado estado,TipoActividades tipoActividad) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, null, null, tipoActividad);
		this.estado =estado ;
		this.preguntas= preguntasEncuesta;
		this.respuestas = respuestasEncuestas;
		
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

	protected void setEstado(Estado estado) {
		this.estado = estado;
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