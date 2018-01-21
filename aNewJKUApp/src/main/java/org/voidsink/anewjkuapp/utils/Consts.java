/*
 *       ____.____  __.____ ___     _____
 *      |    |    |/ _|    |   \   /  _  \ ______ ______
 *      |    |      < |    |   /  /  /_\  \\____ \\____ \
 *  /\__|    |    |  \|    |  /  /    |    \  |_> >  |_> >
 *  \________|____|__ \______/   \____|__  /   __/|   __/
 *                   \/                  \/|__|   |__|
 *
 *  Copyright (c) 2014-2017 Paul "Marunjar" Pretsch
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */

package org.voidsink.anewjkuapp.utils;

import org.voidsink.anewjkuapp.BuildConfig;

public class Consts {

    public static final String ARG_RECREATE_SYNC_ALARM = "RECREATE_SYNC_ALARM";
    public static final String ARG_UPDATE_CAL = "UPDATE_CAL";
    public static final String ARG_UPDATE_CAL_COURSES = "UPDATE_CAL_LVA";
    public static final String ARG_UPDATE_CAL_EXAM = "UPDATE_CAL_EXAM";
    public static final String ARG_UPDATE_KUSSS = "UPDATE_KUSSS";
    public static final String ARG_UPDATE_KUSSS_CURRICULA = "UPDATE_KUSSS_CURRICULA";
    public static final String ARG_UPDATE_KUSSS_COURSES = "UPDATE_KUSSS_COURSES";
    public static final String ARG_UPDATE_KUSSS_ASSESSMENTS = "UPDATE_KUSSS_ASSESSMENTS";
    public static final String ARG_UPDATE_KUSSS_EXAMS = "UPDATE_KUSSS_EXAMS";
    public static final String ARG_TERMS = "TERMS";

    public static final String ARG_FRAGMENT_TITLE = "FRAGMENT_TITLE";
    public static final String ARG_FRAGMENT_ID = "FRAGMENT_ID";
    public static final String ARG_FRAGMENT_TAG = "show_fragment";

    public static final String SYNC_SHOW_PROGRESS = "showProgress";

    public static final String ARG_CALENDAR_NOW = "cal_now";
    public static final String ARG_CALENDAR_THEN = "cal_then";

    public static final String ARG_TAB_FRAGMENT_TITLE = "st_title";
    public static final String ARG_TAB_FRAGMENT_POS = "st_pos";

    //strings for logging of screens

    public static final String SCREEN_CALENDAR = "/calendar";
    public static final String SCREEN_CALENDAR_2 = "/calendar2";
    public static final String SCREEN_EXAMS = "/exams";
    public static final String SCREEN_ASSESSMENTS = "/assessments";
    public static final String SCREEN_COURSES = "/courses";
    public static final String SCREEN_STAT = "/stat";
    public static final String SCREEN_MENSA = "/mensa";
    public static final String SCREEN_MAP = "/map";
    public static final String SCREEN_OEH_INFO = "/info";
    public static final String SCREEN_OEH_RIGHTS = "/rights";
    public static final String SCREEN_CURRICULA = "/curricula";

    public static final String SCREEN_ABOUT = "/about";
    public static final String SCREEN_SETTINGS = "/settings";
    public static final String SCREEN_SETTINGS_KUSSS = "/settings/kusss";
    public static final String SCREEN_SETTINGS_APP = "/settings/app";
    public static final String SCREEN_SETTINGS_TIMETABLE = "/settings/timetable";

    public static final String SCREEN_SETTINGS_DASHCLOCK = "/settings/dashclock";
    public static final String SCREEN_LOGIN = "/login";
    public static final String SCREEN_DASHCLOCK = "/dashclock";

    // Laoder IDs
    public static final int LOADER_ID_COURSES = 1;
    public static final int LOADER_ID_ASSESSMENTS = 2;

    public static final String PROPERTY_ID = "UA-51633871-1";

    public static final String CHANNEL_ID_GRADES = BuildConfig.APPLICATION_ID + ".GRADES";
    public static final String CHANNEL_ID_EXAMS = BuildConfig.APPLICATION_ID + ".EXAMS";
    public static final String CHANNEL_ID_DEFAULT = BuildConfig.APPLICATION_ID + ".DEFAULT";

    private Consts() {
    }
}
