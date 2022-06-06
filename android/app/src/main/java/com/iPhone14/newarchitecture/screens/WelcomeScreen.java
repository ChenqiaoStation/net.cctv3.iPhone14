package com.iPhone14.newarchitecture.screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.iPhone14.MainActivity;
import com.iPhone14.R;
import com.iPhone14.newarchitecture.cache.SQLiteOpener;
import com.iPhone14.newarchitecture.cache.SQLiteTypes;

public class WelcomeScreen extends AppCompatActivity {
    private AlertDialog welcomeDialog = null;
    private SQLiteDatabase db = null;

    /**
     * 是否显示过隐私政策
     */
    private boolean isHaveShownWelcomeScreen() {
        Cursor cursor = db.rawQuery("select * from nativeCaches where id = ?", new String[]{SQLiteTypes.IS_SHOWN_WELCOME_SCREEN});
        String result = "";
        if (cursor == null || cursor.getCount() == 0) {
            // 没数据
            db.execSQL("insert into nativeCaches values(?, ?)", new Object[]{SQLiteTypes.IS_SHOWN_WELCOME_SCREEN, "0"});
        } else {
            while (cursor.moveToNext()) {
                result = cursor.getString(cursor.getColumnIndex("value"));
            }
        }
        return result.equals("1");
    }

    private void setLinkClickable(final SpannableStringBuilder clickableHtmlBuilder,
                                  final URLSpan urlSpan) {
        int start = clickableHtmlBuilder.getSpanStart(urlSpan);
        int end = clickableHtmlBuilder.getSpanEnd(urlSpan);
        int flags = clickableHtmlBuilder.getSpanFlags(urlSpan);
        ClickableSpan clickableSpan = new ClickableSpan() {
            public void onClick(View view) {
                // Do something with URL here.
                // System.out.println("Click HTML urlSpan: " + urlSpan.getURL());
                // Toast.makeText(HelloMISActivity.this, "Click HTML urlSpan: " + urlSpan.getURL(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WelcomeScreen.this, MyWebViewScreen.class);
                intent.putExtra("url", urlSpan.getURL());
                startActivity(intent);
            }
        };
        clickableHtmlBuilder.setSpan(clickableSpan, start, end, flags);
    }

    private CharSequence getClickableHtml(String html) {
        Spanned spannedHtml = Html.fromHtml(html);
        SpannableStringBuilder clickableHtmlBuilder = new SpannableStringBuilder(spannedHtml);
        URLSpan[] urls = clickableHtmlBuilder.getSpans(0, spannedHtml.length(), URLSpan.class);
        for (final URLSpan span : urls) {
            setLinkClickable(clickableHtmlBuilder, span);
        }
        return clickableHtmlBuilder;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.screen_welcome, null);
        db = new SQLiteOpener(this).getReadableDatabase();
        if (isHaveShownWelcomeScreen()) {
            // 之前显示过启动页了
            Intent it = new Intent(WelcomeScreen.this, MainActivity.class);
            //  禁止 ReactNative 页面回退到此页面
            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(it);
            finish();
        } else {
            // 第一次授权的弹窗
            View welcomeView = View.inflate(this, R.layout.alert_welcome, null);
            TextView textTitle = welcomeView.findViewById(R.id.dialog_once_title);
            // 标题加粗
            textTitle.setTypeface(null, Typeface.BOLD);
            // 富文本
            TextView messageText = welcomeView.findViewById(R.id.dialog_once_message);
            messageText.setMovementMethod(LinkMovementMethod.getInstance());
            messageText.setText(
                    getClickableHtml("欢迎您使用采之汲。我们非常重视用户的隐私和个人信息保护。在您使用我们的服务时，我们将需要收集和使用您的个人信息。点击“同意”表示您同意和接受" +
                            "<a href=\"https://resource.caizhiji.com.cn/protocol/user-protocol.html\">《采之汲用户协议》</a>" +
                            "和" +
                            "<a href=\"https://resource.caizhiji.com.cn/protocol/privacy-protocol.html\">《采之汲隐私政策》</a>" +
                            "。" +
                            "我们提醒您审慎阅读其中涉及您的责任和权利的黑体条款。")
            );
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this)
                    .setPositiveButton("同意", new MyDialogClicker(ScreenTypes.WELCOME_DIALOG_YES))
                    .setNegativeButton("不同意", new MyDialogClicker(ScreenTypes.WELCOME_DIALOG_NO))
                    .setView(welcomeView)
                    .setCancelable(false);
            welcomeDialog = alertBuilder.create();
            welcomeDialog.show();
        }
        setContentView(view);
    }

    class MyDialogClicker implements DialogInterface.OnClickListener {
        private String id;

        public MyDialogClicker(String id) {
            this.id = id;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (this.id) {
                case ScreenTypes.WELCOME_DIALOG_YES:
                    db.execSQL("update nativeCaches set value = ? where id = ?", new Object[]{"1", SQLiteTypes.IS_SHOWN_WELCOME_SCREEN});
                    Intent it = new Intent();
                    it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    it.setClass(WelcomeScreen.this, MainActivity.class);
                    startActivity(it);
                    finish();
                    break;
                case ScreenTypes.WELCOME_DIALOG_NO:
                    Process.killProcess(Process.myPid());
                    break;
                default:
                    break;
            }
        }
    }
}
