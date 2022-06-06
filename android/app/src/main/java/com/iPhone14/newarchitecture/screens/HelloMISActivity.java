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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.iPhone14.MainActivity;
import com.iPhone14.R;
import com.iPhone14.newarchitecture.cache.SQLiteOpener;
import com.iPhone14.newarchitecture.cache.SQLiteTypes;

public class HelloMISActivity extends AppCompatActivity {
    private AlertDialog onceDialog = null;
    private AlertDialog twiceDialog = null;
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
                Toast.makeText(HelloMISActivity.this, "Click HTML urlSpan: " + urlSpan.getURL(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HelloMISActivity.this, MyWebViewActivity.class);
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
        View view = View.inflate(this, R.layout.activity_hello_mis, null);
        db = new SQLiteOpener(this).getReadableDatabase();
        if (isHaveShownWelcomeScreen()) {
            // 之前显示过启动页了
            Intent reactNativeIntent = new Intent(HelloMISActivity.this, MainActivity.class);
            startActivity(reactNativeIntent);
        } else {
            view.findViewById(R.id.hello_mis_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onceDialog.show();
                }
            });
            // 第一次授权的弹窗
            View onceView = View.inflate(this, R.layout.alert_secret_once, null);
            TextView textTitle = onceView.findViewById(R.id.dialog_once_title);
            // 标题加粗
            textTitle.setTypeface(null, Typeface.BOLD);
            // 富文本
            TextView messageText = onceView.findViewById(R.id.dialog_once_message);
            messageText.setMovementMethod(LinkMovementMethod.getInstance());
            messageText.setText(
                    getClickableHtml("欢迎您使用采之汲。我们非常重视用户的隐私和个人信息保护。在您使用我们的服务时，我们将需要收集和使用您的个人信息。点击“同意”表示您同意和接受" +
                            "<a href=\"https://resource.caizhiji.com.cn/protocol/user-protocol.html\">《采之汲用户协议》</a>" +
                            "和" +
                            "<a href=\"https://resource.caizhiji.com.cn/protocol/privacy-protocol.html\">《采之汲隐私政策》</a>" +
                            "。" +
                            "我们提醒您审慎阅读其中涉及您的责任和权利的黑体条款。")
            );
            AlertDialog.Builder onceBuilder = new AlertDialog.Builder(this)
                    .setPositiveButton("同意", new MyDialogClicker("onceConfirm"))
                    .setNegativeButton("不同意", new MyDialogClicker("onceCancel"))
                    .setView(onceView);
            onceDialog = onceBuilder.create();
            // 第二次授权的弹窗
            /**
             AlertDialog.Builder twiceBuilder = new AlertDialog.Builder(this)
             .setPositiveButton("我再看看", new MyDialogClicker("twiceConfirm"))
             .setNegativeButton("狠心离开", new MyDialogClicker("twiceCancel"))
             .setView(View.inflate(this, R.layout.alert_secret_twice, null));
             twiceDialog = twiceBuilder.create();
             */
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
                case "onceConfirm":
                    db.execSQL("update nativeCaches set value = ? where id = ?", new Object[]{"1", SQLiteTypes.IS_SHOWN_WELCOME_SCREEN});
                    Intent reactNativeIntent = new Intent(HelloMISActivity.this, MainActivity.class);
                    startActivity(reactNativeIntent);
                    break;
                case "onceCancel":
                    twiceDialog.show();
                    break;
                case "twiceConfirm":
                    onceDialog.show();
                    break;
                case "twiceCancel":
                    Process.killProcess(Process.myPid());
                    break;
                default:
                    break;
            }
        }
    }
}