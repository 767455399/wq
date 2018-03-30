package com.example.hasee.taiheapp.activity.litepal;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hasee.taiheapp.R;
import com.example.hasee.taiheapp.base.BaseActivity;
import com.example.hasee.taiheapp.tools.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ShowLitePalActivity extends BaseActivity {
    public static final int ITEM_HEAD = 0;
    public static final int ITEM_RECODE = 1;
    public static final int ITEM_NO_RECODE = 2;
    public static final int ITEM_FOOTER = 3;
    public static final int FOOTER_VIEW = 4;
    private RecyclerView recyclerView;
    private ShowLitePalAdapter adapter;
    public List<Book>list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_show_lite_pal);
        recyclerView = f(R.id.recyclerView);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(ShowLitePalActivity.this, 2);
//        gridLayoutManager.setSpanSizeLookup(new AutoSpanSizeLookUp());
//        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ShowLitePalAdapter();
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void initData() {
        List<Book>books= DataSupport.findAll(Book.class);
        for(int i=0;i<books.size();i++){
            int id=books.get(i).getBookId();
            double price=books.get(i).getBookPrice();
            String name=books.get(i).getBookName();
        }
        list=books;
    }

    public class ShowLitePalAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(ShowLitePalActivity.this).inflate(R.layout.item_show_lite_pal, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(ShowLitePalActivity.this).load(list.get(position).imageUrl).placeholder(R.mipmap.ic_launcher).error(R.mipmap.error).into(holder.iconImageView);
            holder.idTextView.setText(String.valueOf(list.get(position).bookId));
            holder.nameTextView.setText(list.get(position).bookName);
            holder.priceTextView.setText(String.valueOf(list.get(position).bookPrice));

        }

//        @Override
//        public int getItemViewType(int position) {
//            if(0==position){
//                return ITEM_HEAD;
//            }else if(position==){}
//        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        ImageView iconImageView;
        TextView idTextView;
        TextView nameTextView;
        TextView priceTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            iconImageView=(ImageView)itemView.findViewById(R.id.iconImageView);
            idTextView=(TextView)itemView.findViewById(R.id.idTextView);
            nameTextView=(TextView)itemView.findViewById(R.id.nameTextView);
            priceTextView=(TextView)itemView.findViewById(R.id.priceTextView);
        }
    }

//    class AutoSpanSizeLookUp extends GridLayoutManager.SpanSizeLookup {
//
//        @Override
//        public int getSpanSize(int position) {
//            switch (adapter.getItemViewType(position)) {
//                case ITEM_HEAD:
//                    return 2;
//                case ITEM_RECODE:
//                    return 1;
//                case ITEM_FOOTER:
//                    return 2;
//                case ITEM_NO_RECODE:
//                    return 2;
//                case FOOTER_VIEW:
//                    return 2;
//                default:
//                    return 1;
//            }
//        }
//    }
}
