package ru.techmas.androidtemplate.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.techmas.androidtemplate.App;
import ru.techmas.androidtemplate.utils.presenter.Resources;


@Module
public class UtilsModule {

    private App app;

    public UtilsModule(App app) {
        this.app = app;
    }

    @Provides
    Resources provideResources() {
        return new Resources(app);
    }

}
