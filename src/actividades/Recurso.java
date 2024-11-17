package actividades;


public class Recurso extends Actividad{

	private String material;
	private Estado estado;
	
	public Recurso(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, String material) {
			// Llamado al constructor padre
			super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
			// Agregamos material puesto que es una actividad.
			this.material = material;
			this.estado = Estado.PENDIENTE;
	}
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	public Estado realizarRecurso() {
		estado = Estado.EXITOSA;
		return estado;
	}
}
