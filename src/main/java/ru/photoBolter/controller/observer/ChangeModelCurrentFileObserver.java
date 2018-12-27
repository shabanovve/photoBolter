package ru.photoBolter.controller.observer;

import ru.photoBolter.controller.observable.ChangeModelCurrentFileObservable;
import ru.photoBolter.model.PathContainer;

import java.util.ArrayList;
import java.util.List;

public class ChangeModelCurrentFileObserver {

    private final List<ChangeModelCurrentFileObservable> observedList = new ArrayList();

    public List<ChangeModelCurrentFileObservable> getObservedList() {
        return observedList;
    }

    public void changeCurrentFile(PathContainer pathContainer) {
        observedList.forEach(observed -> observed.changeCurrentFile(pathContainer));
    }
}
