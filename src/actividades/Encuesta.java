package actividades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Encuesta extends Actividad{
	
	private List<String> preguntas;	
	public Encuesta(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, int tiempoLimite) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, tiempoLimite);
		this.preguntas = new ArrayList<String>();
		this.tipoActividad = TipoActividades.Encuesta;
	}

	public void agregarPregunta(String pregunta) {
		if(this.preguntas==null) {
			 this.preguntas= new ArrayList<String>();
		}
		preguntas.add(pregunta);
	}
	
	public void verPreguntas() {
		int i =0;
		for (String pregunta:preguntas) {
			i++;
			System.out.println(i+". "+pregunta);
		}
	}	
	
	public List<String> getListaPreguntas() {
		if(this.preguntas==null) {
		 this.preguntas= new ArrayList<String>();
		}
		return this.preguntas;
	}

	public void setListaPreguntas(List<String> preguntas) {
		this.preguntas = preguntas;
	}


	@Override
	public void realizarActividad( ) throws Exception {
		verPreguntas();
	}
	
}