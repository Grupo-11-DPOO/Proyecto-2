package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import actividades.Actividad;
import actividades.Encuesta;
import actividades.Examen;
import actividades.Quiz;
import actividades.Recurso;
import actividades.Tarea;
import exceptions.UsuarioExistenteException;
import persistencia.PersistenciaUsuarios;
import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.SistemaRegistro;

public class Consola {
	
	public static SistemaRegistro sistemaRegistro = new SistemaRegistro();
	private String loginGlobal;
	private static HashMap<String, Profesor> datosProfesor = sistemaRegistro.getDatosProfesores();
	public static HashMap<String, Actividad> actividades = sistemaRegistro.actividades;
	private static Profesor profesorActual;
	private static String[] opciones = {"Iniciar sesion", "Registrarse", "Salir"};
	private static String[] opcionesRegistro = {"Crear usuario: Profesor", "Crear usuario: Estudiante", "Salir"};
	private static String[] opcionesMenuProfesor = {"Crear LearningPath", "Editar LearningPath", "Crear actividad", "Clonar actividad", "Salir"};
	private static String[] opcionesTipoActividad = {"Encuesta", "Examen", "Quiz", "Recurso", "Tarea"};
	private static String[] opcionesClonar = {"Clonar con ID de actividad", "Volver"};
	
	// PRUEBA
    /**
     * Le pide al usuario que ingrese una cadena de caracteres
     * @param mensaje El mensaje con el que se solicita la información
     * @return La cadena introducida por el usuario
     */
    protected static String pedirCadenaAlUsuario( String mensaje )
    {
        try
        {
            System.out.print( mensaje + ": " );
            BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
            String input = reader.readLine( );
            return input;
        }
        catch( IOException e )
        {
            System.out.println( "Error leyendo de la consola" );
        }
        return "error";
    }

    /**
     * Le pide al usuario que ingrese un número que no puede tener cifras decimales
     * @param mensaje El mensaje con el que se solicita la información
     * @return El valor introducido por el usuario
     */
    protected static int pedirEnteroAlUsuario( String mensaje )
    {
        int valorResultado = Integer.MIN_VALUE;
        while( valorResultado == Integer.MIN_VALUE )
        {
            try
            {
                System.out.print( mensaje + ": " );
                BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
                String input = reader.readLine( );
                int numero = Integer.parseInt( input );
                valorResultado = numero;
            }
            catch( NumberFormatException nfe )
            {
                System.out.println( "El valor digitado no es un entero" );
            }
            catch( IOException e )
            {
                System.out.println( "Error leyendo de la consola" );
            }
        }
        return valorResultado;
    }

    /**
     * Le pide al usuario que ingrese un número que puede tener cifras decimales
     * @param mensaje El mensaje con el que se solicita la información
     * @return El valor introducido por el usuario
     */
    protected static float pedirNumeroAlUsuario( String mensaje )
    {
        float valorResultado = Integer.MIN_VALUE;
        while( valorResultado == Integer.MIN_VALUE )
        {
            try
            {
                System.out.print( mensaje + ": " );
                BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
                String input = reader.readLine( );
                float numero = Float.parseFloat( input );
                valorResultado = numero;
            }
            catch( NumberFormatException nfe )
            {
                System.out.println( "El valor digitado no es un número" );
            }
            catch( IOException e )
            {
                System.out.println( "Error leyendo de la consola" );
            }
        }
        return valorResultado;
    }

    /**
     * Le pide al usuario que seleccione una de las opciones que se le presenten
     * @param coleccionOpciones
     * @return Retorna la opción seleccionada (el valor, no su posición).
     */
    protected String pedirOpcionAlUsuario( Collection<? extends Object> coleccionOpciones )
    {
        String[] opciones = new String[coleccionOpciones.size( )];
        int pos = 0;
        for( Iterator<? extends Object> iterator = coleccionOpciones.iterator( ); iterator.hasNext( ); pos++ )
        {
            opciones[ pos ] = iterator.next( ).toString( );
        }

        System.out.println( "Seleccione una de las siguientes opciones:" );
        for( int i = 1; i <= opciones.length; i++ )
        {
            System.out.println( " " + i + ". " + opciones[ i - 1 ] );
        }

        String opcion = pedirCadenaAlUsuario( "\nEscriba el número que corresponde a la opción deseada" );
        try
        {
            int opcionSeleccionada = Integer.parseInt( opcion );
            if( opcionSeleccionada > 0 && opcionSeleccionada <= opciones.length )
                return opciones[ opcionSeleccionada - 1 ];
            else
            {
                System.out.println( "Esa no es una opción válida. Digite solamente números entre 1 y " + opciones.length );
                return pedirOpcionAlUsuario( coleccionOpciones );
            }
        }
        catch( NumberFormatException nfe )
        {
            System.out.println( "Esa no es una opción válida. Digite solamente números." );
            return pedirOpcionAlUsuario( coleccionOpciones );
        }
    }
    
    protected static void crearUsuario(String[] opciones) {
        System.out.println( "------------------------------------------------------" );
        System.out.println( "Registro de usuarios" );
        System.out.println( "------------------------------------------------------" );
        
        for( int i = 1; i <= opciones.length; i++ )
        {
            System.out.println( " " + i + ". " + opciones[ i - 1 ] );
        }
        int opcionSeleccionada = pedirEnteroAlUsuario( "Escoja la opción deseada" );
        try
        {
            if( opcionSeleccionada > 0 && opcionSeleccionada <= opciones.length ) {

            	switch (opcionSeleccionada) {
            		case 3:
            			System.out.println("Saliendo del programa...");
    	                System.exit(0);
    	                
            		case 1:
            			crearProfesor();
            			System.out.println("¡Profesor registrado exitosamente!");
            			return;
            		case 2:
            			crearEstudiante();
            			System.out.println("¡Estudiante registrado exitosamente!");
            			return;
            	}
            }
            	
            else
            {
                System.out.println( "Esa no es una opción válida. Digite solamente números entre 1 y " + opciones.length );
                crearUsuario( opciones );
            }
        }
        catch( NumberFormatException nfe )
        {
            System.out.println( "Esa no es una opción válida. Digite solamente números." );
            crearUsuario( opciones );
        }
    }
    
    protected static void crearProfesor() {
        System.out.println( "------------------------------------------------------" );
        System.out.println( "Registar Profesor" );
        System.out.println( "------------------------------------------------------" );
    	
        String login = pedirCadenaAlUsuario("Nombre de usuario");
        String password = pedirCadenaAlUsuario("Contraseña");
        
        try {
			sistemaRegistro.registrarProfesor(login, password);
			return;
		} catch (UsuarioExistenteException e) {
			System.out.println( "Este nombre de usuario ya es existe." );
            crearProfesor();
		}	
    }
    
    protected static void crearEstudiante() {
        System.out.println( "------------------------------------------------------" );
        System.out.println( "Registar Estudiante" );
        System.out.println( "------------------------------------------------------" );
    	
        String login = pedirCadenaAlUsuario("Nombre de usuario");
        String password = pedirCadenaAlUsuario("Contraseña");
        List<String> intereses = pedirIntereses();
        
        try {
        	sistemaRegistro.registrarEstudiante(login, password, intereses);
			return;
		} catch (UsuarioExistenteException e) {
			System.out.println( "Este nombre de usuario ya es existe." );
            crearEstudiante();
		}	
    }
    
    protected static List<String> pedirIntereses(){
        System.out.println( "------------------------------------------------------" );
        System.out.println( "Agregar intereses" );
        System.out.println( "------------------------------------------------------" );
        System.out.println("Ingrese los 3 principales intereses que tenga (orden no importa). Por ejemplo: derecho, programar y arte.");
        List<String> intereses = new ArrayList<String>();
        for (int i = 1; i < 4; i++) {
        	String interes = pedirCadenaAlUsuario("Ingrese un interés: ");
        	intereses.add(interes);
        }
        return intereses;
    }
    
    protected static void iniciarSesion() {
        System.out.println( "------------------------------------------------------" );
        System.out.println( "Iniciar sesión" );
        System.out.println( "------------------------------------------------------" );
        int tipoUsuario = pedirEnteroAlUsuario("Tipo de usuario (1 para profesor, 0 para estudiante)");
        String login = pedirCadenaAlUsuario("Nombre de usuario");
        String password = pedirCadenaAlUsuario("Contraseña");
        boolean resultado;
        if (tipoUsuario == 1) {
        	resultado = sistemaRegistro.iniciarSesionProfesor(login, password);
        } else if (tipoUsuario == 0) {
        	resultado = sistemaRegistro.iniciarSesionEstudiante(login,password);
        } else {
        	System.out.println("Ingrese de nuevo su tipo de usuario...");
        	resultado = false;
        	iniciarSesion();
        }
        if (resultado) {
        	System.out.println( "------------------------------------------------------" );
        	System.out.println("Bienvenido "+login+"!");
        	if (tipoUsuario == 1) {
        		//manda al menu de profesor
        		profesorActual = datosProfesor.get(login);
        		menuProfesor();
        	} else {
        		// manda al menu de estudiante
        	}
        } else {
        	System.out.println("Credenciales incorrectos.");
        	iniciarSesion();
        }
        
        
    }
    

    /**
     * Muestra un menú y le pide al usuario que seleccione una opción
     * @param nombreMenu El nombre del menu
     * @param opciones Las opciones que se le presentan al usuario
     * @return El número de la opción seleccionada por el usuario, contando desde 1
     */
    protected static int mostrarMenu( String nombreMenu, String[] opciones )
    {
        System.out.println( "------------------------------------------------------" );
        System.out.println( nombreMenu );
        System.out.println( "------------------------------------------------------" );

        for( int i = 1; i <= opciones.length; i++ )
        {
            System.out.println( " " + i + ". " + opciones[ i - 1 ] );
        }
        int opcionSeleccionada = pedirEnteroAlUsuario( "Escoja la opción deseada" );
        try
        {
            if( opcionSeleccionada > 0 && opcionSeleccionada <= opciones.length ) {
                return opcionSeleccionada;
            }
            else
            {
                System.out.println( "Esa no es una opción válida. Digite solamente números entre 1 y " + opciones.length );
                return mostrarMenu( nombreMenu, opciones );
            }
        }
        catch( NumberFormatException nfe )
        {
            System.out.println( "Esa no es una opción válida. Digite solamente números." );
            return mostrarMenu( nombreMenu, opciones );
        }
    }
    
    public static void menuProfesor() {
    	try {
			int opcionSeleccionada = mostrarMenu("Menu Principal Profesores", opcionesMenuProfesor);
			
			switch (opcionSeleccionada) {
			case 5:
				System.out.println( "------------------------------------------------------" );
				System.out.println("Saliendo del programa.");
				System.out.println( "------------------------------------------------------" );
                System.exit(0);
			case 1:
				//menu crear learning path
                break;
			case 2:
				// menu editar learning path
				break;
			case 3:
				// menu crear actividad
				menuCrearActividad();
				break;
			case 4:
				// menu clonar actividad
				menuClonarActividad();
				break;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    }
    
    public static void menuCrearActividad() {
		System.out.println( "------------------------------------------------------" );
		System.out.println("Crear actividad");
		try {
			int opcionSeleccionada = mostrarMenu("Seleccione el tipo de actividad a crear", opcionesTipoActividad);
			String titulo;
			String objetivo;
			String descripcion;
			String nivel;
			int duracionMinutos;
			String obligatorioString;
			boolean obligatorio;
			// Parametros que todas las actividades tienen:
			titulo = pedirCadenaAlUsuario("Ingrese el titulo");
			objetivo = pedirCadenaAlUsuario("Ingrese el objetivo");
			descripcion = pedirCadenaAlUsuario("Ingrese la descripción");
			nivel = pedirCadenaAlUsuario("Ingrese el nivel (bajo, intermedio o avanzado)");
			duracionMinutos = pedirEnteroAlUsuario("Ingrese el tiempo estimado en minutos");
			obligatorioString = pedirCadenaAlUsuario("¿Es obligatorio?");
			obligatorioString.toLowerCase();
			// Por defecto no es
			obligatorio = false;
			if (obligatorioString.equals("si")) {
				obligatorio = true;
			}
			String tienePrerequisitosString = pedirCadenaAlUsuario("¿Tiene prerequisitos?");
			tienePrerequisitosString.toLowerCase();
			// Por defecto no tiene
			List<Actividad> listaPrerequisitos = null;
			if (tienePrerequisitosString.equals("si")) {
				// Entro al metodo para agregar prerequisitos
				listaPrerequisitos = crearPrerequisitos();
			}
			String idActividad;
			
			switch (opcionSeleccionada) {
			case 1:
				// Crear encuesta
				Encuesta encuesta = profesorActual.crearActividadEncuesta(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
				encuesta.setPrerequisitos(listaPrerequisitos);
				// Atributos propios de encuesta
				// Agregamos preguntas
				int cantidadPreguntas = pedirEnteroAlUsuario("Cantidad de preguntas de la encuesta");
				int i = 0;
				while (i < cantidadPreguntas) {
					String enunciado = pedirCadenaAlUsuario("Enunciado pregunta");
					encuesta.agregarPregunta(enunciado);
				}
				profesorActual.guardarActividad(encuesta);
				idActividad = encuesta.getId();
				System.out.println("La actividad fue cargada exitosamente con el id "+idActividad);
				break;
				
			case 2:
				// Crear examen
				Examen examen = profesorActual.crearActividadExamen(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
				examen.setPrerequisitos(listaPrerequisitos);
				// Atributos propios de encuesta
				// Agregamos preguntas
				int cantidadPreguntas1 = pedirEnteroAlUsuario("Cantidad de preguntas del examen");
				int i1 = 0;
				while (i1 < cantidadPreguntas1) {
					String enunciado = pedirCadenaAlUsuario("Enunciado pregunta");
					encuesta.agregarPregunta(enunciado);
				}
				profesorActual.guardarActividad(examen);
				idActividad = examen.getId();
				System.out.println("La actividad fue cargada exitosamente con el id "+idActividad);
				break;
				
			case 3:
				// Crear quiz
				// Atributos propios de quiz
				float calificacionMinima = pedirNumeroAlUsuario("Calificación mínima (0 a 100)");
				
				Quiz quiz = profesorActual.crearActividadQuiz(titulo, objetivo, descripcion,nivel ,duracionMinutos, obligatorio, calificacionMinima);
				quiz.setPrerequisitos(listaPrerequisitos);
				// Agregamos preguntas
				int cantidadPreguntas2 = pedirEnteroAlUsuario("Cantidad de preguntas");
				int i2 = 0;
				while (i2 < cantidadPreguntas2) {
					String enunciado = pedirCadenaAlUsuario("Enunciado pregunta");
					String opcionCorrecta = pedirCadenaAlUsuario("Opción correcta");
					quiz.agregarPregunta(enunciado, opcionCorrecta);
					// Opcion A
					String explicacionA = pedirCadenaAlUsuario("Opción A");
					quiz.agregarOpcion(enunciado, "A", explicacionA);
					// Opcion B
					String explicacionB = pedirCadenaAlUsuario("Opción B");
					quiz.agregarOpcion(enunciado, "B", explicacionB);
					// Opcion C
					String explicacionC = pedirCadenaAlUsuario("Opción C");
					quiz.agregarOpcion(enunciado, "C", explicacionC);
					// Opcion D
					String explicacionD = pedirCadenaAlUsuario("Opción D");
					quiz.agregarOpcion(enunciado, "D", explicacionD);
					i2++;
				}
				profesorActual.guardarActividad(quiz);
				idActividad = quiz.getId();
				System.out.println("La actividad fue cargada exitosamente con el id "+idActividad);
				break;
				
			case 4:
				// Crear recurso
				// Atributos propios de recurso
				String contenidoRecurso = pedirCadenaAlUsuario("Ingrese el link del contenido del recurso");
				Recurso recurso = profesorActual.crearActividadRecurso(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, contenidoRecurso);
				recurso.setPrerequisitos(listaPrerequisitos);
				// Guardamos
				profesorActual.guardarActividad(recurso);
				idActividad = recurso.getId();
				System.out.println("La actividad fue cargada exitosamente con el id "+idActividad);
				break;
				
			case 5:
				// Crear tarea
				// Atributos propios de tarea
				String contenidoIndicaciones = pedirCadenaAlUsuario("Ingrese las instrucciones de la tarea");
				String medioEntrega = pedirCadenaAlUsuario("Ingrese el medio de entrega");
				Tarea tarea = profesorActual.crearActividadTarea(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, contenidoIndicaciones, medioEntrega);
				tarea.setPrerequisitos(listaPrerequisitos);
				// Guardamos
				profesorActual.guardarActividad(tarea);
				idActividad = tarea.getId();
				System.out.println("La actividad fue cargada exitosamente con el id "+idActividad);
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
    
    public static List<Actividad> crearPrerequisitos(){
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("Agregar Prerequisitos");
    	System.out.println( "------------------------------------------------------" );
    	// Imprime todas las actividades, solo id y titulo 
		 for (Map.Entry<String, Actividad> entry : actividades.entrySet()) {
	            String id = entry.getKey();
	            Object titulo = entry.getValue();
	
	            if (titulo instanceof Map) {
	                Map<?, ?> valueMap = (Map<?, ?>) titulo;
	                Object tituloOb = valueMap.get("titulo");
	                System.out.println(String.format("%-10s | %-20s", id, tituloOb));
	            } else {
	                System.out.println(String.format("%-10s | %-20s", id, "Tipo no soportado"));
	            }
	        }
    	List<Actividad> listaPrerequisitos = new ArrayList<>();
    	int cantidadPrerequisitos = pedirEnteroAlUsuario("Ingrese la cantidad de prerequisitos");
    	int i = 0;
    	while (i <= cantidadPrerequisitos) {
	    	String codigoActividad = pedirCadenaAlUsuario("Ingrese el id de la actividad a agregar");
	    	// toma la lista de actividadades totales y las filtra.
	    	Actividad actividadAgregar = actividades.get(codigoActividad);
	    	listaPrerequisitos.add(actividadAgregar);
	    	i++;
    	}
		return listaPrerequisitos;
    	
    }
    
    public static void menuClonarActividad() {
		System.out.println( "------------------------------------------------------" );
		System.out.println("Clonar actividad");
		System.out.println( "------------------------------------------------------" );
		System.out.println("Lista actividades:");
		// Imprime todas las actividades, solo id y titulo 
		 for (Map.Entry<String, Actividad> entry : actividades.entrySet()) {
	            String id = entry.getKey();
	            Object titulo = entry.getValue();

	            if (titulo instanceof Map) {
	                Map<?, ?> valueMap = (Map<?, ?>) titulo;
	                Object tituloOb = valueMap.get("titulo");
	                System.out.println(String.format("%-10s | %-20s", id, tituloOb));
	            } else {
	                System.out.println(String.format("%-10s | %-20s", id, "Tipo no soportado"));
	            }
	        }
	  
		try {
			int opcionSeleccionada = mostrarMenu("¿Desea clonar?", opcionesClonar);
			
			switch (opcionSeleccionada) {
			case 1:
				String idActividadAClonar = pedirCadenaAlUsuario("Ingrese ID");
				Actividad actividad = actividades.get(idActividadAClonar);
				profesorActual.clonarActividad(actividad);
				System.out.println("La actividad fue clonada exitosamente.");
				menuProfesor();
				break;
			case 2:
				menuProfesor();
				break;
			}
		}
			catch (Exception e) {
			e.printStackTrace();
		}
		
    }
    	

	public static void main(String[] args)  {		
		// Tengo que cargar todos los datos
		// Cuando toda la informacion este cargada se revisa	
		String tituloConsola = "Bienvenido al Sistema Operativo de LearningPaths G11!";

		int opcionSeleccionada = 0;
		
		while (true) {
		try {
			opcionSeleccionada = mostrarMenu(tituloConsola, opciones);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		switch (opcionSeleccionada) {
			case 3:
				System.out.println( "------------------------------------------------------" );
				System.out.println("Saliendo del programa.");
				System.out.println( "------------------------------------------------------" );
                System.exit(0);
			case 1:
				iniciarSesion();
				break;
			case 2:
				crearUsuario(opcionesRegistro);
		}
	}
	}
}