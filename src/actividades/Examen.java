package actividades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Examen extends Actividad{
	
	private List<String> preguntas;	
	private HashMap<String, ArrayList<String>> respuestas;

	public Examen(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
		this.preguntas = new ArrayList<>();
		this.respuestas = new HashMap<>();
		this.tipoActividad= TipoActividades.Examen;
	}

	public void agregarPregunta(String pregunta) {
		if(this.preguntas==null) {
			this.preguntas = new ArrayList<String>();
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
		if(!preguntas.isEmpty()) {
		
			for (String pregunta:preguntas) {
				i++;
				System.out.println(i+". "+pregunta);
			}
		}
		else {
			System.out.println("No hay preguntas que mostrar");
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


	public Estado contestarExamen(String id, ArrayList<String> respuesta) {
		
		this.respuestas.put(id, respuesta);
		
		return Estado.PENDIENTE;
		
	}
}
	