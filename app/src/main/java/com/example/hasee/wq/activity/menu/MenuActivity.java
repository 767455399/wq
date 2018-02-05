package com.example.hasee.wq.activity.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hasee.wq.R;

public class MenuActivity extends AppCompatActivity {
    private TextView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menu = (TextView) findViewById(R.id.menu);
    }

    /**
     * 根据showAsAction来控制menu的显示形式
     * always：菜单项永远不会被收纳到溢出菜单中，因此在菜单项过多的情况下可能超出菜单栏的显示范围。
     * ifRoom：在空间足够时，菜单项会显示在菜单栏中，否则收纳入溢出菜单中。
     * withText：无论菜单项是否定义了icon属性，都只会显示它的标题，而不会显示图标。使用这种方式的菜单项默认会被收纳入溢出菜单中。
     * never：菜单项永远只会出现在溢出菜单中。
     *
     * @param menu
     * @return
     */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_one, menu);
        /**
         * return true表示显示菜单，返回false表示隐藏菜单
         */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                menu.setText("add");
//                Toast.makeText(MenuActivity.this,"添加",Toast.LENGTH_SHORT).show();
                break;
            case R.id.two:
                menu.setText("two");
//                Toast.makeText(MenuActivity.this,"删除",Toast.LENGTH_SHORT).show();
                break;
            case R.id.oneitem:
                menu.setText("oneitem");
                break;
            case R.id.twoitem:
                menu.setText("twoitem");
                break;
        }
        return true;
    }

    public static void actionStart(Context context,String data,String data2){
        Intent intent=new Intent(context,MenuActivity.class);
        intent.putExtra("data1",data);
        intent.putExtra("data2",data2);
        context.startActivity(intent);

    }
}
