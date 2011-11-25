/**
 * TextSplit is a service software for Ukrainian National Information Agency:
 * UKRINFORM 2011
 * version 0.3r1
 * 
 * TODO: make new method for Case indepent header search.
 * TODO: make service header in EKOP separation.
 * TODO: create other split modes.
 * TODO: clipboard integretion (IMPORTANT!).
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
        
        //Making main window visible
        inputApp.setVisible(true);
        
        //Debug output
        System.out.println("Начало работы!");
    }
    
    /**
     * Flushing stack to new text spliting
     */
    public static void FlushStack() {
        System.out.println("Отчистка стека!");
        
        //Flushing text stack
        stringStack = new ArrayList();
        
        //Switch output window to normal state
        outApp.out.setText("");
        outApp.forwardButton.setEnabled(true);
        outApp.backwardButton.setEnabled(true);
        outApp.stateLabel.setText("ИДЕТ ОБРАБОТКА");
    }
    
    /**
     * Split text method for EKOP messages
     */
    public static void ekopSplit() {
        
        //Get text from input windoiw
        String input = inputApp.textField.getText();
        System.out.println("Получен текст:" + input.length());
        
        //Begin main execution only if text larger then allowed limit
        if (input.length() > maxText) {
            
            //Draft text separation by using textSeparator expression
            stackPieces = input.split(textSeparator);
            System.out.println("Количество 'кусков': " + stackPieces.length);
            
            //Result string declaration
            String rstr = "";
            
            //Main cycle
            for (int cpiece = 0; cpiece < stackPieces.length; cpiece++) {
                
                //Call to ekopFindHeader method
                ekopFindHeader(cpiece);
                
                //Extract string from draft separation array
                String cstr = stackPieces[cpiece];
                
                //Forcely end this loop if current string moved by ekopFindHeader method
                if (cstr.isEmpty()) {
                    continue;
                }
                System.out.println("'Кусок' номер " + cpiece);
                
                //Checking if result string and current string larger than limit
                if (rstr.length() + cstr.length() > maxText) {
                    
                    //If result string is not empty then it will be added in the stack
                    if (!rstr.isEmpty()) {
                        stringStack.add(rstr);
                        System.out.println("Добавление результирующей строки.");
                        
                        //Assignment result string to current (make sense in next loop)
                        rstr = cstr;
                    }
                    
                    //Else current string will be added in the stack
                    //TODO: add checking procedure.
                    else {
                        stringStack.add(cstr);
                        System.out.println("Добавление текущей строки.");
                    }
                }
                
                //Result string smaller than limit
                else {
                    
                    //Result string is empty
                    if (rstr.equals("")) {
                        
                        //Assignment result string to current (make sense in next loop)
                        rstr = cstr;
                        System.out.println("Присвоение строки.");
                    }
                    else {
                        
                        //Append current string with textSeparator to result stirng
                        rstr = rstr + textSeparator + cstr;
                        System.out.println("Сложение строк.");
                    }
                }
                
                //Last piece will be added automaticly
                if (cpiece == stackPieces.length - 1) {
                    stringStack.add(rstr);
                    System.out.println("Добавление остаточной строки.");
                }
            }
            
            //Cheking stack
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
        if (currentStr.equals(currentStr.toUpperCase())) {
            stackPieces[currentIndex + 1] = currentStr + textSeparator + stackPieces[currentIndex + 1];
            stackPieces[currentIndex] = "";
        }
    }
}
