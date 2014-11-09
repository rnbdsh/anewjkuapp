package org.voidsink.anewjkuapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import org.voidsink.anewjkuapp.LvaCard;
import org.voidsink.anewjkuapp.LvaCardArrayAdapter;
import org.voidsink.anewjkuapp.R;
import org.voidsink.anewjkuapp.base.BaseFragment;
import org.voidsink.anewjkuapp.kusss.ExamGrade;
import org.voidsink.anewjkuapp.kusss.Lva;
import org.voidsink.anewjkuapp.kusss.LvaWithGrade;
import org.voidsink.anewjkuapp.utils.AppUtils;
import org.voidsink.anewjkuapp.view.StickyCardListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;

@SuppressLint("ValidFragment")
public class LvaDetailFragment extends BaseFragment {

    private static final String TAG = LvaDetailFragment.class.getSimpleName();

    private List<String> mTerms;
    private StickyCardListView mListView;
    private LvaCardArrayAdapter mAdapter;

    private List<LvaWithGrade> mLvas = new ArrayList<>();
    private List<Card> mLvaCards = new ArrayList<>();

    public LvaDetailFragment() {
        this("", new ArrayList<Lva>(), new ArrayList<ExamGrade>());
    }

    public LvaDetailFragment(List<String> terms, List<Lva> lvas,
                             List<ExamGrade> grades) {
        this.mTerms = terms;
        this.mLvas = AppUtils.getLvasWithGrades(terms, lvas, grades);
    }

    public LvaDetailFragment(String term, List<Lva> lvas, List<ExamGrade> grades) {
        this(Arrays.asList(new String[]{term}), lvas, grades);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_card_lva_detail, container,
                false);

        mListView = (StickyCardListView) view.findViewById(R.id.lva_card_list);

        mLvaCards.clear();
        for (LvaWithGrade g : mLvas) {
            mLvaCards.add(new LvaCard(getContext(), g));
        }

        mAdapter = new LvaCardArrayAdapter(getContext(), mLvaCards);
        mListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.lva, menu);
    }
}
