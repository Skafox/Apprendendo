package com.fatecerss.tcc.apprendendo.adapter;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.fatecerss.tcc.apprendendo.view.HomeActivity;

/**
 * Created by Sandro on 18/05/2018.
 */

public class BackgroundTask extends AsyncTask<Void, Void, Void> {
    private ProgressDialog dialog;

    public BackgroundTask(HomeActivity activity) {
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Doing something, please wait.");
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

}
