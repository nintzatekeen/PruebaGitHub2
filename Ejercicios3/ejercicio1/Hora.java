package ejercicio1;

public class Hora {
	private int hora, minutos;
	public int getHora() {
		return hora;
	}
	public int getMinutos() {
		return minutos;
	}
	public Hora(int hour, int minutes) {
		hora=hour;
		minutos=minutes;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hora;
		result = prime * result + minutos;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hora other = (Hora) obj;
		if (hora != other.hora)
			return false;
		if (minutos != other.minutos)
			return false;
		return true;
	}
}
