
public class Main {
    public static void main(String[] args){
        // initialize calculator instance
        Calculator calculator = new Calculator();

        // run help command first so users know what to do
        calculator.HandleCommand("help");

        // counts our errors
        int errcount = 0;

        // runtime loop
        while(calculator.running){

            // this makes sure we have a way to break out of the while loop
            if(errcount > 10){
                System.out.println("getting a ton of errors here breaking out of while loop");
                break;
            }


            // run singular iteration
            try {
                calculator.Loop();
            } catch(Exception err){
                System.out.println("Got Error when trying to run single iteration: " + err.toString());
                errcount ++;
            }
        }
    }
}