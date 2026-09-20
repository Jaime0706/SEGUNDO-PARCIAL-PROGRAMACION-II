public class Motocicleta extends Vehiculo {
    private static final double TARIFA_HORA = 6.00;

    public Motocicleta(String placa, String propietario, String horaIngreso, double horasUtilizadas) {
        super(placa, propietario, horaIngreso, horasUtilizadas);
    }

    @Override
    public double calcularCosto() {
        double subtotal = getHorasUtilizadas() * TARIFA_HORA;
        if (getHorasUtilizadas() > 5) {
            subtotal *= 0.90;
        }
        return subtotal;
    }

    @Override
    public String getTipoVehiculo() {
        return "Motocicleta";
    }
}
