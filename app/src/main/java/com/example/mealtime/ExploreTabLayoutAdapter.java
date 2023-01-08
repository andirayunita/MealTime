package com.example.mealtime;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ExploreTabLayoutAdapter extends FragmentStateAdapter {

    public ExploreTabLayoutAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                ExploreInfoFragment exploreInfoFragment = new ExploreInfoFragment();
                return exploreInfoFragment;
            case 1:
                ExplorePostsFragment explorePostsFragment = new ExplorePostsFragment();
                return explorePostsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
