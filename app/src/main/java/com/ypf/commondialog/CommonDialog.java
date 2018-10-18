package com.ypf.commondialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


/*
* @作者 ypf
* @创建日期 2018/7/25
* @描述 通用dialog
*/
public class CommonDialog implements View.OnClickListener {

    private TextView title, content;
    private Button sure, cancel;

    private View space;

    private OnConfirmClickListener onConfirmClickListener;
    private OnCancelClickListener onCancelClickListener;
    private Dialog commonDialog;
    
    public CommonDialog(Context context){
        this(context,true);
    }

    public CommonDialog(Context context, boolean isCancelable) {
        commonDialog = new Dialog(context);
        commonDialog.setCancelable(isCancelable);
        //规避四角问题!
        Window commonDialogWindow = commonDialog.getWindow();
        if (commonDialogWindow!=null){
            commonDialogWindow.setBackgroundDrawableResource(R.color.transparent);
        }
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);
        //宽高由此处控制,布局内最外层宽高设置无效
        commonDialog.addContentView(dialogView,new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        initView(dialogView);
        initEvent();
    }
    
    public CommonDialog setTitle(String title){
        this.title.setText(title);
        return this;
    }
    
    public CommonDialog setContent(String content){
        this.content.setText(content);
        return this;
    }

    private void initEvent() {
        sure.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void initView(View dialogWindow) {
        title = (TextView) dialogWindow.findViewById(R.id.tv_commonDialog_title);
        content = (TextView) dialogWindow.findViewById(R.id.tv_commonDialog_content);
        sure = (Button) dialogWindow.findViewById(R.id.bt_commonDialog_sure);
        cancel = (Button) dialogWindow.findViewById(R.id.bt_commonDialog_cancel);
        space = dialogWindow.findViewById(R.id.v_commonDialog_space);
    }

    public void dismiss() {
        if (commonDialog != null) {
            commonDialog.dismiss();
            commonDialog.cancel();
            commonDialog = null;
        }
    }

    public boolean isShowing() {
        return commonDialog != null && commonDialog.isShowing();
    }

    public void show() {
        if (commonDialog != null && !commonDialog.isShowing()) {
            commonDialog.show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_commonDialog_sure) {
            dismiss();
            if (onConfirmClickListener!=null){
                onConfirmClickListener.onConfirmClick();
            }
        } else if (id == R.id.bt_commonDialog_cancel) {
            dismiss();
            if (onCancelClickListener!=null){
                onCancelClickListener.onCancelClick();
            }
        }
    }

    public CommonDialog setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
        return this;
    }

    public CommonDialog setOnCancelClickListener(OnCancelClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
        return this;
    }

    public interface OnConfirmClickListener {
        public void onConfirmClick();
    }

    public interface OnCancelClickListener {
        public void onCancelClick();
    }

    public CommonDialog setCancelButtonVisibility(int visibility){
        if (cancel!=null){
            cancel.setVisibility(visibility);
        }
        if (space!=null){
            space.setVisibility(visibility);
        }
        return this;
    }
}
