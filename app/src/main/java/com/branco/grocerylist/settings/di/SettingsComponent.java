package com.branco.grocerylist.settings.di;

import com.branco.grocerylist.common.di.ActivityScope;
import com.branco.grocerylist.settings.ui.SettingsActivity;

import dagger.Subcomponent;

/**
 * Created by guilhermebranco on 5/6/17.
 */

@ActivityScope
@Subcomponent(
        modules = {  SettingsModule.class }
)
public interface SettingsComponent {

    void inject(SettingsActivity settingsActivity);
}
