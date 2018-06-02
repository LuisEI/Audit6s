package adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

public class SpinnerAdaptador extends ArrayAdapter<String> {

    public SpinnerAdaptador(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        return count>0 ? count-1 : count ;
    }
}
