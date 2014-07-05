package no.invisibleink.app.view.fragment;

import no.invisibleink.app.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.fragment_section_list, container, false);
        return rootView;
    }
    
}

