package ru.photoBolter.model;

import junit.framework.Assert;
import org.junit.Test;
import ru.photoBolter.util.FilePathHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileServiceTest {
    private static final String TEST_SUFIX = "/2016/05may/160524/IMG_6659.JPG";

    private Path findTestJpgFile(String fileName) throws IOException {
        Stream<Path> pathStream = Files.find(
                Paths.get(System.getProperty("user.dir")),
                2,
                (path, basicFileAttributes) -> path.toFile().getName().equals(fileName)
        );
        return pathStream.findFirst().orElseThrow(RuntimeException::new);
    }

    @Test
    public void testGetSufix() throws IOException {
        Path testJpgFile = findTestJpgFile("IMG_6659.JPG");
        LocalDate localDateFromMetadata = FilePathHelper.getLocalDateFromMetadata(testJpgFile);
        String sufix = FilePathHelper.createSufix(
                localDateFromMetadata,
                FilePathHelper.getName(testJpgFile)
        );
        Assert.assertTrue(TEST_SUFIX.equals(sufix));
    }

    @Test
    public void defineCreateDateTest() throws IOException {
        Path testJpgFile = findTestJpgFile("DSCN1532.JPG");
        FileService fileService = new FileService();
        List<PathContainer> pathContainers = new ArrayList<>();
        pathContainers.add(new PathContainer(testJpgFile));
        fileService.defineCreateDate(pathContainers);
        PathContainer pathContainer = pathContainers.stream()
                .findFirst()
                .orElseThrow(RuntimeException::new);
        Assert.assertTrue(pathContainer.getCreateDate().getYear() == 2006);
    }
}