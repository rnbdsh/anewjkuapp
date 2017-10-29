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

package org.voidsink.anewjkuapp.kusss;

import org.voidsink.anewjkuapp.R;

public enum LvaState {
    OPEN, DONE, ALL;

    public int getStringResID() {
        switch (this) {
            case OPEN:
                return R.string.lva_state_open;
            case DONE:
                return R.string.lva_state_done;
            case ALL:
                return R.string.lva_state_all;
            default:
                return R.string.lva_state_unknown;
        }
    }

    public int getStringResIDExt() {
        switch (this) {
            case OPEN:
                return R.string.lva_state_ext_open;
            case DONE:
                return R.string.lva_state_ext_done;
            case ALL:
                return R.string.lva_state_ext_all;
            default:
                return R.string.lva_state_unknown;
        }
    }
}
