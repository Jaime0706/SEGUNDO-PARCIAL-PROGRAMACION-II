public abstract class Vehiculo {
    private String placa;
    private String propietario;
    private String horaIngreso;
    private double horasUtilizadas;

    public Vehiculo(String placa, String propietario, String horaIngreso, double horasUtilizadas) {
        this.placa = placa;
        this.propietario = propietario;
        this.horaIngreso = horaIngreso;
        this.horasUtilizadas = horasUtilizadas;
    }


    public String getPlaca() {
        return placa;
    }

    public String getPropietario() {
        return propietario;
    }

    public String getHoraIngreso() {
        return horaIngreso;
    }

    public double getHorasUtilizadas() {
        return horasUtilizadas;
    }

    public abstract double calcularCosto();

    public abstract String getTipoVehiculo();

    public void mostrarInformacion() {
        System.out.printf("Tipo: %-12s | Placa: %-8s | Propietario: %-15s | Ingreso: %-6s | Horas: %-4.1f | Costo: Q%.2f%n",
                getTipoVehiculo(), placa, propietario, horaIngreso, horasUtilizadas, calcularCosto());
    }
}