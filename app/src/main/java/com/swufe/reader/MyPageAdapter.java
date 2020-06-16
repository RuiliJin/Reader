package com.swufe.reader;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter {
    private String[] title = new String[]{"热销榜","红袖风云榜","新书榜","周推荐榜","完本榜","更新榜","打赏榜","点击榜","收藏榜"};
    public MyPageAdapter(FragmentManager manager){
        super(manager);
    }
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new RexiaoFragment();
        }else if(position == 1){
            return new FengyunFragment();
        }else if(position == 2){
            return new XinShuFragment();
        }else if(position == 3){
            return new TuijianFragment();
        }else if(position == 4){
            return new WanbenFragment();
        }else if(position == 5){
            return new GengxinFragment();
        }else if(position == 6){
            return new DashangFragment();
        }else if(position == 7){
            return new DianjiFragment();
        }else{
            return new ShoucangFragment();
        }
    }

    @Override
    public int getCount() {
        return 9;
    }


    @Override
    public CharSequence getPageTitle(int position) {

        return title[position];

    }
}