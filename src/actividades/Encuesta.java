package actividades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Encuesta extends Actividad{
	
<<<<<<< HEAD
	private List<String> preguntas;
	private List<String> respuestas;
	private Estado estado;

	
=======
	private List<String> preguntas;	
>>>>>>> branch 'master' of https://github.com/Grupo-11-DPOO/Proyecto-2.git
	public Encuesta(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
<<<<<<< HEAD
			boolean obligatorio, int tiempoLimite, List<Actividad> prerequisitos, List<String> preguntas,
			List<String> respuestas) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, tiempoLimite, prerequisitos);
		this.preguntas = preguntas;
		this.respuestas = respuestas;
		this.estado = Estado.PENDIENTE;
		
		
=======
			boolean obligatorio, int tiempoLimite) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, tiempoLimite);
		this.preguntas = new ArrayList<String>();
		this.tipoActividad = TipoActividades.Encuesta;
>>>>>>> branch 'master' of https://github.com/Grupo-11-DPOO/Proyecto-2.git
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

<<<<<<< HEAD
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

=======
>>>>>>> branch 'master' of https://github.com/Grupo-11-DPOO/Proyecto-2.git

	@Override
	public void realizarActividad( ) throws Exception {
		verPreguntas();
	}
	
}