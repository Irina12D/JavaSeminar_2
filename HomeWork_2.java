
import java.io.FileWriter;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.Random;
import java.util.Arrays;

public class HomeWork_2 {

    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Задачи 1-3 на обработку json строки:");
        String strIn = ReadFromFile("dataIn.txt");
        WriteToFile("dataOut.txt", task1(strIn));
        System.out.println();

        int[] arrTask4 = createArray();
        System.out.println("Исходный массив:");
        System.out.println(Arrays.toString(arrTask4));
        System.out.println("Отсортированный массив:");
        System.out.println(Arrays.toString(sort(arrTask4)));        
    }

    // метод для получения исходной json строки из файла dataIn.txt 
    // я, правда, сразу для обработки возвращаю строку без крайних фигурных скобок
    // а ещё удаляю из строки все открывающие фигурные скобки и кавычки
    // и заменяю последовательность символов "}," на символ "}"
    // можно возвращать саму строку, но я сразу начала с этого метода, 
    // поэтому оставила для удобства возврат строки без всего лишнего 
    static String ReadFromFile(String filePath) 
    { 
        StringBuilder res = new StringBuilder();
        try(Scanner sc = new Scanner(new FileReader(filePath)))
            {
                res.append(sc.nextLine());
            }
            
        catch (Exception e)
            {
                e.printStackTrace();
            }
        res.delete(0,1);
        res.delete(res.length()-2, res.length()-1);
        String s = res.toString().replaceAll("[{\"]", "");    
        return s.replaceAll("},", "}"); //res.toString().replaceAll("[{\"]", "");
    }

    // метод для формирования ответа на задачу 1
    static String task1(String inputStr)
    {
        String[] data = inputStr.split("}");
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String[] record = data[i].split(",");
            s.append("Student ");
            s.append(record[0].substring(record[0].indexOf(":") + 1));
            s.append(" has got mark ");
            s.append(record[1].substring(record[1].indexOf(":") + 1));
            s.append(" by subject ");
            s.append(record[2].substring(record[2].indexOf(":") + 1));
            s.append("\n");
        }
        int n = s.length();  
        s.delete(n-1, n-1);
        return s.toString();
    }

    // метод для записи результирующей строки в файл dataOut.txt с созданием лог файла 
    static void WriteToFile(String filePath, String data) 
    { 
        
        Logger logger = Logger.getAnonymousLogger();
        FileHandler fH = null;
        try {
            fH = new FileHandler("logJSON.txt");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(fH);

        try (FileWriter f = new FileWriter(filePath))
            {
                f.write(data);
                logger.log(Level.INFO, "Data has been successfully recorded");
            }
            catch (IOException e) 
            {
                logger.log(Level.WARNING, e.getMessage());
            }
    }


    // Реализуйте алгоритм сортировки пузырьком числового массива, результат после каждой итерации запишите в лог-файл

    // метод создания массива чисел
    static int[] createArray()
    {
        Random rnd = new Random(); 
        int size = rnd.nextInt(15) + 5;
        int[] arr = new int[size];
        for(int i = 0; i < size; i++) {
           arr[i] = rnd.nextInt(100);
        }
        return arr;
    }

    // сортировка с выводом результатов в файл logArray.txt	
    static int[] sort(int[] mas)
    {
        Logger logger = Logger.getAnonymousLogger();
        FileHandler fH = null;
        SimpleFormatter formatter = new SimpleFormatter();
        try {
            fH = new FileHandler("logArray.txt");
            fH.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(fH);

        boolean obmen = true;
        int n = mas.length;
        int k = 0; 
        for (; k < n - 1 && obmen; k++)
	    {
            obmen = false;
            for(int i = 0; i < n - k - 1; i++)
                if (mas[i] > mas[i + 1])
                {
                    int temp = mas[i];
                    mas[i] = mas[i + 1];
                    mas[i + 1] = temp;
                    obmen = true;
                }
            logger.info("Iteration no " + (k+1) + "\n" + Arrays.toString(mas) + "\n");
    	}
        logger.info(" \nTotalresult: the array is sorted in " + k + " iterations");
        return mas;
    }
}


  

