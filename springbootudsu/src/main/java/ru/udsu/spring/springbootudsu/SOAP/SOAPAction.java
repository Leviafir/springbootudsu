package ru.udsu.spring.springbootudsu.SOAP;

import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class SOAPAction {

    //функция отправляющая запрос на сервер центробанка РФ в виде xml и создающая xml на основе его ответа
    //создание класса соединения и установка самого соединения
    //информация центробанка: http://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx?op=GetCursOnDateXML
    public static void SOAPMessageAndCreateXML(String date) throws SOAPException, TransformerException {

        // работа с сервером, где в первой строчке указывается адрес сервера
        String destination = "http://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx";
        //Создаем соединение
        SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = soapConnFactory.createConnection();
        //создаем SOAP-сообщение (передача информации между клиентом и сервисом является отдельным XML-документом)
        SOAPMessage soapMessage = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL).createMessage();
        //указываем параметры; создаем объекты, представляющие различные компоненты сообщения
        SOAPPart part = soapMessage.getSOAPPart();
        SOAPEnvelope envelope = part.getEnvelope();
        //создаем тело XML-файла
        SOAPBody body = envelope.getBody();
        //указываем параметры телу документа
        SOAPElement bodyElement = body.addChildElement(envelope.createName("GetCursOnDateXML", null, "http://web.cbr.ru/"));
        //создаем дочерний элемент в теле, который указывает, за какую дату нам необходимы котировки
        bodyElement.addChildElement("On_date").addTextNode(date);
        //сохраняем изменения
        soapMessage.saveChanges();
        //отправляем запрос на сервер
        SOAPMessage reply = connection.call(soapMessage, destination);
        //создаем новый файл с название rate.xml
        File file = new File("rate.xml");
        //подготавливаем полученный ответ с сервера к записи
        //Получение содержимого ответа
        Source sourceContent = reply.getSOAPPart().getContent();
        //Создание XSLT-процессора, предоставляет способ для автоматического перевода XML-данных из одной формы в другую
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        //определяем будут ли добавляться в файл дополнительные пробелы при построении XML дерева.
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        //записываем ответ в новосозданный файл
        transformer.transform(sourceContent, new StreamResult(file));
    }
}
