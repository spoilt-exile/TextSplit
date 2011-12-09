/**
 * TextSplit is a service software for Ukrainian National Information Agency:
 * UKRINFORM 2011
 * version v0.4r1
 */

package textsplit;

import java.util.ArrayList;

/**
 * Main class
 * @author Stanislav Nepochatov
 */
public class TextSplit {
    
    /**
     * Maximum length of message
     */
    private static int maxLength = 15980;
    
    /**
     * System dependent line seporator
     */
    private static String lineSeparator = System.getProperty("line.separator");
    
    /**
     * Text separator example string
     */
    private static String textSeparator;
    
    /**
     * EKOP service header
     */
    private static String EKOPServiceHeader;
    
    /**
     * ArrayList for store splited string
     */
    public static ArrayList stringStack = new ArrayList();
    
    /**
     * Draft separation array
     */
    private static String[] stackPieces;
    
    /**
     * Input frame variable
     */
    private static InputFrame inputApp = new InputFrame();
    
    /**
     * Output frame variable
     */
    public static OutputFrame outApp;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Making main window visible
        inputApp.setVisible(true);
        
        //Initializate output window
        outApp = new OutputFrame(inputApp, false);

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
     * @param withHeader append EKOP service header if equals true;
     */
    public static void ekopSplit(Boolean withHeader) {
        
        //Assign text separator
        textSeparator = lineSeparator + lineSeparator;
        
        //Get text from input windoiw
        String input = inputApp.textField.getText();
        System.out.println("Получен текст:" + input.length());
        
        //Assign local max length variable
        int localMaxLength;
        if (withHeader) {
            localMaxLength = 15300;
        }
        else {
            localMaxLength = maxLength;
        }
        
        //Begin main execution only if text larger then allowed limit
        if (input.length() > localMaxLength) {
            
            //Create EKOP service header
            if (withHeader) {
                ekopCreateHeader(input);
            }
            
            //Draft text separation by using textSeparator expression
            stackPieces = input.split(textSeparator);
            System.out.println("Количество 'кусков': " + stackPieces.length);
            
            //Result string declaration
            String rstr = "";
            
            //Main cycle
            for (int cpiece = 0; cpiece < stackPieces.length; cpiece++) {
                
                if (cpiece < stackPieces.length - 1) {
                    //Call to ekopFindHeader method
                    ekopFindHeader(cpiece);
                }

                //Extract string from draft separation array
                String cstr = stackPieces[cpiece];
                
                //Forcely end this loop if current string moved by ekopFindHeader method
                if (cstr.isEmpty()) {
                    continue;
                }
                System.out.println("'Кусок' номер " + cpiece);

                //Checking if result string and current string larger than limit
                if (rstr.length() + cstr.length() > localMaxLength) {
                    
                    //If result string is not empty then it will be added in the stack
                    if (!rstr.isEmpty()) {
                        ekopAddToStack(rstr);
                        System.out.println("Добавление результирующей строки.");
                        
                        //Assignment result string to current (make sense in next loop)
                        rstr = cstr;
                    }
                    
                    //Else current string will be added in the stack
                    else {
                        ekopAddToStack(cstr);
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
                    ekopAddToStack(rstr);
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
     * @param currentIndex current position in pieces array;
     */
    private static void ekopFindHeader(Integer currentIndex) {
        String currentStr = stackPieces[currentIndex];
        if (currentStr.equals(currentStr.toUpperCase())) {
            stackPieces[currentIndex + 1] = currentStr + textSeparator + stackPieces[currentIndex + 1];
            stackPieces[currentIndex] = "";
        }
    }
    
    /**
     * Create automatic header for EKOP messages
     */
    private static void ekopCreateHeader(String inputStr) {
        String ekopExample = "В ВЫПУСКЕ:";
        EKOPServiceHeader = inputStr.split(ekopExample)[0] + ekopExample + textSeparator;
    }
    
    /**
     * Add string to stack with appending EKOP service header
     * @param inputStr 
     */
    private static void ekopAddToStack(String inputStr) {
        if (stringStack.isEmpty()) {
            stringStack.add(inputStr);
        }
        else {
            stringStack.add(EKOPServiceHeader + inputStr);
        }
    }
    
    /**
     * Split text by lines
     */
    public static void lineSplit() {
        
        //Assign text separator
        textSeparator = lineSeparator;
        
        //Get text from input windoiw
        String input = inputApp.textField.getText();
        System.out.println("Получен текст:" + input.length());
        
        //Assign local max length variable
        int localMaxLength = maxLength;
        
        //Begin main execution only if text larger then allowed limit
        if (input.length() > localMaxLength) {
            
            //Draft text separation by using textSeparator expression
            stackPieces = input.split(textSeparator);
            System.out.println("Количество 'кусков': " + stackPieces.length);
            
            //Result string declaration
            String rstr = "";
            
            //Main cycle
            for (int cpiece = 0; cpiece < stackPieces.length; cpiece++) {

                //Extract string from draft separation array
                String cstr = stackPieces[cpiece];
                System.out.println("'Кусок' номер " + cpiece);
                
                //Checking if result string and current string larger than limit
                if (rstr.length() + cstr.length() > localMaxLength) {
                    
                    //If result string is not empty then it will be added in the stack
                    if (!rstr.isEmpty()) {
                        stringStack.add(rstr);
                        System.out.println("Добавление результирующей строки.");
                        
                        //Assignment result string to current (make sense in next loop)
                        rstr = cstr;
                    }
                    
                    //Else current string will be added in the stack
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
}
