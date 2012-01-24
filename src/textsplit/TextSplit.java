/**
 * TextSplit is a service software for National News Agency of Ukraine:
 * UKRINFORM 2011
 * version v0.6 alpha2
 * TODO: add case independent EKOP header search method;
 */

package textsplit;

import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Main class
 * @author Stanislav Nepochatov
 */
public class TextSplit {
    
    /**
     * System locale
     */
    private static Locale sysLocale = Locale.getDefault();
    
    /**
     * Localization handle object
     */
    public static ResourceBundle localizator;
    
    /**
     * Maximum length of message.
     * Changed to 13000 in order to fix issue with applying messages
     */
    private static int maxLength = 13000;
    
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
    private static InputFrame inputApp;
    
    /**
     * Output frame variable
     */
    public static OutputFrame outApp;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Setting localization
        localizator = ResourceBundle.getBundle("textsplit.LocBundle", sysLocale);
        
        //Making main window visible
        inputApp = new InputFrame();
        inputApp.setVisible(true);
        
        //Initializate output window
        outApp = new OutputFrame(inputApp, false);

        //Debug output
        System.out.println(localizator.getString("log_hello"));
    }
    
    /**
     * Flushing stack to new text spliting
     */
    public static void FlushStack() {
        System.out.println(localizator.getString("log_flushing_stack"));
        
        //Flushing text stack
        stringStack = new ArrayList();
        
        //Switch output window to normal state
        outApp.out.setText("");
        outApp.forwardButton.setEnabled(true);
        outApp.backwardButton.setEnabled(true);
        outApp.stateLabel.setText(localizator.getString("out_gui_busy_window_title"));
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
        System.out.println(localizator.getString("log_generic_recv") + input.length());
        
        //Assign local max length variable
        int localMaxLength;
        if (withHeader) {
            localMaxLength = 12900; //Fix problem with too large messages
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
            System.out.println(localizator.getString("log_generic_pieces_count") + " " + stackPieces.length);
            
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
                System.out.println(localizator.getString("log_generic_current_piece") + "" + cpiece);

                //Checking if result string and current string larger than limit
                if (rstr.length() + cstr.length() > localMaxLength) {
                    
                    //If result string is not empty then it will be added in the stack
                    if (!rstr.isEmpty()) {
                        ekopAddToStack(rstr, withHeader);
                        System.out.println(localizator.getString("log_generic_add_res"));
                        
                        //Assignment result string to current (make sense in next loop)
                        rstr = cstr;
                    }
                    
                    //Else current string will be added in the stack
                    else {
                        ekopAddToStack(cstr, withHeader);
                        System.out.println(localizator.getString("log_generic_add_curr"));
                    }
                }
                
                //Result string smaller than limit
                else {
                    
                    //Result string is empty
                    if (rstr.equals("")) {
                        
                        //Assignment result string to current (make sense in next loop)
                        rstr = cstr;
                        System.out.println(localizator.getString("log_generic_str_assign"));
                    }
                    else {
                        
                        //Append current string with textSeparator to result stirng
                        rstr = rstr + textSeparator + cstr;
                        System.out.println(localizator.getString("log_generic_str_append"));
                    }
                }
                
                //Last piece will be added automaticly
                if (cpiece == stackPieces.length - 1) {
                    ekopAddToStack(rstr, withHeader);
                    System.out.println(localizator.getString("log_generic_add_rest"));
                }
            }
            
            //Cheking stack
            if (stringStack.isEmpty()) {
                System.out.println(localizator.getString("log_generic_empty_stack"));
            }
            else {
                outApp.showStack();
            }
        }
        else {
            TextSplit.warningMessage(localizator.getString("warning_not_limit1") + "\n" + localizator.getString("warning_count") + input.length() + ";");
            System.out.println(localizator.getString("log_cancel"));
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
     * @param inputStr message string;
     */
    private static void ekopCreateHeader(String inputStr) {
        String ekopExample = "В ВЫПУСКЕ:";
        //TODO: add handling none EKOP headers;
        EKOPServiceHeader = inputStr.split(ekopExample)[0] + ekopExample + textSeparator;
        System.out.print(localizator.getString("log_generic_empty_stack"));
    }
    
    /**
     * Add string to stack with appending EKOP service header
     * @param inputStr string which will be appended to stack;
     * @param withHeader append EKOP service header if equals true;
     */
    private static void ekopAddToStack(String inputStr, Boolean withHeader) {
        if (stringStack.isEmpty() || withHeader == false) {
            System.out.print(localizator.getString("log_ekop_add_generic_str"));
            stringStack.add(inputStr);
        }
        else {
            System.out.print(localizator.getString("log_ekop_add_header_str"));
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
        System.out.println(localizator.getString("log_generic_recv") + input.length());
        
        //Begin main execution only if text larger then allowed limit
        if (input.length() > maxLength) {
            
            //Draft text separation by using textSeparator expression
            stackPieces = input.split(textSeparator);
            System.out.println(localizator.getString("log_generic_pieces_count") + " " + stackPieces.length);
            
            //Result string declaration
            String rstr = "";
            
            //Main cycle
            for (int cpiece = 0; cpiece < stackPieces.length; cpiece++) {

                //Extract string from draft separation array
                String cstr = stackPieces[cpiece];
                System.out.println(localizator.getString("log_generic_current_piece") + "" + cpiece);
                
                //Checking if result string and current string larger than limit
                if (rstr.length() + cstr.length() > maxLength) {
                    
                    //If result string is not empty then it will be added in the stack
                    if (!rstr.isEmpty()) {
                        stringStack.add(rstr);
                        System.out.println(localizator.getString("log_generic_add_res"));
                        
                        //Assignment result string to current (make sense in next loop)
                        rstr = cstr;
                    }
                    
                    //Else current string will be added in the stack
                    else {
                        stringStack.add(cstr);
                        System.out.println(localizator.getString("log_generic_add_curr"));
                    }
                }
                
                //Result string smaller than limit
                else {
                    
                    //Result string is empty
                    if (rstr.equals("")) {
                        
                        //Assignment result string to current (make sense in next loop)
                        rstr = cstr;
                        System.out.println(localizator.getString("log_generic_str_assign"));
                    }
                    else {
                        
                        //Append current string with textSeparator to result stirng
                        rstr = rstr + textSeparator + cstr;
                        System.out.println(localizator.getString("log_generic_str_append"));
                    }
                }
                
                //Last piece will be added automaticly
                if (cpiece == stackPieces.length - 1) {
                    stringStack.add(rstr);
                    System.out.println(localizator.getString("log_generic_add_rest"));
                }
            }
            
            //Cheking stack
            if (stringStack.isEmpty()) {
                System.out.println(localizator.getString("log_generic_empty_stack"));
            }
            else {
                outApp.showStack();
            }
        }
        else {
            TextSplit.warningMessage(localizator.getString("warning_not_limit1") + "\n" + localizator.getString("warning_count") + input.length() + ";");
            System.out.println(localizator.getString("log_cancel"));
        }
    }
    
    /**
     * Show graphical warning message
     * @param Message text of warning message
     */
    public static void warningMessage (String Message) {
        final JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, Message, localizator.getString("warning"), JOptionPane.WARNING_MESSAGE);
    }
}
