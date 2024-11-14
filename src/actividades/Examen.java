package actividades;

import java.util.ArrayList;
import java.util.List;

public class Examen extends Actividad{
	
	private List<String> preguntas;	

	public Examen(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, int tiempoLimite) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, tiempoLimite);
		this.preguntas = new ArrayList<>();
		this.tipoActividad= TipoActividades.Examen;
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
    
	public List<String> getListaPreguntas() {
		if(this.preguntas==null) {
			this.preguntas = new ArrayList<String>();
		}
		return preguntas;
	}

	public void setListaPreguntas(List<String> preguntas) {
		this.preguntas = preguntas;
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
}
	