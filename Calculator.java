import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Scanner;

public class Calculator {

    // regex to check if string is a numeric value
    private static String RegexCheckStringNumeric = "(-?\\d+(\\.\\d+)?)|(-?\\.\\d+)";

    // generalize the commands as a enum instead of a per string basis
    private enum CommandType {
        Exit,
        Clear,
        Help,
        Add,
        Sub,
        Mult,
        Div,
        Pow,
        Round,
        Sqrt,
        Set,
        Sin,
        Cos,
        Tan,
        Unknown
    };
    
    // get commandtype as string
    private String GetCommandTypeAsString(CommandType type){
        switch (type) {
            case Exit: return "Exit";
            case Clear: return "Clear";
            case Help: return "Help";
            case Add: return "Add";
            case Sub: return "Sub";
            case Mult: return "Mult";
            case Div: return "Div";
            case Pow: return "Pow";
            case Round: return "Round";
            case Sqrt: return "Sqrt";
            case Set: return "Set";
            case Sin: return "Sin";
            case Cos: return "Cos";
            case Tan: return "Tan";
            case Unknown: return "Unknown";
            default: return "Unexpected";
        }
    }

    // gets the commandtype that's being attempted to be invoked
    private CommandType GetCommandTypeFromString(String text){
        switch(text.toLowerCase()){
            case "exit":
            case "quit":
                return CommandType.Exit;
            case "clear":
            case "cls":
                return CommandType.Clear;
            case "help":
            case "h":
            case "?":
                return CommandType.Help;
            case "+":
            case "add":
                return CommandType.Add;
            case "-":
            case "sub":
                return CommandType.Sub;
            case "*":
            case "mult":
                return CommandType.Mult;
            case "/":
            case "\\":
            case "div":
                return CommandType.Div;
            case "pow":
            case "exp":
            case "^":
                return CommandType.Pow;
            case "round":
                return CommandType.Round;
            case "sin":
                return CommandType.Sin;
            case "cos":
                return CommandType.Cos;
            case "tan":
                return CommandType.Tan;
            case "set":
                return CommandType.Set;
            case "sqrt":
                return CommandType.Sqrt;
            default:
                return CommandType.Unknown;
        }
    }

    // gets the required arguments
    public int CommandTypeArgRequirement(CommandType type){
        switch (type) {
            case Add:
            case Sub:
            case Mult:
            case Div:
            case Pow:
            case Set:
                return 1;
            case Sqrt:
            case Sin:
            case Cos:
            case Tan:
            case Round:
            case Exit:
            case Clear:
            default: return 0;
        }
    }


    // stores the current value
    public BigDecimal CurrentValue;

    // scanner to read input from console
    private Scanner scanner;

    // a boolean value to check if we need to stop running
    public boolean running = false;

    // initializes our variables
    public Calculator(){
        scanner = new Scanner(System.in);
        CurrentValue = new BigDecimal(0);
        running = true;
    }

    // gets input from the console
    private String GetInputString(){
        if(!running){
            System.out.println("cannot get input when calculator isn't running");
            return "";
        }

        System.out.print("> ");
        String line;
        
        try {
            line = scanner.nextLine();
        } catch(IllegalStateException err){
            running = false;
            return "";
        }
        return line;
    }

    // checks if a string is a numeric value using our regex
    private boolean VerifyStringNumeric(String text){
        return text.matches(RegexCheckStringNumeric);
    }

    // clears the console
    public static void ConsoleClear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // draws current value to screen
    public void Draw(){
        // ConsoleClear();
        System.out.println("value: " + CurrentValue.toPlainString());
    }

    // handles the add command
    private void HandleAdd(String valueString){
        if(!VerifyStringNumeric(valueString)){
            System.out.println("Argument passed is not a valud numeric value");
            return;
        }

        BigDecimal number = new BigDecimal(valueString);
        CurrentValue = CurrentValue.add(number, MathContext.UNLIMITED);
    }

    // handles the sub command
    private void HandleSub(String valueString){
        if(!VerifyStringNumeric(valueString)){
            System.out.println("Argument passed is not a valud numeric value");
            return;
        }

        BigDecimal number = new BigDecimal(valueString);
        CurrentValue = CurrentValue.subtract(number, MathContext.UNLIMITED);
    }

    // handles the mult command
    private void HandleMult(String valueString){
        if(!VerifyStringNumeric(valueString)){
            System.out.println("Argument passed is not a valud numeric value");
            return;
        }

        BigDecimal number = new BigDecimal(valueString);
        CurrentValue = CurrentValue.multiply(number, MathContext.UNLIMITED);
    }

    // handles the div command
    private void HandleDiv(String valueString){
        if(!VerifyStringNumeric(valueString)){
            System.out.println("Argument passed is not a valud numeric value");
            return;
        }

        BigDecimal number = new BigDecimal(valueString);
        CurrentValue = CurrentValue.divide(number, MathContext.UNLIMITED);
    }

    // handles the pow command
    private void HandlePow(String valueString){
        if(!VerifyStringNumeric(valueString)){
            System.out.println("Argument passed is not a valud numeric value");
            return;
        }

        BigDecimal number = new BigDecimal(valueString);
        CurrentValue = CurrentValue.pow(number.intValue(), MathContext.UNLIMITED);
    }

    // handles the sin command
    private void HandleSin(){
        double number = CurrentValue.doubleValue();
        CurrentValue = new BigDecimal(Math.sin(number));
    }

    // handles the cos command
    private void HandleCos(){
        double number = CurrentValue.doubleValue();
        CurrentValue = new BigDecimal(Math.cos(number));
    }

    // handles the tan command
    private void HandleTan(){
        double number = CurrentValue.doubleValue();
        CurrentValue = new BigDecimal(Math.tan(number));
    }

    // handles the set command
    private void HandleSet(String valueString){
        CurrentValue = new BigDecimal(valueString);
    }

    // handles the sqrt command
    private void HandleSqrt(){
        CurrentValue = CurrentValue.sqrt(MathContext.UNLIMITED);
    }

    // handles the round command
    private void HandleRound(String[] args){
        if(args.length == 1){
            CurrentValue = new BigDecimal(Math.round(CurrentValue.doubleValue()));
        } else {
            if(!VerifyStringNumeric(args[1])){
                System.out.println("Argument passed is not a valud numeric value");
                return;
            }

            BigDecimal number = new BigDecimal(args[1]);
            CurrentValue = CurrentValue.round(new MathContext(number.intValue()));
        }
    }

    // prints help msg
    private void HandleHelp(){
        ConsoleClear();
        System.out.print(
        "## Commands\n" + 
        "- Exit (exit, quit): Exits the program.\n" + 
        "- Clear (clear, cls): Clears the screen or console.\n" + 
        "- Help (help): Displays a list of available commands with descriptions.\n" + 
        "- Add (add, +): Adds the given number to the current value.\n" + 
        "- Sub (sub, -): Subtracts the given number from the current value.\n" + 
        "- Mult (mult, *): Multiplies the current value by the given number.\n" + 
        "- Div (div, /, \\): Divides the current value by the given number. Handles division by zero.\n" + 
        "- Pow (pow, exp, ^): Takes in 1 argument, raises the current value to the power of the input value (CurrentValue ^ <input>).\n" + 
        "- Round (round): Rounds the current value to the specified number of decimal places or 0.\n" + 
        "- Sqrt (sqrt): Calculates the square root of the current value.\n" + 
        "- Set (set): Sets the current value to the input number.\n" + 
        "- Sin (sin): Calculates the sine of the current value.\n" + 
        "- Cos (cos): Calculates the cosine of the current value.\n" + 
        "- Tan (tan): Calculates the tangent of the current value.\nPress enter to continue . . .");
        scanner.nextLine();
        ConsoleClear();
    }

    // parses input to provide arguments to handle
    private String[] GetCommandArgs(String commandString){
        String[] outArgs;
        if(!commandString.contains(" ")){
            outArgs = new String[1];
            outArgs[0] = commandString;
        } else {
            outArgs = commandString.split("[ \\t]+");
        }
        return outArgs;
    }

    // tells the loop to stop running
    private void HandleExit(){
        running = false;
    }

    // use this to invoke a command based on the command type
    private void CallCommand(CommandType type, String[] args){
        switch (type) {
            case Exit:
                HandleExit();
                break;
            case Clear:
                ConsoleClear();
                break;
            case Help:
                HandleHelp();
                break;
            case Add:
                HandleAdd(args[1]);
                break;
            case Sub:
                HandleSub(args[1]);
                break;
            case Mult:
                HandleMult(args[1]);
                break;
            case Div:
                HandleDiv(args[1]);
                break;
            case Pow:
                HandlePow(args[1]);
                break;
            case Sqrt:
                HandleSqrt();
                break;
            case Set:
                HandleSet(args[1]);
                break;
            case Sin:
                HandleSin();
                break;
            case Cos:
                HandleCos();
                break;
            case Tan:
                HandleTan();
                break;
            case Round:
                HandleRound(args);
                break;
            default:
                System.out.println("unknown command: " + args[0]);
                System.out.println("got args: " + Arrays.toString(args));
                break;
        }
    }

    // handles input asssuming we've provided a command string
    public void HandleCommand(String inputString){
        String[] args = GetCommandArgs(inputString);
        int argc = args.length;

        if(argc == 0){
            System.out.println("No commands given");
            return;
        }

        CommandType type = GetCommandTypeFromString(args[0]);
        int requiredArgumentCount = CommandTypeArgRequirement(type);

        // checks that the arguments given meets the requirements
        if((argc - 1) < requiredArgumentCount){
            System.out.println("failed to provide the neccisary amount of arguments for command: " + GetCommandTypeAsString(type) + "\nrequired arguments: " + Integer.toString(requiredArgumentCount));
            return;
        }

        CallCommand(type, args);
    }


    // call this function to handle 1 loop iteration
    public void Loop(){
        // check if calculator is running
        if(!running){
            return; // prevent loop from running if running == false
        }
        // draw our current value
        Draw();
        String inputString = GetInputString().toLowerCase();

        // check if there's actually any real input here
        if(inputString.length() == 0){
            System.out.println("no input given");
            return;
        }

        // command handler
        HandleCommand(inputString);
        // removes any extra zeros
        CurrentValue = CurrentValue.stripTrailingZeros();
    }
}