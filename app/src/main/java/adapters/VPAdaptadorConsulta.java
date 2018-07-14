package adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.liraheta.audit6s.R;

import java.util.List;

public class VPAdaptadorConsulta extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private List<String> mImagenes;

    public VPAdaptadorConsulta(Context mContext, List<String> mImagenes) {
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
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        final ImageView imageViewPager = itemView.findViewById(R.id.imagenViewPager);

        GlideApp
                .with(itemView)
                .load(mImagenes.get(position))
                .centerCrop()
                .into(imageViewPager);

        container.addView(itemView);

        imageViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanzarImagenFull(position);
            }
        });


//        final Bitmap mImage = BitmapFactory.decodeFile(mImagenes.get(position));
//        if(mImage != null){
//            final Bitmap mImageBitmap = Bitmap.createScaledBitmap(mImage, 500, 400, true);
//            imageViewPager.setImageBitmap(mImageBitmap);
//            container.addView(itemView);
//
//            imageViewPager.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    LanzarImagenFull(mImage);
//                }
//            });
//        }
        return itemView;
    }

    private void LanzarImagenFull(int pos) {

        final View alertFullImage = mLayoutInflater.inflate(R.layout.alert_dialog_image_full, null);

        ImageView imageFull = alertFullImage.findViewById(R.id.imageViewFull);
        Button botonSalir = alertFullImage.findViewById(R.id.btnSalirConsulta);

//        Bitmap mImage = BitmapFactory.decodeFile(mImagenes.get(pos));
//        imageFull.setImageBitmap(mImage);

        GlideApp
                .with(alertFullImage)
                .load(mImagenes.get(pos))
                .into(imageFull);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        builder.setView(alertFullImage);
        final AlertDialog alertDialog = builder.create();

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
