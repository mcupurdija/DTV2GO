package rs.multitelekom.dtv2go.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import rs.multitelekom.dtv2go.R;
import rs.multitelekom.dtv2go.ui.MainActivity;
import rs.multitelekom.dtv2go.util.AppConstants;

public class SettingsFragment extends PreferenceFragment {

    private MainActivity mainActivity;

    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getActivity();
        mainActivity = ((MainActivity) context);

        ListView list = (ListView) view.findViewById(android.R.id.list);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) list.getLayoutParams();
        marginLayoutParams.setMargins(0, 144, 0, 0);
        list.setLayoutParams(marginLayoutParams);

        Preference updateData = findPreference(AppConstants.UPDATE_DATA_PREFERENCE_KEY);
        updateData.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                mainActivity.updateData(true);
                return true;
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                switch (key) {
                    case AppConstants.QUALITY_PREFERENCE_KEY:
                        mainActivity.refreshDrawer();
                        break;
                    default:
                        break;
                }
            }
        };
        preferences.registerOnSharedPreferenceChangeListener(listener);
    }
}
