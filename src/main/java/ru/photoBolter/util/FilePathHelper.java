package ru.photoBolter.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import ru.photoBolter.exception.MetadataException;
import ru.photoBolter.exception.ValidateDateException;

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
        int monthNumber = YearMonth.from(localDate).getMonth().getValue();
        String monthNumberString = monthNumber < 10 ? "0" + String.valueOf(monthNumber) : String.valueOf(monthNumber);
        String monthSymbols = YearMonth.from(localDate).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toLowerCase();
        int dayOfMonthInt = MonthDay.from(localDate).getDayOfMonth();
        String dayOfMonthString = dayOfMonthInt < 10 ? "0" + String.valueOf(dayOfMonthInt) : String.valueOf(dayOfMonthInt);
        String datePath = new StringBuffer().append(year)
                .append("/").append(monthNumberString)
                .append(monthSymbols)
                .append("/").append(year % 1000).append(monthNumberString).append(dayOfMonthString)
                .append("/").toString();

        validate(year, monthNumber, dayOfMonthInt);
        return "/" + datePath + source.getName(source.getNameCount() - 1);
    }

    private static void validate(int year, int mouthNumber, int dayOfMonthInt) {
        if (year < 1970) {
            throw new ValidateDateException("Wrong year = " + year);
        }

        if (mouthNumber < 1 || mouthNumber > 12) {
            throw new ValidateDateException("Wrong month = " + mouthNumber);
        }

        if (dayOfMonthInt < 1 || dayOfMonthInt > 31) {
            throw new ValidateDateException("Wrong day = " + dayOfMonthInt);
        }
    }

    private static LocalDate getLocalDateFromMetadata(Path source) {
        Metadata metadata;
        try {
            metadata = ImageMetadataReader.readMetadata(Files.newInputStream(source));
        } catch (ImageProcessingException e) {
            throw new MetadataException(e);
        } catch (IOException e) {
            throw new MetadataException(e);
        }

        Directory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        return directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static boolean checkCopy(Path currentFile, Path destinationDirectory) {
        if (currentFile.toFile().isDirectory()) {
            return false;
        }

        if (!isItJpgFile(currentFile)) {
            return false;
        }
        return Files.exists(getDestinationPath(currentFile, destinationDirectory));
    }

    private static boolean isItJpgFile(Path file) {
        return file
                .getName(file.getNameCount() - 1)
                .toString()
                .toLowerCase()
                .contains(".jpg");
    }

    public static Path getDestinationPath(Path source, Path destinationDirectory) {
        return Paths.get(destinationDirectory.toString() + getSufix(source));
    }


}
