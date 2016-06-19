package com.abs104a.transitionstestapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.Explode;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TestFragment extends Fragment {

    private final static String SHARED_VIEW_NAME = "SharedViewName";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final int layout;

        Bundle bundle = getArguments();
        String sharedViewName;
        try{
            sharedViewName = bundle.getString(SHARED_VIEW_NAME);
        }catch (NullPointerException e){
            sharedViewName = null;
        }
        if(sharedViewName != null)
            Log.v("sharedViewName",sharedViewName);
        else
            Log.v("sharedViewName","null");
        if(sharedViewName == null){
            layout = R.layout.fragment_test_item1;
        }else if(sharedViewName.equals(getContext().getString(R.string.item1))){
            layout = R.layout.fragment_test_item1;
        }else if(sharedViewName.equals(getContext().getString(R.string.item2))){
            layout = R.layout.fragment_test_item2;
        }else if(sharedViewName.equals(getContext().getString(R.string.item3))){
            layout = R.layout.fragment_test_item3;
        }else if(sharedViewName.equals(getContext().getString(R.string.item4))){
            layout = R.layout.fragment_test_item4;
        }else{
            layout = R.layout.fragment_test_item1;
        }

        final View rootView = inflater.inflate(layout,null);
        initViews(rootView);

        return rootView;
    }

    void initViews(View rootView){

        final View equalView = rootView.findViewById(R.id.item_equal);
        final View averageView= rootView.findViewById(R.id.item_average);
        final View shopView= rootView.findViewById(R.id.item_shop_num);
        final View cheapShopView= rootView.findViewById(R.id.item_cheap_shop);
        final View hintView = rootView.findViewById(R.id.text_hint);

        equalView.setTransitionName(equalView.getContext().getString(R.string.item1));
        averageView.setTransitionName(averageView.getContext().getString(R.string.item2));
        shopView.setTransitionName(shopView.getContext().getString(R.string.item3));
        cheapShopView.setTransitionName(cheapShopView.getContext().getString(R.string.item4));
        hintView.setTransitionName(hintView.getContext().getString(R.string.hint));

        final View.OnClickListener listener = (view) -> {
            //クリックされた時のリスナ
            final TransitionSet set = new TransitionSet();
            set.addTransition(new Explode());

            final Fragment fragment = new TestFragment();
            //データのセット
            Bundle args = new Bundle();
            args.putString(SHARED_VIEW_NAME,view.getTransitionName());
            //TODO データを入れる
            fragment.setArguments(args);
            fragment.setEnterTransition(set);
            fragment.setSharedElementEnterTransition(TransitionInflater.from(getActivity()).inflateTransition(R.transition.trans_move));
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.container,fragment )
                    .addSharedElement(equalView,        equalView.getTransitionName())
                    .addSharedElement(averageView,      averageView.getTransitionName())
                    .addSharedElement(shopView,         shopView.getTransitionName())
                    .addSharedElement(cheapShopView,    cheapShopView.getTransitionName())
                    .addSharedElement(hintView,         hintView.getTransitionName())
                    .addToBackStack(null)
                    .commit();

        };

        //リスナのセット
        if(equalView instanceof TextView)       equalView.setOnClickListener(listener);
        if(averageView instanceof TextView)     averageView.setOnClickListener(listener);
        if(shopView instanceof TextView)        shopView.setOnClickListener(listener);
        if(cheapShopView instanceof TextView)   cheapShopView.setOnClickListener(listener);

    }

}
