package ru.techmas.androidtemplate.interfaces.utils_view;

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.techmas.androidtemplate.activities.BaseActivity;
import ru.techmas.androidtemplate.interfaces.views.BaseView;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface NavigatorActivityView extends BaseView {
    void startActivity(Class<? extends BaseActivity> activityClass);
    void startActivityForResult(Class<? extends BaseActivity> activityClass, int requestCode);
}
