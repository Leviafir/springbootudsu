package ru.udsu.spring.springbootudsu.Controllers;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.udsu.spring.springbootudsu.Parser.Parser;
import ru.udsu.spring.springbootudsu.SOAP.SOAPAction;

import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


@RestController
public class rateControl {

    //контроллер для обработки запроса типа /rate/{code} (дата не указана)
    @RequestMapping("/rate/{code}")
    HashMap<String, String> hello(@PathVariable String code) throws TransformerException, SOAPException, ParseException {
        //берем дату
        Date date = new Date();
        //указываем формат отображения даты
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //форматируем дату по заданному выше правилу и записываем результат в виде строки
        String stringDate = df.format(date);
        //три строки ниже увеличивают значение дня на 1 (берем 'завтра')
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(df.parse(stringDate));
        calendar.add(Calendar.DATE, 1);
        //отправляем нашу дату в обработчик
        SOAPAction.SOAPMessageAndCreateXML(df.format(calendar.getTime()));
        //вызываем функцию, которая ищет в созданном XML файле неообходимую нам валюту и выводит
        return Parser.parserXML(code, df.format(calendar.getTime())).toMap();
    }
}
