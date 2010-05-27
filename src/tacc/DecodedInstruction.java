package tacc;

public class DecodedInstruction
{
	public static final int ADD = 0;
	public static final int SUB = 1;
	public static final int VOID = 2;
	public static final int GOTO = 3;

	/*
	 * Tipo di istruzione (vedi costanti)
	 */
	public int opcode;

	/*
	 * Variabile di riferimento
	 */
	public String variable;

	/*
	 * Eventuale etichetta
	 */
	public String label = null;

	/*
	 * Etichetta in cui saltare (per GOTO)
	 */
	public String jump = null;

	/**
	 * Utilizzabile ai fini di debugging, stampa il contenuto dell'istruzione.
	 *
	 * @return String
	 */
	public String toString()
	{
		String s = "[opcode: ";
		switch (this.opcode) {
			case ADD:
				s = s.concat("ADD");
				break;
			case SUB:
				s = s.concat("SUB");
				break;
			case VOID:
				s = s.concat("VOID");
				break;
			case GOTO:
				s = s.concat("GOTO, jump: " + this.jump);
				break;
		}

		s = s.concat(", variable: " + this.variable);
		if (this.label != null) s = s.concat(", label: " + this.label);

		return s+"]";
	}

	/**
	 * Restituisce true se l'istruzione ha un GOTO che porta in E.
	 *
	 * @return boolean
	 */
	public boolean isTerminator()
	{
		if (this.opcode != GOTO) return false;

		if (this.jump.charAt(0) == 'E') return true;
		else return false;
	}

}
