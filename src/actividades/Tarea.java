package actividades;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Tarea extends Actividad{
	
	private Estado estado;
    private String medioEntrega;
    private String contenido;
    private String id;
	public Tarea(String id, String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio,
			LocalDate fechaLimite, List<String> prerrequisitos,double rating,List<String> resenas,String medioEntrega,Estado estado,   TipoActividades tipoActividad) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, fechaLimite, prerrequisitos,rating,resenas,medioEntrega,estado,tipoActividad);
		this.id = id;
		this.medioEntrega = medioEntrega;
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
