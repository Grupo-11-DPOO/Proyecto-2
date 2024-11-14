package actividades;

import java.util.List;

public class Recurso extends Actividad{

	private String material;
	private Estado estado;
	
	public Recurso(String id, String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio,float rating,List<String> resenas ,String material,Estado estado,TipoActividades tipoActividad) {
			// Llamado al constructor padre
			super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, null, null,tipoActividad);
			// Agregamos material puesto que es una actividad.
			this.material = material;
			this.estado = estado;
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
	
	@Override
	public void realizarActividad() throws Exception {
		//super.advertenciaPrerequisitos(estudiante); no entiendo como se implementa eso, genera error
		getMaterial();
		// Al obtener el material, se confia en que el estudiante haya usado el recurso.
		// El estado se pone como exitoso (true).
		estado = Estado.EXITOSA;
	}
}
