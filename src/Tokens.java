public class Tokens {
    public static Lexical getToken (String s)
    {
        
        int i=0, current_state=0;
        boolean error = false;
        while ((i<s.length())&&(!error)){
        switch (current_state)
        {
            case 0 : switch (s.charAt(i)) {
                case 'e' : current_state=1;break;
                case 'r' : current_state=9;break;
                case 'k' : current_state=18;break;
                case 'o' : current_state=22;break;
                case 'z' : current_state=33;break;
                case '(' : current_state=47;break;
                case ')' : current_state=48;break;
                case ':' : current_state=49;break;
                case ';' : current_state=50;break;
                case ',' : current_state=51;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 1 : switch (s.charAt(i)){
                case 'n' : current_state=2;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 2 : switch (s.charAt(i)){
                case 't' : current_state=3;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 3 : switch (s.charAt(i)){
                case 'i' : current_state=4;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 4 : switch (s.charAt(i)){
                case 't' : current_state=5;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 5 : switch (s.charAt(i)){
                case 'i' : current_state=6;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 6 : switch (s.charAt(i)){
                case 'e' : current_state=7;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 7 : switch (s.charAt(i)){
                case 's' : current_state=8;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 8 : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
            break;
            case 9 : switch (s.charAt(i)){
                case 'e' : current_state=10;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 10 : switch (s.charAt(i)){
                case 'l' : current_state=11;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 11 : switch (s.charAt(i)){
                case 'a' : current_state=12;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 12 : switch (s.charAt(i)){
                case 't' : current_state=13;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 13 : switch (s.charAt(i)){
                case 'i' : current_state=14;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 14 : switch (s.charAt(i)){
                case 'o' : current_state=15;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 15 : switch (s.charAt(i)){
                case 'n' : current_state=16;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 16 : switch (s.charAt(i)){
                case 's' : current_state=17;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 17 : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
            break;
            case 18 : switch (s.charAt(i)){
                case 'e' : current_state=19;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 19 : switch (s.charAt(i)){
                case 'y' : current_state=20;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 20 : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
            break;
            case 22 : switch (s.charAt(i)){
                case 'n' : current_state=23;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 23 : switch (s.charAt(i)){
                case 'e' : current_state=24;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 24 : switch (s.charAt(i)){
                case '-' : current_state=25;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 25 : switch (s.charAt(i)){
                case 'o' : current_state=26;break;
                case 'm' : current_state=29;break;
                default :current_state=-1;break;
            }
            break;
            case 26 : switch (s.charAt(i)){
                case 'n' : current_state=27;break;
                default :current_state=-1;break;
            }
            break;
            case 27 : switch (s.charAt(i)){
                case 'e' : current_state=28;break;
                default :current_state=-1;break;
            }
            break;
            case 28 : switch (s.charAt(i)){
                default : current_state=-1;break;
            }
            break;
            case 29 : switch (s.charAt(i)){
                case 'a' : current_state=30;break;
                default :current_state=-1;break;
            }
            break;
            case 30 : switch (s.charAt(i)){
                case 'n' : current_state=31;break;
                default :current_state=-1;break;
            }
            break;
            case 31 : switch (s.charAt(i)){
                case 'y' : current_state=32;break;
                default :current_state=-1;break;
            }
            break;
            case 32 : switch (s.charAt(i)){
                default : current_state=-1;break;
            }
            break;
            case 33 : switch (s.charAt(i)){
                case 'e' : current_state=34;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 34 : switch (s.charAt(i)){
                case 'r' : current_state=35;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 35 : switch (s.charAt(i)){
                case 'o' : current_state=36;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 36 : switch (s.charAt(i)){
                case '-' : current_state=37;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 37 : switch (s.charAt(i)){
                case 'o' : current_state=38;break;
                case 'm' : current_state=42;break;
                default :current_state=-1;break;
            }
            break;
            case 38 : switch (s.charAt(i)){
                case 'n' : current_state=39;break;
                default :current_state=-1;break;
            }
            break;
            case 39 : switch (s.charAt(i)){
                case 'e' : current_state=41;break;
                default :current_state=-1;break;
            }
            break;
            case 41 : switch (s.charAt(i)){
                default : current_state=-1;break;
            }
            break;
            case 42 : switch (s.charAt(i)){
                case 'a' : current_state=43;break;
                default :current_state=-1;break;
            }
            break;
            case 43 : switch (s.charAt(i)){
                case 'n' : current_state=44;break;
                default :current_state=-1;break;
            }
            break;
            case 44 : switch (s.charAt(i)){
                case 'y' : current_state=45;break;
                default :current_state=-1;break;
            }
            break;
            case 45 : switch (s.charAt(i)){
                default : current_state=-1;break;
            }
            break;
            case 46 : switch (s.charAt(i)){
                case '_' : current_state=46;break;
                default : if (isLetter(s.charAt(i)) || isDigit(s.charAt(i))){current_state=46;}
                else {current_state=-1;}
                break;
            }
            break;
            case 47 : switch (s.charAt(i)){
                default :current_state=-1;break;
            }
            break;
            case 48 : switch (s.charAt(i)){
                default :current_state=-1;break;
            }
            break;
            case 49 : switch (s.charAt(i)){
                default :current_state=-1;break;
            }
            break;
            case 50 : switch (s.charAt(i)){
                default :current_state=-1;break;
            }
            break;
            case 51 : switch (s.charAt(i)){
                default :current_state=-1;break;
            }
            break;
            }
        if (current_state==-1) error=true;
        i++;
    }
        switch (current_state){
            case 8 : return new Lexical("Keyword",s);
            case 17 : return new Lexical("Keyword",s);
            case 20 : return new Lexical("Keyword",s);
            case 28 : return new Lexical("Keyword",s);
            case 32 : return new Lexical("Keyword",s);
            case 41 : return new Lexical("Keyword",s);
            case 45 : return new Lexical("Keyword",s);
            case 46 : return new Lexical("Identifier",s);
            case 47 : return new Lexical("LeftParenthesis",s);
            case 48 : return new Lexical("RightParenthesis",s);
            case 49 : return new Lexical("Punctuation(Comma)",s);
            case 50 : return new Lexical("Punctuation(SemiColon)",s);
            case 51 : return new Lexical("Punctuation(Colon)",s);
        }
        return new Lexical("undefined",s);
        }
        static boolean isLetter(char c){
            return ((c>='a' && c<='z') || (c>='A' && c<='Z'));
        }
        static boolean isDigit(char c){
            return (c>='0' && c<='9');
        }
}
