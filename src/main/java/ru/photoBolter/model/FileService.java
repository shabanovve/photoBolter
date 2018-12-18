package ru.photoBolter.model;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.*;
import java.time.format.TextStyle;
import java.util.Locale;


public class FileService {

    public void copyFile(Path source, Path destinationDirectory) {
        try {
            Path destination = Paths.get(destinationDirectory.toString() + getSufix(source));
            if (!destination.toFile().exists()) {
                destination.toFile().mkdirs();
            }
            Files.copy(
                    source,
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSufix(Path source) {
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

    private LocalDate getLocalDateFromMetadata(Path source) {
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
}
