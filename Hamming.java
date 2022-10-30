import java.io.*;
import java.util.*;

class Hamming {
    public static void main(String args[]) throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        // -------------------------------------------SENDER'S
        // END--------------------------------------------------

        // Taking message bits from user
        System.out.println("Enter the length of message bits: ");
        int inputLen = Integer.parseInt(br.readLine());
        int input[] = new int[inputLen];
        System.out.println("Enter the message bits: ");
        for (int i = 0; i < inputLen; i++) {
            input[i] = Integer.parseInt(br.readLine());
        }

        // Calculating the number of parity bits required
        int parityCount = 0;
        for (int i = 0; i < inputLen; i++) {
            if (Math.pow(2, parityCount) < inputLen + parityCount + 1) {
                parityCount++;
            } else {
                break;
            }
        }
        System.out.println("Number of reduntant bits required are " + parityCount);

        // Making an array with r1,r2,...
        int inputLen1 = inputLen;
        int cwLen = inputLen + parityCount;
        int cw[] = new int[cwLen];
        for (int i = 0; i < cwLen; i++) {
            if (i == 0 || i == 1 || i == 3 || i == 7 || i == 15 || i == 31 || i == 63 || i == 127 || i == 255 ||
                    i == 511 || i == 1023 || i == 2047) {
                cw[i] = -1;
            } else {
                for (int k = (inputLen1 - 1); k >= 0; k--) {
                    cw[i] = input[k];
                    inputLen1--;
                    break;
                }
            }
        }

        // Calculating r1,r2,r4,....
        int countR = 0;
        for (int i = 0; i < cwLen; i++) {
            if (cw[i] == -1) {
                countR++;
                int count1s = 0;
                for (int k = i; k < cwLen; k++) {
                    int j = k + 1;
                    String binaryJ = Integer.toBinaryString(j);
                    char binaryArray[] = binaryJ.toCharArray();
                    char revBinaryArray[] = new char[binaryArray.length];
                    int d1 = (binaryArray.length - 1);
                    for (int c = 0; c < binaryArray.length; c++) {
                        for (int d = d1; d >= 0; d--) {
                            revBinaryArray[c] = binaryArray[d];
                            d1--;
                            break;
                        }
                    }
                    if ((revBinaryArray[countR - 1]) == '1') {
                        if (cw[k] == 1) {
                            count1s++;
                        }
                    }
                }
                if (count1s % 2 == 0) {
                    System.out.println("Value of R" + countR + " is 0");
                    cw[i] = 0;
                } else {
                    System.out.println("Value of R" + countR + " is 1");
                    cw[i] = 1;
                }
            }
        }

        // Printing final Codeword
        System.out.print("Generated codeword at sender's side is: ");
        for (int i = (cwLen - 1); i >= 0; i--) {
            System.out.print(" " + cw[i]);
        }
        System.out.println();
        System.out.println();

        // ---------------------------------------------RECEIVER'S
        // END---------------------------------------------

        System.out.println("Enter the index number of bit to be changed (-1 if no bit is to be changed): ");
        int bitChanged = Integer.parseInt(br.readLine());
        if (bitChanged == -1) {
            for (int i = (cwLen - 1); i >= 0; i--) {
                System.out.print(" " + cw[i]);
            }
            System.out.println();
            System.out.println("Checking for errors.....");
            System.out.print("The value of r is: 0000");
            System.out.println();
            System.out.println("Therefore, no error is detected in codeword recived by the receiver.");
        } else {
            if (cw[bitChanged - 1] == 0) {
                cw[bitChanged - 1] = 1;
            } else {
                cw[bitChanged - 1] = 0;
            }
            System.out.println("Codeword received by receiver will be: ");
            for (int i = (cwLen - 1); i >= 0; i--) {
                System.out.print(" " + cw[i]);
            }
            System.out.println();
            System.out.println("Checking for errors.....");

            // Calculating r1,r2,r4,.... again
            int countR1 = 0;
            char rArray[] = new char[parityCount];
            for (int i = 0; i < cwLen; i++) {
                if (i == 0 || i == 1 || i == 3 || i == 7 || i == 15 || i == 31 || i == 63 || i == 127 || i == 255 ||
                        i == 511 || i == 1023 || i == 2047) {
                    countR1++;
                    int count1s = 0;
                    for (int k = i; k < cwLen; k++) {
                        int j = k + 1;
                        String binaryJ = Integer.toBinaryString(j);
                        char binaryArray[] = binaryJ.toCharArray();
                        char revBinaryArray[] = new char[binaryArray.length];
                        int d1 = (binaryArray.length - 1);
                        for (int c = 0; c < binaryArray.length; c++) {
                            for (int d = d1; d >= 0; d--) {
                                revBinaryArray[c] = binaryArray[d];
                                d1--;
                                break;
                            }
                        }
                        if ((revBinaryArray[countR1 - 1]) == '1') {
                            if (cw[k] == 1) {
                                count1s++;
                            }
                        }
                    }
                    if (count1s % 2 == 0) {
                        System.out.println("Value of R" + countR1 + " is 0");
                        // cw[i]=0;
                        rArray[countR1 - 1] = '0';
                    } else {
                        System.out.println("Value of R" + countR1 + " is 1");
                        // cw[i]=1;
                        rArray[countR1 - 1] = '1';
                    }
                }
            }
            System.out.print("The value of r is: ");
            for (int i = (parityCount - 1); i >= 0; i--) {
                System.out.print(rArray[i]);
            }
            System.out.println();

            // Converting the binary error index to decimal
            int d1 = rArray.length - 1;
            char revRArray[] = new char[rArray.length];
            for (int c = 0; c < rArray.length; c++) {
                for (int d = d1; d >= 0; d--) {
                    revRArray[c] = rArray[d];
                    d1--;
                    break;
                }
            }
            StringBuilder binaryErrorIndex = new StringBuilder();
            for (int j = 0; j < revRArray.length; j++) {
                binaryErrorIndex.setLength(j + 1);
                binaryErrorIndex.setCharAt(j, revRArray[j]);
            }
            String binaryErrorIndex1 = binaryErrorIndex.toString();
            int decimalErrorIndex = Integer.parseInt(binaryErrorIndex1, 2);
            System.out.println("Error is detected at index " + decimalErrorIndex);

            // Correcting the error
            if (cw[decimalErrorIndex - 1] == 0) {
                cw[decimalErrorIndex - 1] = 1;
            } else {
                cw[decimalErrorIndex - 1] = 0;
            }
            System.out.println("Corrected codeword will be: ");
            for (int i = (cwLen - 1); i >= 0; i--) {
                System.out.print(" " + cw[i]);
            }
            System.out.println();
        }
    }
}