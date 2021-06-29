package ru.alexkm07.techjparser;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.alexkm07.techjparser.utils.FilesUtils;
import ru.alexkm07.techjparser.utils.InfoForSearsh;
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
    private final LogReader logReader;


    public TechJParserApplication(LogToEventConverter logToEventConverter, LogReader logReader) {
        this.logToEventConverter = logToEventConverter;
        this.logReader = logReader;
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
        List<Path> pathListLogs = FilesUtils.pathsLogsToList(Paths.get("/home/aleksei/Documents/JavaDev/techJParser/1c_techlogs/excep/"));
        logReader.setInfoForSearsh(new InfoForSearsh());
        int i = 0;
        for (Path pathLog : pathListLogs) {
            i++;
            logReader.setLogPath(pathLog);

            long startParsingFile = System.currentTimeMillis();
            if (!logReader.readLogFile()) continue;
            List<Map<String, String>> listRows = logReader.getLogRows();
            if(listRows == null){
                continue;
            }
            long endParsingFile = System.currentTimeMillis();
            //System.out.println("parsing time " + (endParsingFile-startParsingFile));
            long startSaveObjects = System.currentTimeMillis();
            int rowCount = 0;
            for (Map<String, String> rowLog : listRows) {
                Object o = logToEventConverter.rowToEvent(rowLog);
                if(o!=null) rowCount++;

            }
            long endSaveObjects = System.currentTimeMillis();
            //System.out.println("saving time " + (endSaveObjects-startSaveObjects));
            System.out.println("parsing log " + pathLog.toString() + " files count "
                    + pathListLogs.size() +  " files parsing " + i
                    +" processing time ms " + (endSaveObjects-startParsingFile)
                    + " event in file " + listRows.size() +" saved rows " + rowCount);
        }

    }
}
