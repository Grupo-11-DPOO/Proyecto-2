package learningPaths;

import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import actividades.*;

public class LearningPath implements Identificable{
	
	private String id;
	protected String titulo;
	protected String descripcion;
	protected String nivel;
	protected int duracion;
	private double rating;
    public LocalDateTime fechaCreacion;
	private LocalDateTime fechaModificacion;
	private int version;
	public List<Actividad> actividades;
	
	// Metodos
	
	public LearningPath(String titulo, String descripcion, String nivel, int duracion) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.duracion = duracion;
		this.fechaCreacion = LocalDateTime.now();
		this.fechaModificacion = LocalDateTime.now();
		this.actividades = new ArrayList<Actividad>();
		this.rating = 0;
		this.id = this.crearId();
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getNivel() {
		return nivel;
	}
	
	public float calcularRating(List<Actividad> listaActividades) {
		float suma = 0;
		int cantidad = 0;
		for (Actividad acti: listaActividades){
			suma += acti.getRating();
			cantidad ++;
		}
		float rpt = suma/cantidad;
		return rpt;
	}

	public int getDuracion() {
		return duracion;
	}

	public double getRating() {
		return rating;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public int getVersion() {
		return version;
	}

	protected void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	protected void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	protected void setNivel(String nivel) {
		this.nivel = nivel;
	}

	protected void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = LocalDateTime.now();
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public Actividad getActividad(List<Actividad> actividades, String id) {
		Actividad activ = null;
		for(Actividad actividad: actividades) {
			String id_activ = actividad.getId();
			if(id_activ == id) {
				activ = actividad;
			}
		}
		return activ;
	}
	
	public Actividad getActividad(String nombre, List<Actividad> actividades) {
		Actividad activ = null;
		for(Actividad actividad: actividades) {
			String tituloActiv = actividad.getTitulo();
			if(tituloActiv == nombre) {
				activ = actividad;
			}
		}
		return activ;
	}
	
	public List<Actividad> getListaActividades(){
		return actividades;
	}

	@Override
	public String crearId() {
		String id = UUID.randomUUID().toString(); // Genera un ID único
	    return id;
	}

	@Override
	public String getId() {
		return id;
	}
}
