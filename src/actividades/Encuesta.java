package actividades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Encuesta extends Actividad{
	
	private List<String> preguntas;
	private HashMap<String, ArrayList<String>> respuestas;

	public Encuesta(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, int tiempoLimite) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, tiempoLimite);
		this.preguntas = new ArrayList<String>();
		this.respuestas = new HashMap<>();
		this.tipoActividad = TipoActividades.Encuesta;
	}

	public void agregarPregunta(String pregunta) {
		if(this.preguntas==null) {
			 this.preguntas= new ArrayList<String>();
		}
		preguntas.add(pregunta);
	}
	
	public void eliminarPregunta(String pregunta) {
		
		boolean x = this.preguntas.contains(pregunta);
		
		if (x) {
			
			this.preguntas.remove(pregunta);
		}
	}
	
	public void verPreguntas() {
		int i =0;
		for (String pregunta:preguntas) {
			i++;
			System.out.println(i+". "+pregunta);
		}
	}	
	
	

	public List<String> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<String> preguntas) {
		this.preguntas = preguntas;
	}

	public HashMap<String, ArrayList<String>> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(HashMap<String, ArrayList<String>> respuestas) {
		this.respuestas = respuestas;
	}

	
	public void contestarExamen(String idEstudiante, String)
	
}