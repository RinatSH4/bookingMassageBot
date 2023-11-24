import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class Booking {
    private HashMap<String, String> book;
    private String time, name;

    Booking () {
        book = new HashMap<>();
        book.put("10:00-11:00", "");
        book.put("11:00-12:00", "");
        book.put("12:00-13:00", "");
        book.put("13:00-14:00", "");
        book.put("14:00-15:00", "");
        book.put("15:00-16:00", "");
        book.put("16:00-17:00", "");
        book.put("17:00-18:00", "");
    }

    String[] getFree() {
        int i = 0;
        String[] arr = new String[freeCounter()];

        for(HashMap.Entry entry : book.entrySet())
            if(entry.getValue().equals("")) {
                arr[i] = entry.getKey().toString();
                i++;
            }

        return arr;
    }

    private int freeCounter() {
        int i = 0;
        for(HashMap.Entry entry : book.entrySet())
            if(entry.getValue().equals(""))
                i++;
        return i;
    }

    void setBook(String textMessage) {
        if(textMessage.contains(":"))
            time = textMessage;
        else if(textMessage.contains(" ") && !textMessage.equals("Проверить свободные места")) {
            name = textMessage;
            addBook(time, name);
        }
    }

    private void addBook(String time, String name) {
        for(HashMap.Entry entry : book.entrySet())
            if(entry.getValue().equals(""))
                book.put(time, name);
    }

    String getBook() {
        String time = "", name = "";
        for(HashMap.Entry entry : book.entrySet())
            if(!entry.getValue().equals("")) {
                time = entry.getKey().toString();
                name = entry.getValue().toString();
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        return "Забронировано " + time + " : " + name;
    }

    void checkAdmin() {
        System.out.println("\nЗабронированы: ");
        for(HashMap.Entry entry : book.entrySet())
            if(!entry.getValue().equals(""))
                System.out.println(entry.getKey() + " : " + entry.getValue());

    }
}
