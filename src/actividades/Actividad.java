package actividades;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.UUID;
import java.time.LocalDate;
import java.util.Calendar;
import learningPaths.Identificable;
import usuarios.Estudiante;

public abstract class Actividad implements Identificable {
	
	private String id;
	public String titulo;
	public String descripcion;
	public String objetivo;
	public String nivel;
	public int duracionMinutos;
	private String resultado;
	public boolean obligatorio;
	public Date fechaLimite;
	public float rating;
	public List<Actividad> prerequisitos;
	public int cantidadRating = 0;
	public int sumaRating = 0;
	public List<String> resenas;
	public TipoActividades tipoActividad;
	
	// Metodos
	
	public Actividad(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio, 
			Date fechaLimite, List<Actividad> prerequisitos2, TipoActividades tipoActividad) {
		this.titulo = titulo;
		this.objetivo = objetivo;
		this.descripcion = descripcion;
		this.duracionMinutos = duracionMinutos;
		this.obligatorio = obligatorio;
		this.fechaLimite = fechaLimite;
		this.prerequisitos = prerequisitos2;
		this.tipoActividad= tipoActividad;
	}

	public Actividad(String titulo2, String objetivo2, String descripcion2, String nivel2, int duracionMinutos2,
			boolean obligatorio2, LocalDate fechaLimite2, List<String> prerrequisitos, double rating2,
			List<String> resenas2, String medioEntrega, Estado estado, TipoActividades tipoActividad2) {
		// TODO Auto-generated constructor stub
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public int getDuracionMinutos() {
		return duracionMinutos;
	}

	public void setDuracionMinutos(int duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}

	public String getResultado() {
		return resultado;
	}

	protected void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public boolean isObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

	public Date getFechaLimite() {
		return fechaLimite;
	}

	protected void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public List<Actividad> getPrerequisitos() {
		return prerequisitos;
	}

	public void setPrerequisitos(List<Actividad> prerequisitos) {
		this.prerequisitos = prerequisitos;
	}
	
	// Los ratings deben estar entre 0 y 5.
	protected void agregarRating(int ratingPersonal) throws Exception{
		if (ratingPersonal <= 0 || ratingPersonal > 5) {
			throw new Exception("Rating debe estar entre 0 y 5.");
		} else {
			cantidadRating += 1;
			sumaRating += ratingPersonal;
			this.rating = sumaRating/cantidadRating;
		}
	}
	
	public float getRating() {
		return rating;
	}
	
	protected void agregarResena(String resenaInput) {
		resenas.add(resenaInput);
	}
	
	public List<String> getResenas() {
		return resenas;
	}
	
	protected void advertenciaPrerequisitos(Estudiante estudiante) throws Exception {
		if (!prerequisitos.isEmpty()) {
			ListIterator<Actividad> listaPrerequisitosIterable = prerequisitos.listIterator();
			for (Actividad act:prerequisitos) {
				//if (!usuarios.Estudiante.estudiante.listaActividadesCompletadas.contains(act)) {
					throw new Exception("Usted no cumple con los prerequisitos de la actividad pero puede continuar bajo su responsabilidad.");
				//}
			}
		}
	}
	
    public void establecerFechaLimite(Actividad actividadAnterior, int horasDespues) {
        if (actividadAnterior == null || actividadAnterior.getDuracionMinutos() <= 0) {
            System.out.println("La actividad anterior no es válida.");
            this.fechaLimite = null;
        } else {
	        Calendar calendario = Calendar.getInstance();
	        calendario.setTime(new Date());
	        calendario.add(Calendar.MINUTE, actividadAnterior.getDuracionMinutos());
	        calendario.add(Calendar.HOUR, horasDespues);        
	        // Establece la nueva fecha límite
        this.fechaLimite = calendario.getTime();
        }
    }
    
    // Método para verificar si la actividad se está realizando después de la fecha límite
    protected boolean revisarFechaLimiteExcedida() {
        if (fechaLimite != null) {
            Date fechaActual = new Date();
            if (fechaActual.after(fechaLimite)) {
                System.out.println("Advertencia: Está realizando la actividad después de la fecha límite recomendada.");
                return true;
            }
            return false;
        }
        return false;
    }
    
    // Se clona la actividad por si un profesor desea poder ser el nuevo dueño y modificarla.
    public Actividad clonarActividad() {
        try {
            Actividad copia = (Actividad) this.clone();
            copia.crearId();
            return copia;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
			
	@Override
	public String crearId() {
		// Genera el ID
    	String id = UUID.randomUUID().toString();
        return id;
	}

	@Override
	public String getId() {
		return id;
	}
	
	public TipoActividades getTipoActividad() {
		return tipoActividad;
	}
	
	// Este metodo se implementara por los distintos tipos de actividad.
	public abstract void realizarActividad( ) throws Exception;	
}
