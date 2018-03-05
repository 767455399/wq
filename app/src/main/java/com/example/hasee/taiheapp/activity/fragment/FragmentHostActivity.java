package com.example.hasee.taiheapp.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

public class FragmentHostActivity extends AppCompatActivity {
    private static final String FRAGMENT_NAME="fragment_name";
    private static final String FRAGMENT_BUNDLE="fragment_bundle";
    private static final String FRAGMENT_HASHCODE="fragment_hashcode";
    private static SparseArray<Fragment> fragmentMap=new SparseArray<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void openFragment(Context context, Fragment fragment){
        context.startActivity(newIntent(context,fragment));
    }

    public static Intent newIntent(Context context,Fragment fragment){
        Intent intent=new Intent(context,FragmentHostActivity.class);
        intent.putExtra(FRAGMENT_HASHCODE,fragment.hashCode());
        intent.putExtra(FRAGMENT_NAME,fragment.getClass().getName());
        intent.putExtra(FRAGMENT_BUNDLE,fragment.getArguments());
        fragmentMap.put(fragment.hashCode(),fragment);
        return intent;

    }
}
