package model;

public class CalcularHuellaCarbono {

    // Factores de emisión (kg CO₂/km)
    private static final double COCHE = 0.21;
    private static final double AUTOBUS = 0.10;
    private static final double TREN = 0.04;
    private static final double BICI = 0.00;
    private static final double PIE = 0.00;

    public double calcularSemanal(String transporte, double kmDiarios, int diasSemanales) {
        double factor;
        switch (transporte.toUpperCase()) {
            case "COCHE": factor = COCHE; break;
            case "AUTOBUS": factor = AUTOBUS; break;
            case "TREN": factor = TREN; break;
            case "BICI": factor = BICI; break;
            case "PIE": factor = PIE; break;
            default: throw new IllegalArgumentException("Transporte no válido");
        }
        return factor * kmDiarios * diasSemanales;
    }

    public String clasificarImpacto(double kgSem) {
        if (kgSem <= 5) return "Baja";
        else if (kgSem <= 15) return "Media";
        else return "Alta";
    }

    public String proponerCompensacion(double kgSem) {
        double arboles = kgSem / 0.40;
        double kmBici = kgSem / 0.21;
        return String.format("Para compensar %.2f kg: ~%.1f árboles (anuales) o %.1f km en bici si sustituyes el coche esta semana.", 
                             kgSem, arboles, kmBici);
    }
	
}
