package de.letsplaybar.discordbot.command.utils;

public class BrainFuck {

    private int ptr; // Data pinter

    // Max memory limit. It is the highest number which
    // can be represented by an unsigned 16-bit binary
    // number. Many computer programming environments
    // beside brainfuck may have predefined
    // constant values representing 65535.
    private int length ;

    // Array of byte type simulating memory of max
    // 65535 bits from 0 to 65534.
    private byte memory[];

    private StringBuilder builder;

    public BrainFuck(){
        length  = 65535;
    }

    public String convert(String input){
        try {
            builder = new StringBuilder();
            memory =new byte[length];
            interpret(input);
            return  builder.toString();
        }finally {
            builder = null;
            memory = null;
            System.gc();
        }
    }

    public String convertBack(String input){
        try {
            builder = new StringBuilder();
            toBrainfuck(input);
            return builder.toString();
        }finally {
            builder = null;
            System.gc();
        }
    }


    // Interpreter function which accepts the code
    // a string parameter
    private void interpret(String s)
    {
        int c = 0;

        // Parsing through each character of the code
        for (int i = 0; i < s.length(); i++)
        {
            // BrainFuck is a tiny language with only
            // eight instructions. In this loop we check
            // and execute all those eight instructions


            // > moves the pointer to the right
            if (s.charAt(i) == '>')
            {
                if (ptr == length - 1)//If memory is full
                    ptr = 0;//pointer is returned to zero
                else
                    ptr ++;
            }

            // < moves the pointer to the left
            else if (s.charAt(i) == '<')
            {
                if (ptr == 0) // If the pointer reaches zero

                    // pointer is returned to rightmost memory
                    // position
                    ptr = length - 1;
                else
                    ptr --;
            }

            // + increments the value of the memory
            // cell under the pointer
            else if (s.charAt(i) == '+')
                memory[ptr] ++;

                // - decrements the value of the memory cell
                // under the pointer
            else if (s.charAt(i) == '-')
                memory[ptr] --;

                // . outputs the character signified by the
                // cell at the pointer
            else if (s.charAt(i) == '.')
                builder.append((char)(memory[ptr]));

                // , inputs a character and store it in the
                // cell at the pointer
            else if (s.charAt(i) == '[')
            {
                if (memory[ptr] == 0)
                {
                    i++;
                    while (c > 0 || s.charAt(i) != ']')
                    {
                        if (s.charAt(i) == '[')
                            c++;
                        else if (s.charAt(i) == ']')
                            c--;
                        i ++;
                    }
                }
            }

            // ] jumps back to the matching [ if the
            // cell under the pointer is nonzero
            else if (s.charAt(i) == ']')
            {
                if (memory[ptr] != 0)
                {
                    i --;
                    while (c > 0 || s.charAt(i) != '[')
                    {
                        if (s.charAt(i) == ']')
                            c ++;
                        else if (s.charAt(i) == '[')
                            c --;
                        i --;
                    }
                    i --;
                }
            }
        }
    }

    private void toBrainfuck(String text){
        if (text.length()<=3){
            String result = "";
            for (char c:text.toCharArray()) {
                result += ">";
                if (c<12) {
                    for (int i=0;i<c;i++) {
                        result += "+";
                    }
                    result += ".>";
                } else {
                    int root = (int) Math.sqrt(c);
                    for (int i = 0; i<root;i++) {
                        result += "+";
                    }
                    result += "[>";
                    int quotient = c/root;
                    for (int i = 0; i<quotient;i++) {
                        result += "+";
                    }
                    result += "<-]>";
                    int remainder = c - (root*quotient);
                    for (int i = 0; i<remainder;i++) {
                        result += "+";
                    }
                    result += ".";
                }
            }
            builder.append(result.substring(1));
        } else {
            int[][] offsets = new int[3][text.length()];
            int counter = 0;
            String result = "---";

            for(char c:text.toCharArray()) {
                offsets[0][counter] = c/49;
                int temp = c%49;
                offsets[1][counter] = temp/7;
                offsets[2][counter] = temp%7;
                counter++;
            }

            for (int o:offsets[0]) {
                switch (o) {
                    case 0: result+=">--";
                        break;
                    case 1: result+=">-";
                        break;
                    case 2: result+=">";
                        break;
                    case 3: result+=">+";
                        break;
                    case 4: result+=">++";
                        break;
                    case 5: result+=">+++";
                        break;
                }
            }
            result += ">+[-[>+++++++<-]<+++]>----";
            for (int o:offsets[1]) {
                switch (o) {
                    case 0: result+=">---";
                        break;
                    case 1: result+=">--";
                        break;
                    case 2: result+=">-";
                        break;
                    case 3: result+=">";
                        break;
                    case 4: result+=">+";
                        break;
                    case 5: result+=">++";
                        break;
                    case 6: result+=">+++";
                        break;
                }
            }
            result += ">+[-[>+++++++<-]<++++]>----";
            for (int o:offsets[2]) {
                switch (o) {
                    case 0: result+=">---";
                        break;
                    case 1: result+=">--";
                        break;
                    case 2: result+=">-";
                        break;
                    case 3: result+=">";
                        break;
                    case 4: result+=">+";
                        break;
                    case 5: result+=">++";
                        break;
                    case 6: result+=">+++";
                        break;
                }
            }
            result += ">+[-<++++]>[.>]";
            builder.append(result);
        }
    }




}
