package hr.hajduk.kreso.xo_game;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class CustomPreferenceActivity extends PreferenceActivity{

    @Override
        protected void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new CustomPreferenceFragment()).commit();
        }

        public static class CustomPreferenceFragment extends PreferenceFragment {

            @Override
            public void onCreate(final Bundle savedInstancesState){
                super.onCreate(savedInstancesState);
                addPreferencesFromResource(R.xml.app_settings);
            }

        }
    }


