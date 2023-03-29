package cat.dam.biel.ocaloka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cat.dam.biel.ocaloka.R;

public class ItemAdapterBoards extends BaseAdapter {

    Context context;
    String[] nomTaulers;

    public ItemAdapterBoards(Context context, String[] nomTaulers) {
        this.context = context;
        this.nomTaulers = nomTaulers;
    }

    @Override
    public int getCount() {
        return nomTaulers.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.item_layout_spinner_taulells,parent,false);
        TextView textView = convertView.findViewById(R.id.tv_taulells);

        textView.setText(nomTaulers[position]);
        return convertView;
    }
}
