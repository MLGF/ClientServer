//SER210
//Jacob Bjornberg
//4/24/15

// Fig. 27.6: ServerTest.javaS
// Test the Server application.
import javax.swing.JFrame;

public class ServerTest {
	public static void main(String[] args) {
		Server application = new Server();

		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.runServer();
	}
}