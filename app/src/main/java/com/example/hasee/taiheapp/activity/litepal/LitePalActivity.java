package com.example.hasee.taiheapp.activity.litepal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.base.BaseActivity;
import com.example.hasee.taiheapp.tools.ToastUtil;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class LitePalActivity extends BaseActivity {
    private Button creatDataButton, addDataButton, delteDataButton, updateDataButton, viewButton;
    private EditText idEditText, nameEditText, priceEditText, linkEditText;

    String id;
    String name;
    String price;
    String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_lite_pal);
        creatDataButton = f(R.id.creatDataButton);
        addDataButton = f(R.id.addDataButton);
        delteDataButton = f(R.id.delteDataButton);
        updateDataButton = f(R.id.updateDataButton);
        idEditText = f(R.id.idEditText);
        nameEditText = f(R.id.nameEditText);
        priceEditText = f(R.id.priceEditText);
        linkEditText = f(R.id.linkEditText);
        viewButton = f(R.id.viewButton);
        creatDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建数据库
                LitePal.getDatabase();
            }
        });

        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        delteDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //条件限制
                // DataSupport.deleteAll("Book", "id<?", "3");
                DataSupport.deleteAll(Book.class);
            }
        });

        updateDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LitePalActivity.this, ShowLitePalActivity.class));
//                lookData();
            }
        });


    }

    private void lookData() {
        List<Book> books = DataSupport.findAll(Book.class);
        for (int i = 0; i < books.size(); i++) {
            int id = books.get(i).getBookId();
            double price = books.get(i).getBookPrice();
            String name = books.get(i).getBookName();
        }
    }

    private void updateDate() {
        getEditTextDate();
        ShopModel shopModel = new ShopModel();
        ShopModel.GoodsListBean list = new ShopModel.GoodsListBean();
        if (!TextUtils.isEmpty(id)) {
            list.id = Integer.parseInt(id);
        }
        if (!TextUtils.isEmpty(name)) {
            list.name = name;
        }
        if (!TextUtils.isEmpty(price)) {
            list.price = Double.parseDouble(price);
        }
        if (!TextUtils.isEmpty(link)) {
            list.link = link;
        }
        if (TextUtils.isEmpty(id) && TextUtils.isEmpty(name) && TextUtils.isEmpty(price) && TextUtils.isEmpty(link)) {
            return;
        } else {
            shopModel.updateAll("id=?", "1");
        }

    }

    private void addData1() {
        for (int i = 0; i < 10; i++) {
            Book book = new Book();
            book.setBookId(i);
            book.setBookName("苹果" + i);
            book.setBookPrice(11.11);
            book.setImageUrl("http://img.my.csdn.net/uploads/201309/01/1378037128_5291.jpg");
            book.save();
        }
    }

    private void addData() {
        getEditTextDate();
        if (TextUtils.isEmpty(id)) {
            ToastUtil.showNormalToast("请输入商品id");
        } else if (TextUtils.isEmpty(name)) {
            ToastUtil.showNormalToast("请输入商品名称");
        } else if (TextUtils.isEmpty(price)) {
            ToastUtil.showNormalToast("请输入商品价格");
        } else {
            Book book = new Book();
            book.setBookId(Integer.parseInt(id));
            book.setBookName(name);
            book.setBookPrice(Double.parseDouble(price));
            if (link.contains(".jpg")) {
                book.setImageUrl(link);
            } else {
                book.setImageUrl("http://img.my.csdn.net/uploads/201309/01/1378037128_5291.jpg");
            }
            book.save();
        }

    }

    @Override
    public void initData() {

    }

    private void getEditTextDate() {
        id = idEditText.getText().toString();
        name = nameEditText.getText().toString();
        price = priceEditText.getText().toString();
        link = linkEditText.getText().toString();
    }
}
