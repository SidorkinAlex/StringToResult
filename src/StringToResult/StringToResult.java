package StringToResult;

import java.util.LinkedList;

public class StringToResult implements StringToResultInterface {
    final char PLUS_SYMBOL='+';
    final char MINUS_SYMBOL='-';
    final char SPLIT_SYMBOL='/';
    final char MULTIPLY_SYMBOL='*';
    final char PERCENT_SYMBOL='%';


    private boolean isOperation(char c)
    {
        return c == PLUS_SYMBOL || c == MINUS_SYMBOL || c == SPLIT_SYMBOL || c == PERCENT_SYMBOL || c == MULTIPLY_SYMBOL;
    }

    public boolean interval(char c)
    {
        return c == ' '; //сключение выкидываем
    }
    private int opearatorsPriority(char operand)
    {
        switch (operand) {
            case PLUS_SYMBOL:
            case MINUS_SYMBOL:
                return 1;
            case MULTIPLY_SYMBOL:
            case SPLIT_SYMBOL:
            case PERCENT_SYMBOL:
                return 2;
            default:
                return -1;
        }
    }
    private void operator(LinkedList<Float> cislo, char znak)
    {
        float r = cislo.removeLast();
        float l = cislo.removeLast();
        switch (znak) {
            case PLUS_SYMBOL:
                cislo.add(l + r);
                break;
            case MINUS_SYMBOL:
                cislo.add(l - r);
                break;
            case MULTIPLY_SYMBOL:
                cislo.add(l * r);
                break;
            case SPLIT_SYMBOL:
                cislo.add(l / r);
                break;
            case PERCENT_SYMBOL:
                cislo.add(l % r);
                break;
        }
    }


    public Float make(String s)
    {
        StringToResult obj = new StringToResult();
        LinkedList<Float> h = new LinkedList<Float>();
        LinkedList<Character> op = new LinkedList<Character>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (obj.interval(c))
                continue;

            if (c == '(')
            {
                op.add('('); //если скобка то обрабатываем в первую очередь
            }
            else if (c == ')')
            {
                while (op.getLast() != '(') //проверьте правильность написания скобок
                    operator(h, op.removeLast());
                op.removeLast();
            }
            else if (obj.isOperation(c)) //вы ввели символ = ДА
            {
                while (!op.isEmpty() && obj.opearatorsPriority(op.getLast()) >= obj.opearatorsPriority(c))
                    //проверить правильность списка операций
                    operator(h, op.removeLast());
                op.add(c);
            } else
            {
                String operand = "";
                while (i < s.length() && Character.isDigit(s.charAt(i)) || i < s.length() && (s.charAt(i)=='.'))
                    //ребираем числа и точки
                    operand += s.charAt(i++); //перейти к следующему индексу
                --i;
                h.add(Float.parseFloat(operand));
            }
        }

        while (!op.isEmpty())
            operator(h, op.removeLast());
        return h.get(0);

    }
}
