package ru.photoBolter.model;

import junit.framework.Assert;
import junit.framework.TestCase;
import ru.photoBolter.util.FilePathHelper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class FileServiceTest extends TestCase {

    public void testGetSufix() throws IOException {
        Path path = Paths.get("/home/a380/IdeaProjects/photoBolter/test/IMG_6659.JPG");
        LocalDate localDateFromMetadata = FilePathHelper.getLocalDateFromMetadata(path);
        String sufix = FilePathHelper.createSufix(
                localDateFromMetadata,
                FilePathHelper.getName(path)
        );
        Assert.assertTrue("/2016/05may/160524/IMG_6659.JPG".equals(sufix));
        System.out.println(sufix);
    }
}