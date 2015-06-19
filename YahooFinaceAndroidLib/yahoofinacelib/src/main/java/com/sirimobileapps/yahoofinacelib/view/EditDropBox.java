package com.sirimobileapps.yahoofinacelib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.sirimobileapps.yahoofinacelib.R;

/**
 * Created by smart on 19/6/15.
 */
public class EditDropBox extends ViewGroup {


    private  ListView mListView = null;
    private  EditText mEditText = null;
    Context mContext = null;


    public EditDropBox(Context context) {
        super(context);
        init(context, null);
    }

    public EditDropBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public void init(Context context , AttributeSet attrs)
    {
        mContext = context;
        mListView  = new ListView(context,attrs);
        mEditText  = new EditText(context,attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EditDropBox,
                0, 0);

        try {
            mEditText.setHint(a.getString(R.styleable.EditDropBox_hint));

        } finally {
            a.recycle();
        }




    }




    public void enableListView()
    {
        mListView.setVisibility(VISIBLE);
    }


}
