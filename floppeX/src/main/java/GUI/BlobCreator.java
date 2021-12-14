package GUI;

public class BlobCreator {
	    public static byte[] generateRandomBlob() {
	        return generateRandomBlob(65536);
	    }

	    public static byte[] generateRandomBlob(int size) {
	        byte[] chunk = new byte[size];
	        for (int k = 0; k < chunk.length; k++) {
	            chunk[k] = (byte) (Math.random() * 255);
	        }
	        return chunk;
	    }
}
