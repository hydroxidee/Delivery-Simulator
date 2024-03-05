package Food_Delivery_Simulator;

@SuppressWarnings("serial")
public class Exceptions {
	//throws exception for when the format is incorrect
	static class incorrectFormatException extends Exception {
		public incorrectFormatException(String message) {
			super(message);
		}
	}
	
	//throws exception for when data is missing
	static class missingDataException extends Exception {
		public missingDataException(String message) {
			super(message);
		}
	}
	
	//throws exception for when latitude or longitude are out of range
	static class outOfBoundsException extends Exception {
		public outOfBoundsException(String message) {
			super(message);
		}
	}
}
