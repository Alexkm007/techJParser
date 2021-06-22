package ru.alexkm07.techjparser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.alexkm07.techjparser.utils.FilesUtils;
import ru.alexkm07.techjparser.utils.LogReader;
import ru.alexkm07.techjparser.utils.LogToEventConverter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
public class TechJParserApplication implements CommandLineRunner {


    private final LogToEventConverter logToEventConverter;

    public TechJParserApplication(LogToEventConverter logToEventConverter) {
        this.logToEventConverter = logToEventConverter;
    }

    public static void main(String[] args) {
        SpringApplication.run(TechJParserApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


//        if(args.length < 1) {
//            throw new Exception("Укажите путь к логам");
//        }
//
//        List<Path> pathListLogs = FilesUtils.pathsLogsToList(Paths.get(args[0]));
        List<Path> pathListLogs = FilesUtils.pathsLogsToList(Paths.get("/home/aleksei/Documents/JavaDev/techJParser/1c_techlogs/"));

        int i = 0;
        for (Path pathLog : pathListLogs) {
            i++;
            LogReader logReader = new LogReader(pathLog);
            System.out.println("parsing log " + pathLog.toString() + " files count " + pathListLogs.size() +  " files parsing " + i);

            if (!logReader.readLogFile()) continue;
            List<Map<String, String>> listRows = logReader.getLogRows();

            for (Map<String, String> rowLog : listRows) {
                Object o = logToEventConverter.rowToEvent(rowLog);
            }

        }

    }
}
