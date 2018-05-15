package ru.udsu.spring.springbootudsu.Controllers;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.udsu.spring.springbootudsu.Parser.Parser;
import ru.udsu.spring.springbootudsu.SOAP.SOAPAction;

import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import java.util.HashMap;

//преобразование обычных java классов в XML или JSON форматы
@RestController
public class rateControlWithDate {

    //контроллер для обработки запроса типа /rate/{code}/{date} (дату задает пользователь)
    //@RequestMapping задает методам контроллера адреса, по которым они будут доступны клиенту
    //НМ использует хеш-таблицу для хранения карточки, класс реализует интерфейс Map (хранение данных в виде пар ключ/значение)
    //throws перечисляет исключения
    @RequestMapping("/rate/{code}/{date}")
    HashMap<String, String> hello(@PathVariable String code, @PathVariable String date ) throws TransformerException, SOAPException {
        //забираем переданную дату и отправляем в обработчик
        SOAPAction.SOAPMessageAndCreateXML(date);
        //вызываем функцию, которая ищет в созданном XML файле неообходимую нам валюту и выводит
        return Parser.parserXML(code, date).toMap();
    }
}
