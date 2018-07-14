package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.liraheta.audit6s.R;


import java.util.List;

public class ViewPageAdaptador extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private List<String> mImagenes;

    public ViewPageAdaptador(Context mContext, List<String> mImagenes) {
        this.mContext = mContext;
        this.mImagenes = mImagenes;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mImagenes.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        ImageView imageViewPager = itemView.findViewById(R.id.imagenViewPager);

        GlideApp
                .with(itemView)
                .load(mImagenes.get(position))
                .centerCrop()
                .into(imageViewPager);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
