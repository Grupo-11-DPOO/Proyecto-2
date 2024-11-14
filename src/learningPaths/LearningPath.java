package learningPaths;

import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

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
	
	public LearningPath(String id, String titulo, String descripcion, String nivel, int duracion, double rating2, int version2, LocalDateTime fechaCreacion2, LocalDateTime fechaModificacion2, List<Actividad> actividades1) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.duracion = duracion;
		this.fechaCreacion = LocalDateTime.now();
		this.actividades = new ArrayList<Actividad>();
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
	
	public Actividad getActividad(Actividad actividad) {
		return actividad;
	}
	
	public List<Actividad> getListaActividades(){
		return actividades;
	}
	
	public JSONObject toJSON() {
	    JSONObject json = new JSONObject();

	    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	    json.put("id", this.id);
	    json.put("titulo", this.titulo);
	    json.put("descripcion", this.descripcion);
	    json.put("nivel", this.nivel);
	    json.put("duracion", this.duracion);
	    json.put("rating", this.rating);
	    json.put("version", this.version);
	    json.put("fechaCreacion", this.fechaCreacion.format(formatter));
	    json.put("fechaModificacion", this.fechaModificacion.format(formatter));

	    // Convertir la lista de actividades en un array de JSON
	    JSONArray actividadesJSON = new JSONArray();
	    for (Actividad actividad : this.actividades) {
	        actividadesJSON.put(actividad.toJSON());
	    }

	    json.put("actividades", actividadesJSON);

	    return json;
	}

	@Override
	public void crearId() {
		String id = UUID.randomUUID().toString(); // Genera un ID único
	    this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}
}
