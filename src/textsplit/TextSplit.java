/**
 * TextSplit is a service software for Ukrainian National Information Agency:
 * UKRINFORM 2011
 * version 0.3
 */

package textsplit;

import java.util.ArrayList;

/**
 * Main class
 * @author s.nepochatov
 */
public class TextSplit {
    
    /**
     * Maximum length of message
     */
    public static int maxText = 15980;
    
    /**
     * System dependent line seporator
     */
    public static String lineSeparator = System.getProperty("line.separator");
    
    /**
     * Text separator example string
     */
    public static String textSeparator = lineSeparator + lineSeparator;
    
    /**
     * ArrayList for store splited string
     */
    public static ArrayList stringStack = new ArrayList();
    
    /**
     * 
     */
    public static String[] stackPieces;
    
    /**
     * Input frame variable
     */
    public static InputFrame inputApp = new InputFrame();
    
    /**
     * Output frame variable
     */
    public static OutputFrame outApp = new OutputFrame(inputApp, false);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        inputApp.setVisible(true);
        System.out.println("Начало работы!");
    }
    
    /**
     * Flushing stack to new split
     */
    public static void FlushStack() {
        stringStack = new ArrayList();
        outApp.out.setText("");
        outApp.forwardButton.setEnabled(true);
        outApp.backwardButton.setEnabled(true);
        outApp.stateLabel.setText("ИДЕТ ОБРАБОТКА");
    }
    
    /**
     * Split text method for EKOP messages
     */
    public static void ekopSplit() {
        String input = inputApp.textField.getText();
        System.out.println("Получен текст:" + input.length());
        if (input.length() > maxText) {
            stackPieces = input.split(textSeparator);
            System.out.println("Количество 'кусков': " + stackPieces.length);
            String rstr = "";
            for (int cpiece = 0; cpiece < stackPieces.length; cpiece++) {
                ekopFindHeader(cpiece);
                String cstr = stackPieces[cpiece];
                if (cstr.isEmpty()) {
                    continue;
                }
                System.out.println("'Кусок' номер " + cpiece);
                if (rstr.length() + cstr.length() > maxText) {
                    if (!rstr.isEmpty()) {
                        stringStack.add(rstr);
                        System.out.println("Добавление результирующей строки.");
                        rstr = cstr;
                    }
                    else {
                        stringStack.add(cstr);
                        System.out.println("Добавление текущей строки.");
                    }
                }
                else {
                    if (rstr.equals("")) {
                        rstr = cstr;
                        System.out.println("Присвоение строки.");
                    }
                    else {
                        rstr = rstr + textSeparator + cstr;
                        System.out.println("Сложение строк.");
                    }
                }
                if (cpiece == stackPieces.length - 1) {
                    stringStack.add(rstr);
                    System.out.println("Добавление остаточной строки.");
                }
            }
            if (stringStack.isEmpty()) {
                System.out.println("Стек пуст!");
            }
            else {
                outApp.showStack();
            }
        }
    }
    
    /**
     * Find header in EKOP messages
     * @param currentIndex 
     */
    public static void ekopFindHeader(Integer currentIndex) {
        String currentStr = stackPieces[currentIndex];
        String currentCopy = currentStr.toUpperCase();
        if (currentStr.equals(currentCopy)) {
            stackPieces[currentIndex + 1] = currentStr + textSeparator + stackPieces[currentIndex + 1];
            stackPieces[currentIndex] = "";
        }
    }
}
