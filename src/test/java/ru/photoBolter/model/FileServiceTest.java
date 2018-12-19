package ru.photoBolter.model;

import junit.framework.Assert;
import junit.framework.TestCase;
import ru.photoBolter.util.FilePathHelper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileServiceTest extends TestCase {

    public void testGetSufix() throws IOException {
        Path path = Paths.get("/home/a380/IdeaProjects/photoBolter/test/IMG_6659.JPG");
        Path dest = Paths.get("/home/a380/IdeaProjects/photoBolter/test/dest");
        String sufix = FilePathHelper.getSufix(path);
        Assert.assertTrue("/2016/05may/160524/IMG_6659.JPG".equals(sufix));
        System.out.println(sufix);
    }
}