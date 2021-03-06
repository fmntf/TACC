package tacc;

public class Main {

    public static void main(String[] args)
    {
		boolean debug = false;
		boolean dump  = false;

		if (args.length == 0) {
			Main.wrongUse();
		}

		// processa gli argomenti della linea di comando
		for (int i=1; i<args.length; i++) {
			switch (args[i].charAt(1)) {
				case 's':
					debug = true;
					break;

				case 'd':
					dump = true;
					break;

				default:
					Main.wrongUse();
			}
		}

		Machine tvm = new Machine(debug);

		if (!tvm.loadScript(args[0])) {
			System.out.println("Non è stato possibile aprire/processare il file " + args[0]);
			System.exit(1);
		}

		System.out.println("Avvio della macchina TACC..\n");
		tvm.run();
		System.out.println("Elaborazione terminata! La macchina ha restituito " + tvm.getResponse() + ".\n");

		if (dump) {
			System.out.println("Dump delle variabili:");
			tvm.dumpMemory();
		}
    }

	public static void wrongUse()
	{
		System.out.println("USO: ./tacc script.t [-s] [-d]");
		System.out.println(" -s: mostra i passi di esecuzione");
		System.out.println(" -d: esegue il dump delle variabili al termine");
		System.exit(1);
	}

}
