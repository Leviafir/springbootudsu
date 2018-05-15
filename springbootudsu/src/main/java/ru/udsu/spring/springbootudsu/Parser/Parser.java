package ru.udsu.spring.springbootudsu.Parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.udsu.spring.springbootudsu.Models.Course;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

//Класс для поиска в xml-файле необходимой нам валюты
public class Parser {

    //функция, выполняющая поиск по xml-файлу
    public static Course parserXML(String code, String date) {
        //создаем экземпляр объекта
        Course course = new Course();
        //указываем название файла, который будем читать
        String filepath = "rate.xml";
        //берем документ по заданному имени и обрабатываем его с помощью библиотек DOM(Document Object Model)
        File xmlFile = new File(filepath);
        //создадим XML документ с помощью DOM анализатора (парсера); создает DocumentBuilder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            //создание нового пустого документа
            builder = factory.newDocumentBuilder();
            //создание нового документа из файла
            Document doc = builder.parse(xmlFile);
            //метод объединяет смежные текстовые узлы и удаляет пустые
            doc.getDocumentElement().normalize();
            //записываем в список все объекты с названием ValuteCursOnDate (вместе с дочерними тегами и их значениями)
            NodeList nodeList = doc.getElementsByTagName("ValuteCursOnDate");
            //пробегаемся по этому списку
            for (int i = 0; i < nodeList.getLength(); i++) {
                //ищем среди всего списка тот, название которого совпадает с запрашиваемым
                if (getTagValue("VchCode", (Element) nodeList.item(i)).equals(code)) {
                    //после того, как мы его нашли, записываем в наш созданный объект информацию
                    course.setCode(getTagValue("Vname", (Element) nodeList.item(i)));
                    course.setRate(getTagValue("Vcurs", (Element) nodeList.item(i)));
                    course.setDate(date);

                }
            }
            //Обработка исключений. Вывод на консоль ошибки с помощью метода printStackTrace,
            // определенного в классе Exception.
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        //возвращаем наш объект
        return course;
    }

    //функция для взятия значения из переданного в него тега (работает на основных функциях)
    private static String getTagValue(String tag, Element element) {
        //создается динамическая коллекция (любые изменения в DOM тут же отражаются на коллекции)
        // getElementsByTagName - берет элемент по указанному тегу
        //getChildNodes - берет дочерний элемент
        //item()возвращает элемент из списка по его индексу
        //Node позволяет различным типам быть обработанными
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

}
