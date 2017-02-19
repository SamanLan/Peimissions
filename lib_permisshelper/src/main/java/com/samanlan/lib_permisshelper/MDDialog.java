package com.samanlan.lib_permisshelper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/2/19.
 */

public class MDDialog extends Dialog {

    View view;
    MDClickLIstener listener;

    public MDDialog(Context context) {
        super(context);
        init(context);
    }

    public MDDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context) {
        view = View.inflate(context, R.layout.permission_dialog_layout, null);
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.okClick();
                }
            }
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.cancelClick();
                }
            }
        });
        setContentView(view);
        WindowManager windowManager = ((Activity)context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();// 屏幕
        WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
        layoutParams.width=display.getWidth()-30;
        getWindow().setAttributes(layoutParams);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public void setText(CharSequence charSequence) {
        TextView textView = (TextView) view.findViewById(R.id.content);
        textView.setText(charSequence);
    }

    public void setText(String string) {
        TextView textView = (TextView) view.findViewById(R.id.content);
        textView.setText(string);
    }

    public void setListener(MDClickLIstener listener) {
        this.listener = listener;
    }

    public interface MDClickLIstener{
        void okClick();

        void cancelClick();
    }
}
