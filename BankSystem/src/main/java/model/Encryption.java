package model;

public class Encryption {
    public String encrypt(String string) {
		char[] stringArray = string.toCharArray();
		int length = stringArray.length;

		for(int i=0; i<length; i++) {
		    int value = (int)(string.charAt(i)); //get the ASCII value
		    value = value-i-length; //apply the encryption formula to the value
		    stringArray[i] = (char)value;; //assign the new value to stringArray
		}
		return String.valueOf(stringArray);
    }

    public String decrypt(String string) {
		char[] stringArray = string.toCharArray();
		int length = stringArray.length;

		for(int i=0; i<length; i++) {
		    int value = (int)(string.charAt(i)); //get the ASCII value
		    value = value+i+length; //apply the decryption formula to the value
		    stringArray[i] = (char)value;; //assign the new value to stringArray
		}
		return String.valueOf(stringArray);
    }
}