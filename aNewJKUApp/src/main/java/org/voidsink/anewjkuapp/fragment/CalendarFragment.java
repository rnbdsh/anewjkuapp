package org.voidsink.anewjkuapp.fragment;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.voidsink.anewjkuapp.ImportCalendarTask;
import org.voidsink.anewjkuapp.R;
import org.voidsink.anewjkuapp.base.BaseFragment;
import org.voidsink.anewjkuapp.calendar.CalendarCard;
import org.voidsink.anewjkuapp.calendar.CalendarCardArrayAdapter;
import org.voidsink.anewjkuapp.calendar.CalendarContractWrapper;
import org.voidsink.anewjkuapp.calendar.CalendarUtils;
import org.voidsink.anewjkuapp.utils.Analytics;
import org.voidsink.anewjkuapp.utils.AppUtils;
import org.voidsink.anewjkuapp.utils.Consts;
import org.voidsink.anewjkuapp.view.StickyCardListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.gmariotti.cardslib.library.internal.Card;

public class CalendarFragment extends BaseFragment {

    private static final String TAG = CalendarFragment.class.getSimpleName();
    long now = 0, then = 0;
    private StickyCardListView mListView;
    private CalendarCardArrayAdapter mAdapter;
    private ContentObserver mCalendarObserver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_card_calendar, container,
                false);

        mListView = (StickyCardListView) view.findViewById(R.id.calendar_card_events);

        Button loadMore = (Button) view.findViewById(R.id.calendar_card_load);

        loadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMoreData();
            }
        });
        loadMore.setClickable(true);

        mAdapter = new CalendarCardArrayAdapter(getContext(), new ArrayList<Card>());

//		mListView.addFooterView(loadMore);
        mListView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init range
        now = System.currentTimeMillis();
        then = now + 14 * DateUtils.DAY_IN_MILLIS;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.calendar, menu);
    }

    private void loadMoreData() {
        then += 31 * DateUtils.DAY_IN_MILLIS;

        Analytics.eventLoadMoreEvents(getContext(), then - now);

        loadData();
    }

    private void loadData() {
        Log.d(TAG, "loadData");
        new CalendarLoadTask().execute();
    }

    @Override
    public void onStart() {
        super.onStart();

        mCalendarObserver = new CalendarContentObserver(new Handler());
        getActivity().getContentResolver().registerContentObserver(
                CalendarContractWrapper.Events.CONTENT_URI().buildUpon()
                        .appendPath("#").build(), false, mCalendarObserver);
    }

    @Override
    public void onStop() {
        getActivity().getContentResolver().unregisterContentObserver(
                mCalendarObserver);

        super.onStop();
    }

    @Override
    protected String getScreenName() {
        return Consts.SCREEN_CALENDAR;
    }

    private class CalendarLoadTask extends AsyncTask<String, Void, Void> {
        private ProgressDialog progressDialog;
        private List<Card> mCards;
        private Map<String, Integer> mColors;
        private Context mContext;

        @Override
        protected Void doInBackground(String... urls) {
            mCards = new ArrayList<>();

            // fetch calendar colors
            this.mColors = new HashMap<String, Integer>();
            ContentResolver cr = mContext.getContentResolver();
            Cursor c = cr
                    .query(CalendarContractWrapper.Calendars.CONTENT_URI(),
                            new String[]{
                                    CalendarContractWrapper.Calendars._ID(),
                                    CalendarContractWrapper.Calendars
                                            .CALENDAR_COLOR()}, null, null,
                            null);
            while (c.moveToNext()) {
                this.mColors.put(c.getString(0), c.getInt(1));
            }
            c.close();

            Account mAccount = AppUtils.getAccount(mContext);
            if (mAccount != null) {
                String calIDLva = CalendarUtils.getCalIDByName(mContext,
                        mAccount, CalendarUtils.ARG_CALENDAR_LVA);
                String calIDExam = CalendarUtils.getCalIDByName(mContext,
                        mAccount, CalendarUtils.ARG_CALENDAR_EXAM);

                if (calIDLva == null || calIDExam == null) {
                    Log.w(TAG, "no events loaded, calendars not found");
                    return null;
                }

                // load events
                boolean eventsFound = false;
                cr = mContext.getContentResolver();
                do {
                    c = cr.query(
                            CalendarContractWrapper.Events.CONTENT_URI(),
                            ImportCalendarTask.EVENT_PROJECTION,
                            "("
                                    + CalendarContractWrapper.Events
                                    .CALENDAR_ID()
                                    + " = ? or "
                                    + CalendarContractWrapper.Events
                                    .CALENDAR_ID() + " = ? ) and "
                                    + CalendarContractWrapper.Events.DTEND()
                                    + " >= ? and "
                                    + CalendarContractWrapper.Events.DTSTART()
                                    + " <= ? and "
                                    + CalendarContractWrapper.Events.DELETED()
                                    + " != 1",
                            new String[]{calIDExam, calIDLva,
                                    Long.toString(now), Long.toString(then)},
                            CalendarContractWrapper.Events.DTSTART() + " ASC");

                    if (c != null) {
                        // check if c is empty
                        eventsFound = c.moveToNext();
                        if (!eventsFound) {
                            // if empty then increase "then" for max 1 year
                            if (then - now < DateUtils.YEAR_IN_MILLIS) {
                                then += 31 * DateUtils.DAY_IN_MILLIS;
                            } else {
                                eventsFound = true;
                            }
                        }
                        // restore cursor position
                        c.moveToPrevious();
                        if (!eventsFound) {
                            // close cursor before loading next events
                            c.close();
                        }
                    }
                } while (c != null && !eventsFound);
                if (c != null && !c.isClosed()) {
                    while (c.moveToNext()) {
                        mCards.add(new CalendarCard(mContext,
                                c.getLong(ImportCalendarTask.COLUMN_EVENT_ID),
                                mColors.get(c
                                        .getString(ImportCalendarTask.COLUMN_EVENT_CAL_ID)),
                                c.getString(ImportCalendarTask.COLUMN_EVENT_TITLE),
                                c.getString(ImportCalendarTask.COLUMN_EVENT_DESCRIPTION),
                                c.getString(ImportCalendarTask.COLUMN_EVENT_LOCATION),
                                c.getLong(ImportCalendarTask.COLUMN_EVENT_DTSTART),
                                c.getLong(ImportCalendarTask.COLUMN_EVENT_DTEND)));

                    }
                    c.close();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mContext = CalendarFragment.this.getContext();
            if (mContext == null) {
                Log.e(TAG, "context is null");
            }

            progressDialog = ProgressDialog.show(mContext,
                    mContext.getString(R.string.progress_title),
                    mContext.getString(R.string.progress_load_calendar), true);
        }

        @Override
        protected void onPostExecute(Void result) {
            mAdapter.clear();
            mAdapter.addAll(mCards);
            progressDialog.dismiss();
            super.onPostExecute(result);
        }

    }

    private class CalendarContentObserver extends ContentObserver {

        public CalendarContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            loadData();
        }
    }
}
