package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;

public class Consola {
	
	private static String[] opciones = {"Iniciar sesion: Profesor", "Iniciar sesion: Estudiante", "Registrarse","Salir"};
	
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
    protected int pedirEnteroAlUsuario( String mensaje )
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
    protected double pedirNumeroAlUsuario( String mensaje )
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
        String opcion = pedirCadenaAlUsuario( "Escoja la opción deseada" );
        try
        {
            int opcionSeleccionada = Integer.parseInt( opcion );
            if( opcionSeleccionada > 0 && opcionSeleccionada <= opciones.length )
                return opcionSeleccionada;
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
	

	public static void main(String[] args) {
		
		// Tengo que cargar todos los datos
		// Cuando toda la informacion este cargada se revisa	
		String tituloConsola = "Bienvenido al Sistema Operativo de LearningPaths G11!";
		
		while (true) {
			int opcionSeleccionada = 0;
			try {
				opcionSeleccionada = mostrarMenu(tituloConsola, opciones);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			switch (opcionSeleccionada) {
				case 4:
					System.out.println("Saliendo del programa...");
	                System.exit(0);
				case 1:
					System.out.println("Bienvenido al Login de profesores...");
					// manda a una consola para iniciar sesion como profesor
				case 2:
					System.out.println("Bienvenido al Login de estudiantes...");
					// manda a una consola para iniciar sesion como profesor
				case 3:
					System.out.println("Bienvenido al registro de usuarios...");
					// manda a la consola de registro
					
					
			}
		}
		
		
	}
}
