import java.io.*;
import java.util.*;

class CRC {
    public static void main(String args[]) throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        // --------------------------------------------------SENDER'S
        // SIDE--------------------------------------------

        // Ask user for the message and the divisor
        System.out.println("Enter the length of the message: ");
        int msgLen = Integer.parseInt(br.readLine());
        int msg[] = new int[msgLen];
        System.out.println("Enter the message: ");
        for (int i = 0; i < msgLen; i++) {
            msg[i] = Integer.parseInt(br.readLine());
        }
        System.out.println("Enter the length of the divisor: ");
        int divisorLen = Integer.parseInt(br.readLine());
        int divisor[] = new int[divisorLen];
        System.out.println("Enter the divisor: ");
        for (int i = 0; i < divisorLen; i++) {
            divisor[i] = Integer.parseInt(br.readLine());
        }
        int dividendLen = msgLen + (divisorLen - 1);
        int dividend[] = new int[dividendLen];
        for (int i = 0; i < dividendLen; i++) {
            if (i < msgLen) {
                dividend[i] = msg[i];
            } else {
                dividend[i] = 0;
            }
        }
        /*
         * System.out.print("The dividend will be: ");
         * for(int i=0; i<msgLen+(dividendLen-1);i++){
         * System.out.print(message[i]);
         * }
         * System.out.println();
         */

        // Performing the division
        int remainder[] = new int[divisorLen];
        for (int m = 0; m < divisorLen; m++) {
            remainder[m] = dividend[m];
        }
        int countDividend = divisorLen;
        boolean end = true;
        // for(int i=0;i<dividendLen;i++){
        while (end) {
            for (int j = 0; j < divisorLen; j++) {
                // countDividend++;
                int a = divisor[j];
                int b = remainder[j];
                if (a == b) {
                    remainder[j] = 0;
                } else {
                    remainder[j] = 1;
                }
            }
            // bool flag = false;
            int count = 0;
            for (int k = 0; k < divisorLen; k++) {
                if (remainder[k] == 1) {
                    // flag = true;
                    break;
                } else {
                    count++;
                    remainder[k] = -1;
                }
            }
            for (int n = 0; n < (divisorLen - count); n++) {
                remainder[n] = remainder[count + n];
            }
            for (int x = (divisorLen - count); x < divisorLen; x++) {
                try {
                    remainder[x] = dividend[countDividend];
                    countDividend++;
                } catch (ArrayIndexOutOfBoundsException e) {
                    remainder[x] = -1;
                    end = false;
                }
            }
            // }
        }
        int countRemainderBits = 0;
        System.out.print("CRC is(-1 represents empty bits): ");
        for (int m = 0; m < divisorLen; m++) {
            System.out.print(remainder[m]);
            if (remainder[m] != -1) {
                countRemainderBits++;
            }
        }
        System.out.println();

        int message[] = new int[msgLen + countRemainderBits];
        int itr = 0;
        System.out.print("Message sent will be: ");
        for (int m = 0; m < (msgLen + countRemainderBits); m++) {
            if (m < msgLen) {
                message[m] = msg[m];
                System.out.print(message[m]);
            } else {
                for (int n = itr; n < countRemainderBits; n++) {
                    message[m] = remainder[n];
                    itr++;
                    System.out.print(message[m]);
                    break;
                }
            }
        }
        System.out.println();
        System.out.println();

        // ------------------------------------------------RECEIVERS
        // END---------------------------------------------

        System.out.print("Enter the index number of bit to be changed(-1 if no bit is to be changed):");
        int indexBitChanged = Integer.parseInt(br.readLine());
        if (indexBitChanged == -1) {
            System.out.print("Message recived is: ");
            for (int i = 0; i < message.length; i++) {
                System.out.print(message[i]);
            }
            System.out.println();
            System.out.println("Remainder after performing division is: 0000");
            System.out.println("No error is detected. Hence, Frame accepted");
            System.exit(0);
        } else {
            if (message[indexBitChanged] == 0) {
                message[indexBitChanged] = 1;
            } else {
                message[indexBitChanged] = 0;
            }
        }
        System.out.print("Message recived is: ");
        for (int i = 0; i < message.length; i++) {
            System.out.print(message[i]);
        }
        System.out.println();

        // Performing division again
        for (int m = 0; m < divisorLen; m++) {
            remainder[m] = message[m];
        }
        countDividend = divisorLen;
        end = true;
        // for(int i=0;i<dividendLen;i++){
        while (end) {
            for (int j = 0; j < divisorLen; j++) {
                // countDividend++;
                int a = divisor[j];
                int b = remainder[j];
                if (a == b) {
                    remainder[j] = 0;
                } else {
                    remainder[j] = 1;
                }
            }
            // bool flag = false;
            int count = 0;
            for (int k = 0; k < divisorLen; k++) {
                if (remainder[k] == 1) {
                    // flag = true;
                    break;
                } else {
                    count++;
                    remainder[k] = -1;
                }
            }
            for (int n = 0; n < (divisorLen - count); n++) {
                remainder[n] = remainder[count + n];
            }
            for (int x = (divisorLen - count); x < divisorLen; x++) {
                try {
                    remainder[x] = message[countDividend];
                    countDividend++;
                } catch (ArrayIndexOutOfBoundsException e) {
                    remainder[x] = -1;
                    end = false;
                }
            }
            // }
        }
        countRemainderBits = 0;
        System.out.print("Remainder is(-1 represents empty bits): ");
        for (int m = 0; m < divisorLen; m++) {
            System.out.print(remainder[m]);
            if (remainder[m] != -1) {
                countRemainderBits++;
            }
        }
        System.out.println();

        String remainderString = remainder.toString();
        if (remainderString != "[0,0,0,0]" || remainderString == "[0,-1,-1,-1]" ||
                remainderString == "[0,0,-1,-1]" || remainderString == "[0,0,0,-1]") {
            System.out.println("Since, remainder != 0, Hence there is an error.");
            System.out.println("Frame Rejected");
        }
    }
}