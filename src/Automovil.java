public class Automovil extends Vehiculo {
    private static final double TARIFA_HORA = 10.00;

    public Automovil(String placa, String propietario, String horaIngreso, double horasUtilizadas) {
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
        return "Automóvil";
    }
}