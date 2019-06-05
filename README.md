# AnimalFront
Тестовое задание для ГСК Угория. Опцианальная часть задания. Клиентский интерфейс для работы с REST API

Тестовое задание на знание RESTful.

Сделать небольшую базу данных (MS SQL/Postgresql):
- Таблица Животные;
- Таблица Цвет шкуры;
- Таблица Вид животного;
- Таблица Местоположение;
- Таблица Регион;

Между таблицами настроить связи и зависимости.

Для сущности животное реализовать API (RESTful) для выполнения CRUD-операций. Будет плюсом, если реализовать UI, любой на ваш выбор.
Кроме этого необходимо организовать поиск животного по региону (возможен множественный выбор), цвету и виду.

Результат разместить на GitHub используя концепцию GitFlow.


<b>Результат разработки клиентской части:</b>
<p>
Реализованы следующие функции:
  
- Возвратить весь список животных;
- Возвратить животного по коду;
- Поиск животных соответствующих критериям поиска;
- Создать новое животное;
- Обновить информацию о животном;
- удалить животноe.
  
Архив с дистрибутивом находится по адресу https://github.com/SantAleks/AnimalFront/blob/Devolop/distr/AnimalFront.7z
Для запуска необходимо:
1) распаковать архив;
2) внутри полученной папки в файле application.properties задать параметры подключения к бэкенд-сервису (backend.port, backend.ip) и порт на котором должен принимать клиентские запросы клиентский сервис (server.port);
3) запустить сервис файлом AnimalFront.bat.

<b>Пример работы интерфейса:</b>
<p>
- Запрос всех записей:
<p>  
  <img src="https://github.com/SantAleks/AnimalFront/blob/Devolop/doc/ВывестиВсех.JPG" alt="Запрос всех записей">
<p> 
- Запрос одной записи:
<p> 
  <img src="https://github.com/SantAleks/AnimalFront/blob/Devolop/doc/ИскатьПоКоду.JPG" alt="Запрос одной записи">
<p> 
- Поиск по критериям:
<p> 
  <img src="https://github.com/SantAleks/AnimalFront/blob/Devolop/doc/ПоискПоПараметрам.JPG" alt="Поиск по критериям">
<p> 
- Создание новой записи:
<p> 
  действие
<p> 
  <img src="https://github.com/SantAleks/AnimalFront/blob/Devolop/doc/СозданиеДействие.JPG" alt="Создание новой записи. Действие">
<p> 
  результат
<p> 
  <img src="https://github.com/SantAleks/AnimalFront/blob/Devolop/doc/СозданиеРезультат.JPG" alt="Создание новой записи. Результат">
<p> 
- Обновление записи:
<p> 
  действие
<p> 
  <img src="https://github.com/SantAleks/AnimalFront/blob/Devolop/doc/ОбновлениеДействие.JPG" alt="Обновление записи. Действие">
<p> 
  результат
<p> 
  <img src="https://github.com/SantAleks/AnimalFront/blob/Devolop/doc/ОбновлениеРезультат.JPG" alt="Обновление записи. Результат">
<p> 
- Удаление записи:
<p> 
  действие
<p> 
  <img src="https://github.com/SantAleks/AnimalFront/blob/Devolop/doc/УдалениеДействие.JPG" alt="Удаление записи. Действие">
<p> 
  результат
<p> 
  <img src="https://github.com/SantAleks/AnimalFront/blob/Devolop/doc/УдалениеРезультат.JPG" alt="Удаление записи. Результат">  
