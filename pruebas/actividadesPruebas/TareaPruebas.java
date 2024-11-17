package actividadesPruebas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividades.Estado;
import actividades.Tarea;

class TareaPruebas {
    Tarea tarea;

    @BeforeEach
    void setUp() {
        // Inicializamos una nueva tarea antes de cada prueba
        tarea = new Tarea(
                "Tarea de Programación", 
                "Practicar conceptos básicos de programación", 
                "Implementar un programa en Java", 
                "Intermedio", 
                120, 
                true, 
                "Crear un programa que calcule la suma de números", 
                "Plataforma virtual"
        );
    }

    @AfterEach
    void tearDown() {
        tarea = null; // Limpiamos la referencia después de cada prueba
    }

    @Test
    void testGetMedioEntrega() {
        assertEquals("Plataforma virtual", tarea.getMedioEntrega(), "El medio de entrega no es el esperado.");
    }

    @Test
    void testGetContenido() {
        assertEquals("Crear un programa que calcule la suma de números", tarea.getContenido(), "El contenido no es el esperado.");
    }

    @Test
    void testSetContenido() {
        tarea.setContenido("Actualizar el programa para restar números");
        assertEquals("Actualizar el programa para restar números", tarea.getContenido(), "El contenido no se actualizó correctamente.");
    }

    @Test
    void testEstadoInicial() {
        assertEquals(Estado.PENDIENTE, tarea.getEstadoTarea(), "El estado inicial de la tarea no es PENDIENTE.");
    }

    @Test
    void testEnviarTareaExito() {
        try {
            tarea.enviarTarea();
        } catch (Exception e) {
            assertEquals("El medio de entrega es incorrecto.", e.getMessage(), "El mensaje de excepción no es el esperado.");
        }
        assertEquals(Estado.ENVIADA, tarea.getEstadoTarea(), "El estado de la tarea no es ENVIADA después de enviarla.");
    }

    @Test
    void testEnviarTareaDosVeces() {
        try {
            tarea.enviarTarea(); // Primer envío exitoso
        } catch (Exception e) {
            fail("No debería haber lanzado una excepción en el primer envío.");
        }

        Exception exception = assertThrows(Exception.class, () -> {
            tarea.enviarTarea(); // Segundo envío debería fallar
        });

        assertEquals("La tarea ya se encuentra enviada.", exception.getMessage(), "El mensaje de error no es correcto.");
    }

    @Test
    void testMarcarComoExitosa() {
        try {
            tarea.enviarTarea();
        } catch (Exception e) {
            fail("No debería haber lanzado una excepción al enviar la tarea.");
        }
        tarea.marcarComoExitosa(true);
        assertEquals(Estado.EXITOSA, tarea.getEstadoTarea(), "La tarea no fue marcada como EXITOSA correctamente.");
    }

    @Test
    void testMarcarComoNoExitosa() {
        try {
            tarea.enviarTarea();
        } catch (Exception e) {
            fail("No debería haber lanzado una excepción al enviar la tarea.");
        }
        tarea.marcarComoExitosa(false);
        assertEquals(Estado.NO_EXITOSA, tarea.getEstadoTarea(), "La tarea no fue marcada como NO_EXITOSA correctamente.");
    }

    @Test
    void testMarcarComoExitosaSinEnviar() {
        tarea.marcarComoExitosa(true);
        assertEquals(Estado.PENDIENTE, tarea.getEstadoTarea(), "El estado no debería cambiar si la tarea no fue enviada.");
    }

  
}
