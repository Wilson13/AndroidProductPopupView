package sg.com.bitwave.shoppingsystem.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sg.com.bitwave.shoppingsystem.ProductListingActivity;
import sg.com.bitwave.shoppingsystem.R;

public class ProductCardFragment extends Fragment implements View.OnClickListener {

    public ProductCardFragment() {
        // Empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_product_card, container, false);
        Typeface robotoBlack = Typeface.createFromAsset(getActivity().getAssets(), "roboto-black.ttf");
        Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(), "roboto-light.ttf");
        TextView tvTitle2 = (TextView) rootview.findViewById(R.id.tv_title_2);
        TextView tvPrice2 = (TextView) rootview.findViewById(R.id.tv_price_2);
        tvTitle2.setTypeface(robotoBlack);
        tvPrice2.setTypeface(robotoLight);

        rootview.findViewById(R.id.card_view_2).setOnClickListener(this);

        return rootview;
    }

    @Override
    public void onClick(View view) {
        ((ProductListingActivity)getActivity()).displayCardAlert();
    }
}
