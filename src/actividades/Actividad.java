package actividades;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import learningPaths.Identificable;

public abstract class Actividad implements Identificable {
	
	protected String id;
	public String titulo;
	public String objetivo;
	public String descripcion;
	public String nivel;
	public int duracionMinutos;
	public boolean obligatorio;
	public int tiempoLimite;
	private float rating;
	protected List<Actividad> prerequisitos;
	public int cantidadRating = 0;
	public int sumaRating = 0;
	public List<String> resenas; // las resenas que un estudiante ingresa a la actividad y pueda visualizar un profesor
	protected TipoActividades tipoActividad;
	protected Estado estado;
	// Metodos
	
	public Actividad(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio) {
		this.titulo = titulo;
		this.objetivo = objetivo;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.duracionMinutos = duracionMinutos;
		this.obligatorio = obligatorio;
		this.prerequisitos = new ArrayList<Actividad>();
		this.resenas = new ArrayList<String>();
		this.rating = 0;
		this.setEstado(Estado.PENDIENTE);
		crearId();
		
		
	}

	public int getTiempoLimite() {
		return tiempoLimite;
	}

	public void setTiempoLimite(int tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	}

	public int getCantidadRating() {
		return cantidadRating;
	}

	public void setCantidadRating(int cantidadRating) {
		this.cantidadRating = cantidadRating;
	}

	public int getSumaRating() {
		return sumaRating;
	}

	public void setSumaRating(int sumaRating) {
		this.sumaRating = sumaRating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public void setResenas(List<String> resenas) {
		this.resenas = resenas;
	}

	public void setTipoActividad(TipoActividades tipoActividad) {
		this.tipoActividad = tipoActividad;
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
	

	public boolean isObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}


	public List<Actividad> getPrerequisitos() {
		return prerequisitos;
	}

	public void setPrerequisitos(List<Actividad> prerequisitos) {
		this.prerequisitos = prerequisitos;
	}
	
	// Los ratings deben estar entre 0 y 5.
	public void agregarRating(float ratingPersonal) throws Exception{
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
	
	public void agregarResena(String resenaInput) {
		resenas.add(resenaInput);
	}
	
	public List<String> getResenas() {
		return resenas;
	}
	
//	protected void advertenciaPrerequisitos(Estudiante estudiante) throws Exception {
//		if (!prerequisitos.isEmpty()) {
//			ListIterator<Actividad> listaPrerequisitosIterable = prerequisitos.listIterator();
//			for (Actividad act:prerequisitos) {
//				String codigoAct = act.getId();
//				HashMap<String, Estado> registroActividadesEstudiante = estudiante.getRegistroActividades();
//				if (!registroActividadesEstudiante.containsKey(codigoAct)) {
//					throw new Exception("Usted no cumple con los prerequisitos de la actividad pero puede continuar bajo su responsabilidad.");
//				}
//			}
//		}
//	}
//	Falta revisar y reconstruir la funcion ya que se cambio el nombre de la variable a tiempoLimite y el tipo a int para mayor facilidad y diseño
//    public void establecerFechaLimite(Actividad actividadAnterior, int horasDespues) {
//        if (actividadAnterior == null || actividadAnterior.getDuracionMinutos() <= 0) {
//            System.out.println("La actividad anterior no es válida.");
//            this.tiempoLimite = null;
//        } else {
//	        Calendar calendario = Calendar.getInstance();
//	        calendario.setTime(new Date());
//	        calendario.add(Calendar.MINUTE, actividadAnterior.getDuracionMinutos());
//	        calendario.add(Calendar.HOUR, horasDespues);        
//	        // Establece la nueva fecha límite
//        this.tiempoLimite = calendario.getTime();
//        }
//    }
//    
//    // Método para verificar si la actividad se está realizando después de la fecha límite
//    protected boolean revisarFechaLimiteExcedida() {
//        if (tiempolimite != null) {
//            Date fechaActual = new Date();
//            if (fechaActual.after(fechaLimite)) {
//                System.out.println("Advertencia: Está realizando la actividad después de la fecha límite recomendada.");
//                return true;
//            }
//            return false;
//        }
//        return false;
//    }
    
    // Se clona la actividad por si un profesor desea poder ser el nuevo dueño y modificarla.
	
	public void agregarPrerrequisito(Actividad actividad) {
		
		this.prerequisitos.add(actividad);
		
	}
	
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
	public void crearId() {
		// Genera el ID
    	String id = UUID.randomUUID().toString();
        this.id= id;
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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}	
}
