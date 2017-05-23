package com.fydo.Config;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;

import com.fydo.R;



public class Dialog extends android.app.Dialog {
    ImageView imageLoader;
    public Dialog(Context context) {

        super(context, R.style.FullHeightDialog);
        this.setContentView(R.layout.loading_dialog);
        this.setCancelable(false);
        imageLoader = (ImageView) findViewById(R.id.imageLoader);

        final int[] imageArray = { R.mipmap.loader_one, R.mipmap.loader_two,
                R.mipmap.loader_three };

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                imageLoader.setImageResource(imageArray[i]);
                i++;
                if (i > imageArray.length - 1) {
                    i = 0;
                }
                handler.postDelayed(this,700);
            }
        };
        handler.postDelayed(runnable, 700);


//        progressBar.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.colorPrimary), Mode.MULTIPLY);
    }
}
