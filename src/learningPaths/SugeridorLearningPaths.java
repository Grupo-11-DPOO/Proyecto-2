package learningPaths;

import java.util.ArrayList;
import java.util.List;

import usuarios.Estudiante;

public class SugeridorLearningPaths {
    
    public List<LearningPath> listaCompletaLearningPaths;

    public SugeridorLearningPaths(List<LearningPath> listaCompletaLearningPaths) {
        this.listaCompletaLearningPaths = new ArrayList<>(listaCompletaLearningPaths);
    }

    // Calculamos un índice de similitud entre los intereses y el nombre/descripción de un Learning Path.
    public int calcularSimilitud(Estudiante estudiante, LearningPath learningPath) {
        int puntuacion = 0;
        for (String interes : estudiante.getIntereses()) {
            if (learningPath.getTitulo().toLowerCase().contains(interes.toLowerCase()) ||
                learningPath.getDescripcion().toLowerCase().contains(interes.toLowerCase())) {
                puntuacion++;
            }
        }
        return puntuacion;
    }

    // Método principal que ordena los Learning Paths en orden de preferencia al estudiante.
    public List<LearningPath> recomendarLearningPaths(Estudiante estudiante) {
        listaCompletaLearningPaths.sort((lp1, lp2) -> calcularSimilitud(estudiante, lp2) - calcularSimilitud(estudiante, lp1));
        return listaCompletaLearningPaths;
    }
}
