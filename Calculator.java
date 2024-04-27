
/* LIBRARIES */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator implements ActionListener {

    JFrame frame;

    JPanel panel;

    /* Scientific buttons */

    JButton sqrt,
            factorial,
            inverse,
            power,
            square,
            cube,
            percentage,
            sin,
            cos,
            tan,
            log,
            invert;

    /* Standard calculator buttons */

    JButton addButton,
            subButton,
            mulButton,
            divButton,
            decButton,
            equButton,
            clrButton,
            delButton;

    /* list of buttons for numbers */

    JButton[] numberButtons = new JButton[10];

    /* Array which will hold the layout */

    JButton[] layout;

    /* textField and JLabel to display numbers and operations */

    JTextField textField;
    JLabel upperText;

    /* GUI START */

    public Calculator() {

        frame = new JFrame("Calculator");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);

        frame.setSize(700,
                1000);

        frame.setLayout(null);

        // DECLARING BUTTONS

        sqrt = new JButton("√x");

        factorial = new JButton("n!");

        inverse = new JButton("1/x");

        power = new JButton("X^Y");

        square = new JButton("X^2");

        cube = new JButton("X^3");

        percentage = new JButton("%");

        sin = new JButton("Sin");

        cos = new JButton("Cos");

        tan = new JButton("Tan");

        log = new JButton("Log");

        invert = new JButton("+/-");

        addButton = new JButton("+");

        subButton = new JButton("-");

        mulButton = new JButton("*");

        divButton = new JButton("/");

        decButton = new JButton(".");

        equButton = new JButton("=");

        delButton = new JButton("DEL");

        clrButton = new JButton("C");

        for (int i = 0; i < 10; i++) {

            numberButtons[i] = new JButton(String.valueOf(i));

        }

        /* ADDING BUTTONS TO LAYOUT */

        layout = new JButton[] {

                sqrt,
                factorial,
                inverse,
                clrButton,
                delButton,

                power,
                cube,
                square,
                percentage,
                divButton,

                sin,
                numberButtons[7],
                numberButtons[8],
                numberButtons[9],
                mulButton,

                cos,
                numberButtons[4],
                numberButtons[5],
                numberButtons[6],
                subButton,

                tan,
                numberButtons[1],
                numberButtons[2],
                numberButtons[3],
                addButton,

                log,
                invert,
                numberButtons[0],
                decButton,
                equButton };

        /* DECLARING TEXTFIELDS */

        textField = new JTextField("0");

        textField.setBounds(20,
                50,
                640,
                120);

        textField.setFont(new Font("Arial",
                Font.PLAIN,
                60));

        textField.setFocusable(false);

        upperText = new JLabel("");

        upperText.setBounds(20,
                20,
                640,
                20);

        upperText.setFont(new Font("Arial",
                Font.PLAIN,
                20));

        /* INITIALISING PANEL LAYOUT & SIZE */

        panel = new JPanel();

        panel.setBounds(10,
                210,
                660,
                745);

        panel.setLayout(new GridLayout(6,
                5,
                5,
                5));

        /* LOOP BUTTONS ONTO PANEL, ADD ACTION LISTENERS AND SET FONT & SIZE */

        for (int i = 0; i < layout.length; i++) {

            layout[i].addActionListener(this);

            layout[i].setFont(new Font("Arial", Font.BOLD, 45));

            panel.add(layout[i]);

        }

        frame.add(textField);

        frame.add(upperText);

        frame.add(panel);

        frame.setVisible(true);

    }

    /* GUI END */

    /* MAIN METHOD WHICH WILL RUN */
    public static void main(String[] args) {

        Calculator calc = new Calculator();

    }

    /*
     * OP var will be changed depending on which operator is pressed on the
     * calculator e.g. (+, -, x, / etc.)
     * 
     * compOperator will be used for the scientific buttons that only need one
     * number for the calculation e.g. (Sin, Cos, x^2)
     */

    String Operator;
    String compOperator;

    // Only two numbers will be calculated at one time.

    double firstNum = 0.0;
    double secondNum;
    double result;

    /*
     * 'firstAssigned' will be used to let the program know when to assign the
     * second number.
     * 
     * 'newNumber' will be used to let the program know when to clear the textField
     * for a new number, it also allows the user to swtich Operators if they
     * accidentally selected the wrong one.
     */

    boolean firstAssigned = false;
    boolean newNumber = false;

    @Override

    public void actionPerformed(ActionEvent e) {

        // Iterates through the 'numberButtons' array

        for (int i = 0; i < numberButtons.length; i++) {

            /* NUMBER BUTTONS */

            if (e.getSource() == numberButtons[i]) {

                /*
                 * If 'textField' only has the contents '0', it will clear the textField before
                 * displaying any numbers, this prevent inputs such as '000000001', but stil
                 * allows for decimal inputs.
                 */

                if (textField.getText().matches("0")) {
                    textField.setText("");

                }

                /* Same principle as the above, only for negative numbers. */

                else if (textField.getText().matches("-0")) {
                    textField.setText("-");

                }

                /*
                 * if the 'newNumber' boolean is true, textField will clear to display
                 * newNumber.
                 */

                else if (newNumber) {
                    textField.setText("");
                }

                /* newNumber is then set to false as a number will have been pressed. */

                newNumber = false;
                textField.setText(textField.getText() + numberButtons[i].getText());
            }

        }

        /* ADDITION */

        if (e.getSource() == addButton) {

            /*
             * If the 'newNumber' boolean is true, check if the last character is a '+', if
             * it is not a '+', change the operator to a plus. This just allows the user to
             * switch operators if they accidentally choose the wrong operator.
             */

            if (newNumber) {

                if (upperText.getText().charAt(upperText.getText().length() - 2) != '+') {
                    Operator = "+";

                    upperText.setText(upperText.getText().substring(0, upperText.getText().length() - 2) + "+ ");
                }

            }

            /*
             * Once 'newNumber' is false, the program will check if 'firstAssigned' is false
             * (firstNum hasn't been assigned), if this is false, program will assign
             * 'firstNum', set the booleans 'firstAssigned' and 'newNumber' to true and
             * finally set the 'Operator' to '+'.
             * 
             * Next, the program will check if the last character is a ')', '^2' or '^3',
             * and if this is true it will keep the previous number in its brackets.
             * 
             * For example, if we have 'cos(12)' displayed in the textField and we press the
             * '+' button, the 'upperText' will display 'cos(12) +', rather than the
             * converted number '0.843.. +', this is purely to make the UI more clean. Same
             * for (x)^2 and (x)^3
             */

            else {
                if (!firstAssigned) {
                    firstNum = Double.parseDouble(textField.getText().toString());
                    firstAssigned = true;
                    newNumber = true;
                    Operator = "+";

                    if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                            || upperText.getText().endsWith(")^3")) {
                        upperText.setText(upperText.getText() + " + ");
                    } else {
                        upperText.setText(firstNum + " + ");
                    }

                    /*
                     * Once 'firstNum' is assigned, the program will move to assigning 'secondNum'
                     * to the number inputted by the user, it will then calculate the result of the
                     * 'firstNum' and 'secondNum' with the given operator.
                     * 
                     * The operator passed into the 'Calculate' function will not always be '+' as
                     * this button works as a trigger.
                     * For example, if user inputs '2 * 2' then presses the '+', the program will
                     * calculate '2 * 2' and the next calculation will be '2 * 2 + x', this is why
                     * the 'Operator' is only set to '+' once 'Calculate' has been called and the
                     * result is displayed.
                     */

                } else {
                    secondNum = Double.parseDouble(textField.getText().toString());
                    result = Calculate(firstNum, secondNum);

                    textField.setText(Double.toString(result));

                    /* This is the same as the other IF statement above, again for the UI. */

                    if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                            || upperText.getText().endsWith(")^3")) {
                        upperText.setText(upperText.getText() + " + ");
                    } else {
                        upperText.setText(upperText.getText() + secondNum + " + ");
                    }

                    /*
                     * Here 'firstNum' is set to the 'result' so that the calculations can be
                     * chained indefinetely. 'Operator' is also set for the next calculation.
                     * 
                     * Example: '2 * 2 + 5'
                     * 
                     * - User inputs '2 * 2'
                     * - firstNum = 2, secondNum = 2, Operator = '*'
                     * 
                     * - User inputs '+'
                     * 
                     * - both numbers are passed into Calculate function with the operator still
                     * - being '*', so '2 * 2' is calculated.
                     * - 'firstNum' is set to result (4), 'Operator' is set to '+'
                     * - firstNum = 4, secondNum = 2, Operator = '+'
                     * 
                     * - User inputs '5' then '-'
                     * 
                     * - 'secondNum' is set to '5', both numbers are passed into 'Calculate'
                     * - function with operator being '+' so '5 + 4' is calculated.
                     * - firstNum = 4, secondNum = 5, Operator = '+'
                     * - '9' is displayed
                     * 
                     * - 'firstNum' is set to 'result' (9), Operator is set to '-' and loop
                     * - continues.
                     */

                    firstNum = result;
                    newNumber = true;
                    Operator = "+";
                }
            }

        }

        /*
         * SUBTRACTION, MULTIPLICATION, DIVISON, POWER OF (X^Y) & PERCENTAGE ALL WORK
         * THE SAME AS ADDITION
         */

        /* SUBTRACTION */

        if (e.getSource() == subButton) {

            if (newNumber) {

                if (upperText.getText().charAt(upperText.getText().length() - 2) != '-') {
                    Operator = "-";

                    upperText.setText(upperText.getText().substring(0, upperText.getText().length() - 2) + "- ");
                }

            } else {
                if (!firstAssigned) {
                    firstNum = Double.parseDouble(textField.getText().toString());
                    firstAssigned = true;
                    newNumber = true;
                    Operator = "-";

                    if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                            || upperText.getText().endsWith(")^3")) {
                        upperText.setText(upperText.getText() + " - ");
                    } else {
                        upperText.setText(firstNum + " - ");
                    }

                } else {
                    secondNum = Double.parseDouble(textField.getText().toString());
                    result = Calculate(firstNum, secondNum);

                    textField.setText(Double.toString(result));

                    if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                            || upperText.getText().endsWith(")^3")) {
                        upperText.setText(upperText.getText() + " - ");
                    } else {
                        upperText.setText(upperText.getText() + secondNum + " - ");
                    }

                    firstNum = result;
                    newNumber = true;
                    Operator = "-";
                }
            }

        }

        /* MULTIPLICATION */

        if (e.getSource() == mulButton) {

            if (newNumber) {

                if (upperText.getText().charAt(upperText.getText().length() - 2) != '*') {
                    Operator = "*";

                    upperText.setText(upperText.getText().substring(0, upperText.getText().length() - 2) + "* ");
                }

            } else {
                if (!firstAssigned) {
                    firstNum = Double.parseDouble(textField.getText().toString());
                    firstAssigned = true;
                    newNumber = true;
                    Operator = "*";

                    if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                            || upperText.getText().endsWith(")^3")) {
                        upperText.setText(upperText.getText() + " * ");
                    } else {
                        upperText.setText(firstNum + " * ");
                    }

                } else {
                    secondNum = Double.parseDouble(textField.getText().toString());
                    result = Calculate(firstNum, secondNum);

                    textField.setText(Double.toString(result));

                    if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                            || upperText.getText().endsWith(")^3")) {
                        upperText.setText(upperText.getText() + " * ");
                    } else {
                        upperText.setText(upperText.getText() + secondNum + " * ");
                    }

                    firstNum = result;
                    newNumber = true;
                    Operator = "*";
                }
            }

        }

        /* DIVISION */

        if (e.getSource() == divButton) {

            if (newNumber) {

                if (upperText.getText().charAt(upperText.getText().length() - 2) != '/') {
                    Operator = "/";

                    upperText.setText(upperText.getText().substring(0, upperText.getText().length() - 2) + "/ ");
                }

            } else {
                if (!firstAssigned) {
                    firstNum = Double.parseDouble(textField.getText().toString());
                    firstAssigned = true;
                    newNumber = true;
                    Operator = "/";

                    if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                            || upperText.getText().endsWith(")^3")) {
                        upperText.setText(upperText.getText() + " / ");
                    } else {
                        upperText.setText(firstNum + " / ");
                    }

                } else {
                    secondNum = Double.parseDouble(textField.getText().toString());
                    result = Calculate(firstNum, secondNum);

                    textField.setText(Double.toString(result));

                    if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                            || upperText.getText().endsWith(")^3")) {
                        upperText.setText(upperText.getText() + " / ");
                    } else {
                        upperText.setText(upperText.getText() + secondNum + " / ");
                    }

                    firstNum = result;
                    newNumber = true;
                    Operator = "/";
                }
            }

        }

        /* POWER OF */

        if (e.getSource() == power) {

            if (newNumber) {

                if (upperText.getText().charAt(upperText.getText().length() - 2) != '^') {
                    Operator = "^";

                    upperText.setText(upperText.getText().substring(0, upperText.getText().length() - 2) + "^ ");
                }

            } else {
                if (!firstAssigned) {
                    firstNum = Double.parseDouble(textField.getText().toString());
                    firstAssigned = true;
                    newNumber = true;
                    Operator = "^";

                    if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                            || upperText.getText().endsWith(")^3")) {
                        upperText.setText(upperText.getText() + " ^ ");
                    } else {
                        upperText.setText(firstNum + " ^ ");
                    }

                } else {
                    secondNum = Double.parseDouble(textField.getText().toString());
                    result = Calculate(firstNum, secondNum);

                    textField.setText(Double.toString(result));

                    if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                            || upperText.getText().endsWith(")^3")) {
                        upperText.setText(upperText.getText() + " ^ ");
                    } else {
                        upperText.setText(upperText.getText() + secondNum + " ^ ");
                    }

                    firstNum = result;
                    newNumber = true;
                    Operator = "^";
                }
            }

        }

        /* PERCENTAGE */

        if (e.getSource() == percentage) {

            if (newNumber) {

                if (upperText.getText().charAt(upperText.getText().length() - 2) != '%') {
                    Operator = "%";

                    upperText.setText(upperText.getText().substring(0, upperText.getText().length() - 2) + "% ");
                }

            } else {
                if (!firstAssigned) {
                    firstNum = Double.parseDouble(textField.getText().toString());
                    firstAssigned = true;
                    newNumber = true;
                    Operator = "%";

                    if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                            || upperText.getText().endsWith(")^3")) {
                        upperText.setText(upperText.getText() + " % ");
                    } else {
                        upperText.setText(firstNum + " % ");
                    }

                } else {
                    secondNum = Double.parseDouble(textField.getText().toString());
                    result = Calculate(firstNum, secondNum);

                    textField.setText(Double.toString(result));

                    if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                            || upperText.getText().endsWith(")^3")) {
                        upperText.setText(upperText.getText() + " % ");
                    } else {
                        upperText.setText(upperText.getText() + secondNum + " % ");
                    }

                    firstNum = result;
                    newNumber = true;
                    Operator = "%";
                }
            }

        }

        /* DECIMAL POINT */

        if (e.getSource() == decButton) {

            /*
             * If there is a decimal point in our textField, this button does nothing.
             * Prevents multiple decimals in numbers.
             */

            if (!textField.getText().contains(".")) {
                textField.setText(textField.getText() + ".");
            }

        }

        /* SINGLE NUMBER CALCULATION BUTTONS */

        /*
         * All of these buttons set the 'compOperator' to the given symbol and call on
         * the 'complexCalculate' function.
         */

        if (e.getSource() == sqrt) {

            compOperator = "√";
            complexCalculate();

        }

        if (e.getSource() == factorial) {

            compOperator = "n!";
            complexCalculate();

        }

        if (e.getSource() == inverse) {

            compOperator = "1/x";
            complexCalculate();

        }

        if (e.getSource() == cube) {

            compOperator = "x^3";
            complexCalculate();

        }

        if (e.getSource() == square) {

            compOperator = "x^2";
            complexCalculate();

        }

        if (e.getSource() == sin) {

            compOperator = "sin";
            complexCalculate();

        }

        if (e.getSource() == cos) {

            compOperator = "cos";
            complexCalculate();

        }

        if (e.getSource() == tan) {

            compOperator = "tan";
            complexCalculate();

        }

        if (e.getSource() == log) {

            compOperator = "log";
            complexCalculate();

        }

        /* INVERT */

        if (e.getSource() == invert) {

            /*
             * This button simply changes positive numbers to negative and negative to
             * positive
             */

            /*
             * If a 'newNumber' is yet to be inputted and this button is pressed, it will
             * remove the old number and just display a '-', which then can be given a
             * number, this can also be removed by pressing the button again.
             */

            if (newNumber && !textField.getText().contains("-")) {
                textField.setText("-");
            } else if (newNumber) {
                textField.setText("");
            } else {

                /*
                 * If 'newNumber' is false, the invert will just add and remove '-' from the
                 * start of the numbers already displayed on screen.
                 */

                if (!textField.getText().contains("-")) {
                    textField.setText("-" + textField.getText());

                } else if (textField.getText().contains("-")) {
                    textField.setText(textField.getText().replace("-", ""));
                }
            }

        }

        /* CLEAR */

        if (e.getSource() == clrButton) {

            /*
             * Clear button sets both 'firstNum' and 'secondNum' to 0, and sets both text
             * displays to blank
             */

            firstNum = 0.0;
            secondNum = 0.0;

            textField.setText("0");
            upperText.setText("");

        }

        /* DELETE */

        if (e.getSource() == delButton) {

            /*
             * Delete button sets the textField to a substring of itself, minus the last
             * character. if the length of the string displayed reaches 0, the textField
             * will display a '0', rather than no number.
             */

            if (textField.getText().length() > 1) {
                textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
            } else {
                textField.setText("0");
            }

        }

        /* EQUALS */

        if (e.getSource() == equButton) {

            /* If a new number is waiting to be inputted, this button will do nothing. */

            if (newNumber) {

            } else {

                /*
                 * If a new number has been inputted, it will check if the first number has been
                 * assigned. if not then no calculation can be done as we need two numbers.
                 * 
                 * Once first number has been assigned, the program will assign the secondNum to
                 * the number inputted into the textField, then the 'Calculate' function will be
                 * called and stored in the 'result' variable.
                 */

                if (firstAssigned) {
                    secondNum = Double.parseDouble(textField.getText().toString());
                    result = Calculate(firstNum, secondNum);

                    /* Display for UI, same seen in ADDITION */

                    if (upperText.getText().endsWith(")")) {
                        upperText.setText(upperText.getText() + " =");

                    } else {
                        upperText.setText(upperText.getText() + secondNum + " =");
                    }

                    /*
                     * Displays result and sets firstAssigned to false, therefore 'firstNum' will be
                     * assigned to result once an operator has been selected by the user.
                     */

                    textField.setText(String.valueOf(result));
                    firstAssigned = false;
                }

            }

        }

    }

    /* Calculate function for the more simple calculations */

    public double Calculate(double firstNum, double secondNum) {

        /*
         * This function takes in two numbers, and depending on which operator is
         * chosen, will spit out a result
         */

        switch (Operator) {
            case "+": // addition
                return firstNum + secondNum;
            case "-": // subtraction
                return firstNum - secondNum;
            case "*": // multiplication
                return firstNum * secondNum;
            case "/": // division
                return firstNum / secondNum;
            case "^": // power
                return Math.pow(firstNum, secondNum);
            case "%":
                return (firstNum / secondNum) * 100;
            default:
                return 0.0;

        }

    }

    /* these variables are used for the more complicated calculations */

    double complexNumber;
    double factNum;
    double radians;

    /* Function for most of the scientific operations */

    public void complexCalculate() {

        /*
         * 'complexNumber' is assigned to the number inputted by the user in the
         * textField
         */

        complexNumber = Double.parseDouble(textField.getText());

        switch (compOperator) {
            // SQUARE ROOT
            case "√":

                /*
                 * Sets the textField to the square root of
                 * 'complexNumber'
                 */

                textField.setText(String.valueOf(Math.sqrt(complexNumber)));

                /*
                 * This IF statement is the same as the one seen in the ADDITION, SUBTRACTIION
                 * etc. and is only for the UI.
                 */

                if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                        || upperText.getText().endsWith(")^3")) {
                    upperText.setText("√(" + upperText.getText() + ")");
                } else {
                    upperText.setText(upperText.getText() + "√(" + complexNumber + ")");
                }

                /*
                 * 'newNumber' is set to false so that the user can select another operator,
                 * such as '+'.
                 */
                newNumber = false;
                break;
            /* FACTORIAL */
            case "n!":
                /* New variable to hold the end result */
                factNum = complexNumber;
                /* Try, Catch block in case number is too big */
                try {

                    /*
                     * For loop that will multiply, first by n-1, then n-2 etc. until it reaches 1.
                     * For example: '5'
                     * 5 * 4 * 3 * 2 * 1 = 120
                     */

                    for (double i = complexNumber - 1; i > 0; i--) {
                        factNum *= i;
                    }

                    /* Displays result */
                    textField.setText(String.valueOf(factNum));
                    if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                            || upperText.getText().endsWith(")^3")) {
                        upperText.setText("!(" + upperText.getText() + ")");
                    } else {
                        upperText.setText(upperText.getText() + "!(" + complexNumber + ")");
                    }
                    newNumber = false;
                } catch (Exception e) {
                    textField.setText("ERROR");
                }
                break;
            /* INVERSE NUMBER */
            case "1/x":

                textField.setText(String.valueOf(1 / complexNumber));

                /* FOR UI */

                if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                        || upperText.getText().endsWith(")^3")) {
                    upperText.setText("1/(" + upperText.getText() + ")");
                } else {
                    upperText.setText(upperText.getText() + "1/(" + complexNumber + ")");
                }
                newNumber = false;
                break;
            /* CUBE */
            case "x^3":
                textField.setText(String.valueOf(Math.pow(complexNumber, 3)));
                if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                        || upperText.getText().endsWith(")^3")) {
                    upperText.setText("(" + upperText.getText() + ")^3");
                } else {
                    upperText.setText(upperText.getText() + "(" + complexNumber + ")^3");
                }
                newNumber = false;
                break;
            /* SQUARE */
            case "x^2":
                textField.setText(String.valueOf(Math.pow(complexNumber, 2)));
                if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                        || upperText.getText().endsWith(")^3")) {
                    upperText.setText("(" + upperText.getText() + ")^2");
                } else {
                    upperText.setText(upperText.getText() + "(" + complexNumber + ")^2");
                }
                newNumber = false;
                break;
            /* SIN */
            case "sin":
                /*
                 * For SIN, COS & TAN, we need to convert doubles to radians, these values can
                 * then be passed into the math functions.
                 */
                radians = Math.toRadians(complexNumber);
                textField.setText(String.valueOf(Math.sin(radians)));

                if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                        || upperText.getText().endsWith(")^3")) {
                    upperText.setText("sin(" + upperText.getText() + ")");
                } else {
                    upperText.setText(upperText.getText() + "sin(" + complexNumber + ")");
                }
                newNumber = false;
                break;
            /* COS */
            case "cos":
                radians = Math.toRadians(complexNumber);
                textField.setText(String.valueOf(Math.cos(radians)));

                if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                        || upperText.getText().endsWith(")^3")) {
                    upperText.setText("cos(" + upperText.getText() + ")");
                } else {
                    upperText.setText(upperText.getText() + "cos(" + complexNumber + ")");
                }
                newNumber = false;

                break;

            /* TAN */
            case "tan":
                radians = Math.toRadians(complexNumber);
                textField.setText(String.valueOf(Math.tan(radians)));

                if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                        || upperText.getText().endsWith(")^3")) {
                    upperText.setText("tan(" + upperText.getText() + ")");
                } else {
                    upperText.setText(upperText.getText() + "tan(" + complexNumber + ")");
                }
                newNumber = false;

                break;

            /* LOG */
            case "log":
                textField.setText(String.valueOf(Math.log10(complexNumber)));

                if (upperText.getText().endsWith(")") || upperText.getText().endsWith(")^2")
                        || upperText.getText().endsWith(")^3")) {
                    upperText.setText("log(" + upperText.getText() + ")");
                } else {
                    upperText.setText(upperText.getText() + "log(" + complexNumber + ")");
                }
                newNumber = false;
                break;

        }

    }

}