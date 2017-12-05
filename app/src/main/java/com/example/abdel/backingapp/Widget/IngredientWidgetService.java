package com.example.abdel.backingapp.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by abdel on 12/2/2017.
 */

public class IngredientWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        
        return new IngredientRemoteViewsFactory(this.getApplicationContext(),intent);
    }
}

