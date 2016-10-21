package sg.com.bitwave.shoppingsystem;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import sg.com.bitwave.shoppingsystem.Views.PopupCardImageView;
import sg.com.bitwave.shoppingsystem.fragments.ProductCardFragment;
import sg.com.bitwave.shoppingsystem.fragments.ProductListFragment;

public class ProductListingActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_listing);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_view_module_white_48dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_view_list_white_48dp);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProductCardFragment(), "Card");
        adapter.addFragment(new ProductListFragment(), "List");
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();
        private List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            // return null to display only the icon
            return null; //fragmentTitleList.get(position);
        }
    }

    public void displayCardAlert() {

        LayoutInflater mLayoutInflater = this.getLayoutInflater();
        View popCardView = mLayoutInflater.inflate(R.layout.layout_popup_card, null);

        /*RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);*/

        Resources r = getResources();
        float widthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, r.getDisplayMetrics());
        float heightPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 420, r.getDisplayMetrics());

        //Dialog popupDialog = new Dialog(this);
        //popupDialog.setContentView(popCardView, params);
        //popupDialog.show();
        //popupDialog.getWindow().setLayout((int) widthPx, (int) heightPx); //Controlling width and height.
        //popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        AlertDialog.Builder popupDialog = new AlertDialog.Builder(this);
        popupDialog.setView(popCardView);

        // Set up large image and small images
        Bitmap bitmap = ((BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.test_image_5)).getBitmap();
        Bitmap smallBitmaps[] = new Bitmap[3];
        smallBitmaps[0] = ((BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.test_image_6)).getBitmap();
        smallBitmaps[1] = ((BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.test_image_7)).getBitmap();
        smallBitmaps[2] = ((BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.test_image_8)).getBitmap();

        PopupCardImageView mPCIV = (PopupCardImageView) popCardView.findViewById(R.id.pciv_image);
        mPCIV.setLargeImage(bitmap);
        mPCIV.setSmallImages(smallBitmaps);

        //popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //popupDialog.show();

        AlertDialog popupAD = popupDialog.create();
        popupAD.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupAD.show();
        popupAD.getWindow().setLayout((int) widthPx, (int) heightPx); //Controlling width and height.
        popupAD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }
}
