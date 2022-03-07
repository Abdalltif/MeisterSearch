package com.abdullateif.meistersearch.presentation.search_tasks.binding;

import android.annotation.TargetApi;
import android.os.Build;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

@BindingMethods({
        @BindingMethod(type = SearchView.class, attribute = "android:onQueryTextFocusChange", method = "setOnQueryTextFocusChangeListener"),
        @BindingMethod(type = SearchView.class, attribute = "android:onSearchClick", method = "setOnSearchClickListener"),
        @BindingMethod(type = SearchView.class, attribute = "android:onClose", method = "setOnCloseListener"),
})
public class SearchViewBindingAdapter {
    @BindingAdapter("android:onQueryTextChange")
    public static void setListener(SearchView view, OnQueryTextChange listener) {
        setListener(view, null, listener);
    }

    @BindingAdapter({"app:onQueryTextSubmit", "android:onQueryTextChange"})
    public static void setListener(SearchView view, final OnQueryTextSubmit submit,
                                   final OnQueryTextChange change) {
        if (submit == null && change == null){
            view.setOnQueryTextListener(null);
        } else {
            view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (submit != null) {
                        return submit.onQueryTextSubmit(query);
                    } else {
                        return false;
                    }
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (change != null) {
                        return change.onQueryTextChange(newText);
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public interface OnQueryTextSubmit {
        boolean onQueryTextSubmit(String query);
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public interface OnQueryTextChange {
        boolean onQueryTextChange(String newText);
    }
}

