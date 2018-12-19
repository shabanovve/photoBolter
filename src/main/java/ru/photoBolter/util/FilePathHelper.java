package ru.photoBolter.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;

public class FilePathHelper {

    public static String getSufix(Path source) {
        LocalDate localDate = getLocalDateFromMetadata(source);

        int year = Year.from(localDate).getValue();
        int mounthNumber = YearMonth.from(localDate).getMonth().getValue();
        String mounthNumberString = mounthNumber < 10 ? "0" + String.valueOf(mounthNumber) : String.valueOf(mounthNumber);
        String mounthSymbols = YearMonth.from(localDate).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toLowerCase();
        int dayOfMonthInt = MonthDay.from(localDate).getDayOfMonth();
        String dayOfMonthString = dayOfMonthInt < 10 ? "0" + String.valueOf(dayOfMonthInt) : String.valueOf(dayOfMonthInt);
        String datePath = new StringBuffer().append(year)
                .append("/").append(mounthNumberString)
                .append(mounthSymbols)
                .append("/").append(year % 1000).append(mounthNumberString).append(dayOfMonthString)
                .append("/").toString();
        return "/"  + datePath + source.getName(source.getNameCount() - 1);
    }

    private static LocalDate getLocalDateFromMetadata(Path source) {
        Metadata metadata;
        try {
            metadata = ImageMetadataReader.readMetadata(Files.newInputStream(source));
        } catch (ImageProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Directory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        return directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static boolean checkCopy(Path currentFile, Path destinationDirectory) {
        return Files.exists(getDestinationPath(currentFile, destinationDirectory));
    }

    public static Path getDestinationPath(Path source, Path destinationDirectory) {
        return Paths.get(destinationDirectory.toString() + getSufix(source));
    }


}
