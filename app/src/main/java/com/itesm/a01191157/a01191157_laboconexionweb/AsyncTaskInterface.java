package com.itesm.a01191157.a01191157_laboconexionweb;

import java.util.ArrayList;

/**
 * Created by alfredo_altamirano on 10/16/15.
 */
public interface AsyncTaskInterface {

    public void onAsyncTaskSucceed(ArrayList<Clima> climasList);
    public void onAsyncTaskFailed();

}
