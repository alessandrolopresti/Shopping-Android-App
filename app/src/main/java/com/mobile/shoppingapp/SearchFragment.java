package com.mobile.shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Map;

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment" ;
    private RecyclerView mSearchRecyclerView;
    private SearchAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mSearchRecyclerView = (RecyclerView) view
                .findViewById(R.id.search_recycler_view);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        SearchLab searchLab = SearchLab.get(getActivity());
        Map<String, String> searches = searchLab.getSearches();


        mAdapter = new SearchAdapter(searches);
        mSearchRecyclerView.setAdapter(mAdapter);
    }

    private class SearchHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private String mTitle;
        private String mDescription;

        private TextView mTitleTextView;
        private TextView mDescriptionTextView;

        public SearchHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_search, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.search_title);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.search_description);
        }

        public void bind(String title, String description) {
            mTitle = title;
            mDescription = description;
            Log.d(TAG, "Title vale: " + title);
            Log.d(TAG, "Description vale: " + description);
            mTitleTextView.setText(mTitle);
            mDescriptionTextView.setText(mDescription);
        }

        @Override
        public void onClick(View view) {

            Toast.makeText(getActivity(),
                    mTitle + " clicked!", Toast.LENGTH_SHORT)
                    .show();
            if (mTitle.equals("Recognize what you want"))   {
                startActivity(new Intent(getActivity(), CloudVision.class));

            }
            if (mTitle.equals("Traditional search"))    {
                TraditionalSearchFragment newFragment = new TraditionalSearchFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(((ViewGroup)getView().getParent()).getId() , newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        }
    }

    private class SearchAdapter extends RecyclerView.Adapter<SearchHolder> {

        private Map<String, String> mSearches;

        public SearchAdapter(Map<String, String> searches) {
            mSearches = searches;
        }

        @Override
        public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SearchHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(SearchHolder holder, int position) {
            String[] item  = mSearches.entrySet().toArray()[position].toString().split("=");
            String title = item[0];
            String description = item[1];
            holder.bind(title, description);
        }

        @Override
        public int getItemCount() {
            return mSearches.size();
        }
    }
}
