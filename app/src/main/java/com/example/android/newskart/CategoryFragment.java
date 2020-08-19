package com.example.android.newskart;

import android.icu.util.ValueIterator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class CategoryFragment extends Fragment {


    private static final String TAG = "CategoryFragment";


    private final String GLOBAL="Global";
    private final String SPORTS="Sports";
    private final String BUSINESS="Business";
    private final String HEALTH="Health";
    private final String ENTERTAINMENT="Fun";
    private final String GAMING="Gaming";


    RecyclerView categoryRecyclerView;
    private ArrayList<String> categoryList;
    private CategoryAdapter categoryAdapter;


    public CategoryFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewNewsCategoryFragment = inflater.inflate(R.layout.fragment_category, container, false);

        categoryRecyclerView = (RecyclerView) viewNewsCategoryFragment.findViewById(R.id.recycler_list_category);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categoryList=new ArrayList<String>();
        categoryList.add(GLOBAL);
        categoryList.add(SPORTS);
        categoryList.add(BUSINESS);
        categoryList.add(HEALTH);
        categoryList.add(ENTERTAINMENT);
        categoryList.add(GAMING);

        categoryAdapter = new CategoryAdapter(getContext(), getActivity(), categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);

        return viewNewsCategoryFragment;
    }
}