/**
 * TextSplit is a service software for Ukrainian National Information Agence:
 * UKRINFORM 2011
 * version 0.2
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
    public static int maxText = 15000;
    
    /**
     * System dependent line seporator
     */
    public static String lineSeparator = System.getProperty("line.separator");
    
    /**
     * Text separator example string
     */
    public static String textSeparator = lineSeparator + lineSeparator + lineSeparator;
    
    /**
     * ArrayList for store splited string
     */
    public static ArrayList stringStack = new ArrayList();
    
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
     * Split text method
     */
    public static void SplitText() {
        String input = inputApp.textField.getText();
        System.out.println("Получен текст:" + input.length());
        if (input.length() > maxText) {
            String[] pieces = input.split("\n\n");
            System.out.println("Количество 'кусков': " + pieces.length);
            String rstr = "";
            for (int cpiece = 0; cpiece < pieces.length; cpiece++) {
                String cstr = pieces[cpiece];
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
                if (cpiece == pieces.length - 1) {
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
}
