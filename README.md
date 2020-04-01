# task5-classes-interfaces-enums

[![Build Status](https://travis-ci.com/itmo-java-basics-2020/task5-classes-interfaces-enums-NikitaKop.svg?branch=task5)](https://travis-ci.com/itmo-java-basics-2020/task5-classes-interfaces-enums-NikitaKop)

Компания, где Вы работаете разрабатывает собственную СУБД типа ключ-значение. Требования к хранилищу простые - оно должно быть легковесным приложением на Java, которое позволяет долговечно (на диске) хранить пары ключ-значение и быстро (оценки производительность в данной работе не важны) возвращать значение по заданному ключу.

Данная СУДБ может иметь несколько **Баз данных**, каждая из которых представляет собой набор **Таблиц.**

Вашей команде поручено разработать иерархию классов, которая реализует консольный интерфейс для сервера базы данных.

## Требования

**Команда** - действие, которое может быть выполнено хранилищем. Характеризуется *именем* и *параметрами* команды. 

**Параметр** - обязательный аргумент команды. В вашей реализации значение параметра задается без указания его имени и характеризуются только позицией в команде. (см. ниже)

Команда поступает на обработку в виде строки, состоящей из термов (последовательностей непробельных символов), разделенных пробелом (" "). Термы являются названием команды и значениями ее параметров. Первый терм в данной строке всегда является названием (идентификатором) команды. Последующие за ним являются значениями параметров. К какому параметру относится значение всегда определятся позицией терма в данной строке - так, при выполнении команды "Создать базу данных" первым параметром всегда является имя создаваемой базы данных.

Хранилище должно поддерживать следующие команды:

- Создать базу данных

    `CREATE_DATABASE` <имя создаваемой базы данных>

- Создать таблицу

    `CREATE_TABLE` <имя базы данных> <имя создаваемой таблицы>

- Обновить значение по ключу, если ключ не существует - создать ключ и ассоциировать с ним переданное значение

    `UPDATE_KEY` <имя базы данных> <имя таблицы> <ключ> <значение>

- Прочитать значение по заданному ключу

    `READ_KEY` <имя базы данных> <имя таблицы> <ключ>

## Дано:

Вам доступны следующие интерфейсы:

- Database - интерфейс, предоставляющий методы базы данных, вы можете использовать его для делегирования выполнения команд.

        public interface Database {
            String getName();
        
            void createTableIfNotExists(String tableName) throws DatabaseException;
        
            void createTableIfNotExists(String tableName, int segmentSizeInBytes) throws DatabaseException;
        
            void write(String tableName, String objectKey, String objectValue) throws DatabaseException;
        
            String read(String tableName, String objectKey) throws DatabaseException;
        }

- ExecutionEnvironment - интерфейс, который позволяет вам получить объект базы данных по ее имени.

        public interface ExecutionEnvironment {
            Optional<Database> getDatabase(String name);
        }

## Указания к реализации:

### Команды

Мы предлагаем инкапсулировать логику инициализации и выполнения каждой из команд в виде отдельной реализации интерфейса `DatabaseCommand` 

    public interface DatabaseCommand {
        DatabaseCommandResult execute();
    }

Каждая реализация будет иметь конструктор, который ответственен за *инициализацию* экземпляра команды и метод `execute` , который производит непосредственное *выполнение* команды. Каждая команда может выполнять абсолютно различную бизнес-логику и возвращать разный результат, однако все реализации команды должны реализовывать один и тот же интерфейс, таким образом, код, который будет ответственен за выполнение команды может не иметь ни малейшего понятия о том какую **именно** команду он исполняет, важно, что он знает как ее исполнить. В этом и заключается суть шаблона проектирования **"[команда](https://refactoring.guru/ru/design-patterns/command)"**

Как вы могли заметить, метод `execute` возвращает значение типа `DatabaseCommandResult` . Данный интерфейс предоставляет доступ к результату выполнения команды. Некоторые команды возвращают значение, которое должно быть предоставлено пользователю, например, команда `UPDATE_KEY` . Для некоторых важно вернуть только статус выполнения - успешно ли выполнилась команда или нет. Для команд, выполнение которых было неуспешным, пользователи хотели бы видеть сообщение, которое дает представление о том, что могло пойти не так.

    public interface DatabaseCommandResult {
        Optional<String> getResult();
    
    		DatabaseCommandStatus getStatus();
    
    		boolean isSuccess();
    
    		String getErrorMessage();
    
    		enum DatabaseCommandStatus {
    				SUCCESS, FAILED
    		}
    }

Создайте единственную реализацию интерфейса `DatabaseCommandResult` в виде внутреннего класса данного интерфейса. Сделайте так, чтобы создать экземпляр данного класса было возможно только воспользовавшись статическими методами интерфейса `DatabaseCommandResult success(String result)` и `DatabaseCommandResult error(String message)` . Таким образом вы обеспечите пользователей вашего класса возможностью просто создать результат выполнения команды, но все же оставите ему возможность в будущем реализовать свои варианты данного интерфейса.

### Сервер базы данных

Как вы помните, сами команды поступают вам от пользователя в виде строк определенного формата (см. выше). Ваша задача - каждый раз, когда вам поступает такая строка - превратить ее в объект-команду, проверив на корректность формат входных данных, а после этого выполнить команду и вернуть результат ее выполнения.

Реализуйте метод класса `DatabaseServer` `executeNextCommand` , который принимает строку и возвращает результат выполнения команды, которая представлена данной строкой. Класс должен иметь ровно один конструктор, который принимает на вход экземпляр интерфейса `ExecutionEnvironment` .

    public class DatabaseServer {
    
        public DatabaseServer(ExecutionEnvironment env) {
            throw new UnsupportedOperationException(); // TODO implement
        }
    
        public DatabaseCommandResult executeCommand(String commandText) {
            throw new UnsupportedOperationException(); // TODO implement
        }
    
        public static void main(String[] args) {
            // here you can place code which creates DatabaseServer instance by
    				// passing mocked ExecutionEnvironment into it for testing purposes
        }
    }

### Инициализация команд

Превратить строку в команду - задача несложная, необходимо только написать несколько условных операторов или использовать конструкцию switch-case. Однако, я бы хотел, чтобы для превращения строки в объект-команду вы не пользовались ни тем ни другим. 

Для этого вам потребуется написать несколько различных реализаций **абстрактного** метода:

    DatabaseCommand getCommand(ExecutionEnvironment env, String... args);

Где и как реализовать этот метод - ваше решение, однако полный балл можно будет получить, если воспользоваться методом предложенным далее или написать что-то, что по моему субъективному мнению окажется более грациозным. Итак, далее *спойлеры*, кто хочет думать исключительно сам - тому не читать.

Предлагаю реализовать класс `DatabaseCommands` , который и предоставляет абстрактный метод, описанный выше. Данный класс будет являться перечислением, каждая из констант-экземпляров которого будет инкапсулировать логику создания той или иной команды (реализовывать по-своему абстрактный метод). Названия констант перечисления будут совпадать с именами команд, что позволит получить экземпляр (как мы говорили на лекциях он будет являться [синглтоном](https://refactoring.guru/ru/design-patterns/singleton)) константы воспользовавшись некоторым методом класса `Enum` и не пользоваться никакими из условных операторов.

Если присмотреться, то вот что выйдет:

- мы создали некий интерфейс, который умеет возвращать команду

    DatabaseCommand getCommand(ExecutionEnvironment env, String... args);

- мы написали реализацию этого интерфейса для каждой из команд, теперь мы можем пользоваться данными экземплярами для создания необходимых объектов. Причем вызывающий нас код не обязан знать какие **именно** объекты-команды он создает, важно, что они реализуют интерфейс `DatabaseCommand`

Мне кажется, что все это очень напоминает паттерн "[Абстрактная фабрика](https://refactoring.guru/ru/design-patterns/abstract-factory)", хоть мы немножко и отклонились от его эталонной реализации.

### Соединим

После того, как вы реализуете все команды, научитесь инициализировать их из переданной строки - вы в финале, ваш код почти работает. Не забудьте еще и то, что код не должен прекращать свою работу при возникновении непредвиденных ошибок, хоть мы и не проходили как их обрабатывать.
