package actividades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Examen extends Actividad{
	
	private List<String> preguntas;
	private List<String> respuestas;
	private Estado estado;
	
	public Examen(String id, String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, String resultado,
			boolean obligatorio,float rating, List<String> resenas,List<String> preguntasExamen,List<String> respuestasExamen, Estado estadoExamen, TipoActividades tipoActividad) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, null, null, tipoActividad);
		this.estado = Estado.PENDIENTE;
	}

	public void agregarPregunta(String pregunta) {
		if(this.preguntas==null) {
			this.preguntas = new ArrayList<String>();
		}
		preguntas.add(pregunta);
	}
	
	public void verPreguntas() {
		for (String pregunta:preguntas) {
			System.out.println(pregunta);
		}
	}
	
    public void marcarComoExitosa(boolean exitosa) {
        if (estado == Estado.ENVIADA) {
            this.estado = exitosa ? Estado.EXITOSA : Estado.NO_EXITOSA;
            System.out.println("La tarea ha sido marcada como " + (exitosa ? "exitosa." : "no exitosa."));
        } else {
            System.out.println("La tarea no ha sido enviada todavía.");
        }
    }
    
	public List<String> getPreguntas() {
		if(this.preguntas==null) {
			this.preguntas = new ArrayList<String>();
		}
		return preguntas;
	}

	public void setPreguntas(List<String> preguntas) {
		this.preguntas = preguntas;
	}

	public List<String> getRespuestas() {
		if(this.respuestas==null) {
			this.respuestas = new ArrayList<String>();
		}
		return respuestas;
	}

	public void setRespuestas(List<String> respuestas) {
		this.respuestas = respuestas;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public void realizarActividad( ) throws Exception {
		verPreguntas();
	}
	
	public void responder(String respuesta) {
		respuestas.add(respuesta);
	}
}
	