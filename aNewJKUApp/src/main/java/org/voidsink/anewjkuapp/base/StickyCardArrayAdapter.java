/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package org.voidsink.anewjkuapp.base;

import android.content.Context;

import org.voidsink.anewjkuapp.view.StickyCardListView;

import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public abstract class StickyCardArrayAdapter extends CardArrayAdapter implements StickyListHeadersAdapter {

    protected StickyCardListView mCardListView;

    public StickyCardArrayAdapter(Context context, List<Card> cards) {
        super(context, cards);
    }

    public void setCardListView(StickyCardListView cardListView) {
        this.mCardListView = cardListView;
    }
}
