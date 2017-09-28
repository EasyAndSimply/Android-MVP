package ru.techmas.androidtemplate.interfaces.utils_view;

import ru.techmas.androidtemplate.interfaces.views.BaseView;

public interface NetworkView extends BaseView {
    void showProgress(boolean show);
    void showData(boolean show);
    void showError(boolean show, String text);
}
