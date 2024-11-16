package actividades;

public class Tarea extends Actividad{
	
	private Estado estado;
    private String medioEntrega;
    private String contenido;
    
	public Tarea(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio,
			String contenido, String medioEntrega) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
		this.medioEntrega = medioEntrega;
		this.contenido = contenido;
    	this.estado = Estado.PENDIENTE;
	}
	
	public void enviarTarea() throws Exception {
		if (estado == Estado.PENDIENTE) {
			this.estado = Estado.ENVIADA;
			throw new Exception("El medio de entrega es incorrecto.");
		} else {
			throw new Exception("La tarea ya se encuentra enviada.");
		}
	}
	
	public String getMedioEntrega() {
		return medioEntrega;
	}
	
    public void marcarComoExitosa(boolean exitosa) {
        if (estado == Estado.ENVIADA) {
            this.estado = exitosa ? Estado.EXITOSA : Estado.NO_EXITOSA;
            System.out.println("La tarea ha sido marcada como " + (exitosa ? "exitosa." : "no exitosa."));
        } else {
            System.out.println("La tarea no ha sido enviada todavía.");
        }
    }
	
	public Estado getEstadoTarea() {
		return estado;
	}
	
	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	@Override
	public void realizarActividad( ) throws Exception {
		//super.advertenciaPrerequisitos(estudiante);
		enviarTarea();
	}
}
