import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

public class Mycalulator extends JFrame implements ActionListener {
    Queue<String> queue;   //存储历史纪录
    String []An= {"","Clear","Undo","√","7","8","9","+","4","5","6","-",
            "1","2","3","*","0",".","=","/"};
    JButton b[]=new JButton[An.length];
    JTextField jt;
    String input=""; //输入的字符串
    public Mycalulator() {
        queue = new LinkedList<String>();  //初始化历史纪录
        Container P=getContentPane();
        JPanel jp1=new JPanel();
        JPanel jp2=new JPanel();
        JPanel jp3=new JPanel();
        GridLayout g=new GridLayout(5,4,3,3); //4*4网格,间距3
        jp1.setLayout(new BorderLayout()); //边布局
        jp2.setLayout(g);
        jp3.setLayout(new GridLayout(1, 2,3,3));

        jt=new JTextField();
        jt.setPreferredSize(new Dimension(160,30)); //文本大小
        jt.setHorizontalAlignment(SwingConstants.LEFT); //左对齐
        P.add(jp1,BorderLayout.NORTH); //北
        jp1.add(jt,BorderLayout.WEST); //西
        jp1.add(jp3,BorderLayout.EAST); //东

        for(int i=0;i< An.length;i++) //添加按钮
        {
            b[i]=new JButton(An[i]);
            jp2.add(b[i]);
            b[i].addActionListener(this);
        }
        P.add(jp2,BorderLayout.CENTER);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 250);
        setTitle("计算器");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int t=0;
        String s=e.getActionCommand();
        if(s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/")) {
            input+=" "+s+" "; //如果碰到运算符，就在运算符前后分别加一个空格
        }else if(s.equals("Clear")) {
            input = "";
            queue.clear();//清空历史纪录
        }else if(s.equals("√")) {

            double result = Math.sqrt(Double.parseDouble(input));
            if ((int)result - result == 0){  //如果为0，则为整数，直接输出
                input = String.valueOf(result);

            }else{
                DecimalFormat decimalFormat=new DecimalFormat();
                decimalFormat.applyLocalizedPattern("#0.0000000000");
                input = decimalFormat.format(result);
            }
        }else if(s.equals("Undo")) {
            try {
                input = queue.remove();
            }catch (NoSuchElementException ex){
                input = input;
            }
        }
        else if(s.equals("=")) {
            input=compute(input);
            jt.setText(input);
            queue.offer(input);
            input="";
            t=1;
        }
        else
            input += s;
        if(t==0) {
            jt.setText(input);
        }
    }
    private String compute(String str) {
        String array[];
        array=str.split(" ");
        Stack<Double> s=new Stack<Double>();
        Double a=Double.parseDouble(array[0]);
        s.push(a);
        for(int i=1;i<array.length;i++) {
            if(i%2==1) {
                if(array[i].compareTo("+")==0)
                {
                    double b= Double.parseDouble(array[i+1]);
                    s.push(b);
                }
                if(array[i].compareTo("-")==0)
                {
                    double b= Double.parseDouble(array[i+1]);
                    s.push(-b);
                }
                if(array[i].compareTo("*")==0)
                {
                    double b= Double.parseDouble(array[i+1]);
                    double c=s.pop();
                    c*=b;
                    s.push(c);
                }
                if(array[i].compareTo("/")==0)
                {
                    double b= Double.parseDouble(array[i+1]);
                    double c=s.pop();
                    c/=b;
                    s.push(c);
                }
            }
        }
        double sum=0;
        while(!s.isEmpty()) {
            sum+=s.pop();
        }
        String result=String.valueOf(sum);
        return result;
    }
    public static void main(String[] args) {
        new Mycalulator();



    }
}