package ru.udsu.spring.springbootudsu.Models;

import java.util.HashMap;


//Класс курс - содержит в себе информацию о необходимом пользователю курсе
public class Course {
    //описываем переменные
    static String code;
    static String rate;
    static String date;
    //описываем гетеры (возвращают значение поля-метод, читают из определённого участка памяти
    //его содержимое и возвращают, как значение типа соответствующего поля класса)
    public static String getCode() { return code;}
    public static String getRate() { return rate;}
    public static String getDate() { return date;}
    //описываем сетеры (задают значение полю, размещают в соответствующем участке памяти значение,
    //переданное параметром)
    public static void setCode(String c) { code = c;}
    public static void setRate(String r) { rate = r;}
    public static void setDate(String d) { date = d;}

    //переопределяем метод toString (Override - аннотация, за которой следует переопределение метода)
    @Override
    public String toString() {
        return " code: " + code + " rate: " + rate + " date: " + date;
    }

    //создаем метод toMap, который преобразовывает наш объект в HashMap<String, String>
    public HashMap<String, String> toMap() {
        //объявляем пустой конструктор
        HashMap<String, String> CourseMap = new HashMap<>();
        //добавление элементов
        CourseMap.put("code", code);
        CourseMap.put("rate", rate);
        CourseMap.put("date", date);
        //возвращаем наш объект
        return CourseMap;
    }
}
