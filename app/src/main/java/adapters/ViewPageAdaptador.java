package adapters;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.liraheta.audit6s.R;

import java.util.List;

public class ViewPageAdaptador extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;

    List<String> mImagenes;

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
        Bitmap mImage = BitmapFactory.decodeFile(mImagenes.get(position));
        Bitmap mImageBitmap = Bitmap.createScaledBitmap(mImage, 500, 400, true);
        imageViewPager.setImageBitmap(mImageBitmap);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
