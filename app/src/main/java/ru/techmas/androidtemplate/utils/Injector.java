package ru.techmas.androidtemplate.utils;

import ru.techmas.androidtemplate.di.components.PresenterComponent;
import ru.techmas.androidtemplate.di.components.ViewComponent;


public class Injector {

    private static PresenterComponent presenterComponent;
    private static ViewComponent viewComponent;


    public static PresenterComponent getPresenterComponent() {
        return presenterComponent;
    }

    public static void setPresenterComponent(PresenterComponent presenterComponent) {
        Injector.presenterComponent = presenterComponent;
    }

    public static ViewComponent getViewComponent() {
        return viewComponent;
    }

    public static void setViewComponent(ViewComponent viewComponent) {
        Injector.viewComponent = viewComponent;
    }

}
