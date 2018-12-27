package ru.photoBolter.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import ru.photoBolter.exception.*;
import ru.photoBolter.model.PathContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FilePathHelper {

    public static String createSufix(LocalDate localDate, String fileName) {

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
        return "/" + datePath + fileName;
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

    public static LocalDate getLocalDateFromMetadata(Path source) {
        if (!isItJpgFile(source)) {
            return null;
        }

        Metadata metadata;
        try {
            metadata = ImageMetadataReader.readMetadata(Files.newInputStream(source));
        } catch (ImageProcessingException e) {
            throw new MetadataException(e);
        } catch (IOException e) {
            throw new MetadataException(e);
        }

        Directory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        if (directory == null) {
            throw new NoDirectoryInJpgException("File:" + source.toString());
        }
        Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        if (date == null) {
            throw new NoDateInJpgException("File:" + source.toString());
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static boolean checkCopy(PathContainer currentFile, Path destinationDirectory) {
        if (currentFile.getPath().toFile().isDirectory()) {
            return false;
        }

        if (!isItJpgFile(currentFile.getPath())) {
            return false;
        }
        LocalDate localDateFromMetadata = getLocalDateFromMetadata(currentFile.getPath());
        return Files.exists(
                getDestinationPath(
                        localDateFromMetadata,
                        getName(currentFile.getPath()),
                        destinationDirectory
                )
        );
    }

    private static boolean isItJpgFile(Path file) {
        return file
                .getName(file.getNameCount() - 1)
                .toString()
                .toLowerCase()
                .contains(".jpg");
    }

    public static Path getDestinationPath(LocalDate createDate, String fileName, Path destinationDirectory) {
        return Paths.get(
                destinationDirectory.toString() + createSufix(createDate, fileName)
        );
    }

    public static String getName(Path path) {
        return path
                .getName(path.getNameCount() - 1)
                .toString();
    }

    public static PathContainer getPathContainer(Path currentFile, List<PathContainer> pathContainers) {
        return pathContainers.stream()
                .filter(pathContainer -> pathContainer.getPath().equals(currentFile))
                .findFirst()
                .orElseThrow(NoSuchPathContainerException::new);
    }

}
