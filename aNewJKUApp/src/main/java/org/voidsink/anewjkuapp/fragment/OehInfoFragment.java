package org.voidsink.anewjkuapp.fragment;

import org.voidsink.anewjkuapp.R;
import org.voidsink.anewjkuapp.base.BaseFragment;
import org.voidsink.anewjkuapp.utils.Consts;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OehInfoFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_oeh_info, container,
				false);

		((TextView) view.findViewById(R.id.oeh_info_main_summary))
				.setMovementMethod(LinkMovementMethod.getInstance());
		((TextView) view.findViewById(R.id.oeh_info_jku_summary))
				.setMovementMethod(LinkMovementMethod.getInstance());
		((TextView) view.findViewById(R.id.oeh_info_contact_summary))
				.setMovementMethod(LinkMovementMethod.getInstance());

		return view;
	}

    @Override
    protected String getScreenName() {
        return Consts.SCREEN_OEH_INFO;
    }
}
