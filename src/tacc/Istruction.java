package tacc;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Istruction
{

	protected String istruction;

	protected Pattern addPattern;
	protected Pattern subPattern;
	protected Pattern voidPattern;
	protected Pattern gotoPattern;

	public Istruction(String istr)
	{
		this.istruction = istr.trim();
		this.initPatterns();
	}

	protected void initPatterns()
	{
		String label = "([A-E]\\d*)";
		String labelPtr = "(\\[" + label + "\\])?";
		String variable = "([x|z]\\d?|Y)";

		this.addPattern = Pattern.compile(labelPtr + "\\s*" + variable + "\\s*<-\\s*" + variable + "\\s*\\+\\s*1");
		this.subPattern = Pattern.compile(labelPtr + "\\s*" + variable + "\\s*<-\\s*" + variable + "\\s*-\\s*1");
		this.voidPattern = Pattern.compile(labelPtr + "\\s*" + variable + "\\s*<-\\s*" + variable);
		this.gotoPattern = Pattern.compile(labelPtr + "\\s*IF\\s*" + variable + "\\s*!=\\s*0\\s*GOTO\\s*" + label);
	}

	/**
	 * Determina se l'istruzione è del tipo "x si becca x+1"
	 *
	 * @throws tacc.IstructionException
	 * @return boolean
	 */
	public boolean isAdd() throws tacc.IstructionException
	{
		Matcher matcher = this.addPattern.matcher(this.istruction);

		if (!matcher.find()) return false;

		// controllo che la variabile sia la stessa
		if (!matcher.group(3).equals(matcher.group(4))) {
			throw new tacc.IstructionException("Variabile non corrispondente: " + this.istruction);
		}

		return true;
	}

	/**
	 * Determina se l'istruzione è del tipo "x si becca x-1"
	 *
	 * @throws tacc.IstructionException
	 * @return boolean
	 */
	public boolean isSub() throws tacc.IstructionException
	{
		Matcher matcher = this.subPattern.matcher(this.istruction);

		if (!matcher.find()) return false;

		// controllo che la variabile sia la stessa
		if (!matcher.group(3).equals(matcher.group(4))) {
			throw new tacc.IstructionException("Variabile non corrispondente: " + this.istruction);
		}

		return true;
	}

	/**
	 * Determina se l'istruzione è del tipo "x si becca x"
	 *
	 * @throws tacc.IstructionException
	 * @return boolean
	 */
	public boolean isVoid() throws tacc.IstructionException
	{
		Matcher matcher = this.voidPattern.matcher(this.istruction);

		if (!matcher.find()) return false;

		// controllo che la variabile sia la stessa
		if (!matcher.group(3).equals(matcher.group(4))) {
			throw new tacc.IstructionException("Variabile non corrispondente: " + this.istruction);
		}

		return true;
	}

	/**
	 * Determina se l'istruzione è del tipo "if x1 != 0 goto E"
	 *
	 * @throws tacc.IstructionException
	 * @return boolean
	 */
	public boolean isGoto() throws tacc.IstructionException
	{
		Matcher matcher = this.gotoPattern.matcher(this.istruction);

		if (!matcher.find()) return false;

		return true;
	}

	/**
	 * Controlla la validità dell'istruzione.
	 *
	 * @return boolean
	 */
	public boolean isValid()
	{
		try {
			if (this.isAdd()) return true;
			if (this.isSub()) return true;
			if (this.isVoid()) return true;
			if (this.isGoto()) return true;
		}
		catch (tacc.IstructionException e) {
			System.out.println(e.getMessage());
			return false;
		}

		return false;
	}

	public DecodedInstruction decode()
	{
		DecodedInstruction di = new DecodedInstruction();

		try {
			if (this.isAdd()) {
				Matcher matcher = this.addPattern.matcher(this.istruction);
				matcher.find();
				di.opcode = DecodedInstruction.ADD;
				di.label = matcher.group(2);
				di.variable = matcher.group(3);
				return di;
			}
			if (this.isSub()) {
				Matcher matcher = this.subPattern.matcher(this.istruction);
				matcher.find();
				di.opcode = DecodedInstruction.SUB;
				di.label = matcher.group(2);
				di.variable = matcher.group(3);
				return di;
			}
			if (this.isVoid()) {
				Matcher matcher = this.voidPattern.matcher(this.istruction);
				matcher.find();
				di.opcode = DecodedInstruction.VOID;
				di.label = matcher.group(2);
				di.variable = matcher.group(3);
				return di;
			}
			if (this.isGoto()) {
				Matcher matcher = this.gotoPattern.matcher(this.istruction);
				matcher.find();

				di.opcode = DecodedInstruction.GOTO;
				di.label = matcher.group(2);
				di.variable = matcher.group(3);
				di.jump = matcher.group(4);
				return di;
			}
		}
		catch (tacc.IstructionException e) {}

		return null;
	}

}
