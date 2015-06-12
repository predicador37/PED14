package es.uned.ped14.conocimiento;

public enum NivelConocimiento{
	BAJO("nivel.bajo"),
	MEDIO("nivel.medio"),
	ALTO("nivel.alto"),
	OTRO("nivel.otro");
	
	private String valor;
	 
   private NivelConocimiento(String valor) {
        this.valor = valor;
    }
	 
    public String getValor() {
        return valor;
    }
	
}