package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import exceptions.UsuarioExistenteException;
import persistencia.PersistenciaUsuarios;
import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.SistemaRegistro;

public class Consola {
	
	static SistemaRegistro sistemaRegistro = new SistemaRegistro();
	private static String[] opciones = {"Iniciar sesion", "Registrarse", "Salir"};
	private static String[] opcionesRegistro = {"Crear usuario: Profesor", "Crear usuario: Estudiante", "Salir"};
	
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
    protected static double pedirNumeroAlUsuario( String mensaje )
    {
        double valorResultado = Integer.MIN_VALUE;
        while( valorResultado == Integer.MIN_VALUE )
        {
            try
            {
                System.out.print( mensaje + ": " );
                BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
                String input = reader.readLine( );
                double numero = Double.parseDouble( input );
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
        	System.out.println("Bienvenido "+login+"!");
        	if (tipoUsuario == 1) {
        		//manda al menu de profesor
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
    	
    }
    
	

	public static void main(String[] args)  {
		
		// Tengo que cargar todos los datos
		// Cuando toda la informacion este cargada se revisa	
		
		String tituloConsola = "Bienvenido al Sistema Operativo de LearningPaths G11!";
		sistemaRegistro = new SistemaRegistro();

		
		while (true) {
			int opcionSeleccionada = 0;
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
