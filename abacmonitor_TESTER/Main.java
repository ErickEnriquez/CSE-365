

public class Main {

	public static void main(String[] args) throws Exception {
		Run e = new Run();
		String commandLine = "";
		for(String command: args){
			commandLine = commandLine + " " + command;
		}

		String[] commands = commandLine.split(";");

		for(String command: commands){

			command = command.trim();//trim any whitespace from the commad

			e.executeCommand(command);
		}
	}
}
