package  ru.alexkm07.techjparser.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesUtils {

   public static List<Path> pathsLogsToList(Path pathToLogs){

        List<Path> pathList = new ArrayList<>();
        final int maxDepth = 1000;
        Stream<Path> matches = null;

        try {
            matches = Files.find(pathToLogs,maxDepth,(path, basicFileAttributes) -> String.valueOf(path).endsWith(".log"));
            pathList = matches.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            matches.close();
        }

        return pathList;

    }

}